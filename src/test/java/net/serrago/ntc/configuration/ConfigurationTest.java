package net.serrago.ntc.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConfigurationTest {
    @Test
    void defaultConfiguration_expectNonNullDefaults() {
        assertThat(Configuration.defaultConfiguration())
                .satisfies(configuration -> assertThat(configuration.prefixes()).isNotNull());
    }

    @Nested
    class YamlParsing {
        @Test
        void givenYaml_expectFieldsToBeParsedCorrectly() throws JsonProcessingException {
            String yaml = """
                    trap-type-oid-prefix:
                      - .1.3.6.1.6.3.1.1.5
                      - .1.3.6.1.2.1.10.166.3
                      - .1.3.6.1.4.1.9.9.117.2
                      - .1.3.6.1.2.1.10.32.0.1
                      - .1.3.6.1.2.1.14.16.2.2
                      - .1.3.6.1.4.1.9.10.137.0.1
                    """;
            YAMLMapper mapper = new YAMLMapper();

            assertThat(mapper.readValue(yaml, Configuration.class))
                    .isEqualTo(new Configuration(List.of(
                            ".1.3.6.1.6.3.1.1.5",
                            ".1.3.6.1.2.1.10.166.3",
                            ".1.3.6.1.4.1.9.9.117.2",
                            ".1.3.6.1.2.1.10.32.0.1",
                            ".1.3.6.1.2.1.14.16.2.2",
                            ".1.3.6.1.4.1.9.10.137.0.1"
                    )));
        }
    }
}