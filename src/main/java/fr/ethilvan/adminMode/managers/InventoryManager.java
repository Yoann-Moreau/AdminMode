package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySection;
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
		InventorySnapshot inventorySnapshot = getPlayerInventory(player);
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


	public InventorySnapshot getPlayerInventory(Player player) {
		return new InventorySnapshot(
				player.getInventory().getContents().clone(),
				player.getInventory().getArmorContents().clone(),
				player.getInventory().getExtraContents().clone()
		);
	}


	public void setPlayerInventoryFromSnapshot(Player player, InventorySnapshot inventorySnapshot) {
		player.getInventory().setContents(inventorySnapshot.getMainInventory());
		player.getInventory().setArmorContents(inventorySnapshot.getArmorInventory());
		player.getInventory().setExtraContents(inventorySnapshot.getExtraInventory());
	}
}
