package fr.ethilvan.adminMode.inventory;

import org.bukkit.inventory.ItemStack;


public class InventorySnapshot {

	private ItemStack[] mainInventory;
	private ItemStack[] armorInventory;
	private ItemStack[] extraInventory;


	public InventorySnapshot(
			ItemStack[] mainInventory,
			ItemStack[] armorInventory,
			ItemStack[] extraInventory
	) {
		this.mainInventory = mainInventory;
		this.armorInventory = armorInventory;
		this.extraInventory = extraInventory;
	}


	public ItemStack[] getMainInventory() {
		return this.mainInventory;
	}

	public void setMainInventory(ItemStack[] mainInventory) {
		this.mainInventory = mainInventory;
	}


	public ItemStack[] getArmorInventory() {
		return this.armorInventory;
	}

	public void setArmorInventory(ItemStack[] armorInventory) {
		this.armorInventory = armorInventory;
	}


	public ItemStack[] getExtraInventory() {
		return this.extraInventory;
	}

	public void setExtraInventory(ItemStack[] extraInventory) {
		this.extraInventory = extraInventory;
	}
}
