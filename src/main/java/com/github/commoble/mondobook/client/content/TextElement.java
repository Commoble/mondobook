package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.Margins;
import com.github.commoble.mondobook.client.api.internal.PaddedDrawable;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.util.ListUtil;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class TextElement extends Element
{
	private final String text;
	
	public TextElement(RawElement raw)
	{
		super(raw);
		this.text = raw.getData();
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		Margins margins = style.getMargins();
		int textWidth = containerWidth - margins.left - margins.right;
		Style textStyle = style.getTextStyle();
		FontRenderer fontRenderer = style.getFontRenderer();
		List<ITextComponent> lines = RenderComponentsUtil.splitText(new StringTextComponent(this.text).setStyle(textStyle), textWidth, fontRenderer, true, true);
		return ListUtil.mapFirstMiddleLast(lines,
			text -> PaddedDrawable.of(0, margins.top, margins.left, margins.right, new TextLineDrawable(text, style)),
			text -> PaddedDrawable.of(0, 0, margins.left, margins.right, new TextLineDrawable(text, style)),
			text -> PaddedDrawable.of(margins.bottom, 0, margins.left, margins.right, new TextLineDrawable(text, style)));
	}
}
