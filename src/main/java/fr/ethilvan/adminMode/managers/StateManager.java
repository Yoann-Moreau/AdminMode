package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class StateManager {

	private final AdminMode adminMode;


	public StateManager(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	public void activateAdminMode(Player player) {
		InventorySnapshot inventorySnapshot = adminMode.getInventoryManager().getPlayerInventory(player);
		adminMode.getPlayerInventories().put(player.getUniqueId(), inventorySnapshot);
		Location location = player.getLocation().clone();
		adminMode.getPlayerLocations().put(player.getUniqueId(), location);
		adminMode.getPlayerGameModes().put(player.getUniqueId(), player.getGameMode());
		adminMode.getAdminModeStatuses().put(player.getUniqueId(), true);
		player.setGameMode(GameMode.CREATIVE);
		adminMode.getInventoryManager().setPlayerInventoryFromSnapshot(player, adminMode.getDefaultInventory());
		player.sendRichMessage("<green>You are now in Admin Mode.");
	}


	public void deactivateAdminMode(Player player) {
		adminMode.getAdminModeStatuses().put(player.getUniqueId(), false);
		player.setGameMode(adminMode.getPlayerGameModes().get(player.getUniqueId()));
		InventorySnapshot inventorySnapshot = adminMode.getPlayerInventories().get(player.getUniqueId());
		adminMode.getInventoryManager().setPlayerInventoryFromSnapshot(player, inventorySnapshot);
		player.teleportAsync(adminMode.getPlayerLocations().get(player.getUniqueId()));
		player.sendRichMessage("<gray>You are no longer in Admin Mode.");
	}
}
