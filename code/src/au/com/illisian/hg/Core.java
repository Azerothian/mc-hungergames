package au.com.illisian.hg;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import au.com.illisian.hg.MainPlugin;
import au.com.illisian.hg.Core;
import au.com.illisian.hg.managers.ChatManager;
import au.com.illisian.hg.managers.FightManager;
import au.com.illisian.hg.managers.WorldManager;

public class Core {
	

	public static String _spawnWorld = "spawn";
	public static String _survivalWorldPrefix = "survival";
	public static String _survivalWorldName = "survival0";
	public static String _survivalWorldSource = "survival_src";
	public static int count = 0;
	static MainPlugin _plugin;
	static ChatManager _chatManager;
	static FightManager _fightManager;
	static WorldManager _worldManager;
	

	public static ChatManager getChatManager() {
		return _chatManager;
	}
	
	public static FightManager getFightManager() {
		return _fightManager;
	}
	public static WorldManager getWorldManager() {
		return _worldManager;
	}
	
	public static MainPlugin getPlugin() {
		return _plugin;
	}
	
	public static void Initialise(MainPlugin plugin) {
		_plugin = plugin;
		_chatManager = new ChatManager();
		_worldManager = new WorldManager();
		if(WorldUtils.GetWorld(Core._survivalWorldName) != null)
		{
			WorldUtils.MovePlayersFromWorld( Core._survivalWorldName,  Core._spawnWorld, true);
			WorldUtils.RemoveWorld(Core._survivalWorldName, false);
		}
		WorldUtils.CopyWorld(Core._survivalWorldSource, Core._survivalWorldName);
		_fightManager = new FightManager();
		
		
		
	}
	public static void Deinitialise() {

	}

	public static void RegisterListener(Listener _listener) {
		Core.getPlugin().getServer().getPluginManager()
				.registerEvents(_listener, Core.getPlugin());
	}

	public static void RegisterCommand(String name, CommandExecutor executor) {
		Core.getPlugin().getCommand(name).setExecutor(executor);
	}

}
