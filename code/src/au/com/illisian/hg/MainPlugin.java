package au.com.illisian.hg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import au.com.illisian.hg.Core;

/**
 * @author azeroth
 *
 */
public class MainPlugin extends JavaPlugin {	

	public void onEnable() {
		Core.Initialise(this);
	}

	public void onDisable() {
		Core.Deinitialise();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		
		
		return false;
	}

}
