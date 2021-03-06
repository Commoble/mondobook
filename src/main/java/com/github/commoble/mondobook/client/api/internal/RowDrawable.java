package com.github.commoble.mondobook.client.api.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class RowDrawable implements Drawable
{
	private final List<Drawable> drawables;
	private final int height;	// height is equal to the tallest height among its child drawables
	private final int width;	// width is the sum of the widths of its child drawables
	private final List<DrawableWithOffset> children;
	
	public RowDrawable(List<Drawable> drawables)
	{
		this.drawables = drawables;
		this.height = this.drawables.stream()
			.map(Drawable::getHeight)
			.reduce(0, Math::max);
		this.width = this.drawables.stream()
			.map(Drawable::getWidth)
			.reduce(0, (a,b) -> a + b);
		
		this.children = new ArrayList<>();
		int nextX = 0;
		for (Drawable drawable : drawables)
		{
			int childWidth = drawable.getWidth();
			this.children.add(new DrawableWithOffset(nextX, 0, childWidth, drawable));
			nextX += childWidth;
		}
	}

	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
//		int nextX = startX;
//		int cellWidth = maxWidth / this.drawables.size();
//		for (Drawable drawable : this.drawables)
//		{
//			drawable.render(renderer, nextX, startY, cellWidth, mouseX, mouseY);
//			nextX += drawable.getWidth();
//		}
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
