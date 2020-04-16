package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

public class PaddedDrawable implements Drawable
{
	private int bottomPadding;
	private int topPadding;
	private int leftPadding;
	private int rightPadding;
	private final Drawable drawable;
	
	public static PaddedDrawable of(int bottomPadding, int topPadding, int leftPadding, int rightPadding, Drawable drawable)
	{
		return new PaddedDrawable(bottomPadding, topPadding, leftPadding, rightPadding, drawable);
	}
	
	private PaddedDrawable(int bottomPadding, int topPadding, int leftPadding, int rightPadding,  Drawable drawable)
	{
		this.bottomPadding = bottomPadding;
		this.topPadding = topPadding;
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.drawable = drawable;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		this.drawable.render(renderer, startX + this.leftPadding, startY + this.topPadding);
	}

	@Override
	public int getHeight()
	{
		return this.drawable.getHeight() + this.topPadding + this.bottomPadding;
	}

}
