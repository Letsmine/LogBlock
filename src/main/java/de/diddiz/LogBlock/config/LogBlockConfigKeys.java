package de.diddiz.LogBlock.config;

import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.booleanKey;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.key;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.lowercaseStringKey;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.mapKey;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.stringKey;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.integerKey;
import static de.diddiz.LogBlock.config.key.ConfigKeyFactory.stringListKey;
import static org.bukkit.Bukkit.getConsoleSender;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.permissions.PermissionDefault;

import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.Tool;
import de.diddiz.LogBlock.ToolBehavior;
import de.diddiz.LogBlock.ToolMode;
import de.diddiz.LogBlock.config.key.ConfigKey;

public final class LogBlockConfigKeys {
	private LogBlockConfigKeys() {}
	
	public static final ConfigKey<String> VERSION = lowercaseStringKey("version", Config.CURRENT_CONFIG_VERSION);
	
	public static final ConfigKey<List<String>> LOGGEDWORLDS = stringListKey("loggedWorlds", List.of());
	
	// MYSQL
	public static final ConfigKey<String> MYSQL_PROTOCOL = stringKey("mysql.protocol", "mysql");
	public static final ConfigKey<String> MYSQL_HOST = stringKey("mysql.host", "localhost");
	public static final ConfigKey<Integer> MYSQL_PORT = integerKey("mysql.port", 3306);
	public static final ConfigKey<String> MYSQL_DATABASE = stringKey("mysql.database", "minecraft");
	public static final ConfigKey<String> MYSQL_USER = stringKey("mysql.user", "username");
	public static final ConfigKey<String> MYSQL_PASSWORD = stringKey("mysql.password", "pass");
	public static final ConfigKey<Boolean> MYSQL_USESSL = booleanKey("mysql.useSSL", true);
	public static final ConfigKey<Boolean> MYSQL_REQUIRESSL = booleanKey("mysql.requireSSL", false);
	
	// CONSUMER
	public static final ConfigKey<Integer> CONSUMER_DELAYBETWEENRUNS = integerKey("consumer.delayBetweenRuns", 2);
	public static final ConfigKey<Integer> CONSUMER_FORCETOPROCESSATLEAST = integerKey("consumer.forceToProcessAtLeast", 200);
	public static final ConfigKey<Integer> CONSUMER_TIMEPERRUN = integerKey("consumer.timePerRun", 1000);
	public static final ConfigKey<Boolean> CONSUMER_USEBUKKITSCHEDULER = booleanKey("consumer.useBukkitScheduler", true);
	public static final ConfigKey<Integer> CONSUMER_QUEUEWARNINGSIZE = integerKey("consumer.queueWarningSize", 1000);
	
	// CLEARLOG
	public static final ConfigKey<Boolean> CLEARLOG_DUMPDELETEDLOG = booleanKey("clearlog.dumpDeletedLog", false);
	public static final ConfigKey<Boolean> CLEARLOG_ENABLEAUTOCLEARLOG = booleanKey("clearlog.enableAutoClearLog", false);
	public static final ConfigKey<List<String>> CLEARLOG_AUTO = stringListKey("clearlog.auto", List.of());
	public static final ConfigKey<String> CLEARLOG_AUTOCLEARLOGDELAY = stringKey("clearlog.autoClearLogDelay", "6h");
	
	// LOGGING
	public static final ConfigKey<Boolean> LOGGING_LOGBEDEXPLOSIONSASPLAYERWHOTRIGGEREDTHESE = booleanKey("logging.logBedExplosionsAsPlayerWhoTriggeredThese", true);
	public static final ConfigKey<Boolean> LOGGING_LOGCREEPEREXPLOSIONSASPLAYERWHOTRIGGEREDTHESE = booleanKey("logging.logCreeperExplosionsAsPlayerWhoTriggeredThese", false);
	public static final ConfigKey<Boolean> LOGGING_LOGFIRESPREADASPLAYERWHOCREATEDIT = booleanKey("logging.logFireSpreadAsPlayerWhoCreatedIt", true);
	public static final ConfigKey<Boolean> LOGGING_LOGFLUIDFLOWASPLAYERWHOTRIGGEREDIT = booleanKey("logging.logFluidFlowAsPlayerWhoTriggeredIt", false);
	public static final ConfigKey<String> LOGGING_LOGKILLSLEVEL = stringKey("logging.logKillsLevel", "PLAYERS");
	public static final ConfigKey<Boolean> LOGGING_LOGENVIRONMENTALKILLS = booleanKey("logging.logEnvironmentalKills", false);
	public static final ConfigKey<Boolean> LOGGING_LOGPLAYERINFO = booleanKey("logging.logPlayerInfo", false);
	public static final ConfigKey<List<String>> LOGGING_HIDDENPLAYERS = stringListKey("logging.hiddenPlayers", List.of());
	public static final ConfigKey<List<String>> LOGGING_HIDDENBLOCKS = stringListKey("logging.hiddenBlocks", List.of(Material.AIR.name(), Material.CAVE_AIR.name(), Material.VOID_AIR.name()));
	public static final ConfigKey<List<String>> LOGGING_IGNORECHAT = stringListKey("logging.ignoredChat", List.of("/register", "/login"));
	
	// ROLLBACK
	public static final ConfigKey<List<String>> ROLLBACK_DONTROLLBACK = stringListKey("rollback.dontRollback", List.of(Material.LAVA.name(), Material.TNT.name(), Material.FIRE.name()));
	public static final ConfigKey<List<String>> ROLLBACK_REPLACEANYWAY = stringListKey("rollback.replaceAnyway", List.of(Material.LAVA.name(), Material.WATER.name(), Material.FIRE.name(), Material.GRASS_BLOCK.name()));
	public static final ConfigKey<String> ROLLBACK_MAXTIME = stringKey("rollback.maxTime", "2 days");
	public static final ConfigKey<Integer> ROLLBACK_MAXAREA = integerKey("rollback.maxArea", 50);
	
	// LOOKUP
	public static final ConfigKey<Integer> LOOKUP_DEFAULTDIST = integerKey("lookup.defaultDist", 20);
	public static final ConfigKey<String> LOOKUP_DEFAULTTIME = stringKey("lookup.defaultTime", "30 minutes");
	public static final ConfigKey<Integer> LOOKUP_LINESPERPAGE = integerKey("lookup.linesPerPage", 15);
	public static final ConfigKey<Integer> LOOKUP_LINESLIMIT = integerKey("lookup.linesLimit", 1500);
	public static final ConfigKey<Integer> LOOKUP_HARDLINESLIMIT = integerKey("lookup.hardLinesLimit", 100000);
	public static final ConfigKey<String> LOOKUP_DATEFORMAT = stringKey("lookup.dateFormat", "yyyy-MM-dd HH:mm:ss");
	public static final ConfigKey<String> LOOKUP_DATEFORMATSHORT = stringKey("lookup.dateFormatShort", "MM-dd HH:mm");
	
	// QUESTIONER
	public static final ConfigKey<Boolean> QUESTIONER_ASROLLBACKS = booleanKey("questioner.askRollbacks", true);
	public static final ConfigKey<Boolean> QUESTIONER_ASKREDOS = booleanKey("questioner.askRedos", true);
	public static final ConfigKey<Boolean> QUESTIONER_ASKCLEARLOGS = booleanKey("questioner.askClearLogs", true);
	public static final ConfigKey<Boolean> QUESTIONER_ASKCLEARLOGAFTERROLLBACK = booleanKey("questioner.askClearLogAfterRollback", true);
	public static final ConfigKey<Boolean> QUESTIONER_ASKROLLBACKAFTERBAN = booleanKey("questioner.askRollbackAfterBan", false);
	public static final ConfigKey<String> QUESTIONER_BANPERMISSION = stringKey("questioner.banPermission", "mcbans.ban.local");
	
	// TOOLS
	public static final ConfigKey<Set<Tool>> TOOLS = key(c -> {
		var result = new HashSet<Tool>();
		var list = c.getSectionStringMap("tools", Map.of());
		list.entrySet().forEach(entry -> {
			var toolName = entry.getKey();
			var map = entry.getValue();
			
            final List<String> aliases = List.of(map.get("aliases").split(","));
            final ToolBehavior leftClickBehavior = ToolBehavior.valueOf(map.get("leftClickBehavior").toUpperCase());
            final ToolBehavior rightClickBehavior = ToolBehavior.valueOf(map.get("rightClickBehavior").toUpperCase());
            final boolean defaultEnabled = Boolean.valueOf(map.get("defaultEnabled"));
            final Material item = Material.matchMaterial(map.get("item"));
            final boolean canDrop = Boolean.valueOf(map.get("canDrop"));
            final boolean removeOnDisable = Boolean.valueOf(map.get("removeOnDisable"));
            final boolean dropToDisable = Boolean.valueOf(map.get("dropToDisable"));
            final QueryParams params = new QueryParams(LogBlock.getInstance());
            params.prepareToolQuery = true;
            params.parseArgs(getConsoleSender(), Arrays.asList(map.get("params").split(" ")), false);
            final ToolMode mode = ToolMode.valueOf(map.get("mode").toUpperCase());
            final PermissionDefault pdef = PermissionDefault.valueOf(map.get("permissionDefault").toUpperCase());
			var tool = new Tool(toolName, aliases, leftClickBehavior, rightClickBehavior, defaultEnabled, item, canDrop, params, mode, pdef, removeOnDisable, dropToDisable);
			result.add(tool);
		});
		return result;
	});
	
	
	public static final ConfigKey<Boolean> SAFETY_ID_CHECK = booleanKey("safety.id.check", true);
	
	public static final ConfigKey<Boolean> ADDONS_WORLDGUARDLOGGINGFLAGS = booleanKey("addons.worldguardLoggingFlags", false);
	
	public static final ConfigKey<Boolean> DEBUG = booleanKey("debug", false);
}
