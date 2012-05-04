package au.com.illisian.hg.managers;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import au.com.illisian.hg.Core;
import au.com.illisian.hg.Log;
import au.com.illisian.hg.WorldUtils;



public class WorldManager  implements Listener, CommandExecutor {
	public WorldManager() {
		Initialise();
	}
	
	private void Initialise() {
		Core.RegisterListener(this);
		Core.RegisterCommand("warp", this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (cmd.getName().equalsIgnoreCase("warp")) {
					World w = WorldUtils.GetWorld(args[0]);
					if(w != null)
					{
						player.teleport(w.getSpawnLocation());
					} else {
						player.sendMessage("world not found");
					}
					return true;
				}
			}
		} catch (Exception e) {

			Log.severe(e.getMessage() + "\n");
			e.printStackTrace();
		}
		return false;
	}

}
