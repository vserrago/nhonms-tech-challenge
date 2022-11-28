package net.serrago.ntc;

import net.serrago.ntc.configuration.ConfigurationParser;
import picocli.CommandLine;

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
            description = "Which filter algorithm to use"

    )
    String filterAlgorithm;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TrapTypeFilter()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        var configuration = new ConfigurationParser().parse(configPath);
        System.out.println(configuration.prefixes());
        return 0;
    }
}