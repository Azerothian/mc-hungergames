package au.com.illisian.hg.managers;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import au.com.illisian.hg.Core;
import au.com.illisian.hg.Log;
import au.com.illisian.hg.MathUtils;

public class ChatManager implements Listener, CommandExecutor {

	int whisper_distance = 4;
	int say_distance = 12;
	int yell_distance = 30;

	// HashMap<String, List<Player>> _channels;

	public ChatManager() {
		Initialise();
	}

	private void Initialise() {
		// _channels = new HashMap<String, List<Player>>();
		Core.RegisterListener(this);
		Core.RegisterCommand("welcome", this);
		Core.RegisterCommand("talk", this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(PlayerChatEvent evt) {
		
		evt.setCancelled(true);
		String message = evt.getMessage();
		String msg = "";
		int distance = say_distance;
		if (message.toLowerCase().startsWith("yell ")) {
			msg = ChatColor.RED
					+ message.substring(5, message.length()).toUpperCase();
			distance = yell_distance;
		} else if (message.toLowerCase().startsWith("whisper ")) {
			msg = ChatColor.BLUE
					+ message.substring(8, message.length());
			distance = whisper_distance;
		} else if (message.toLowerCase().startsWith("ooc ")) {
			msg = ChatColor.YELLOW + "[OOC] "
					+ message.substring(4, message.length());
			distance = -1;
			sendMessageToServer(evt.getPlayer().getName(), msg);
			return;
		} else {
			msg = message;
			distance = say_distance;
		}

		sendMessage(evt.getPlayer(), ChatColor.WHITE + msg, distance);

	}

	public void sendMessage(Player player, String message, int distance) {
		List<Player> players = player.getWorld().getPlayers();

		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			Player p = i.next();
			if (MathUtils.GetDistance(player.getLocation(), p.getLocation()) <= distance
					|| distance == -1) {
				p.sendMessage(String.format("%s[%s] %s", ChatColor.GOLD,
						player.getName(), message));
			}
		}
	}

	public static void sendMessageToWorld(World world, String from,
			String message) {
		List<Player> players = world.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			Player p = i.next();
			p.sendMessage(String.format("%s[%s] %s", ChatColor.GOLD, from,
					message));

		}
	}

	public static void sendMessageToServer(String from, String message) {
		List<World> worlds = Core.getPlugin().getServer().getWorlds();
		for (Iterator<World> i = worlds.iterator(); i.hasNext();) {
			World w = i.next();
			sendMessageToWorld(w, from, message);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		player.sendMessage(ChatColor.WHITE + "Welcome to Minecraftworld.net");
		player.sendMessage(ChatColor.GREEN
				+ "This server runs a HungerGames Plugin.");
		player.sendMessage(ChatColor.GREEN
				+ "If you are new to the server please type " + ChatColor.RED
				+ "/welcome");
		player.sendMessage(ChatColor.GREEN
				+ "This server runs a chat mod, on how to talk type "
				+ ChatColor.RED + "/talk ");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (cmd.getName().equalsIgnoreCase("talk")) {
					player.sendMessage(ChatColor.GREEN
							+ "Welcome to the Hunger Games TalkArea Mod");
					player.sendMessage(ChatColor.YELLOW
							+ "There are 4 commands; whisper, say, yell, ooc");
					player.sendMessage(ChatColor.GREEN
							+ "Usage: type in chat [cmd] [message]. e.g. ooc Hello. For the say command you just type your message");
					player.sendMessage(ChatColor.RED
							+ "whisper"
							+ ChatColor.YELLOW
							+ ": This will send your text to those within a short range");
					player.sendMessage(ChatColor.RED
							+ "say"
							+ ChatColor.YELLOW
							+ ": This will send your text to those within a medium range, for this command you do not append anything");
					player.sendMessage(ChatColor.RED
							+ "yell"
							+ ChatColor.YELLOW
							+ ": This will shout out your message to a large distance");
					player.sendMessage(ChatColor.RED
							+ "ooc"
							+ ChatColor.YELLOW
							+ ": This will send your message to everyone in the server");
					return true;
				} else if (cmd.getName().equalsIgnoreCase("welcome")) {
					player.sendMessage(ChatColor.YELLOW
							+ "Welcome to the Hunger Games");
					player.sendMessage(ChatColor.YELLOW
							+ "There are 4 Phases to the Hunger Games");
					player.sendMessage(ChatColor.YELLOW
							+ "During Phase 1 and 3 people can use the "
							+ ChatColor.RED + "/timeleft" + ChatColor.YELLOW
							+ " command to see how long left");
					player.sendMessage(ChatColor.GREEN
							+ "Phase 1: Registration : 5 mins");
					player.sendMessage(ChatColor.YELLOW
							+ "At this stage you are able to use "
							+ ChatColor.RED + "/register" + ChatColor.YELLOW
							+ " command to signup");
					player.sendMessage(ChatColor.GREEN
							+ "Phase 2: Preparing : 60 seconds");
					player.sendMessage(ChatColor.YELLOW
							+ "People who have registered will be teleported into position.");
					player.sendMessage(ChatColor.YELLOW
							+ "During this phase you cannot move away from your spawn point. You will be killed.");
					player.sendMessage(ChatColor.GREEN
							+ "Phase 3: Battle : 30 mins");
					player.sendMessage(ChatColor.YELLOW
							+ "Currently there is no end game code so if more then one person is alive. no one wins and its gameover.");
					player.sendMessage(ChatColor.GREEN
							+ "Phase 4: Finish : Resets the map and loops back to registration ");
				}
				return true;
			}
		} catch (Exception e) {

			Log.severe(e.getMessage() + "\n");
			e.printStackTrace();
		}
		return false;
	}

}
