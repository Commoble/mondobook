package com.github.commoble.mondobook.client.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.book.RawStyle.StyleBuilder;

import net.minecraft.util.text.TextFormatting;

class StyleTests
{
	public static int BLACK = 0;
	
	public static BookStyle buildStyleFromJsonStrings(String... strings)
	{
		StyleBuilder builder = new StyleBuilder();
		for (String string : strings)
		{
			builder.add(SimpleJsonDataManager.GSON.fromJson(string, RawStyle.class));
		}
		return builder.build();
	}
	
	@Nested
	class ColorTests
	{
		@Test
		void should_ReturnBlack_When_ColorNotPresent()
		{
			// given
			BookStyle style = buildStyleFromJsonStrings("{}");
				
			// when
			int color = style.getTextColor();

			// then
			Assertions.assertTrue(color == 0x0);
		}
		
		@Test
		void should_ReturnBlack_When_ColorNull()
		{
			// given
			BookStyle style = buildStyleFromJsonStrings("{\"text_color\": null}");
				
			// when
			int color = style.getTextColor();

			// then
			Assertions.assertTrue(color == BLACK);
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray",
			"dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"})
		void should_ReturnCorrectColor_When_TextFormattingNameIsUsed(String colorName)
		{
			// given colorName

			// when
			BookStyle style = buildStyleFromJsonStrings(String.format("{\"text_color\": \"%s\"}", colorName));
			int mcColor = TextFormatting.getValueByName(colorName).getColor();

			// then
			Assertions.assertTrue(style.getTextColor() == mcColor);
		}
		
		@ParameterizedTest
		@CsvFileSource(resources = "/hex-color-params.csv", numLinesToSkip = 1)
		void should_ReturnCorrectColor_When_ValidHexStringIsUsed(String hexString, int colorValue)
		{
			// given hexString, value

			// when
			int styleColor = buildStyleFromJsonStrings(String.format("{\"text_color\": \"%s\"}", hexString)).getTextColor();

			// then
			Assertions.assertEquals(colorValue, styleColor);
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"glob", "EFG", "-40", "FFFFFFF", "0x01", "0x-30", "0xred", "0xFF"})
		void should_ReturnBlack_When_InvalidStringIsUsed(String colorString)
		{
			// given colorString

			// when
			int styleColor = buildStyleFromJsonStrings(String.format("{\"text_color\": \"%s\"}", colorString)).getTextColor();

			// then
			Assertions.assertEquals(BLACK, styleColor);
		}
		
		@Test
		void should_ReturnFinalColor_When_MultipleStylesUsed()
		{
			// given
			String[] jsonStrings = {
				"{}",
				"{\"text_color\": null}",
				"{\"text_color\": \"red\"}",
				"{\"text_color\": \"40\"}",
				"{\"text_color\": null}",
				"{}",
				"{\"text_color\": \"12345\"}",
			};

			// when
			int styleColor = buildStyleFromJsonStrings(jsonStrings).getTextColor();

			// then
			Assertions.assertEquals(0x12345, styleColor);
		}
	}
}
