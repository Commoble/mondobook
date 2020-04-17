package com.github.commoble.mondobook.client.book;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.api.internal.Alignment;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.MarginSide;
import com.github.commoble.mondobook.client.api.internal.Margins;
import com.github.commoble.mondobook.client.api.internal.RawStyle;
import com.github.commoble.mondobook.client.api.internal.RawStyle.StyleBuilder;

import net.minecraft.util.text.TextFormatting;

class StyleTests
{
	private static final int BLACK = 0;
	
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
	
	@Nested
	class MarginTests
	{
		@Test
		void should_HaveZeroMarginOnAllSides_When_NoMarginsSpecified()
		{
			// given an empty style json
			BookStyle style = buildStyleFromJsonStrings("{}");

			// when we check all four margin sides
			Stream<Executable> cases = Arrays.stream(MarginSide.values())
				.map(side -> () -> Assertions.assertEquals(0, style.getMargins().getMarginOnSide(side)));

			// then they should all have values of zero
			Assertions.assertAll(cases);
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"null", "-1", "-4000000"})
		void should_HaveZeroMarginOnAllSides_When_OnlyInvalidGeneralMarginSpecified(String marginString)
		{
			// given a style json with a margin that ought to be interpreted as 0
			BookStyle style = buildStyleFromJsonStrings(String.format("{\"margin\": %s}", marginString));
			
			// when we check all four margin sides
			Stream<Executable> cases = Arrays.stream(MarginSide.values())
				.map(side -> () -> Assertions.assertEquals(0, style.getMargins().getMarginOnSide(side)));

			// then they should all have values of zero
			Assertions.assertAll(cases);
		}
		
		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, Integer.MAX_VALUE})
		void should_HaveSpecifiedMarginOnAllSides_When_OnlyValidGeneralMarginSpecified(int margin)
		{
			// given a style json with a general margin
			BookStyle style = buildStyleFromJsonStrings(String.format("{\"margin\": %s}", margin));
			
			// when we check all four margin sides
			Stream<Executable> cases = Arrays.stream(MarginSide.values())
				.map(side -> () -> Assertions.assertEquals(margin, style.getMargins().getMarginOnSide(side)));

			// then the sides should all match the input margin value
			Assertions.assertAll(cases);
		}
		
		@ParameterizedTest
		@CsvFileSource(resources = "/style-margin-params.csv", numLinesToSkip = 1)
		void should_HaveCorrectMargins_When_SeparateSidesAreSpecified(
			String allIn, String bottomIn, String topIn, String leftIn, String rightIn,
			int bottomOut, int topOut, int leftOut, int rightOut)
		{
			// given a json with margins based on the given inputs
			BookStyle style = buildStyleFromJsonStrings(String.format(
				"{\"margin\": %s, \"bottom_margin\": %s, \"top_margin\": %s, \"left_margin\": %s, \"right_margin\": %s}",
				allIn, bottomIn, topIn, leftIn, rightIn));
			
			// when we check the margins of the resulting style object
			
			// then they should match the expected outputs
			Assertions.assertAll(
				() -> Assertions.assertEquals(bottomOut, style.getMargins().bottom),
				() -> Assertions.assertEquals(topOut, style.getMargins().top),
				() -> Assertions.assertEquals(leftOut, style.getMargins().left),
				() -> Assertions.assertEquals(rightOut, style.getMargins().right)
				);
		}
		
		@Test
		void should_MergeMarginsCorrectly_When_MultipleStylesUsed()
		{

			// given a bunch of styles
			String[] jsonStrings = {
				"{}",
				"{\"margin\": null}",
				"{\"margin\": 5}",
				"{\"right_margin\": 4}",
				"{\"right_margin\": null}",
				"{}",
				"{\"margin\": 6}",
				"{\"left_margin\": 1}",
			};

			// when we merge them together
			Margins margins = buildStyleFromJsonStrings(jsonStrings).getMargins();

			// then nulls should never override anything, the last style to specify a side margin takes precedence on that side,
			// and the last style to specify a general margin should take precedence where no side has been specified
			Assertions.assertAll(
				() -> Assertions.assertEquals(6, margins.bottom),
				() -> Assertions.assertEquals(6, margins.top),
				() -> Assertions.assertEquals(1, margins.left),
				() -> Assertions.assertEquals(4, margins.right)
				);
		}
	}
	
	@Nested
	class AlignmentTests
	{
		@Test
		void should_AlignLeft_When_AligntmentNotSpecified()
		{
			// given an empty json
			BookStyle style = buildStyleFromJsonStrings("{}");
				
			// when we get the alignment from it
			Alignment actualAlignment = style.getAlignment();

			// then it should be LEFT
			Assertions.assertEquals(Alignment.LEFT, actualAlignment);;
		}
		
		@Test
		void should_HaveCorrectAlignment_When_AlignmentSpecified()
		{
			// given these inputs
			String[] inputs = {"null", "left", "center", "right"};
			Alignment[] expectedOutputs = {Alignment.LEFT, Alignment.LEFT, Alignment.CENTER, Alignment.RIGHT};
				
			// when we get the alignment from it
			Alignment[] actualOutputs = Arrays.stream(inputs)
				.map(input -> buildStyleFromJsonStrings(String.format("{\"alignment\": %s}", input)).getAlignment())
				.toArray(Alignment[]::new);

			// then
			Assertions.assertArrayEquals(expectedOutputs, actualOutputs);
		}
		
		@Test
		void should_MergeAlignmentsCorrectly_When_MultipleAlignmentsUsed()
		{

			// given a bunch of styles
			String[] jsonStrings = {
				"{}",
				"{\"alignment\": null}",
				"{\"alignment\": right}",
				"{\"alignment\": center}",
				"{\"alignment\": null}",
			};

			// when we merge them together
			Alignment actualAlignment = buildStyleFromJsonStrings(jsonStrings).getAlignment();
			
			// then nulls should never override anything, and the last style to specify a nonnull alignment should take precedence
			Assertions.assertEquals(Alignment.CENTER, actualAlignment);
		}
	}
}
