package fr.ethilvan.adminMode.tools;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.config.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

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
		PlayerInventory inventory = player.getInventory();
		UUID playerUUID = player.getUniqueId();
		String filePath = configFile.getFilePath();
		if (configFile != ConfigFile.DEFAULT_INVENTORY) {
			filePath += playerUUID;
		}
		filePath += ".yml";
		File file = new File(adminMode.getPlugin().getDataFolder(), filePath);
		YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
		String configSection = configFile.getConfigurationSection();
		fileConfiguration.set(configSection, Arrays.asList(inventory.getContents()));
		try {
			fileConfiguration.save(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
