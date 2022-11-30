package net.serrago.ntc;

import net.serrago.ntc.configuration.ConfigurationParser;
import net.serrago.ntc.filter.FilterAlgorithm;
import net.serrago.ntc.filter.FilterSelector;
import net.serrago.ntc.filter.PrefixFilter;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;
import static picocli.CommandLine.Model.CommandSpec;

@Command(
        name = "oidFilter",
        footer = "Copyright(c) Valentin Serrago, 2022",
        description = "Filter trap type OIDs against pre-specified prefixes"
)
public class OidFilter implements Callable<Integer> {

    //Variables injected via Picocli
    @Spec
    CommandSpec spec;

    @Option(
            names = {"-c", "--config-path"},
            description = """
                    Path to config file.
                    Defaults to "snmp.yml", or "snmp.yaml", in that order."""
    )
    String configPath;

    @Option(
            names = {"-f", "--filter-algorithm"},
            description = """
                    Which algorithm to use.
                    One of: ${COMPLETION-CANDIDATES}""",
            completionCandidates = FilterAlgorithm.AlgorithmLabels.class,
            defaultValue = "trie"
    )
    FilterAlgorithm filterAlgorithm;

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help and exit"
    )
    boolean help;

    // Variables assigned at instantiation
    private final ConfigurationParser configurationParser;
    private final FilterSelector filterSelector;
    private final InputStream in;

    public OidFilter() {
        this(new ConfigurationParser(), new FilterSelector(), System.in);
    }

    public OidFilter(
            ConfigurationParser configurationParser,
            FilterSelector filterSelector,
            InputStream in
    ) {
        this.configurationParser = configurationParser;
        this.filterSelector = filterSelector;
        this.in = in;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new OidFilter())
                .registerConverter(FilterAlgorithm.class, FilterAlgorithm::fromLabel)
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        var configuration = configurationParser.parse(configPath);
        PrefixFilter filter = filterSelector.select(filterAlgorithm);
        filter.initialize(configuration.prefixes());

        BufferedReader stdin = new BufferedReader(new InputStreamReader(in));

        stdin.lines()
                .map(oid -> {
                    boolean match = filter.matches(oid);
                    return String.format("%s: %b", oid, match);
                })
                .forEach(spec.commandLine().getOut()::println);
        return 0;
    }
}