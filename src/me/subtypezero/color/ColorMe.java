package me.subtypezero.color;

import me.subtypezero.color.cmd.ColorCMD;
import me.subtypezero.color.event.UpdateColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * ColorMe plugin
 *
 * Creates the color menu, registers the event handler, and sets up the color command.
 * Note that some chat colors do not have wool equivalents, so some names have been modified.
 * Colors that are generally not pleasing to the eye have been excluded from the color menu.
 */
public class ColorMe extends JavaPlugin implements Listener {
	private Inventory colorInv;

	public void onEnable() {
		setupInventory();
		getServer().getPluginManager().registerEvents(new UpdateColor(colorInv), this);
		getCommand("color").setExecutor(new ColorCMD(colorInv));
	}

	private void setupInventory() {
		colorInv = Bukkit.createInventory(null, 27, "§2§lColor Menu");

		colorInv.setItem(1, createItem(14, "§cRed", "§f§l&c"));
		colorInv.setItem(2, createItem(1, "§6Gold", "§f§l&6"));
		colorInv.setItem(3, createItem(4, "§eYellow", "§f§l&e"));
		colorInv.setItem(4, createItem(13, "§2Green", "§f§l&2"));
		colorInv.setItem(5, createItem(5, "§aLime", "§f§l&a"));
		colorInv.setItem(6, createItem(3, "§bCyan", "§f§l&b"));
		colorInv.setItem(7, createItem(9, "§3Aqua", "§f§l&3"));

		colorInv.setItem(10, createItem(11, "§9Blue", "§f§l&9"));
		colorInv.setItem(11, createItem(6, "§dPink", "§f§l&d"));
		colorInv.setItem(12, createItem(10, "§5Purple", "§f§l&5"));

		colorInv.setItem(14, createItem(0, "§fWhite", "§f§l&f"));
		colorInv.setItem(15, createItem(8, "§7Light-Gray", "§f§l&7"));
		colorInv.setItem(16, createItem(7, "§8Gray", "§f§l&8"));

		Bukkit.getLogger().info("Finished creating color inventory.");
	}

	private ItemStack createItem(int id, String name, String lore) {
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) id);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(new String[] { lore }));
		item.setItemMeta(meta);
		return item;
	}
}
