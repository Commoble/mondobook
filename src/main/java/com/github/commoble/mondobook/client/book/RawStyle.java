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
	private RawSelector selector;	// a selector that will be compared to an element or elements
	private transient Selector bakedSelector;
	/**
	 * This represents a ResourceLocation for a Font;
	 * The Fonts included with vanilla minecraft are "minecraft:default" and "minecraft:alt"
	 * ("alt" is the Standard Galactic Alphabet font used for enchanting table runes)
	 */
	private String font;
	
	/**
	 * This can be one of two possible string formats:
	 * A) one of minecraft's sixteen builtin TextFormatting color names, e.g. "red"
	 * B) a six-digit hexidecimal number in the format "######" (where # is some hex digit in the range 0-F)
	 * 
	 * Invalid strings (such as an unused color name, or improperly formatted hexcode) will fallback to black. 
	 */
	private String text_color;
	
	// the rest of these default to NULL, so that "false" can override "not present"
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;
	
	public Selector getSelector()
	{
		if (this.bakedSelector == null)
		{
			if (this.selector == null)
			{
				this.bakedSelector = AssetFactories.SELECTORS.getFactory(new ResourceLocation("mondobook:all")).apply(null);
			}
			else
			{
				this.bakedSelector = AssetFactories.SELECTORS.apply(this.selector);
			}
		}
		return this.bakedSelector;
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
			if (style.text_color != null)
			{
				this.color = style.text_color;
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
//			textStyle.setColor(TextFormatting.getValueByName(this.color));	// nulls are okay here
			if (this.font == null)
			{
				this.font = new ResourceLocation("minecraft:default");
			}
			
			// the color logic is as follows:
			// if color string matches one of the vanilla TextFormatting color names, use that value
			// otherwise, if color is 
			int finalColor = 0x0; // black
			if (this.color != null)
			{
				TextFormatting textFormat = TextFormatting.getValueByName(this.color);
				if (textFormat != null)
				{
					finalColor = textFormat.getColor();
				}
				else
				{
					try
					{
						int parseAttempt = Integer.parseInt(this.color, 16);
						if (parseAttempt > 0 && parseAttempt <= 0xFFFFFF)
						{
							finalColor = parseAttempt & 0xFFFFFF;	// avoid negative numbers or values greater than RGB White
						}
					}
					catch(NumberFormatException e)
					{	// if the string isn't a valid int, just leave it 0
						finalColor = 0;
					}
				}
			}
			return new BookStyle(this.font, textStyle, finalColor);
		}
	}

}
