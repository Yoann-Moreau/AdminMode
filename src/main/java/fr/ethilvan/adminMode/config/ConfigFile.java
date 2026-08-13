package fr.ethilvan.adminMode.config;

public enum ConfigFile {

	DEFAULT_INVENTORY("default-inventory", "default-inventory"),
	ADMIN_MODE_INVENTORY("players/", "admin-mode-inventory"),
	PLAYER_INVENTORY("players/", "base-inventory"),
	PLAYER_STATUS("players/", "status"),
	PLAYER_LOCATION("players/", "location"),
	PLAYER_GAMEMODE("players/", "gamemode");


	private final String filePath;
	private final String configurationSection;


	ConfigFile(String filePath, String configurationSection) {
		this.filePath = filePath;
		this.configurationSection = configurationSection;
	}


	public String getFilePath() {
		return filePath;
	}


	public String getConfigurationSection() {
		return configurationSection;
	}
}
