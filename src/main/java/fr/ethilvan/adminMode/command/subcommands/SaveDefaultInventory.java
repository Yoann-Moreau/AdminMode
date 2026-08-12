package fr.ethilvan.adminMode.command.subcommands;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.command.Subcommand;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventoryManagement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SaveDefaultInventory extends Subcommand {

	public SaveDefaultInventory(AdminMode adminMode) {
		super(adminMode);
	}


	@Override
	public String getName() {
		return "save-default-inventory";
	}


	@Override
	public String getSyntax() {
		return "/adminmode save-default-inventory";
	}


	@Override
	public String getDescription() {
		return "Saves your current inventory as the default inventory for all Admin Mode users.";
	}


	@Override
	public String getPermission() {
		return "adminmode.savedefaultinventory";
	}


	@Override
	public boolean perform(Player player, @NotNull String[] args) {
		if (!adminMode.getAdminModeStatuses().containsKey(player.getUniqueId()) ||
				adminMode.getAdminModeStatuses().get(player.getUniqueId()) == false) {
			player.sendRichMessage("<red>You must be in Admin Mode to perform this command.");
			return true;
		}
		adminMode.getDefaultInventory().setContents(player.getInventory().getContents());
		InventoryManagement.saveInventoryToConfig(adminMode, ConfigFile.DEFAULT_INVENTORY, player);
		player.sendRichMessage("<green>Default inventory saved successfully.");
		return true;
	}
}
