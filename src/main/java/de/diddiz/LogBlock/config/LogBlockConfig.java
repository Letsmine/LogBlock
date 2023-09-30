package de.diddiz.LogBlock.config;

import java.util.List;

import de.diddiz.LogBlock.config.adapter.ConfigurationAdapter;
import de.diddiz.LogBlock.config.key.ConfigKey;

public class LogBlockConfig extends KeyedConfiguration {

	public LogBlockConfig(ConfigurationAdapter adapter, List<? extends ConfigKey<?>> keys) {
		super(adapter, keys);
	}

}
