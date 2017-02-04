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
	private HashMap<UUID, ChatColor> playerColors = new HashMap<>();

	public UpdateColor(Inventory colorMenu) {
		this.colorMenu = colorMenu;
	}

	/**
	 * Handles inventory clicks for the ColorMenu and updates player chat colors
	 * @param event
	 */
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
					updateColor(player, ChatColor.BLUE, "blue");
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

	/**
	 * Updates the target player's chat color
	 * @param player the target player
	 * @param color the {@code ChatColor} to use
	 * @param desc the color description, to let the player know what their new chat color will be
	 */
	private void updateColor(Player player, ChatColor color, String desc) {
		playerColors.put(player.getUniqueId(), color);
		player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 20.0F);
		player.sendMessage("§7[§2ColorMe§7]§a Your chat color has been set to " + desc + ".");
	}

	/**
	 * Prefixes chat messages with the appropriate color for all players currently using this plugin
	 * @param event
	 */
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		if (this.playerColors.containsKey(uuid)) {
			event.setMessage(this.playerColors.get(uuid) + event.getMessage());
		}
	}

	/**
	 * Removes stolen Color Menu items from player inventories and armor slots
	 * @param event
	 */
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		ItemStack[] items = event.getPlayer().getInventory().getContents();
		for (int i = 0; i < items.length; i++) {
			if ((items[i] != null) && (colorMenu.contains(items[i]))) {
				event.getPlayer().getInventory().remove(items[i]);
			}
		}

		items = event.getPlayer().getInventory().getArmorContents();
		for (int i = 0; i < items.length; i++) {
			if ((items[i] != null) && (colorMenu.contains(items[i]))) {
				event.getPlayer().getInventory().remove(items[i]);
			}
		}
	}

	/**
	 * Removes stolen Color Menu items if they fall on the ground
	 * @param event
	 */
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		ItemStack item = event.getEntity().getItemStack();
		if (item.getType() != Material.WOOL) {
			return;
		}

		if ((item != null) && (colorMenu.contains(item))) {
			event.getEntity().remove();
		}
	}
}
