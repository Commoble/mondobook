package com.github.commoble.mondobook.client.book;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Selector;
import com.github.commoble.mondobook.util.StyleSetter;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

// to be serialized via a basic GSON parser
public class RawStyle
{
	private RawSelector rawSelector;	// a selector that will be compared to an element or elements
	private transient Selector selector;
	/**
	 * This represents a ResourceLocation for a Font;
	 * The Fonts included with vanilla minecraft are "minecraft:default" and "minecraft:alt"
	 * ("alt" is the Standard Galactic Alphabet font used for enchanting table runes)
	 */
	private String font;
	
	private String textColor;	// see TextFormatting for color strings (use lowercase, e.g. "red")
	
	// the rest of these default to NULL, so that "false" can override "not present"
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;
	
	public Selector getSelector()
	{
		if (this.selector == null)
		{
			if (this.rawSelector == null)
			{
				this.selector = AssetFactories.SELECTORS.getFactory(new ResourceLocation("mondobook:all")).apply(null);
			}
			else
			{
				this.selector = AssetFactories.SELECTORS.apply(this.rawSelector);
			}
		}
		return this.selector;
	}
	
	@Nullable
	public ResourceLocation getFont()
	{
		if (this.font == null)
		{
			return null;
		}
		else
		{
			return new ResourceLocation(this.font);
		}
	}
	
	@Nullable
	public String getTextColor()
	{
		return this.textColor;
	}
	
	public Map<StyleSetter, Boolean> getStyleFlags()
	{
		return StyleSetter.buildMap(this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated);
	}
	
	public static class StyleBuilder
	{
		/**
		 * The Fonts included with vanilla minecraft are "minecraft:default" and "minecraft:alt"
		 * ("alt" is the Standard Galactic Alphabet font used for enchanting table runes)
		 */
		private @Nullable ResourceLocation font;
		private @Nullable String color; // see TextFormatting for color strings (use lowercase, e.g. "red")
		private Map<StyleSetter, Boolean> styleFlags = new EnumMap<>(StyleSetter.class);
		
		public StyleBuilder() {}
		
		public StyleBuilder add(RawStyle style)
		{

			// true, false, and nonnull objects override existing values, but nulls do not
			
			if (style.font != null)
			{
				this.font = new ResourceLocation(style.font);
			}
			if (style.textColor != null)
			{
				this.color = style.textColor;
			}
			
			// all of the nonnull flags in the incoming style override existing flags
			style.getStyleFlags().forEach(this::mergeStyleFlag);
			
			return this;
		}
		
		private void mergeStyleFlag(StyleSetter setter, Boolean flag)
		{
			if (flag != null)
			{
				this.styleFlags.put(setter, flag);
			}
		}
		
		public BookStyle build()
		{
			Style textStyle = new Style();
			StyleSetter.applyStyleFlags(this.styleFlags, textStyle);
			textStyle.setColor(TextFormatting.getValueByName(this.color));	// nulls are okay here
			if (this.font == null)
			{
				this.font = new ResourceLocation("minecraft:default");
			}
			return new BookStyle(this.font, textStyle);
		}
	}

}
