package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.book.BookStyle;
import com.github.commoble.mondobook.client.book.RawElement;
import com.github.commoble.mondobook.client.util.RenderUtil;

import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.StringTextComponent;

public class TextElement extends Element
{
	private final String text;
	
	public TextElement(RawElement raw)
	{
		super(raw);
		this.text = raw.getData();
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int textWidth)
	{
		return RenderComponentsUtil.splitText(new StringTextComponent(this.text).setStyle(style.getTextStyle()), textWidth, RenderUtil.getFontRenderer(style.getFont()), true, true)
			.stream()
			.map(text -> new TextLineDrawable(text, style))
			.collect(Collectors.toList());
	}
}
