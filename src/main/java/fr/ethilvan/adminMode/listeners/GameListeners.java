package fr.ethilvan.adminMode.listeners;

import fr.ethilvan.adminMode.AdminMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;


public class GameListeners implements Listener {

	private final AdminMode adminMode;


	public GameListeners(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		if (adminMode.getPlayerStatuses().get(uuid) == null || adminMode.getPlayerStatuses().get(uuid) != true) {
			return;
		}

		adminMode.getStateManager().deactivateAdminMode(player);
	}
}
