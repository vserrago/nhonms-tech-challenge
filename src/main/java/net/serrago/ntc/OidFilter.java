package net.serrago.ntc;

import net.serrago.ntc.configuration.ConfigurationParser;
import net.serrago.ntc.filter.FilterAlgorithm;
import net.serrago.ntc.filter.FilterSelector;
import net.serrago.ntc.filter.PrefixFilter;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(
        name = "oidFilter",
        footer = "Copyright(c) Valentin Serrago, 2022",
        description = "Filter trap type OIDs against pre-specified prefixes"
)
public class OidFilter implements Callable<Integer> {

    @Option(
            names = {"-c", "--config-path"},
            description = "Path to config file"
    )
    String configPath;

    @Option(
            names = {"-f", "--filter-algorithm"},
            description = "Which filter algorithm to use",
            defaultValue = "trie"
    )
    FilterAlgorithm filterAlgorithm;

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help and exit"
    )
    boolean help;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new OidFilter())
                .registerConverter(FilterAlgorithm.class, FilterAlgorithm::fromLabel)
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        var configuration = new ConfigurationParser().parse(configPath);
        PrefixFilter filter = new FilterSelector().select(filterAlgorithm);
        filter.initialize(configuration.prefixes());

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        stdin.lines()
                .map(oid -> {
                    boolean match = filter.matches(oid);
                    return String.format("%s: %b", oid, match);
                })
                .forEach(System.out::println);
        return 0;
    }
}