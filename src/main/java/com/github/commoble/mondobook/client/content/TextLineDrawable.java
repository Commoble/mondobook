package com.github.commoble.mondobook.client.content;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.util.RenderUtil;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
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
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		RenderUtil.getFontRenderer(this.style.getFont())
			.drawString(this.text.getFormattedText(), startX, startY, this.style.getTextColor());
	}

	@Override
	public int getHeight()
	{
		// this could be just one line but
		// eclipse complains about resource leak / closeable not being closed unless we split this up into local vars
		ResourceLocation fontID = this.style.getFont();
		FontRenderer renderer = RenderUtil.getFontRenderer(fontID);
		return renderer.FONT_HEIGHT;
	}

}
