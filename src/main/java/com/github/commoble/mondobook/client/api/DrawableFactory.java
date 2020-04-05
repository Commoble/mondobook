package com.github.commoble.mondobook.client.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

@FunctionalInterface
public interface DrawableFactory
{
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth);
	
	public static final DrawableFactory NONE = new DrawableFactory()
	{

		@Override
		public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth)
		{
			return ImmutableList.of();
		}
	};
}
