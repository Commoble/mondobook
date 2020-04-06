package com.github.commoble.mondobook.client;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.book.RawBook;
import com.github.commoble.mondobook.client.elements.ImageData;
import com.github.commoble.mondobook.client.styles.RawStyle;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

public class AssetManagers
{
	public static final SimpleJsonDataManager<RawBook> BOOK_DATA = new SimpleJsonDataManager<>("mondobooks", RawBook.class);
	public static final SimpleJsonDataManager<ImageData> IMAGE_DATA = new SimpleJsonDataManager<>("mondobookimages", ImageData.class);
	public static final SimpleJsonDataManager<RawStyle> STYLE_DATA = new SimpleJsonDataManager<>("mondobookstyles", RawStyle.class);
	
	// to be called from the MondobookMod instance constructor if on the client
	public static void onClientInit()
	{
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if (manager instanceof IReloadableResourceManager)
		{
			IReloadableResourceManager reloader = (IReloadableResourceManager)manager;
			reloader.addReloadListener(BOOK_DATA);
			reloader.addReloadListener(IMAGE_DATA);
			reloader.addReloadListener(STYLE_DATA);
		}
	}
}
