package au.com.illisian.hg;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
	public static void ResetPlayer(Player player) {
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.getInventory().setArmorContents(
				new ItemStack[player.getInventory().getArmorContents().length]);

	}
}
