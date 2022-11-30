package net.serrago.ntc.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    /**
     * Attempts to parse the configuration file found at the given path. If the provided path is null or empty, then
     * fall back to the default config file names. If the provided path is specified but does not exist, then return the
     * default configuration.
     *
     * @param configFilePath A string value.
     * @return A non-null {@link Configuration}.
     */
    public Configuration parse(String configFilePath) {
        return configFilePath != null && !configFilePath.isBlank()
                ? parse(List.of(configFilePath))
                : parse(DEFAULT_CONFIG_FILE_NAMES);
    }

    /**
     * Attempts to parse the configuration file found from one of the paths in the given list, in list order. Paths will
     * attempt to be read from until a file is parsed, in which case the resulting configuration is returned.
     *
     * @param configFilePaths A list of paths to parse configuration from.
     * @return A non-null {@link Configuration}.
     */
    public Configuration parse(List<String> configFilePaths) {
        if (configFilePaths == null) {
            configFilePaths = Collections.emptyList();
        }

        return configFilePaths.stream()
                .map(this::attemptToParse)
                .flatMap(Optional::stream)
                .findFirst()
                .orElse(Configuration.defaultConfiguration());
    }

    private Optional<Configuration> attemptToParse(String configFilePath) {
        if (configFilePath == null || configFilePath.isBlank()) {
            return Optional.empty();
        }

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
