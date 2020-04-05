package com.github.commoble.mondobook.client.book.elements;

import java.util.List;

import com.github.commoble.mondobook.client.AssetManagers;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableFactory;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.assets.ImageData;
import com.github.commoble.mondobook.client.assets.RawElement;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;

public class ImageElement implements DrawableFactory
{
	private final ImageData image;
	
	public ImageElement(RawElement raw)
	{
		this.image = AssetManagers.IMAGE_DATA.getData(new ResourceLocation(raw.getData()));
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth)
	{
		return ImmutableList.of(this.image);
	}

}
