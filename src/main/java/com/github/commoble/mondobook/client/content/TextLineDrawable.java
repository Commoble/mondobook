package com.github.commoble.mondobook.client.content;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.internal.BookStyle;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class TextLineDrawable implements Drawable
{
	private final ITextComponent text;
	private final BookStyle style;
	
	public static final int BLACK = 0x0;
	
	public TextLineDrawable(ITextComponent text, BookStyle style)
	{
		this.text = text;
		this.style = style;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
	{
		String formattedString = this.text.getFormattedText();
		String unformattedString = this.text.getString();
		FontRenderer fontRenderer = this.style.getFontRenderer();
		int textWidth = fontRenderer.getStringWidth(unformattedString);
		int actualStartX = this.style.getAlignment().getLeft(startX, startX + maxWidth, textWidth);
		fontRenderer.drawString(formattedString, actualStartX, startY, this.style.getTextColor());
	}

	@Override
	public int getHeight()
	{
		// this could be just one line but
		// eclipse complains about resource leak / closeable not being closed unless we split this up into local vars
		FontRenderer renderer = this.style.getFontRenderer();
		return renderer.FONT_HEIGHT;
	}

}
