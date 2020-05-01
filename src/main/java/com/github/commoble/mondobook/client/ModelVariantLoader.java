package com.github.commoble.mondobook.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ModelVariantLoader
{
	private static final String FOLDER = "models/item/mondobooks";
	private static final int JSON_EXTENSION_LENGTH = ".json".length();

	public static void registerSpecialModels()
	{
		int folderNameLength = FOLDER.length() + 1;
		
		IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

		for (ResourceLocation resourcelocation : resourceManager.getAllResourceLocations(FOLDER, ModelVariantLoader::isJsonFile))
		{
			String s = resourcelocation.getPath();
			ResourceLocation id = new ResourceLocation(resourcelocation.getNamespace(), s.substring(folderNameLength, s.length() - JSON_EXTENSION_LENGTH));

			ModelLoader.addSpecialModel(new ModelResourceLocation(new ResourceLocation(id.getNamespace(), "mondobooks/" + id.getPath()), "inventory"));
		}
	}

	public static final boolean isJsonFile(String input)
	{
		return input.endsWith(".json");
	}
}
