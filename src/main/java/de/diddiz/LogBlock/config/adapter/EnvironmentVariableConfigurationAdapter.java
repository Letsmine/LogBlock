package de.diddiz.LogBlock.config.adapter;

import java.util.Locale;

import de.diddiz.LogBlock.LogBlock;

public class EnvironmentVariableConfigurationAdapter extends StringBasedConfigurationAdapter {
	
    private static final String PREFIX = "LOGBLOCK_";

    private final LogBlock plugin;

    public EnvironmentVariableConfigurationAdapter(LogBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String resolveValue(String path) {
        // e.g.
        // 'server'            -> LOGBLOCK_SERVER
        // 'data.table_prefix' -> LOGBLOCK_DATA_TABLE_PREFIX
        String key = PREFIX + path.toUpperCase(Locale.ROOT)
                .replace('-', '_')
                .replace('.', '_');

        String value = System.getenv(key);
        if (value != null) {
            this.plugin.getLogger().info("Resolved configuration value from environment variable: " + key + " = " + (path.contains("password") ? "*****" : value));
        }
        return value;
    }

    @Override
    public LogBlock getPlugin() {
        return this.plugin;
    }

    @Override
    public void reload() {
        // no-op
    }

}
