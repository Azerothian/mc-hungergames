package au.com.illisian.hg;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldUtils {

	public static World GetWorld(String name) {
		if (Core.getPlugin().getServer().getWorld(name) == null) {
			LoadWorld(name);
		}
		return Core.getPlugin().getServer().getWorld(name);
	}

	public static void MovePlayersFromWorld(String sourceWorld,
			String destinationWorld, Boolean resetPlayer) {
		World wDestWorld = GetWorld(destinationWorld);
		World wSrcWorld = GetWorld(sourceWorld);
		Location lSpawn = wDestWorld.getSpawnLocation();
		List<Player> _players = wSrcWorld.getPlayers();

		for (Iterator<Player> pli = _players.iterator(); pli.hasNext();) {
			Player _player = pli.next();
			if (resetPlayer) {
				PlayerUtils.ResetPlayer(_player);
			}
			_player.teleport(lSpawn);
		}
		OfflinePlayer[] offline = Core.getPlugin().getServer()
				.getOfflinePlayers();
		for (int i = 0; i < offline.length; i++) {
			Player p = offline[i].getPlayer();
			if (p != null) {
				p.teleport(lSpawn);
				if (resetPlayer) {
					PlayerUtils.ResetPlayer(offline[i].getPlayer());
				}
			}
		}

	}

	public static void MovePlayersToWorld(Player[] players,
			String destinationWorld, Boolean resetPlayer) {
		World wDestWorld = GetWorld(destinationWorld);
		Location lSpawn = wDestWorld.getSpawnLocation();
		for (int i = 0; i < players.length; i++) {
			players[i].getPlayer().teleport(lSpawn);
			if (resetPlayer) {
				PlayerUtils.ResetPlayer(players[i].getPlayer());
			}
		}

	}

	public static void CopyWorld(String source, String destination) {
		try {
			if (GetWorld(destination) != null) {
				RemoveWorld(destination, false);
			}
			FileUtils.copy(new File(source), new File(destination));
			// removes the uid so we can load it
			FileUtils.delete(new File(destination + "/uid.dat"));
		} catch (IOException ex) {
			Log.info(String
					.format("[CopyWorld] Error occurred while attempting to copy world: %s to %s",
							source, destination));
			ex.printStackTrace();
		}

	}

	public static void UnloadWorld(String name, Boolean save) {
		if (Core.getPlugin().getServer().getWorld(name) != null) {
			Core.getPlugin().getServer().unloadWorld(name, save);
		}
	}

	public static void RemoveWorld(String name, Boolean save) {
		UnloadWorld(name, save);
		try {
			FileUtils.delete(new File(name));
		} catch (IOException ex) {
			Log.info(String
					.format("[RemoveWorld] Error occurred while attempting to remove world: %s to %s",
							name));
			ex.printStackTrace();
		}
	}

	public static void LoadWorld(String name) {
		Log.info(String.format("Loading World '%s'", name));
		WorldCreator wc = new WorldCreator(name);
		World w = Core.getPlugin().getServer().createWorld(wc);
		w.setPVP(true);
		w.setSpawnFlags(false, true);
		w.setDifficulty(Difficulty.NORMAL);
		Log.info(String.format("Loaded World '%s'", w.getName()));
	}

}
