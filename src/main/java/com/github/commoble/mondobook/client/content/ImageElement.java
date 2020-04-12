package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.book.BookStyle;
import com.github.commoble.mondobook.client.book.RawElement;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

public class ImageElement extends Element
{
	private final ImageData image;
	
	public ImageElement(RawElement raw)
	{
		super(raw);
		this.image = AssetManagers.IMAGE_DATA.getData(new ResourceLocation(raw.getData()));
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int textWidth)
	{
		return ImmutableList.of(this.image);
	}

}
