package au.com.illisian.hg.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import au.com.illisian.hg.Core;
import au.com.illisian.hg.Log;
import au.com.illisian.hg.MathUtils;
import au.com.illisian.hg.PlayerUtils;
import au.com.illisian.hg.WorldUtils;

public class FightManager implements Listener, CommandExecutor {

	List<Player> _registeredPlayers;
	List<Player> _fightingPlayers;
	Phase _currentPhase = Phase.Registration;
	HashMap<Location, Player> _playerSpawns;
	Date _initialRegistrationDate;
	Date _initialFightDate;
	Date _initialPreparingDate;
	long _registrationLength = 180; // 5 mins 300
	long _fightLength = 1800; // 30 mins
	long _preparingLength = 60; // 1 min
	int _maxRegistrations = 24;

	public FightManager() {
		Initialise();

	}

	private void Initialise() {
		_registeredPlayers = new ArrayList<Player>();
		_fightingPlayers = new ArrayList<Player>();
		_playerSpawns = new HashMap<Location, Player>();
		Core.RegisterListener(this);
		Core.RegisterCommand("register", this);
		Core.RegisterCommand("timeleft", this);
		Core.RegisterCommand("players", this);
		Reset();
		Core.getPlugin().getServer().getScheduler()
				.scheduleSyncRepeatingTask(Core.getPlugin(), new Runnable() {
					public void run() {
						Log.info("" + _currentPhase.name());
						switch (_currentPhase) {
						case Registration:
							if (_registeredPlayers.size() < 2) {
								_initialRegistrationDate = new Date();
							} else {

								if (secondsLeft(_initialRegistrationDate,
										_registrationLength) <= 0) {
									SetPhase(Phase.Preparing);
								}
							}
							break;
						case Preparing:
							List<Player> _removePlayer = new ArrayList<Player>();
							for (Iterator<Location> l = _playerSpawns.keySet()
									.iterator(); l.hasNext();) {
								Location loc = l.next();
								Player p = _playerSpawns.get(loc);
								if (p != null) {

									double distance = MathUtils.GetDistance(
											loc, p.getLocation());
									Log.info("PREPARE Distance " + distance
											+ " Name " + p.getName());
									if (distance > 1) {
										_removePlayer.add(p);
									}
								}
							}
							for (Iterator<Player> pl = _removePlayer.iterator(); pl
									.hasNext();) {
								Player p = pl.next();
								p.setHealth(0);
								RemovePlayer(p);
							}

							if (secondsLeft(_initialPreparingDate,
									_preparingLength) <= 0) {
								SetPhase(Phase.Fighting);
							}
							break;

						case Fighting:
							Log.info("Size:" + _fightingPlayers.size());

							if (_fightingPlayers.size() <= 1) {
								SetPhase(Phase.Finish);
							}
							if (secondsLeft(_initialFightDate, _fightLength) <= 0) {
								SetPhase(Phase.Finish);
							}
							break;
						case Finish:

							break;

						}
					}
				}, 30L, 100L);

	}

	private void Reset() {
		SetPhase(Phase.Registration);
	}

	public void SetPhase(Phase p) {
		Log.info("Setting Phase " + p.name() + " Current Phase "
				+ _currentPhase.name());
		World _selectedWorld = WorldUtils.GetWorld(Core._survivalWorldName);
		switch (p) {
		case Registration:
			ChatManager.sendMessageToServer("GameMaker",
					"Hunger Games Registrations are now Open");
			_initialRegistrationDate = new Date();
			_playerSpawns.clear();
			_playerSpawns.put(new Location(_selectedWorld, -1570.528500,
					59.500000, -634.064775), null);
			_playerSpawns.put(new Location(_selectedWorld, -1564.688411,
					59.500000, -632.080477), null);
			_playerSpawns.put(new Location(_selectedWorld, -1558.351514,
					59.500000, -628.364395), null);
			_playerSpawns.put(new Location(_selectedWorld, -1554.333646,
					59.500000, -622.425000), null);
			_playerSpawns.put(new Location(_selectedWorld, -1552.534736,
					59.500000, -616.094100), null);
			_playerSpawns.put(new Location(_selectedWorld, -1551.768523,
					59.500000, -610.389770), null);
			_playerSpawns.put(new Location(_selectedWorld, -1552.626635,
					59.500000, -604.211785), null);
			_playerSpawns.put(new Location(_selectedWorld, -1554.714139,
					59.500000, -598.434928), null);
			_playerSpawns.put(new Location(_selectedWorld, -1558.766254,
					59.500000, -592.323436), null);
			_playerSpawns.put(new Location(_selectedWorld, -1564.624804,
					59.500000, -588.618443), null);
			_playerSpawns.put(new Location(_selectedWorld, -1570.561032,
					59.500000, -586.736379), null);
			_playerSpawns.put(new Location(_selectedWorld, -1576.546477,
					59.500000, -585.690590), null);
			_playerSpawns.put(new Location(_selectedWorld, -1582.560524,
					59.500000, -586.631162), null);
			_playerSpawns.put(new Location(_selectedWorld, -1588.530823,
					59.500000, -588.622668), null);
			_playerSpawns.put(new Location(_selectedWorld, -1594.419003,
					59.500000, -592.661281), null);
			_playerSpawns.put(new Location(_selectedWorld, -1598.468925,
					59.500000, -598.543239), null);
			_playerSpawns.put(new Location(_selectedWorld, -1600.457546,
					59.500000, -604.643060), null);
			_playerSpawns.put(new Location(_selectedWorld, -1601.401960,
					59.500000, -610.765276), null);
			_playerSpawns.put(new Location(_selectedWorld, -1600.229103,
					59.500000, -616.515515), null);
			_playerSpawns.put(new Location(_selectedWorld, -1598.435473,
					59.500000, -622.772439), null);
			_playerSpawns.put(new Location(_selectedWorld, -1594.375035,
					59.500000, -629.153951), null);
			_playerSpawns.put(new Location(_selectedWorld, -1588.403777,
					59.500000, -632.594063), null);
			_playerSpawns.put(new Location(_selectedWorld, -1582.389588,
					59.500000, -634.281200), null);
			_playerSpawns.put(new Location(_selectedWorld, -1576.453013,
					59.500000, -635.433815), null);
			break;
		case Preparing:
			_initialPreparingDate = new Date();
			for (Iterator<Player> i = _registeredPlayers.iterator(); i
					.hasNext();) {
				Player player = i.next();
				player.getInventory().clear();
				for (Iterator<Location> l = _playerSpawns.keySet().iterator(); l
						.hasNext();) {
					Location loc = l.next();
					if (_playerSpawns.get(loc) == null) {
						_playerSpawns.put(loc, player);
						player.teleport(loc);
						PlayerUtils.ResetPlayer(player);
						break;
					}

				}
			}
			_registeredPlayers.clear();
			ChatManager.sendMessageToServer("GameMaker",
					"A Hunger Games session is initialising...");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"Welcome \"Tribute's\" to the Hunger Games");
			ChatManager
					.sendMessageToWorld(_selectedWorld, "GameMaker",
							"Do not move from your position for 60 seconds unless you wish to die");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"---Rules---");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"Rule #1 - Kill to win");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"Rule #2 - ?");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"Rule #3 - Profit!!");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"May the odds always be in your favor.");
			ChatManager.sendMessageToWorld(_selectedWorld, "GameMaker",
					"You have 30 mins to win.");
			break;
		case Fighting:
			ChatManager.sendMessageToServer("GameMaker",
					"Let the Hunger Games... Begin");
			_initialFightDate = new Date();
			_fightingPlayers.clear();
			for (Iterator<Player> pi = _playerSpawns.values().iterator(); pi
					.hasNext();) {
				Player pl = pi.next();
				if(pl != null)
				{
					_fightingPlayers.add(pl);
				}
			}
			_playerSpawns.clear();

			break;
		case Finish:
			if (_fightingPlayers.size() == 1) {
				Player player = _fightingPlayers.get(0);
				String message = String
						.format("Congratulations Tribute '%s' you are the winner of the Hunger Games",
								player.getName());
				ChatManager.sendMessageToServer("GameMaker", message);
			} else if (_fightingPlayers.size() == 0) {
				String message = "It seems that everyone died.. Too bad, no one won.";
				ChatManager.sendMessageToServer("GameMaker", message);
			} else {
				String message = "I havent made any end game logic, so matches only go for 30 mins for now, please stay tuned for more additions.";
				ChatManager.sendMessageToServer("GameMaker", message);
			}
			
			WorldUtils.MovePlayersFromWorld(Core._survivalWorldName, Core._spawnWorld, true);
			_fightingPlayers.clear();
			
			WorldUtils.RemoveWorld(Core._survivalWorldName, false);
			Core.count++;
			Core._survivalWorldName = String.format("%s%d",
					Core._survivalWorldPrefix, Core.count);
			WorldUtils.CopyWorld(Core._survivalWorldSource, Core._survivalWorldName);
			SetPhase(Phase.Registration);
			return;

		}
		_currentPhase = p;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (cmd.getName().equalsIgnoreCase("register")) {
					RegisterPlayer(player);
					return true;
				}
				if (cmd.getName().equalsIgnoreCase("timeleft")) {
					TimeLeft(player);
					return true;
				}
				if (cmd.getName().equalsIgnoreCase("position") && player.isOp()) {
					Location loc = player.getLocation();
					String message = String.format("%f, %f, %f", loc.getX(),
							loc.getY(), loc.getZ());
					player.sendMessage(message);
					Log.info(message);
					return true;
				}
				if (cmd.getName().equalsIgnoreCase("warp") && player.isOp()) {
					World w = WorldUtils.GetWorld(Core._survivalWorldSource);
					Location loc = w.getSpawnLocation();
					player.teleport(loc);
					return true;
				}
				if (cmd.getName().equalsIgnoreCase("players")) {
					player.sendMessage("-- Current Fighters --");
					if (_currentPhase.equals(Phase.Fighting)) {
						for (Iterator<Player> pi = _fightingPlayers.iterator(); pi
								.hasNext();) {
							Player pl = pi.next();
							player.sendMessage(pl.getName());
						}
					} else if (_currentPhase.equals(Phase.Registration)) {
						for (Iterator<Player> pi = _registeredPlayers
								.iterator(); pi.hasNext();) {
							Player pl = pi.next();
							player.sendMessage(pl.getName());
						}

					} else if (_currentPhase.equals(Phase.Preparing)) {
						for (Iterator<Player> pi = _playerSpawns.values()
								.iterator(); pi.hasNext();) {
							Player pl = pi.next();
							player.sendMessage(pl.getName());
						}

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

	private void RegisterPlayer(Player player) {
		if (_currentPhase.equals(Phase.Registration)) {
			if (_registeredPlayers.contains(player)) {
				player.sendMessage("You have already registered for the games.");
			} else if (_registeredPlayers.size() < _maxRegistrations) {
				_registeredPlayers.add(player);
				player.sendMessage("You have successfully registered for the games.");
				ChatManager
						.sendMessageToServer(
								"GameMaker",
								String.format(
										"Tribute '%s' has registered themselves for the hunger games.",
										player.getName()));
			}
		} else {
			player.sendMessage("You are unable to register at this time. The Current Phase is "
					+ _currentPhase.name());
		}
	}

	private void TimeLeft(Player player) {

		if (_currentPhase.equals(Phase.Registration)) {
			if (_registeredPlayers.size() < 2) {
				_initialRegistrationDate = new Date();
				player.sendMessage("You need more then 1 player before the countdown will start");
			}

			long secondsRemains = secondsLeft(_initialRegistrationDate,
					_registrationLength);
			player.sendMessage(String
					.format("There are '%d' remaining seconds till the next phase Begins",
							secondsRemains));

		} else if (_currentPhase.equals(Phase.Fighting)) {

			long secondsRemains = secondsLeft(_initialFightDate, _fightLength);
			player.sendMessage(String
					.format("There are '%d' remain seconds till the end of the Hunger Games",
							secondsRemains));

		}
	}

	private long secondsLeft(Date initialDate, long timespan) {
		long _ends = (initialDate.getTime() / 1000) + timespan;
		long _current = (new Date().getTime() / 1000);
		return _ends - _current;

	}

	private void RemovePlayer(Player player) {
		if (_fightingPlayers.contains(player)) {
			_fightingPlayers.remove(player);
			String message = String.format("Tribute '%s' has been killed",
					player.getName());
			ChatManager.sendMessageToServer("GameMaker", message);
		}

		if (_playerSpawns.containsValue(player)) {
			Location _removeKey = null;

			for (Iterator<Location> l = _playerSpawns.keySet().iterator(); l
					.hasNext();) {
				Location loc = l.next();
				if (_playerSpawns.get(loc) == player) {
					_removeKey = loc;
				}
			}
			if (_removeKey != null) {
				_playerSpawns.remove(_removeKey);
				String message = String
						.format("Tribute '%s' has left their position early and has been terminated",
								player.getName());
				ChatManager.sendMessageToServer("GameMaker", message);
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			RemovePlayer(player);

		}
		if (event instanceof PlayerDeathEvent) {
			PlayerDeathEvent e = (PlayerDeathEvent) event;
			e.setDeathMessage(null);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer() != null) {
			if (!event.getPlayer().isOp()
					&& event.getBlock().getType() != Material.LEAVES) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getPlayer() != null) {
			if (!event.getPlayer().isOp()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		RemovePlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
		RemovePlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		if (player.getWorld().getName() != Core._spawnWorld
				&& !_fightingPlayers.contains(player)) {
			World spawn = WorldUtils.GetWorld(Core._spawnWorld);
			player.teleport(spawn.getSpawnLocation());

		}
	}



}
