package com.github.commoble.mondobook.client;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;

public class BakedPage
{
	private final List<Drawable> drawables;
	
	public BakedPage(List<Drawable> drawables)
	{
		this.drawables = drawables;
	}
	
	public List<Drawable> getDrawables()
	{
		return this.drawables;
	}
	
//	public static List<Drawable> getDrawables(RawElement raw, DrawableRenderer renderer, int textWidth)
//	{
//		return AssetFactories.ELEMENTS.apply(raw).getAsDrawables(renderer, textWidth);
//	}

}
