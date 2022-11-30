package net.serrago.ntc.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

// TODO: tests, javadoc, cleanup interface
public class ConfigurationParser {

    static final List<String> DEFAULT_CONFIG_FILE_NAMES = List.of("snmp.yml", "snmp.yaml");

    private final FileSystem fileSystem;
    private final YAMLMapper mapper;

    public ConfigurationParser() {
        this(FileSystems.getDefault(), new YAMLMapper());
    }

    public ConfigurationParser(FileSystem fileSystem, YAMLMapper mapper) {
        this.fileSystem = fileSystem;
        this.mapper = mapper;
    }

    public Configuration parse(String configFilePath) {
        return configFilePath != null && !configFilePath.isBlank()
                ? parse(List.of(configFilePath))
                : parse(DEFAULT_CONFIG_FILE_NAMES);
    }

    public Configuration parse(List<String> configFilePaths) {
        return configFilePaths.stream()
                .map(this::attemptToParse)
                .flatMap(Optional::stream)
                .findFirst()
                .orElse(Configuration.defaultConfiguration());
    }

    private Optional<Configuration> attemptToParse(String configFilePath) {
        Path path = fileSystem.getPath(configFilePath);

        if (!Files.isRegularFile(path)) {
            return Optional.empty();
        }

        Configuration configuration;
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            configuration = mapper.readValue(content, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(configuration);
    }
}
