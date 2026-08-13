package fr.ethilvan.adminMode.listeners;

import fr.ethilvan.adminMode.AdminMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mvplugins.multiverse.inventories.event.WorldChangeShareHandlingEvent;


public class MultiverseInventoriesListeners implements Listener {

	private final AdminMode adminMode;


	public MultiverseInventoriesListeners(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	@EventHandler
	public void onWorldChangeShareHandling(WorldChangeShareHandlingEvent event) {
		Player player = event.getPlayer();
		Boolean playerStatus = adminMode.getPlayerStatuses().get(player.getUniqueId());

		if (playerStatus != null && playerStatus) {
			event.setCancelled(true);
		}
	}
}
