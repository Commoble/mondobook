package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.ImageData;
import com.github.commoble.mondobook.client.api.internal.PaddedDrawable;
import com.github.commoble.mondobook.client.api.internal.RawElement;
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
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		return ImmutableList.of(PaddedDrawable.of(style.getMargins(), this.image));
	}

}
