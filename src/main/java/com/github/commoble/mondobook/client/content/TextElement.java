package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.Borders;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.client.api.internal.SideSizes;

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
		SideSizes padding = style.getMargins();
		Borders borders = style.getBorders();
		SideSizes borderSizes = borders.getSizes();
		SideSizes totalPadding = padding.add(borderSizes);
		int textWidth = containerWidth - totalPadding.left - totalPadding.right;
		Style textStyle = style.getTextStyle();
		FontRenderer fontRenderer = style.getFontRenderer();
		List<ITextComponent> lines = RenderComponentsUtil.splitText(new StringTextComponent(this.text).setStyle(textStyle), textWidth, fontRenderer, true, true);
		
		// the first line of text has the top border and padding, but not the bottom
		// the middle lines have neither the top or bottom border/padding
		// the last line of text does not have the top padding, but has the bottom
		// all lines have the side padding
//		SideSizes firstPadding = padding.without(BoxSide.BOTTOM);
//		Borders firstBorders = borders.without(BoxSide.BOTTOM);
//		SideSizes middlePadding = firstPadding.without(BoxSide.TOP);
//		Borders middleBorders = firstBorders.without(BoxSide.TOP);
//		SideSizes lastPadding = padding.without(BoxSide.TOP);
//		Borders lastBorders = borders.without(BoxSide.TOP);
//		Alignment alignment = style.getAlignment();
//		
//		return ListUtil.mapFirstMiddleLast(lines,
//			text -> PaddedDrawable.of(firstPadding, firstBorders, AlignedDrawable.of(alignment, TextLineDrawable.of(text,style))),
//			text -> PaddedDrawable.of(middlePadding, middleBorders, AlignedDrawable.of(alignment, TextLineDrawable.of(text,style))),
//			text -> PaddedDrawable.of(lastPadding, lastBorders, AlignedDrawable.of(alignment, TextLineDrawable.of(text,style))));
		
		return style.styleMultipleDrawables(lines, TextLineDrawable::of);
	}
}
