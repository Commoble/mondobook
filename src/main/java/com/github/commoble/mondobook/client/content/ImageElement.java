package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.ImageData;

import net.minecraft.util.ResourceLocation;

public class ImageElement extends Element
{
	private final ImageData image;
	
	public ImageElement(ElementPrimer primer)
	{
		super(primer);
		this.image = AssetManagers.IMAGE_DATA.getData(new ResourceLocation(primer.getData()));
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth, boolean shrinkwrap)
	{
		return this.getStyle().getSingleStyledDrawable(new ImageData.ImageDrawable(this.image, this.getStyle()), containerWidth, shrinkwrap);
	}

}
