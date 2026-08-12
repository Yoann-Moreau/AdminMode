package fr.ethilvan.adminMode.command;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.command.subcommands.SaveDefaultInventory;
import fr.ethilvan.adminMode.command.subcommands.SaveInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class AdminModeCommand implements TabExecutor {

	private final AdminMode adminMode;

	private final String name = "adminmode";
	private final String syntax = "/adminmode";
	private final String description = "Toggle Admin Mode";
	private final String permission = "adminmode.adminmode";

	private final ArrayList<Subcommand> subcommands = new ArrayList<>();


	public AdminModeCommand(AdminMode adminMode) {
		this.adminMode = adminMode;

		subcommands.add(new SaveDefaultInventory(adminMode));
		subcommands.add(new SaveInventory(adminMode));
	}


	@Override
	public boolean onCommand(
			@NotNull CommandSender sender,
			@NotNull Command command,
			@NotNull String label,
			@NotNull String @NotNull [] args
	) {

		if (!(sender instanceof Player player)) {
			sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
			return false;
		}

		if (args.length == 0 && !player.hasPermission(permission)) {
			player.sendRichMessage("<red>You do not have permission to use this command.");
			return false;
		}

		if (args.length == 0) {
			perform(player);
			return true;
		}

		for (Subcommand subcommand : subcommands) {
			if (args[0].equalsIgnoreCase(subcommand.getName())) {
				if (!player.hasPermission(subcommand.getPermission())) {
					player.sendMessage("<red>You do not have permission to use this command.");
					return false;
				}
				return subcommand.perform(player, args);
			}
		}

		player.sendMessage("----------------Available commands----------------");
		player.sendRichMessage("<gold>" + syntax + "gray: <white>" + description);
		for (Subcommand subcommand : subcommands) {
			if (!player.hasPermission(subcommand.getPermission())) continue;
			player.sendRichMessage("<gold>" + subcommand.getSyntax() + "gray: <white>" + subcommand.getDescription());
		}
		player.sendMessage("--------------------------------------------------");

		return false;
	}


	@Override
	public @Nullable List<String> onTabComplete(
			@NotNull CommandSender sender,
			@NotNull Command command,
			@NotNull String label,
			@NotNull String @NotNull [] args
	) {

		if (args.length == 1) {
			List<String> subcommandNames = new ArrayList<>();
			for (Subcommand subcommand : subcommands) {
				subcommandNames.add(subcommand.getName());
			}
			return subcommandNames;
		}

		if (args.length > 1) {
			for (Subcommand subcommand : subcommands) {
				if (args[0].equalsIgnoreCase(subcommand.getName())) {
					return subcommand.getAutoCompleteChoices(args);
				}
			}
		}

		return List.of();
	}


	private void perform(Player player) {
		if (!adminMode.getAdminModeStatuses().containsKey(player.getUniqueId())) {
			adminMode.getStateManager().activateAdminMode(player);
			return;
		}
		boolean isInAdminMode = adminMode.getAdminModeStatuses().get(player.getUniqueId());
		if (isInAdminMode) {
			adminMode.getStateManager().deactivateAdminMode(player);
			return;
		}
		adminMode.getStateManager().activateAdminMode(player);
	}
}
