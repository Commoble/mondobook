package com.github.commoble.mondobook.client.content;

import java.util.Optional;

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
	
	public static TextLineDrawable of(ITextComponent text, BookStyle style)
	{
		return new TextLineDrawable(text, style);
	}
	
	private TextLineDrawable(ITextComponent text, BookStyle style)
	{
		this.text = text;
		this.style = style;
	}

	@Override
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		String formattedString = this.text.getFormattedText();
		FontRenderer fontRenderer = this.style.getFontRenderer();
		fontRenderer.drawString(formattedString, startX, startY, this.style.getTextColor());
	}

	@Override
	public int getHeight()
	{
		// this could be just one line but
		// eclipse complains about resource leak / closeable not being closed unless we split this up into local vars
		FontRenderer renderer = this.style.getFontRenderer();
		return renderer.FONT_HEIGHT;
	}

	@Override
	public int getWidth()
	{
		return this.style.getFontRenderer().getStringWidth(this.text.getString());
	}

	@Override
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.of(this.style);
	}

}
