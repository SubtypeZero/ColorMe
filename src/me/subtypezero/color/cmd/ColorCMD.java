package me.subtypezero.color.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * ColorMe command executor
 *
 * Opens the color menu when the /color command is used.
 */
public class ColorCMD implements CommandExecutor {
	private final Inventory colorMenu;

	public ColorCMD(Inventory colorMenu) {
		this.colorMenu = colorMenu;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;

		Inventory colorInv = Bukkit.createInventory(null, colorMenu.getSize(), colorMenu.getName());
		colorInv.setContents(colorMenu.getContents());
		player.openInventory(colorInv);
		return true;
	}
}
