package fr.ethilvan.adminMode;

import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class AdminMode {

	private final AdminModePlugin plugin;

	private final HashMap<UUID, Boolean> adminModeStatuses = new HashMap<>();
	private final HashMap<UUID, InventorySnapshot> adminModeInventories = new HashMap<>();
	private final HashMap<UUID, InventorySnapshot> playerInventories = new HashMap<>();
	private final HashMap<UUID, Location> playerLocations = new HashMap<>();

	private final Inventory defaultInventory;


	public AdminMode(AdminModePlugin plugin) {
		this.plugin = plugin;

		defaultInventory = Bukkit.createInventory(null, InventoryType.PLAYER);

		loadInventories();
	}


	public AdminModePlugin getPlugin() {
		return this.plugin;
	}


	public HashMap<UUID, Boolean> getAdminModeStatuses() {
		return this.adminModeStatuses;
	}


	public HashMap<UUID, InventorySnapshot> getAdminModeInventories() {
		return this.adminModeInventories;
	}


	public HashMap<UUID, InventorySnapshot> getPlayerInventories() {
		return this.playerInventories;
	}


	public HashMap<UUID, Location> getPlayerLocations() {
		return this.playerLocations;
	}


	public Inventory getDefaultInventory() {
		return this.defaultInventory;
	}


	private void loadInventories() {
		loadDefaultInventory();
	}


	private void loadDefaultInventory() {
		File file = new File(plugin.getDataFolder(), ConfigFile.DEFAULT_INVENTORY.getFilePath() + ".yml");
		if (!file.exists()) {
			return;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<?> content = config.getList(ConfigFile.DEFAULT_INVENTORY.getConfigurationSection());
		if (content == null) {
			return;
		}
		ItemStack[] items = content.toArray(new ItemStack[0]);
		getDefaultInventory().setContents(items);
	}
}
