package de.diddiz.LogBlock.config;

import static de.diddiz.LogBlock.config.LogBlockConfigKeys.ADDONS_WORLDGUARDLOGGINGFLAGS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CLEARLOG_AUTO;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CLEARLOG_AUTOCLEARLOGDELAY;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CLEARLOG_DUMPDELETEDLOG;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CLEARLOG_ENABLEAUTOCLEARLOG;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CONSUMER_DELAYBETWEENRUNS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CONSUMER_FORCETOPROCESSATLEAST;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CONSUMER_QUEUEWARNINGSIZE;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CONSUMER_TIMEPERRUN;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.CONSUMER_USEBUKKITSCHEDULER;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.DEBUG;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGEDWORLDS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_HIDDENBLOCKS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_HIDDENPLAYERS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_IGNORECHAT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGBEDEXPLOSIONSASPLAYERWHOTRIGGEREDTHESE;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGCREEPEREXPLOSIONSASPLAYERWHOTRIGGEREDTHESE;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGENVIRONMENTALKILLS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGFIRESPREADASPLAYERWHOCREATEDIT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGFLUIDFLOWASPLAYERWHOTRIGGEREDIT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGKILLSLEVEL;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOGGING_LOGPLAYERINFO;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_DATEFORMAT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_DATEFORMATSHORT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_DEFAULTDIST;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_DEFAULTTIME;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_HARDLINESLIMIT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_LINESLIMIT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.LOOKUP_LINESPERPAGE;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_DATABASE;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_HOST;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_PASSWORD;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_PORT;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_PROTOCOL;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_REQUIRESSL;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_USER;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.MYSQL_USESSL;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_ASKCLEARLOGAFTERROLLBACK;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_ASKCLEARLOGS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_ASKREDOS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_ASKROLLBACKAFTERBAN;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_ASROLLBACKS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.QUESTIONER_BANPERMISSION;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.ROLLBACK_DONTROLLBACK;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.ROLLBACK_MAXAREA;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.ROLLBACK_MAXTIME;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.ROLLBACK_REPLACEANYWAY;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.TOOLS;
import static de.diddiz.LogBlock.config.LogBlockConfigKeys.VERSION;
import static de.diddiz.LogBlock.util.BukkitUtils.friendlyWorldname;
import static de.diddiz.LogBlock.util.Utils.parseTimeSpec;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.Logging;
import de.diddiz.LogBlock.Tool;
import de.diddiz.LogBlock.config.adapter.EnvironmentVariableConfigurationAdapter;
import de.diddiz.LogBlock.config.adapter.MultiConfigurationAdapter;
import de.diddiz.LogBlock.config.adapter.SystemPropertyConfigurationAdapter;
import de.diddiz.LogBlock.config.adapter.YamlConfigurationAdapter;
import de.diddiz.LogBlock.util.ComparableVersion;

public class Config {
    private static LoggingEnabledMapping superWorldConfig;
    private static Map<String, WorldConfig> worldConfigs;
    public static String url, user, password;
    public static String mysqlDatabase;
    public static boolean mysqlUseSSL;
    public static boolean mysqlRequireSSL;
    public static int delayBetweenRuns, forceToProcessAtLeast, timePerRun;
    public static boolean useBukkitScheduler;
    public static int queueWarningSize;
    public static boolean enableAutoClearLog;
    public static List<String> autoClearLog;
    public static int autoClearLogDelay;
    public static boolean dumpDeletedLog;
    public static boolean logBedExplosionsAsPlayerWhoTriggeredThese;
    public static boolean logCreeperExplosionsAsPlayerWhoTriggeredThese, logPlayerInfo;
    public static boolean logFireSpreadAsPlayerWhoCreatedIt;
    public static boolean logFluidFlowAsPlayerWhoTriggeredIt;
    public static LogKillsLevel logKillsLevel;
    public static Set<Material> dontRollback, replaceAnyway;
    public static int rollbackMaxTime, rollbackMaxArea;
    public static Map<String, Tool> toolsByName;
    public static Map<Material, Tool> toolsByType;
    public static int defaultDist, defaultTime;
    public static int linesPerPage, linesLimit, hardLinesLimit;
    public static boolean askRollbacks, askRedos, askClearLogs, askClearLogAfterRollback, askRollbackAfterBan;
    public static String banPermission;
    public static Set<Material> hiddenBlocks;
    public static Set<String> hiddenPlayers;
    public static List<String> ignoredChat;
    public static SimpleDateFormat formatter;
    public static SimpleDateFormat formatterShort;
    public static boolean debug;
    public static boolean logEnvironmentalKills;
    // addons
    public static boolean worldGuardLoggingFlags;
    // Not loaded from config - checked at runtime
    public static boolean mb4 = false;

    public static final String CURRENT_CONFIG_VERSION = "1.19.0";

    public static enum LogKillsLevel {
        PLAYERS,
        MONSTERS,
        ANIMALS;
    }
    
    private static void saveDefaultConfig(LogBlock plugin) {
    	// Save the config.yml if not exists
    	plugin.saveDefaultConfig();
    	
        final List<String> worldNames = plugin.getServer().getWorlds().stream().map(World::getName).collect(Collectors.toList());
        
        if (worldNames.isEmpty()) {
            worldNames.add("world");
            worldNames.add("world_nether");
            worldNames.add("world_the_end");
        }

    	// Get config with config.yml from resources as default
        final var config = plugin.getConfig();
        
        // Add loggedWorlds with currently loaded Worlds
        if (!config.contains("loggedWorlds", true)) {
        	config.set("loggedWorlds", worldNames);
        }
        
        // Add clearlog.auto examples with currently loaded Worlds
        if (!config.contains("clearlog.auto", true)) {
	        final var autoClearlog = new ArrayList<>();
	        for (final String world : worldNames) {
	            autoClearlog.add("world \"" + world + "\" before 365 days all");
	            autoClearlog.add("world \"" + world + "\" player lavaflow waterflow leavesdecay before 7 days all");
	            autoClearlog.add("world \"" + world + "\" entities before 365 days");
	        }
	        config.set("clearlog.auto", autoClearlog);
        }
        
        // Add tools examples
        if (!config.contains("tools", true)) {
        	config.set("tools.tool.aliases", Arrays.asList("t"));
        	config.set("tools.tool.leftClickBehavior", "NONE");
        	config.set("tools.tool.rightClickBehavior", "TOOL");
        	config.set("tools.tool.defaultEnabled", true);
        	config.set("tools.tool.item", Material.WOODEN_PICKAXE.name());
        	config.set("tools.tool.canDrop", true);
        	config.set("tools.tool.removeOnDisable", true);
        	config.set("tools.tool.dropToDisable", false);
        	config.set("tools.tool.params", "area 0 all sum none limit 15 desc since 60d silent");
        	config.set("tools.tool.mode", "LOOKUP");
        	config.set("tools.tool.permissionDefault", "OP");
            
        	config.set("tools.toolblock.aliases", Arrays.asList("tb"));
        	config.set("tools.toolblock.leftClickBehavior", "TOOL");
        	config.set("tools.toolblock.rightClickBehavior", "BLOCK");
        	config.set("tools.toolblock.defaultEnabled", true);
        	config.set("tools.toolblock.item", Material.BEDROCK.name());
        	config.set("tools.toolblock.canDrop", false);
        	config.set("tools.toolblock.removeOnDisable", true);
        	config.set("tools.toolblock.dropToDisable", false);
        	config.set("tools.toolblock.params", "area 0 all sum none limit 15 desc since 60d silent");
        	config.set("tools.toolblock.mode", "LOOKUP");
            config.set("tools.toolblock.permissionDefault", "OP");
        }
        
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public static void load(LogBlock logblock) throws DataFormatException, IOException {
    	saveDefaultConfig(logblock);

        var readConfig = new MultiConfigurationAdapter(logblock, 
        		new SystemPropertyConfigurationAdapter(logblock),
        		new EnvironmentVariableConfigurationAdapter(logblock),
        		new YamlConfigurationAdapter(logblock, new File(logblock.getDataFolder(), "config.yml")));

        ComparableVersion configVersion = new ComparableVersion(VERSION.get(readConfig));
        boolean oldConfig = configVersion.compareTo(new ComparableVersion(CURRENT_CONFIG_VERSION)) < 0;
        
        mysqlDatabase = MYSQL_DATABASE.get(readConfig);
        url = "jdbc:" + MYSQL_PROTOCOL.get(readConfig) + "://" + MYSQL_HOST.get(readConfig) + ":" + MYSQL_PORT.get(readConfig) + "/" + mysqlDatabase;
        user = MYSQL_USER.get(readConfig);
        password = MYSQL_PASSWORD.get(readConfig);
        mysqlUseSSL = MYSQL_USESSL.get(readConfig);
        mysqlRequireSSL = MYSQL_REQUIRESSL.get(readConfig);
        delayBetweenRuns = CONSUMER_DELAYBETWEENRUNS.get(readConfig);
        forceToProcessAtLeast = CONSUMER_FORCETOPROCESSATLEAST.get(readConfig);
        timePerRun = CONSUMER_TIMEPERRUN.get(readConfig);
        useBukkitScheduler = CONSUMER_USEBUKKITSCHEDULER.get(readConfig);
        queueWarningSize = CONSUMER_QUEUEWARNINGSIZE.get(readConfig);
        enableAutoClearLog = CLEARLOG_ENABLEAUTOCLEARLOG.get(readConfig);
        autoClearLog = CLEARLOG_AUTO.get(readConfig);
        dumpDeletedLog = CLEARLOG_DUMPDELETEDLOG.get(readConfig);
        autoClearLogDelay = parseTimeSpec(CLEARLOG_AUTOCLEARLOGDELAY.get(readConfig).split(" "));
        logBedExplosionsAsPlayerWhoTriggeredThese = LOGGING_LOGBEDEXPLOSIONSASPLAYERWHOTRIGGEREDTHESE.get(readConfig);
        logCreeperExplosionsAsPlayerWhoTriggeredThese = LOGGING_LOGCREEPEREXPLOSIONSASPLAYERWHOTRIGGEREDTHESE.get(readConfig);
        logFireSpreadAsPlayerWhoCreatedIt = LOGGING_LOGFIRESPREADASPLAYERWHOCREATEDIT.get(readConfig);
        logFluidFlowAsPlayerWhoTriggeredIt = LOGGING_LOGFLUIDFLOWASPLAYERWHOTRIGGEREDIT.get(readConfig);
        logPlayerInfo = LOGGING_LOGPLAYERINFO.get(readConfig);
        try {
            logKillsLevel = LogKillsLevel.valueOf(LOGGING_LOGKILLSLEVEL.get(readConfig).toUpperCase());
        } catch (final IllegalArgumentException ex) {
            throw new DataFormatException("logging.logKillsLevel doesn't appear to be a valid log level. Allowed are 'PLAYERS', 'MONSTERS' and 'ANIMALS'");
        }
        logEnvironmentalKills = LOGGING_LOGENVIRONMENTALKILLS.get(readConfig);
        hiddenPlayers = new HashSet<>();
        for (final String playerName : LOGGING_HIDDENPLAYERS.get(readConfig)) {
            hiddenPlayers.add(playerName.toLowerCase().trim());
        }
        hiddenBlocks = new HashSet<>();
        for (final String blocktype : LOGGING_HIDDENBLOCKS.get(readConfig)) {
            final Material mat = Material.matchMaterial(blocktype);
            if (mat != null) {
                hiddenBlocks.add(mat);
            } else if (!oldConfig) {
                throw new DataFormatException("Not a valid material in hiddenBlocks: '" + blocktype + "'");
            }
        }
        ignoredChat = new ArrayList<>();
        for (String chatCommand : LOGGING_IGNORECHAT.get(readConfig)) {
            ignoredChat.add(chatCommand.toLowerCase());
        }
        dontRollback = new HashSet<>();
        for (String e : ROLLBACK_DONTROLLBACK.get(readConfig)) {
            Material mat = Material.matchMaterial(e);
            if (mat != null) {
                dontRollback.add(mat);
            } else if (!oldConfig) {
                throw new DataFormatException("Not a valid material in dontRollback: '" + e + "'");
            }
        }
        replaceAnyway = new HashSet<>();
        for (String e : ROLLBACK_REPLACEANYWAY.get(readConfig)) {
            Material mat = Material.matchMaterial(e);
            if (mat != null) {
                replaceAnyway.add(mat);
            } else if (!oldConfig) {
                throw new DataFormatException("Not a valid material in replaceAnyway: '" + e + "'");
            }
        }
        rollbackMaxTime = parseTimeSpec(ROLLBACK_MAXTIME.get(readConfig).split(" "));
        rollbackMaxArea = ROLLBACK_MAXAREA.get(readConfig);

        try {
            formatter = new SimpleDateFormat(LOOKUP_DATEFORMAT.get(readConfig));
        } catch (IllegalArgumentException e) {
            throw new DataFormatException("Invalid specification for  date format, please see http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html : " + e.getMessage());
        }
        try {
            formatterShort = new SimpleDateFormat(LOOKUP_DATEFORMATSHORT.get(readConfig));
        } catch (IllegalArgumentException e) {
            throw new DataFormatException("Invalid specification for  date format, please see http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html : " + e.getMessage());
        }
        
        defaultDist = LOOKUP_DEFAULTDIST.get(readConfig);
        defaultTime =  parseTimeSpec(LOOKUP_DEFAULTTIME.get(readConfig).split(" "));
        linesPerPage = LOOKUP_LINESPERPAGE.get(readConfig);
        linesLimit = LOOKUP_LINESLIMIT.get(readConfig);
        hardLinesLimit = LOOKUP_HARDLINESLIMIT.get(readConfig);
        askRollbacks = QUESTIONER_ASROLLBACKS.get(readConfig);
        askRedos = QUESTIONER_ASKREDOS.get(readConfig);
        askClearLogs = QUESTIONER_ASKCLEARLOGS.get(readConfig);
        askClearLogAfterRollback = QUESTIONER_ASKCLEARLOGAFTERROLLBACK.get(readConfig);
        askRollbackAfterBan = QUESTIONER_ASKROLLBACKAFTERBAN.get(readConfig);
        debug = DEBUG.get(readConfig);
        banPermission = QUESTIONER_BANPERMISSION.get(readConfig);
        final var tools = TOOLS.get(readConfig);
        toolsByName = new HashMap<>();
        toolsByType = new HashMap<>();
        for (final Tool tool : tools) {
            toolsByType.put(tool.item, tool);
            toolsByName.put(tool.name.toLowerCase(), tool);
            for (final String alias : tool.aliases) {
                toolsByName.put(alias, tool);
            }
        }
        worldGuardLoggingFlags = ADDONS_WORLDGUARDLOGGINGFLAGS.get(readConfig);
        final List<String> loggedWorlds = LOGGEDWORLDS.get(readConfig);
        // TODO: Auto Logging worlds
        worldConfigs = new HashMap<>();
        if (loggedWorlds.isEmpty()) {
            throw new DataFormatException("No worlds configured");
        }
        for (final String world : loggedWorlds) {
            worldConfigs.put(world, new WorldConfig(world, new File(logblock.getDataFolder(), friendlyWorldname(world) + ".yml")));
        }
        superWorldConfig = new LoggingEnabledMapping();
        for (final WorldConfig wcfg : worldConfigs.values()) {
            for (final Logging l : Logging.values()) {
                if (wcfg.isLogging(l)) {
                    superWorldConfig.setLogging(l, true);
                }
            }
        }
    }

    public static boolean isLogging(World world, Logging l) {
        final WorldConfig wcfg = worldConfigs.get(world.getName());
        return wcfg != null && wcfg.isLogging(l);
    }

    public static boolean isLogging(String worldName, Logging l) {
        final WorldConfig wcfg = worldConfigs.get(worldName);
        return wcfg != null && wcfg.isLogging(l);
    }

    public static boolean isLogged(World world) {
        return worldConfigs.containsKey(world.getName());
    }

    public static WorldConfig getWorldConfig(World world) {
        return worldConfigs.get(world.getName());
    }

    public static WorldConfig getWorldConfig(String world) {
        return worldConfigs.get(world);
    }

    public static boolean isLogging(Logging l) {
        return superWorldConfig.isLogging(l);
    }

    public static Collection<WorldConfig> getLoggedWorlds() {
        return worldConfigs.values();
    }

    public static boolean isLogging(World world, EntityLogging logging, Entity entity) {
        final WorldConfig wcfg = worldConfigs.get(world.getName());
        return wcfg != null && wcfg.isLogging(logging, entity);
    }

    public static boolean isLoggingAnyEntities() {
        for (WorldConfig worldConfig : worldConfigs.values()) {
            if (worldConfig.isLoggingAnyEntities()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoggingNatualSpawns(World world) {
        final WorldConfig wcfg = worldConfigs.get(world.getName());
        return wcfg != null && wcfg.logNaturalEntitySpawns;
    }
}

class LoggingEnabledMapping {
    private final EnumSet<Logging> logging = EnumSet.noneOf(Logging.class);

    public void setLogging(Logging l, boolean enabled) {
        if (enabled) {
            logging.add(l);
        } else {
            logging.remove(l);
        }
    }

    public boolean isLogging(Logging l) {
        return logging.contains(l);
    }
}
