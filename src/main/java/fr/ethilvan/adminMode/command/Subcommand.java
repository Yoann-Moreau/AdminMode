package fr.ethilvan.adminMode.command;

import fr.ethilvan.adminMode.AdminMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public abstract class Subcommand {

	protected final AdminMode adminMode;


	public Subcommand(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	public abstract String getName();

	public abstract String getSyntax();

	public abstract String getDescription();

	public abstract String getPermission();

	public abstract boolean perform(Player player, @NotNull String[] args);


	public ArrayList<String> getAutoCompleteChoices(String[] args) {
		return new ArrayList<>();
	}
}
