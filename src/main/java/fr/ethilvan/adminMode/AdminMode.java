package fr.ethilvan.adminMode;

import fr.ethilvan.adminMode.managers.FileManager;
import fr.ethilvan.adminMode.managers.InventoryManager;
import fr.ethilvan.adminMode.managers.StateManager;
import fr.ethilvan.adminMode.config.ConfigFile;
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

	private final HashMap<UUID, Boolean> playerStatuses = new HashMap<>();
	private final HashMap<UUID, InventorySnapshot> playerAdminModeInventories = new HashMap<>();
	private final HashMap<UUID, InventorySnapshot> playerInventories = new HashMap<>();
	private final HashMap<UUID, Location> playerLocations = new HashMap<>();
	private final HashMap<UUID, GameMode> playerGameModes = new HashMap<>();

	private InventorySnapshot defaultInventory;

	private final FileManager fileManager;
	private final StateManager stateManager;
	private final InventoryManager inventoryManager;


	public AdminMode(AdminModePlugin plugin) {
		this.plugin = plugin;

		fileManager = new FileManager(this);
		stateManager = new StateManager(this);
		inventoryManager = new InventoryManager(this);

		defaultInventory = new InventorySnapshot();

		loadStates();
		loadInventories();
	}


	public AdminModePlugin getPlugin() {
		return this.plugin;
	}


	public HashMap<UUID, Boolean> getPlayerStatuses() {
		return this.playerStatuses;
	}


	public HashMap<UUID, InventorySnapshot> getPlayerAdminModeInventories() {
		return this.playerAdminModeInventories;
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


	public FileManager getFileManager() {
		return this.fileManager;
	}


	public StateManager getStateManager() {
		return this.stateManager;
	}


	public InventoryManager getInventoryManager() {
		return this.inventoryManager;
	}


	private void loadStates() {
		String filePath = ConfigFile.PLAYER_STATUS.getFilePath();
		File playerDirectory = new File(plugin.getDataFolder(), filePath.substring(0, filePath.lastIndexOf("/")));
		List<String> playerFiles = getFileManager().getFilesFromDirectory(playerDirectory);
		plugin.getLogger().info(playerFiles.size() + " player files have been loaded.");

		for (String playerFileName : playerFiles) {
			UUID uuid = UUID.fromString(playerFileName.substring(0, playerFileName.indexOf(".yml")));
			ConfigFile playerStatusFile = ConfigFile.PLAYER_STATUS;
			ConfigFile playerInventoryFile = ConfigFile.PLAYER_INVENTORY;
			ConfigFile playerLocationFile = ConfigFile.PLAYER_LOCATION;
			ConfigFile playerGamemodeFile = ConfigFile.PLAYER_GAMEMODE;
			// Get player file
			filePath += uuid + ".yml";
			File file = new File(plugin.getDataFolder(), filePath);
			if (!file.exists()) {
				continue;
			}
			// Get player states values from config
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			Boolean status = config.getBoolean(playerStatusFile.getConfigurationSection());
			List<?> baseInventory = config.getList(playerInventoryFile.getConfigurationSection());
			Location location = config.getLocation(playerLocationFile.getConfigurationSection());
			String gamemode = config.getString(playerGamemodeFile.getConfigurationSection());
			if (baseInventory == null || location == null || gamemode == null) {
				continue;
			}
			// Set in memory player states
			ItemStack[] mainItems = baseInventory.toArray(new ItemStack[0]);
			getPlayerStatuses().put(uuid, status);
			getPlayerInventories().put(uuid, new InventorySnapshot(mainItems));
			getPlayerLocations().put(uuid, location);
			getPlayerGameModes().put(uuid, GameMode.valueOf(gamemode));
		}
	}


	private void loadInventories() {
		loadDefaultInventory();
	}


	private void loadDefaultInventory() {
		File file = new File(plugin.getDataFolder(), ConfigFile.DEFAULT_INVENTORY.getFilePath() + ".yml");
		if (!file.exists()) {
			setDefaultInventory(new InventorySnapshot());
			return;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		String section = ConfigFile.DEFAULT_INVENTORY.getConfigurationSection();
		List<?> loadedDefaultInventory = config.getList(section);
		if (loadedDefaultInventory == null) {
			return;
		}
		ItemStack[] mainItems = loadedDefaultInventory.toArray(new ItemStack[0]);
		getDefaultInventory().setMainInventory(mainItems);
	}
}
