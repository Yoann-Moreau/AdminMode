package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;


public class PermissionManager {

	private final AdminMode adminMode;
	private final LuckPerms api;


	public PermissionManager(AdminMode adminMode) {
		this.adminMode = adminMode;
		api = LuckPermsProvider.get();
	}


	public LuckPerms getApi() {
		return api;
	}


	public void addGroupAndNotify(Player player, String groupName) {
		addGroupToPlayer(groupName, player).thenRun(() -> {
			Bukkit.getScheduler().runTask(adminMode.getPlugin(), () -> {
				player.sendRichMessage("<green>You are now in Admin Mode.");
			});
		});
	}

	public void removeGroupAndNotify(Player player, String groupName) {
		removeGroupFromPlayer(groupName, player).thenRun(() -> {
			Bukkit.getScheduler().runTask(adminMode.getPlugin(), () -> {
				player.sendRichMessage("<gray>You are no longer in Admin Mode.");
			});
		});
	}


	public CompletableFuture<Void> addGroupToPlayer(String groupName, Player player) {
		return getApi().getUserManager().modifyUser(player.getUniqueId(), user -> {
			Group group = getApi().getGroupManager().getGroup(groupName);

			if (group == null) {
				return;
			}

			user.data().add(InheritanceNode.builder(group).build());
		});
	}

	public CompletableFuture<Void> removeGroupFromPlayer(String groupName, Player player) {
		return getApi().getUserManager().modifyUser(player.getUniqueId(), user -> {
			Group group = getApi().getGroupManager().getGroup(groupName);

			if (group == null) {
				return;
			}

			user.data().remove(InheritanceNode.builder(group).build());
		});
	}
}
