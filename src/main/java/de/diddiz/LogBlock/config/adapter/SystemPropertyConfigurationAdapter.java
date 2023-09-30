package de.diddiz.LogBlock.config.adapter;

import de.diddiz.LogBlock.LogBlock;

public class SystemPropertyConfigurationAdapter extends StringBasedConfigurationAdapter {
    private static final String PREFIX = "logblock.";

    private final LogBlock plugin;

    public SystemPropertyConfigurationAdapter(LogBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String resolveValue(String path) {
        // e.g.
        // 'server'            -> logblock.server
        // 'data.table_prefix' -> logblock.data.table-prefix
        String key = PREFIX + path;

        String value = System.getProperty(key);
        if (value != null) {
            this.plugin.getLogger().info("Resolved configuration value from system property: " + key + " = " + (path.contains("password") ? "*****" : value));
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
