package fr.ethilvan.adminMode.command.subcommands;

import fr.ethilvan.adminMode.AdminMode;
import fr.ethilvan.adminMode.command.Subcommand;
import fr.ethilvan.adminMode.config.ConfigFile;
import fr.ethilvan.adminMode.inventory.InventorySnapshot;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SaveInventory extends Subcommand {

	public SaveInventory(AdminMode adminMode) {
		super(adminMode);
	}


	@Override
	public String getName() {
		return "save-inventory";
	}


	@Override
	public String getSyntax() {
		return "/adminmode save-inventory";
	}


	@Override
	public String getDescription() {
		return "Saves your current inventory as your next Admin Mode inventory.";
	}


	@Override
	public String getPermission() {
		return "adminmode.use";
	}


	@Override
	public boolean perform(Player player, @NotNull String[] args) {
		if (!adminMode.getAdminModeStatuses().containsKey(player.getUniqueId()) ||
				adminMode.getAdminModeStatuses().get(player.getUniqueId()) == false) {
			player.sendRichMessage("<red>You must be in Admin Mode to perform this command.");
			return true;
		}
		adminMode.getInventoryManager().saveInventoryToConfig(ConfigFile.ADMIN_MODE_INVENTORY, player);
		InventorySnapshot inventorySnapshot = adminMode.getInventoryManager().getPlayerInventory(player);
		adminMode.getAdminModeInventories().put(player.getUniqueId(), inventorySnapshot);
		player.sendRichMessage("<green>Admin Mode inventory saved successfully.");
		return true;
	}
}
