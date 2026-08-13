package fr.ethilvan.adminMode.managers;

import fr.ethilvan.adminMode.AdminMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

	private final AdminMode adminMode;


	public FileManager(AdminMode adminMode) {
		this.adminMode = adminMode;
	}


	public List<String> getFilesFromDirectory(final File folder) {
		if (!folder.exists()) {
			return List.of();
		}
		List<String> fileNames = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				continue;
			}
			fileNames.add(fileEntry.getName());
		}
		return fileNames;
	}
}
