package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


public class StateManager {

	private final AdminMode adminMode;


	public StateManager(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	public void savePlayerStateToConfig(Player player) {
		ConfigFile playerStatusFile = ConfigFile.PLAYER_STATUS;
		ConfigFile playerInventoryFile = ConfigFile.PLAYER_INVENTORY;
		ConfigFile playerLocationFile = ConfigFile.PLAYER_LOCATION;
		ConfigFile playerGamemodeFile = ConfigFile.PLAYER_GAMEMODE;
		String filePath = playerStatusFile.getFilePath();
		filePath += player.getUniqueId() + ".yml";
		File file = new File(adminMode.getPlugin().getDataFolder(), filePath);
		YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
		String statusSection = playerStatusFile.getConfigurationSection();
		String inventorySection = playerInventoryFile.getConfigurationSection();
		String locationSection = playerLocationFile.getConfigurationSection();
		String gamemodeSection = playerGamemodeFile.getConfigurationSection();
		InventorySnapshot inventorySnapshot = adminMode.getInventoryManager().getPlayerInventorySnapshot(player);
		adminMode.getInventoryManager().setInventoryToFileConfig(fileConfiguration, inventorySection, inventorySnapshot);
		fileConfiguration.set(statusSection, Boolean.TRUE);
		fileConfiguration.set(locationSection, player.getLocation());
		fileConfiguration.set(gamemodeSection, player.getGameMode().toString());
		try {
			fileConfiguration.save(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public void activateAdminMode(Player player) {
		adminMode.getStateManager().savePlayerStateToConfig(player);
		InventorySnapshot inventorySnapshot = adminMode.getInventoryManager().getPlayerInventorySnapshot(player);
		adminMode.getPlayerInventories().put(player.getUniqueId(), inventorySnapshot);
		Location location = player.getLocation().clone();
		adminMode.getPlayerLocations().put(player.getUniqueId(), location);
		adminMode.getPlayerGameModes().put(player.getUniqueId(), player.getGameMode());
		adminMode.getPlayerStatuses().put(player.getUniqueId(), true);
		player.setGameMode(GameMode.CREATIVE);
		if (adminMode.getPlayerAdminModeInventories().get(player.getUniqueId()) == null) {
			adminMode.getInventoryManager().setPlayerInventoryFromSnapshot(player, adminMode.getDefaultInventory());
		}
		else {
			adminMode.getInventoryManager().setPlayerInventoryFromSnapshot(
					player,
					adminMode.getPlayerAdminModeInventories().get(player.getUniqueId())
			);
		}
		// Add appropriate permission group to player
		if (adminMode.getPermissionManager() != null) {
			if (player.hasPermission("adminmode.admin")) {
				adminMode.getPermissionManager().addGroupAndNotify(player, "adminmode-admin");
			}
			else {
				adminMode.getPermissionManager().addGroupAndNotify(player, "adminmode-member");
			}
			return;
		}
		//
		player.sendRichMessage("<green>You are now in Admin Mode.");
	}


	public void deactivateAdminMode(Player player) {
		// Save player status to player config file
		ConfigFile playerStatusFile = ConfigFile.PLAYER_STATUS;
		String filePath = playerStatusFile.getFilePath();
		filePath += player.getUniqueId() + ".yml";
		File file = new File(adminMode.getPlugin().getDataFolder(), filePath);
		YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
		String statusSection = playerStatusFile.getConfigurationSection();
		fileConfiguration.set(statusSection, Boolean.FALSE);
		try {
			fileConfiguration.save(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		//
		player.teleport(adminMode.getPlayerLocations().get(player.getUniqueId()));
		// Remove appropriate permission group from player
		if (adminMode.getPermissionManager() != null) {
			if (player.hasPermission("adminmode.admin") && adminMode.getPermissionManager() != null) {
				adminMode.getPermissionManager().removeGroupAndNotify(player, "adminmode-admin");
			}
			else {
				adminMode.getPermissionManager().removeGroupAndNotify(player, "adminmode-member");
			}
		}
		//
		adminMode.getPlayerStatuses().put(player.getUniqueId(), false);
		player.setGameMode(adminMode.getPlayerGameModes().get(player.getUniqueId()));
		InventorySnapshot inventorySnapshot = adminMode.getPlayerInventories().get(player.getUniqueId());
		adminMode.getInventoryManager().setPlayerInventoryFromSnapshot(player, inventorySnapshot);
		//
		if (adminMode.getPermissionManager() != null) {
			return;
		}
		player.sendRichMessage("<gray>You are no longer in Admin Mode.");
	}
}
