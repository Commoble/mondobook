package com.github.commoble.mondobook.client;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

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
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		this.children.forEach(child -> this.renderChild(child, renderer, startX, startY));
	}
	
	private void renderChild(Drawable child, DrawableRenderer renderer, int thisX, int thisY)
	{
		child.render(renderer, thisX, thisY);
	}
	
	@Override
	public int getHeight()
	{
		return this.height;
	}
}
