package com.github.commoble.mondobook.client.content;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.book.BookStyle;
import com.github.commoble.mondobook.client.util.RenderUtil;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

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
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		TextFormatting colorFormat = this.style.getTextStyle().getColor();
		int color = colorFormat != null ? colorFormat.getColor() : BLACK;
		RenderUtil.getFontRenderer(this.style.getFont())
			.drawString(this.text.getFormattedText(), startX, startY, color);
	}

	@Override
	public int getHeight()
	{
		return RenderUtil.getFontRenderer(this.style.getFont()).FONT_HEIGHT;
	}

}
