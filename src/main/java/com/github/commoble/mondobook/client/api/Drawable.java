package com.github.commoble.mondobook.client.api;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.DrawableWithOffset;
import com.github.commoble.mondobook.client.api.internal.ImageData;
import com.github.commoble.mondobook.util.MathUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

/** Interface of things that we can render on a book page **/
public interface Drawable
{
	default void render(DrawableRenderer renderer, int startX, int startY, int containerWidth, int mouseX, int mouseY)
	{
		boolean doHover = MathUtil.isWithin(mouseX, mouseY, startX, startY, this.getWidth(), this.getHeight());
		
		this.renderBackground(renderer, startX, startY, containerWidth, mouseX, mouseY);
		if (doHover)
		{
			this.renderHoverBackground(renderer, startX, startY, containerWidth, mouseX, mouseY);
		}
		this.renderSelf(renderer, startX, startY, containerWidth, mouseX, mouseY);
		if (doHover)
		{
			this.renderHoverForeground(renderer, startX, startY, containerWidth, mouseX, mouseY);
		}
		
		this.getChildren().forEach(child -> child.render(renderer, startX, startY, mouseX, mouseY));
	}
	
	/**
	 * Returns TRUE if this drawable or any of its children rendered a tooltip, false otherwise.
	 * This is only called if the mouse position is already verified to be within the space of this drawable.
	 **/
	default boolean renderTooltips(DrawableRenderer renderer, int startX, int startY, int mouseX, int mouseY)
	{
		// the idea here is that we don't render a tooltip for this drawable if any children of this drawable render a tooltip
		
		boolean renderedChildTooltip = this.getChildren().stream()
			.map(child -> child.renderTooltip(renderer, startX, startY, mouseX, mouseY))
			.reduce(false, (a,b) -> a||b);
		
		// if child tooltip was rendered, own tooltip will not be evaluated or rendered
		return renderedChildTooltip || this.renderOwnTooltip(renderer, mouseX, mouseY);
	}
	
	/**
	 * Return TRUE if a tooltip was rendered, FALSE otherwise.
	 * The mouse position has been verified to be within the bounds of this drawable.
	 * @return
	 */
	default boolean renderOwnTooltip(DrawableRenderer renderer, int mouseX, int mouseY)
	{
		return false;
	}
	
	default boolean handleClicks(DrawableRenderer renderer, int startX, int startY, int mouseX, int mouseY)
	{
		boolean handledChildClick = this.getChildren().stream()
			.map(child -> child.onClick(renderer, startX, startY, mouseX, mouseY))
			.reduce(false, (a,b) -> a||b);
		
		return handledChildClick || this.handleOwnClick(renderer, mouseX, mouseY);
	}
	
	default boolean handleOwnClick(DrawableRenderer renderer, int mouseX, int mouseY)
	{
		Optional<ResourceLocation> bookID = this.getStyle().flatMap(BookStyle::getSubBook);
		bookID.ifPresent(renderer::newBook);
		return bookID.isPresent();
	}
	
	/**
	 * If this is overridden to return a present style, will fill in the background and hover colors.
	 **/ 
	public Optional<BookStyle> getStyle();
	
	/**
	 * @return Returns a list of this drawable's children, with offsets relative to this drawable's position.
	 */
	@Nonnull
	public List<DrawableWithOffset> getChildren();
	
	default void renderBackground(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		this.getStyle().ifPresent(style -> {
			this.fill(startX, startY, style.getBackgroundColor());
			style.getBackgroundImage().ifPresent(image -> ImageData.blitImage(image, renderer.getScreen(), startX, startY, this.getWidth(), this.getHeight()));
		});
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
	
	/**
	 * If this returns false, the drawable cannot be added to the given list.
	 * The page builder checks this first before comparing the height of this drawable to the height of the list.
	 * If the given list is empty, this will not be called (to avoid infinite loops)
	 */
	default public boolean canAddToList(List<DrawableWithOffset> drawables)
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
		public boolean renderOwnTooltip(DrawableRenderer renderer, int mouseX, int mouseY)
		{
			return false;
		}

		@Override
		public Optional<BookStyle> getStyle()
		{
			return Optional.empty();
		}

		@Override
		public List<DrawableWithOffset> getChildren()
		{
			return ImmutableList.of();
		}

	};
}
