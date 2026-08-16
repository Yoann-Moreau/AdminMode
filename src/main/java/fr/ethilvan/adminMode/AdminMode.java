package fr.ethilvan.adminMode;

import fr.ethilvan.adminMode.managers.FileManager;
import fr.ethilvan.adminMode.managers.InventoryManager;
import fr.ethilvan.adminMode.managers.PermissionManager;
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
	private final HashMap<UUID, Integer> playerLevels = new HashMap<>();
	private final HashMap<UUID, Float> playerExperiences = new HashMap<>();
	private final HashMap<UUID, Double> playerHealths = new HashMap<>();
	private final HashMap<UUID, Integer> playerHungers = new HashMap<>();
	private final HashMap<UUID, Float> playerSaturations = new HashMap<>();

	private InventorySnapshot defaultInventory;

	private final FileManager fileManager;
	private final StateManager stateManager;
	private final InventoryManager inventoryManager;
	private PermissionManager permissionManager = null;


	public AdminMode(AdminModePlugin plugin) {
		this.plugin = plugin;

		fileManager = new FileManager(this);
		stateManager = new StateManager(this);
		inventoryManager = new InventoryManager(this);
		if (getPlugin().getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
			permissionManager = new PermissionManager(this);
		}

		defaultInventory = new InventorySnapshot();

		loadDefaultInventory();
		loadPlayerStates();
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


	public HashMap<UUID, Integer> getPlayerLevels() {
		return this.playerLevels;
	}


	public HashMap<UUID, Float> getPlayerExperiences() {
		return this.playerExperiences;
	}


	public HashMap<UUID, Double> getPlayerHealths() {
		return this.playerHealths;
	}


	public HashMap<UUID, Integer> getPlayerHungers() {
		return this.playerHungers;
	}


	public HashMap<UUID, Float> getPlayerSaturations() {
		return this.playerSaturations;
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


	public PermissionManager getPermissionManager() {
		return this.permissionManager;
	}


	private void loadPlayerStates() {
		String filePath = ConfigFile.PLAYER_STATUS.getFilePath();
		File playerDirectory = new File(plugin.getDataFolder(), filePath.substring(0, filePath.lastIndexOf("/")));
		List<String> playerFiles = getFileManager().getFilesFromDirectory(playerDirectory);
		plugin.getLogger().info(playerFiles.size() + " player files have been loaded.");

		ConfigFile playerStatusFile = ConfigFile.PLAYER_STATUS;
		ConfigFile adminModeInventoryFile = ConfigFile.ADMIN_MODE_INVENTORY;
		ConfigFile playerInventoryFile = ConfigFile.PLAYER_INVENTORY;
		ConfigFile playerLocationFile = ConfigFile.PLAYER_LOCATION;
		ConfigFile playerGamemodeFile = ConfigFile.PLAYER_GAMEMODE;

		for (String playerFileName : playerFiles) {
			UUID uuid = UUID.fromString(playerFileName.substring(0, playerFileName.indexOf(".yml")));
			// Get player file
			filePath += uuid + ".yml";
			File file = new File(plugin.getDataFolder(), filePath);
			if (!file.exists()) {
				continue;
			}
			// Get player states values from config
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			Boolean status = config.getBoolean(playerStatusFile.getConfigurationSection());
			List<?> adminModeInventory = config.getList(adminModeInventoryFile.getConfigurationSection());
			List<?> baseInventory = config.getList(playerInventoryFile.getConfigurationSection());
			Location location = config.getLocation(playerLocationFile.getConfigurationSection());
			String gamemode = config.getString(playerGamemodeFile.getConfigurationSection());
			if (baseInventory == null || location == null || gamemode == null) {
				continue;
			}
			// Set in memory player states
			ItemStack[] mainItems = baseInventory.toArray(new ItemStack[0]);
			if (adminModeInventory != null) {
				ItemStack[] adminModeItems = adminModeInventory.toArray(new ItemStack[0]);
				getPlayerAdminModeInventories().put(uuid, new InventorySnapshot(adminModeItems));
			}
			getPlayerStatuses().put(uuid, status);
			getPlayerInventories().put(uuid, new InventorySnapshot(mainItems));
			getPlayerLocations().put(uuid, location);
			getPlayerGameModes().put(uuid, GameMode.valueOf(gamemode));
		}
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
