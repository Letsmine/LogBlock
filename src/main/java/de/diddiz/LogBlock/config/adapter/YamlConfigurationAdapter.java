package de.diddiz.LogBlock.config.adapter;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;

import de.diddiz.LogBlock.LogBlock;

public class YamlConfigurationAdapter implements ConfigurationAdapter {
    private final LogBlock plugin;
    private final File file;
    private YamlConfiguration root;

    public YamlConfigurationAdapter(LogBlock plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        reload();
    }

    @Override
    public void reload() {
        this.root = YamlConfiguration.loadConfiguration(this.file);
    }

    private void throwRuntimeExceptionIfConfigNotLoaded() {
        if (this.root == null) {
            throw new RuntimeException("Config is not loaded.");
        }
    }

    @Override
    public String getString(String path, String def) {
    	throwRuntimeExceptionIfConfigNotLoaded();
        return this.root.getString(path, def);
    }

    @Override
    public int getInteger(String path, int def) {
    	throwRuntimeExceptionIfConfigNotLoaded();
        return this.root.getInt(path, def);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
    	throwRuntimeExceptionIfConfigNotLoaded();
        return this.root.getBoolean(path, def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getStringList(String path, List<String> def) {
    	throwRuntimeExceptionIfConfigNotLoaded();
    	return (List<String>) this.root.getList(path, def);
    }

    @Override
    public Map<String, String> getStringMap(String path, Map<String, String> def) {
    	throwRuntimeExceptionIfConfigNotLoaded();
    	var sec = this.root.getConfigurationSection(path);
    	if (sec == null) {
    		return def;
    	}
    	var result = new HashMap<String, String>();
    	var keys = sec.getKeys(false);
    	for (var key : keys) {
    		var defValue = def.get(key);
    		var value = sec.getString(key, defValue);
    		result.put(key, value);
    	}
    	return result;
    }

    @Override
    public LogBlock getPlugin() {
        return this.plugin;
    }

    @Override
    public Map<String, Map<String, String>> getSectionStringMap(String path, Map<String, Map<String, String>> def) {
    	Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
    	if (def != null) {
    		result.putAll(def);
    	}
    	
    	var section = this.root.getConfigurationSection(path);
    	for (var sectionKey : section.getKeys(false)) {
    		var subSection = section.getConfigurationSection(sectionKey);
    		Map<String, String> sectionMap = new HashMap<String, String>();
    		for (var subSectionKey : subSection.getKeys(false)) {
    			String value;
    			if (subSection.isString(subSectionKey)) {
    				value = subSection.getString(subSectionKey);
    			} else if (subSection.isList(subSectionKey)) {
    				value = subSection.getList(subSectionKey).stream().map(String::valueOf).collect(Collectors.joining(","));
    			} else if (subSection.isBoolean(subSectionKey)) {
    				value = String.valueOf(subSection.getBoolean(subSectionKey));
    			} else {
    				continue;
    			}
    			
    			sectionMap.put(subSectionKey, value);
    		}
    		result.put(sectionKey, Collections.unmodifiableMap(sectionMap));
    	}

        return Collections.unmodifiableMap(result);
    }

}
