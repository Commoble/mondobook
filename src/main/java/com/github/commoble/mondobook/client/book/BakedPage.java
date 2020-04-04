package com.github.commoble.mondobook.client.book;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.commoble.mondobook.data.raw.Element;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BakedPage
{
	private final List<ITextComponent> lines;
	
	public BakedPage(List<ITextComponent> lines)
	{
		this.lines = lines;
	}
	
	public List<ITextComponent> getLines()
	{
		return this.lines;
	}
	
	public static List<BakedPage> fromParagraphs(List<Element> elements, int maxLinesOnPage, int textWidth, FontRenderer font)
	{
		Deque<BakedPage> pageDeck = new ArrayDeque<>();
		Deque<ITextComponent> lineDeck = new ArrayDeque<>();
		
		elements.stream().forEach(paragraph -> lineDeck.addAll(getLinesFromParagraph(paragraph, textWidth, font)));
		PageBuilder builder = new PageBuilder(maxLinesOnPage);
		while (!lineDeck.isEmpty())
		{
			ITextComponent line = lineDeck.pollFirst();
			if (builder.canAddLines(1))
			{
				builder.addLine(line);
			}
			else
			{
				pageDeck.add(builder.build());
				builder = new PageBuilder(maxLinesOnPage);
				builder.addLine(line);
			}
		}
		// make sure we add the last page
		if (!builder.lines.isEmpty())
		{
			pageDeck.add(builder.build());
		}
		return new ArrayList<>(pageDeck);
	}
	
	public static List<ITextComponent> getLinesFromParagraph(Element paragraph, int textWidth, FontRenderer font)
	{
		return RenderComponentsUtil.splitText(new StringTextComponent(paragraph.getText()), textWidth, font, true, true);
	}
		
	
	public static class PageBuilder
	{
		private List<ITextComponent> lines = new ArrayList<>();
		private int currentHeight = 0;
		
		private final int maxLinesOnPage;
		
		public PageBuilder(int maxLinesOnPage)
		{
			this.maxLinesOnPage = maxLinesOnPage;
		}
		
		public boolean canAddLines(int numberOfLinesToAdd)
		{
			return this.currentHeight == 0 || this.currentHeight + numberOfLinesToAdd <= this.maxLinesOnPage;
		}
		
		public boolean canAddLines(List<ITextComponent> lines)
		{
			return this.canAddLines(lines.size());
		}
		
		public void addLine(ITextComponent line)
		{
			this.lines.add(line);
			this.currentHeight++;
		}
		
		public void addLines(List<ITextComponent> lines)
		{
			this.lines.addAll(lines);
			this.currentHeight += lines.size();
		}
		
		public BakedPage build()
		{
			return new BakedPage(this.lines);
		}
	}

}
