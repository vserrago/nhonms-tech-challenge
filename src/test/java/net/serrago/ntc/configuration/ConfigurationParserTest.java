package net.serrago.ntc.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigurationParserTest {

    private static final String DEFAULT_CONFIG_PATH_0 = ConfigurationParser.DEFAULT_CONFIG_FILE_NAMES.get(0);

    private static final String YAML = """
            trap-type-oid-prefix:
              - .1.3
            """;
    private static final Configuration PARSED_YAML = new Configuration(List.of(".1.3"));

    FileSystem fileSystem;

    ConfigurationParser configurationParser;

    @BeforeEach
    void setUp() {
        fileSystem = Jimfs.newFileSystem(
                com.google.common.jimfs.Configuration.unix()
        );
        // Can the YAMLMapper be mocked out? Yes. Should it? I think in this specific circumstance,
        // given the current scope of the class and its tests, using a mock would add unnecessary
        // verbosity to each test method. If additions to the class were to significantly change how
        // the mapper is used, perhaps it would make sense at that time to refactor some or all of
        // the tests to use a mock.
        configurationParser = new ConfigurationParser(fileSystem, new YAMLMapper());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "   ")
    void givenEmptyPath_expectToParseDefaultFileNames(String path) {
        createFile(DEFAULT_CONFIG_PATH_0, YAML);

        assertThat(configurationParser.parse(path))
                .isEqualTo(PARSED_YAML);
    }

    @Test
    void givenNonEmptyPath_andFileExists_expectToParseFile() {
        createFile("path/to/file.yml", YAML);

        assertThat(configurationParser.parse("path/to/file.yml"))
                .isEqualTo(PARSED_YAML);
    }

    @Test
    void givenNonEmptyPath_andFileDoesNotExist_expectDefaultConfig() {
        assertThat(configurationParser.parse("non/existent/file.yml"))
                .isEqualTo(Configuration.defaultConfiguration());
    }

    @Test
    void givenNonEmptyPath_andFileContainsGarbage_expectRuntimeException() {
        createFile("path/to/file.yml", "totally not a configuration yaml file.");

        assertThatThrownBy(() -> configurationParser.parse("path/to/file.yml"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void givenListOfPaths_andSomePathsDoNotExist_expectFirstExistingPathParsed() {
        var paths = List.of("my/path/1.yml", "my/path/2.yml");
        createFile("my/path/2.yml", YAML);

        assertThat(configurationParser.parse(paths))
                .isEqualTo(PARSED_YAML);
    }

    private void createFile(String pathToFile, String contents) {
        var path = fileSystem.getPath(pathToFile);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, contents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}