package com.github.commoble.mondobook.client;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.internal.BookStyle;

public class BakedPage implements Drawable
{
	private final int width;
	private final int height;
	private final List<Drawable> children;
	
	public BakedPage(int width, int height, List<Drawable> children)
	{
		this.width = width;
		this.height = height;
		this.children = children;
	}
	
	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		this.children.forEach(child -> child.render(renderer, startX, startY, maxWidth, mouseX, mouseY));
	}
	
	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public int getWidth()
	{
		return this.height;
	}

	@Override
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		this.children.forEach(child -> child.renderTooltip(renderer, startX, startY, maxWidth, mouseX, mouseY));
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.empty();
	}
}
