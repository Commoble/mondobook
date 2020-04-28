package com.github.commoble.mondobook.client;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.DrawableWithOffset;

public class BakedPage implements Drawable
{
	private final int width;
	private final int height;
	private final List<DrawableWithOffset> children;
	
	public BakedPage(int width, int height, List<DrawableWithOffset> children)
	{
		this.width = width;
		this.height = height;
		this.children = children;
	}
	
	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
//		this.children.forEach(child -> child.render(renderer, startX, startY, maxWidth, mouseX, mouseY));
	}
	
	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.empty();
	}

	@Override
	public List<DrawableWithOffset> getChildren()
	{
		return this.children;
	}
}
