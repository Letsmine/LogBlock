package de.diddiz.LogBlock.config;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.diddiz.LogBlock.config.adapter.ConfigurationAdapter;
import de.diddiz.LogBlock.config.key.ConfigKey;
import de.diddiz.LogBlock.config.key.SimpleConfigKey;

public class KeyedConfiguration {
    private final ConfigurationAdapter adapter;
    private final List<? extends ConfigKey<?>> keys;
    private final Map<ConfigKey<?>, Object> values = new HashMap<>();

    public KeyedConfiguration(ConfigurationAdapter adapter, List<? extends ConfigKey<?>> keys) {
        this.adapter = adapter;
        this.keys = keys;
    }

    protected void init() {
        for (ConfigKey<?> key : this.keys) {
            this.values.put(key, key.get(this.adapter));
        }
    }

    /**
     * Gets the value of a given context key.
     *
     * @param key the key
     * @param <T> the key return type
     * @return the value mapped to the given key. May be null.
     */
    @SuppressWarnings("unchecked")
	public <T> T get(ConfigKey<T> key) {
        return (T)this.values.get(key);
    }

    /**
     * Initialises the given pseudo-enum keys class.
     *
     * @param keysClass the keys class
     * @return the list of keys defined by the class with their ordinal values set
     */
    public static List<SimpleConfigKey<?>> initialise(Class<?> keysClass) {
        // get a list of all keys
        List<SimpleConfigKey<?>> keys = Arrays.stream(keysClass.getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> ConfigKey.class.equals(f.getType()))
                .map(f -> {
                    try {
                        return (SimpleConfigKey<?>) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toUnmodifiableList());

        return keys;
    }
}