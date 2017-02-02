package me.subtypezero.color.event;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * ColorMe event handler
 *
 * Handles color menu events and ensures that items can't be stolen from the menu.
 * Note that some chat colors do not have wool equivalents, so some names have been modified.
 * Colors that are generally not pleasing to the eye have been excluded from the color menu.
 */
public class UpdateColor implements Listener {
	private final Inventory colorMenu;
	private HashMap<UUID, ChatColor> playerColor = new HashMap<>();

	public UpdateColor(Inventory colorMenu) {
		this.colorMenu = colorMenu;
	}

	@EventHandler
	public void onColorSelect(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getName().equals(colorMenu.getName())) {
			event.setCancelled(true);
			player.getOpenInventory().close();

			switch (event.getSlot()) {
				case 1:
					updateColor(player, ChatColor.RED, "red");
					break;
				case 2:
					updateColor(player, ChatColor.GOLD, "gold");
					break;
				case 3:
					updateColor(player, ChatColor.YELLOW, "yellow");
					break;
				case 4:
					updateColor(player, ChatColor.DARK_GREEN, "green");
					break;
				case 5:
					updateColor(player, ChatColor.GREEN, "lime");
					break;
				case 6:
					updateColor(player, ChatColor.DARK_AQUA, "cyan");
					break;
				case 7:
					updateColor(player, ChatColor.AQUA, "aqua");
					break;

				case 10:
					updateColor(player, ChatColor.LIGHT_PURPLE, "blue");
					break;
				case 11:
					updateColor(player, ChatColor.DARK_PURPLE, "purple");
					break;
				case 12:
					updateColor(player, ChatColor.LIGHT_PURPLE, "pink");
					break;

				case 14:
					updateColor(player, ChatColor.WHITE, "white");
					break;
				case 15:
					updateColor(player, ChatColor.GRAY, "gray");
					break;
				case 16:
					updateColor(player, ChatColor.DARK_GRAY, "dark-gray");
					break;
			}
		}
	}

	private void updateColor(Player player, ChatColor color, String desc) {
		this.playerColor.put(player.getUniqueId(), color);
		player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 20.0F);
		player.sendMessage("§5[§a§oColor§2Me§5]§f Your chat will now be in " + desc + "!");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		if (this.playerColor.containsKey(uuid)) {
			event.setMessage(this.playerColor.get(uuid) + event.getMessage());
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {

		// Remove stolen color menu items from the player's inventory
		ItemStack[] items = event.getPlayer().getInventory().getContents();
		for (int i = 0; i < items.length; i++) {
			if ((items[i] != null) && (colorMenu.contains(items[i]))) {
				event.getPlayer().getInventory().remove(items[i]);
			}
		}

		// Remove stolen items from their armor slots too
		items = event.getPlayer().getInventory().getArmorContents();
		for (int i = 0; i < items.length; i++) {
			if ((items[i] != null) && (colorMenu.contains(items[i]))) {
				event.getPlayer().getInventory().remove(items[i]);
			}
		}
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		// Remove stolen color menu items if they happen to fall on the ground
		ItemStack item = event.getEntity().getItemStack();
		if (item.getType() != Material.WOOL) {
			return;
		}

		if ((item != null) && (colorMenu.contains(item))) {
			event.getEntity().remove();
		}
	}
}
