package com.github.commoble.mondobook.client.book.raw;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class Paragraph
{
	private String text;
	
	public List<ITextComponent> toLines(int textWidth, FontRenderer font)
	{
		return RenderComponentsUtil.splitText(new StringTextComponent(this.text), textWidth, font, true, true);
	}
}
