package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.util.MathUtil;

public class DrawableWithOffset
{
	private int x;
	private int y;
	private int containerWidth;
	private Drawable drawable;
	
	/**
	 * 
	 * @param x The x-position of the drawable
	 * @param y The y-position of the drawable
	 * @param drawable The drawable
	 */
	public DrawableWithOffset(int x, int y, int containerWidth, Drawable drawable)
	{
		this.x = x;
		this.y = y;
		this.containerWidth = containerWidth;
		this.drawable = drawable;
	}
	
	public void render(DrawableRenderer renderer, int startX, int startY, int mouseX, int mouseY)
	{
		this.drawable.render(renderer, startX + this.x, startY + this.y, this.containerWidth, mouseX, mouseY);
	}
	
	public boolean onClick(DrawableRenderer renderer, int startX, int startY, int mouseX, int mouseY)
	{
		int thisStartX = startX + this.x;
		int thisStartY = startY + this.y;
		int width = this.drawable.getWidth();
		int height = this.drawable.getHeight();
		if (MathUtil.isWithin(mouseX, mouseY, thisStartX, thisStartY, width, height))
		{
			return this.drawable.handleClicks(renderer, startX + this.x, startY + this.y, mouseX, mouseY);
		}
		return false;
	}
	
	public boolean renderTooltip(DrawableRenderer renderer, int startX, int startY, int mouseX, int mouseY)
	{
		int thisStartX = startX + this.x;
		int thisStartY = startY + this.y;
		int width = this.drawable.getWidth();
		int height = this.drawable.getHeight();
		if (MathUtil.isWithin(mouseX, mouseY, thisStartX, thisStartY, width, height))
		{
			return this.drawable.renderTooltips(renderer, startX + this.x, startY + this.y, mouseX, mouseY);
		}
		return false;
	}
}
