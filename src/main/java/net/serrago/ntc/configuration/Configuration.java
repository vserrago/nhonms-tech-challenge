package net.serrago.ntc.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * The configuration used for filtering trap type OIDs.
 *
 * @param prefixes A list of trap type OID prefixes to filter against.
 */
public record Configuration(
        @JsonProperty("trap-type-oid-prefix")
        List<String> prefixes
) {
    /**
     * Creates a default configuration if one was not provided.
     *
     * @return A non-null {@link Configuration} instance.
     */
    public static Configuration defaultConfiguration() {
        return new Configuration(Collections.emptyList());
    }
}
