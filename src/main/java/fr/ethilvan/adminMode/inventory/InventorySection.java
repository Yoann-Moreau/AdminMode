package fr.ethilvan.adminMode.inventory;

public enum InventorySection {

	MAIN("main-inventory"),
	ARMOR("armor-inventory"),
	EXTRA("extra-inventory");


	private final String section;


	InventorySection(String section) {
		this.section = section;
	}


	public String getSection() {
		return section;
	}
}
