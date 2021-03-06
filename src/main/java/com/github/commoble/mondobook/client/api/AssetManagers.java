package com.github.commoble.mondobook.client.api;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.api.internal.ImageData;
import com.github.commoble.mondobook.client.api.internal.RawBook;
import com.github.commoble.mondobook.client.api.internal.RawFormat;
import com.github.commoble.mondobook.client.api.internal.RawStyle;
import com.github.commoble.mondobook.client.api.internal.TabDataManager;

public class AssetManagers
{
	public static final SimpleJsonDataManager<RawBook> BOOK_DATA = new LocalizableJsonDataManager<>("mondobooks", RawBook.class);
	public static final SimpleJsonDataManager<ImageData> IMAGE_DATA = new SimpleJsonDataManager<>("mondobookimages", ImageData.class);
	public static final SimpleJsonDataManager<RawStyle> STYLE_DATA = new SimpleJsonDataManager<>("mondobookstyles", RawStyle.class);
	public static final TabDataManager TABS = new TabDataManager();
	public static final SimpleJsonDataManager<RawFormat> BOOK_FORMATS = new SimpleJsonDataManager<>("mondobookformats", RawFormat.class);
}
