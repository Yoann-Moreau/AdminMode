package fr.ethilvan.adminMode.inventory;

import org.bukkit.inventory.ItemStack;


public class InventorySnapshot {

	private ItemStack[] mainInventory;


	public InventorySnapshot() {
		this.mainInventory = new ItemStack[0];
	}

	public InventorySnapshot(ItemStack[] mainInventory) {
		this.mainInventory = mainInventory;
	}


	public ItemStack[] getMainInventory() {
		return this.mainInventory;
	}

	public void setMainInventory(ItemStack[] mainInventory) {
		this.mainInventory = mainInventory;
	}
}
