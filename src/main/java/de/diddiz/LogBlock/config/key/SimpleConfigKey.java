package de.diddiz.LogBlock.config.key;

import java.util.function.Function;

import de.diddiz.LogBlock.config.adapter.ConfigurationAdapter;

/**
 * Basic {@link ConfigKey} implementation.
 * @param <T> the value type
 */
public class SimpleConfigKey<T> implements ConfigKey<T> {
	
    private final Function<? super ConfigurationAdapter, ? extends T> function;

    SimpleConfigKey(Function<? super ConfigurationAdapter, ? extends T> function) {
        this.function = function;
    }

    @Override
    public T get(ConfigurationAdapter adapter) {
        return this.function.apply(adapter);
    }

}
