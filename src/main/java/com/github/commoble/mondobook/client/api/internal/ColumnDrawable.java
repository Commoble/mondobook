package com.github.commoble.mondobook.client.api.internal;

import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class ColumnDrawable implements Drawable
{
	private final List<Drawable> drawables;
	private final int height;	// height is the sum of the heights of its child drawables
	private final int width;	// width is the widest width among its child drawables
	
	public ColumnDrawable(List<Drawable> drawables)
	{
		this.drawables = drawables;
		this.height = this.drawables.stream()
			.map(Drawable::getHeight)
			.reduce(0, (a,b) -> a + b);
		this.width = this.drawables.stream()
			.map(Drawable::getWidth)
			.reduce(0, Math::max);
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
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int nextY = startY;
		for (Drawable drawable : this.drawables)
		{
			drawable.renderTooltip(renderer, startX, nextY, maxWidth, mouseX, mouseY);
			nextY += drawable.getHeight();
		}
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.empty();
	}

}
