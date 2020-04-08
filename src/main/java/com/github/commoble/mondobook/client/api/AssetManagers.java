package com.github.commoble.mondobook.client.api;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.book.RawBook;
import com.github.commoble.mondobook.client.book.RawStyle;
import com.github.commoble.mondobook.client.content.ImageData;

public class AssetManagers
{
	public static final SimpleJsonDataManager<RawBook> BOOK_DATA = new SimpleJsonDataManager<>("mondobooks", RawBook.class);
	public static final SimpleJsonDataManager<ImageData> IMAGE_DATA = new SimpleJsonDataManager<>("mondobookimages", ImageData.class);
	public static final SimpleJsonDataManager<RawStyle> STYLE_DATA = new SimpleJsonDataManager<>("mondobookstyles", RawStyle.class);
}
