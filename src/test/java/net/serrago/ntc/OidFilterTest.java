package net.serrago.ntc;

import net.serrago.ntc.filter.FilterAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class OidFilterTest {

    @Nested
    class CommandLineTest {

        CommandLine commandLine;

        StringWriter out;

        @BeforeEach
        void setUp() {
            out = new StringWriter();

            commandLine = new picocli.CommandLine(new OidFilter())
                    .registerConverter(FilterAlgorithm.class, FilterAlgorithm::fromLabel)
                    .setOut(new PrintWriter(out));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "-h",
                "--help",
                "-f no-op --help"
        })
        void givenHelpOptions_expectUsageHelpText(String argsString) {
            String[] args = argsString.split(" ");

            int exitCode = commandLine.execute(args);

            assertThat(exitCode).isEqualTo(0);
            assertThat(out.toString()).isEqualTo("""
                    Usage: oidFilter [-h] [-c=<configPath>] [-f=<filterAlgorithm>]
                    Filter trap type OIDs against pre-specified prefixes
                      -c, --config-path=<configPath>
                                   Path to config file.
                                   Defaults to "snmp.yml", or "snmp.yaml", in that order.
                      -f, --filter-algorithm=<filterAlgorithm>
                                   Which algorithm to use.
                                   One of: no-op, simple, trie
                      -h, --help   Display this help and exit
                    Copyright(c) Valentin Serrago, 2022
                    """);
        }
    }
}