package com.github.commoble.mondobook.client.assets;

import com.github.commoble.mondobook.client.styles.BookStyle;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

// to be serialized via a basic GSON parser
public class RawStyle
{
	private RawSelector selector;	// a selector that will be compared to an element or elements 
	/**
	 * This represents a ResourceLocation for a Font;
	 * The Fonts included with vanilla minecraft are "minecraft:default" and "minecraft:alt"
	 * ("alt" is the Standard Galactic Alphabet font used for enchanting table runes)
	 */
	private String font;
	
	private String color;	// see TextFormatting for color strings (use lowercase, e.g. "red")
	
	// the rest of these default to false
	private boolean bold;
	private boolean italic;
	private boolean underlined;
	private boolean strikethrough;
	private boolean obfuscated;
	
	public BookStyle toBookStyle()
	{
		Style style = new Style();
		style.setBold(this.bold);
		style.setItalic(this.italic);
		style.setUnderlined(this.underlined);
		style.setStrikethrough(this.strikethrough);
		style.setObfuscated(this.obfuscated);
		
		if (this.color == null || this.color.equals(""))
		{
			this.color = "black";
		}
		
		style.setColor(TextFormatting.getValueByName(this.color));
		if (this.font == null || this.font.equals(""))
		{
			this.font = "minecraft:default";
		}
		
		return new BookStyle(new ResourceLocation(this.font), style);
	}
}
