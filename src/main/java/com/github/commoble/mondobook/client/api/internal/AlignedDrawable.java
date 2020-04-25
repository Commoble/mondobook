package com.github.commoble.mondobook.client.api.internal;

import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class AlignedDrawable implements Drawable
{
	private final Alignment alignment;
	private final Drawable drawable;
	
	public static AlignedDrawable of(Alignment alignment, Drawable drawable)
	{
		return new AlignedDrawable(alignment, drawable);
	}
	
	private AlignedDrawable(Alignment alignment, Drawable drawable)
	{
		this.drawable = drawable;
		this.alignment = alignment;
	}
	
	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int alignedStartX = this.alignment.getLeft(startX, startX + maxWidth, this.drawable.getWidth());
		this.drawable.render(renderer, alignedStartX, startY, this.drawable.getWidth(), mouseX, mouseY);
	}

	@Override
	public int getHeight()
	{
		return this.drawable.getHeight();
	}

	@Override
	public int getWidth()
	{
		return this.drawable.getWidth();
	}

	@Override
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		int alignedStartX = this.alignment.getLeft(startX, startX + maxWidth, this.drawable.getWidth());
		this.drawable.renderTooltip(renderer, alignedStartX, startY, this.drawable.getWidth(), mouseX, mouseY);
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.empty();
	}

}
