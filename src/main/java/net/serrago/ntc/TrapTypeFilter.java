package net.serrago.ntc;

import net.serrago.ntc.configuration.ConfigurationParser;
import net.serrago.ntc.filter.FilterAlgorithm;
import net.serrago.ntc.filter.FilterSelector;
import net.serrago.ntc.filter.PrefixFilter;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Option;

public class TrapTypeFilter implements Callable<Integer> {

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

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TrapTypeFilter())
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