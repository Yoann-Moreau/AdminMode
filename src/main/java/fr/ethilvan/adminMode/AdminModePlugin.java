package fr.ethilvan.adminMode;

import fr.ethilvan.adminMode.command.AdminModeCommand;
import fr.ethilvan.adminMode.listeners.GameListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class AdminModePlugin extends JavaPlugin {

	private AdminMode adminMode;

	@Override
	public void onEnable() {
		this.adminMode = new AdminMode(this);
		registerCommands();

		Bukkit.getServer().getPluginManager().registerEvents(new GameListeners(adminMode), this);

		getLogger().info("Enabled.");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabled.");
	}


	private void registerCommands() {
		this.getCommand("adminmode").setExecutor(new AdminModeCommand(adminMode));
	}
}
