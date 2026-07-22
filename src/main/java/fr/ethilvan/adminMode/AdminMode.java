package fr.ethilvan.adminMode;

import java.util.HashMap;
import java.util.UUID;


public class AdminMode {

	private final AdminModePlugin plugin;

	private final HashMap<UUID, Boolean> adminModeStatuses = new HashMap<>();


	public AdminMode(AdminModePlugin plugin) {
		this.plugin = plugin;
	}


	public AdminModePlugin getPlugin() {
		return this.plugin;
	}


	public HashMap<UUID, Boolean> getAdminModeStatuses() {
		return this.adminModeStatuses;
	}
}
