package fr.ethilvan.adminMode.listeners;

import fr.ethilvan.adminMode.AdminMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
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


	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity causingEntity = event.getDamageSource().getCausingEntity();
		Entity deadEntity = event.getEntity();
		if (!(causingEntity instanceof Player player) || deadEntity instanceof Player) return;
		if (adminMode.getPlayerStatuses().get(player.getUniqueId()) == null ||
				adminMode.getPlayerStatuses().get(player.getUniqueId()) != true) {
			return;
		}

		event.getDrops().clear();
		event.setDroppedExp(0);
	}
}
