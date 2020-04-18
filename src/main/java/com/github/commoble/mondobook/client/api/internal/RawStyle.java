package com.github.commoble.mondobook.client.api.internal;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Selector;

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
	
	/**
	 * Can be "null", "left", "right", or "center".
	 * Will be "null" if not specified in json.
	 * When merging multiple styles, nulls will not override values in lower-precedence styles.
	 * If no style declares a nonnull value, "left" will be used.
	 */
	private Alignment alignment;
	
	// the rest of these default to NULL, so that "false" can override "not present"
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;
	private Integer margin; // margin around all four edges of the element, superceded by specific margins
	private Integer bottom_margin;
	private Integer top_margin;
	private Integer left_margin;
	private Integer right_margin;
	private Integer border;	// colored border around all four edges of the element, outside the margin
	private Integer bottom_border;	// border is superceded by specific sides
	private Integer top_border;
	private Integer left_border;
	private Integer right_border;
	private String border_color;
	
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
		private @Nullable String textColor; // see TextFormatting for color strings (use lowercase, e.g. "red")
		private Map<StyleSetter, Boolean> styleFlags = new EnumMap<>(StyleSetter.class);
		private Map<RawSideSizes, Integer> margins = new EnumMap<>(RawSideSizes.class);
		private @Nullable Alignment alignment;
		private @Nullable String borderColor;
		private Map<RawSideSizes, Integer> borderSizes = new EnumMap<>(RawSideSizes.class);
		
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
				this.textColor = style.text_color;
			}
			if (style.alignment != null)
			{
				this.alignment = style.alignment;
			}
			RawSideSizes.mergeAll(this.margins,
				style.margin,
				style.bottom_margin,
				style.top_margin,
				style.left_margin,
				style.right_margin);
			if (style.border_color != null)
			{
				this.borderColor = style.border_color;
			}
			RawSideSizes.mergeAll(this.borderSizes,
				style.border,
				style.bottom_border,
				style.top_border,
				style.left_border,
				style.right_border);
			
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
			return new BookStyle(this.buildFont(), this.buildTextStyle(), this.buildTextColor(), this.buildMargins(), this.buildAlignment(), this.buildBorders());
		}
		
		private Alignment buildAlignment()
		{
			return this.alignment != null ? this.alignment : Alignment.LEFT;
		}
		
		private Style buildTextStyle()
		{
			Style textStyle = new Style();
			StyleSetter.applyStyleFlags(this.styleFlags, textStyle);
			return textStyle;
		}
		
		private ResourceLocation buildFont()
		{
			if (this.font == null)
			{
				return new ResourceLocation("minecraft:default");
			}
			else
			{
				return this.font;
			}
		}
		
		private int parseColorString(String colorString)
		{
			
			// the color logic is as follows:
			// if color string matches one of the vanilla TextFormatting color names, use that value
			// otherwise, if color is 
			int finalTextColor = 0x0; // black
			if (colorString != null)
			{
				TextFormatting textFormat = TextFormatting.getValueByName(colorString);
				if (textFormat != null)
				{
					finalTextColor = textFormat.getColor();
				}
				else
				{
					try
					{
						int parseAttempt = Integer.parseInt(colorString, 16);
						if (parseAttempt > 0 && parseAttempt <= 0xFFFFFF)
						{
							finalTextColor = parseAttempt & 0xFFFFFF;	// avoid negative numbers or values greater than RGB White
						}
					}
					catch(NumberFormatException e)
					{	// if the string isn't a valid int, just leave it 0
						finalTextColor = 0;
					}
				}
			}
			
			return finalTextColor;
		}
		
		private int buildTextColor()
		{
			return this.parseColorString(this.textColor);
		}
		
		private SideSizes buildMargins()
		{
			return new SideSizes(this.margins);
		}
		
		private Borders buildBorders()
		{
			return new Borders(new SideSizes(this.borderSizes), this.parseColorString(this.borderColor));
		}
	}

}
