package com.github.commoble.mondobook.client.elements;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

import net.minecraft.util.text.ITextComponent;

public class TextLineDrawable implements Drawable
{
	private final ITextComponent text;
	
	public static final int BLACK = 0x0;
	public static final int TEXT_HEIGHT = 9;
	
	public TextLineDrawable(ITextComponent text)
	{
		this.text = text;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		renderer.getFont().drawString(this.text.getFormattedText(), startX, startY, BLACK);
	}

	@Override
	public int getHeight()
	{
		return TEXT_HEIGHT;
	}

}
