package com.github.commoble.mondobook.client;

import com.github.commoble.mondobook.client.assets.ImageData;
import com.github.commoble.mondobook.client.assets.RawBook;
import com.github.commoble.mondobook.data.SimpleJsonDataManager;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

public class AssetManagers
{
	public static final SimpleJsonDataManager<RawBook> BOOK_DATA = new SimpleJsonDataManager<>("mondobooks", RawBook.class);
	public static final SimpleJsonDataManager<ImageData> IMAGE_DATA = new SimpleJsonDataManager<>("mondobookimages", ImageData.class);
	
	// call from mod constructor if on client
	public static void onClientInit()
	{
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if (manager instanceof IReloadableResourceManager)
		{
			IReloadableResourceManager reloader = (IReloadableResourceManager)manager;
			reloader.addReloadListener(BOOK_DATA);
			reloader.addReloadListener(IMAGE_DATA);
		}
	}
}
