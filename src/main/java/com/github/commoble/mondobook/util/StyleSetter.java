package com.github.commoble.mondobook.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.util.text.Style;

public enum StyleSetter implements BiConsumer<Style, Boolean>
{
	BOLD((style, bool) -> style.setBold(bool)),
	ITALIC((style, bool) -> style.setItalic(bool)),
	UNDERLINED((style, bool) -> style.setUnderlined(bool)),
	STRIKETHROUGH((style, bool) -> style.setStrikethrough(bool)),
	OBFUSCATED((style, bool) -> style.setObfuscated(bool));
	
	private BiConsumer<Style, Boolean> styler;
	
	private StyleSetter(BiConsumer<Style, Boolean> styler)
	{
		this.styler = styler;
	}

	@Override
	public void accept(Style style, Boolean flag)
	{
		this.styler.accept(style, flag);
	}
	
	/** all args are nullable **/
	public static Map<StyleSetter, Boolean> buildMap(Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated)
	{
		Map<StyleSetter, Boolean> map = new EnumMap<>(StyleSetter.class);
		map.put(BOLD, bold);
		map.put(ITALIC, italic);
		map.put(UNDERLINED, underlined);
		map.put(STRIKETHROUGH, strikethrough);
		map.put(OBFUSCATED, obfuscated);
		return map;
	}
	
	public static void applyStyleFlags(Map<StyleSetter, Boolean> styleFlags, Style textStyle)
	{
		styleFlags.forEach((setter, bool) -> setter.accept(textStyle, bool));
	}
}
