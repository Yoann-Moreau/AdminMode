package fr.ethilvan.adminMode.inventory;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.config.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


public class InventoryManagement {

	public static void saveInventoryToConfig(
			AdminMode adminMode,
			ConfigFile configFile,
			Player player
	) {
		InventorySnapshot inventorySnapshot = InventoryManagement.getPlayerInventory(player);
		UUID playerUUID = player.getUniqueId();
		String filePath = configFile.getFilePath();
		if (configFile != ConfigFile.DEFAULT_INVENTORY) {
			filePath += playerUUID;
		}
		filePath += ".yml";
		File file = new File(adminMode.getPlugin().getDataFolder(), filePath);
		YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
		String configSection = configFile.getConfigurationSection();
		String mainSection = configSection + "." + InventorySection.MAIN.getSection();
		String armorSection = configSection + "." + InventorySection.ARMOR.getSection();
		String extraSection = configSection + "." + InventorySection.EXTRA.getSection();
		fileConfiguration.set(mainSection, Arrays.asList(inventorySnapshot.getMainInventory()));
		fileConfiguration.set(armorSection, Arrays.asList(inventorySnapshot.getArmorInventory()));
		fileConfiguration.set(extraSection, Arrays.asList(inventorySnapshot.getExtraInventory()));
		try {
			fileConfiguration.save(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public static InventorySnapshot getPlayerInventory(Player player) {
		return new InventorySnapshot(
				player.getInventory().getContents().clone(),
				player.getInventory().getArmorContents().clone(),
				player.getInventory().getExtraContents().clone()
		);
	}


	public static void setPlayerInventoryFromSnapshot(Player player, InventorySnapshot inventorySnapshot) {
		player.getInventory().setContents(inventorySnapshot.getMainInventory());
		player.getInventory().setArmorContents(inventorySnapshot.getArmorInventory());
		player.getInventory().setExtraContents(inventorySnapshot.getExtraInventory());
	}
}
