package com.github.commoble.mondobook.client.api;

import java.util.List;

/** Interface of things that we can render on a book page **/
public interface Drawable
{
	/** Render this starting from the given top-left coordinates **/
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth);
	
	/** The height in pixels that this thing takes up when rendered **/
	public int getHeight();
	
	/** The width in pixels that this thing takes up when rendered **/
	public int getWidth();
	
	/**
	 * If this returns false, the drawable cannot be added to the given list.
	 * The page builder checks this first before comparing the height of this drawable to the height of the list.
	 * If the given list is empty, this will not be called (to avoid infinite loops)
	 */
	default public boolean canAddToList(List<Drawable> drawables)
	{
		return true;
	}
	
	/** Drawable that draws nothing **/
	public static final Drawable NONE = new Drawable()
	{
		@Override
		public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
		{
		}

		@Override
		public int getHeight()
		{
			return 0;
		}

		@Override
		public int getWidth()
		{
			return 0;
		}

	};
}
