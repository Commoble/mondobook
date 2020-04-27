package com.github.commoble.mondobook.client.api.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class ColumnDrawable implements Drawable
{
	private final List<Drawable> drawables;
	private final int height;	// height is the sum of the heights of its child drawables
	private final int width;	// width is the widest width among its child drawables
	private final List<DrawableWithOffset> children;
	
	public ColumnDrawable(List<Drawable> drawables)
	{
		this.drawables = drawables;
		this.height = this.drawables.stream()
			.map(Drawable::getHeight)
			.reduce(0, (a,b) -> a + b);
		this.width = this.drawables.stream()
			.map(Drawable::getWidth)
			.reduce(0, Math::max);

		this.children = new ArrayList<>();
		int nextY = 0;
		for (Drawable drawable : drawables)
		{
			int childHeight = drawable.getHeight();
			this.children.add(new DrawableWithOffset(0, nextY, this.width, drawable));
			nextY += childHeight;
		}
	}

	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int nextY = startY;
		for (Drawable drawable : this.drawables)
		{
			drawable.render(renderer, startX, nextY, maxWidth, mouseX, mouseY);
			nextY += drawable.getHeight();
		}
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
