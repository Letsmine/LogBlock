package de.diddiz.LogBlock.config.key;

import de.diddiz.LogBlock.config.adapter.ConfigurationAdapter;

/**
 * Represents a key in the configuration.
 * @param <T> the value type
 */
public interface ConfigKey<T> {

    /**
     * Resolves and returns the value mapped to this key using the given config instance.
     * @param adapter the config adapter instance
     * @return the value mapped to this key
     */
	T get(ConfigurationAdapter adapter);
}
