package fr.ethilvan.adminMode;

import fr.ethilvan.adminMode.managers.InventoryManager;
import fr.ethilvan.adminMode.managers.StateManager;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySection;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
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
	private final HashMap<UUID, GameMode> playerGameModes = new HashMap<>();

	private InventorySnapshot defaultInventory;

	private final StateManager stateManager;
	private final InventoryManager inventoryManager;


	public AdminMode(AdminModePlugin plugin) {
		this.plugin = plugin;

		stateManager = new StateManager(this);
		inventoryManager = new InventoryManager(this);

		defaultInventory = new InventorySnapshot();

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


	public HashMap<UUID, GameMode> getPlayerGameModes() {
		return this.playerGameModes;
	}


	public InventorySnapshot getDefaultInventory() {
		return this.defaultInventory;
	}

	public void setDefaultInventory(InventorySnapshot defaultInventory) {
		this.defaultInventory = defaultInventory;
	}


	public StateManager getStateManager() {
		return this.stateManager;
	}


	public InventoryManager getInventoryManager() {
		return this.inventoryManager;
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
		String section = ConfigFile.DEFAULT_INVENTORY.getConfigurationSection();
		List<?> mainContent = config.getList(section + "." + InventorySection.MAIN.getSection());
		List<?> armorContent = config.getList(section + "." + InventorySection.ARMOR.getSection());
		List<?> extraContent = config.getList(section + "." + InventorySection.EXTRA.getSection());
		if (mainContent == null || armorContent == null || extraContent == null) {
			return;
		}
		ItemStack[] mainItems = mainContent.toArray(new ItemStack[0]);
		ItemStack[] armorItems = armorContent.toArray(new ItemStack[0]);
		ItemStack[] extraItems = extraContent.toArray(new ItemStack[0]);
		getDefaultInventory().setMainInventory(mainItems);
		getDefaultInventory().setArmorInventory(armorItems);
		getDefaultInventory().setExtraInventory(extraItems);
	}
}
