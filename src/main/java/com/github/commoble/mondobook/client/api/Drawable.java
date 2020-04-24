package com.github.commoble.mondobook.client.api;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;

/** Interface of things that we can render on a book page **/
public interface Drawable
{
	default void render(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		boolean doHover = MathUtil.isWithin(mouseX, mouseY, startX, startY, this.getWidth(), this.getHeight());
		this.renderBackground(renderer, startX, startY, maxWidth, mouseX, mouseY);
		if (doHover)
		{
			this.renderHoverBackground(renderer, startX, startY, maxWidth, mouseX, mouseY);
		}
		this.renderSelf(renderer, startX, startY, maxWidth, mouseX, mouseY);
		if (doHover)
		{
			this.renderHoverForeground(renderer, startX, startY, maxWidth, mouseX, mouseY);
		}
	}
	
	/**
	 * If this is overridden to return a present style, will fill in the background and hover colors.
	 **/ 
	public Optional<BookStyle> getStyle();
	
	default void renderBackground(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		this.getStyle().ifPresent(style -> this.fill(startX, startY, style.getBackgroundColor()));
	}
	default void renderHoverBackground(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		this.getStyle().ifPresent(style -> this.fill(startX, startY, style.getBackgroundHoverColor()));
	}
	
	default void renderHoverForeground(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		RenderSystem.pushMatrix();
		RenderSystem.translated(0F, 0F, 500F);
		this.getStyle().ifPresent(style -> this.fill(startX, startY, style.getForegroundHoverColor()));
		RenderSystem.popMatrix();
	}
	
	default void fill(int startX, int startY, int color)
	{
		AbstractGui.fill(startX, startY, startX + this.getWidth(), startY + this.getHeight(), color);
	}
	
	/** Render this starting from the given top-left coordinates **/
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY);
	
	/** The height in pixels that this thing takes up when rendered **/
	public int getHeight();
	
	/** The width in pixels that this thing takes up when rendered **/
	public int getWidth();
	
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY);
	
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
		public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
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

		@Override
		public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
		{
		}

		@Override
		public Optional<BookStyle> getStyle()
		{
			return Optional.empty();
		}

	};
}
