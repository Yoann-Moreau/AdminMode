package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


public class InventoryManager {

	private final AdminMode adminMode;


	public InventoryManager(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	public void saveInventoryToConfig(
			ConfigFile configFile,
			Player player
	) {
		InventorySnapshot inventorySnapshot = getPlayerInventorySnapshot(player);
		UUID playerUUID = player.getUniqueId();
		String filePath = configFile.getFilePath();
		if (configFile != ConfigFile.DEFAULT_INVENTORY) {
			filePath += playerUUID;
		}
		filePath += ".yml";
		File file = new File(adminMode.getPlugin().getDataFolder(), filePath);
		YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
		String configSection = configFile.getConfigurationSection();
		setInventoryToFileConfig(fileConfiguration, configSection, inventorySnapshot);
		try {
			fileConfiguration.save(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public void setInventoryToFileConfig(
			YamlConfiguration fileConfiguration,
			String configSection,
			InventorySnapshot inventorySnapshot
	) {
		fileConfiguration.set(configSection, Arrays.asList(inventorySnapshot.getMainInventory()));
	}


	public InventorySnapshot getPlayerInventorySnapshot(Player player) {
		return new InventorySnapshot(player.getInventory().getContents().clone());
	}


	public void setPlayerInventoryFromSnapshot(Player player, InventorySnapshot inventorySnapshot) {
		player.getInventory().setContents(inventorySnapshot.getMainInventory());
	}
}
