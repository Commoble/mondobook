package com.github.commoble.mondobook.client.book.elements;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.assets.RawElement;
import com.github.commoble.mondobook.client.api.DrawableFactory;
import com.github.commoble.mondobook.client.book.drawables.TextLineDrawable;

import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.StringTextComponent;

public class TextElement implements DrawableFactory
{
	private final String text;
	
	public TextElement(RawElement raw)
	{
		this.text = raw.getData();
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, int textWidth)
	{
		return RenderComponentsUtil.splitText(new StringTextComponent(this.text), textWidth, renderer.getFont(), true, true)
			.stream()
			.map(TextLineDrawable::new)
			.collect(Collectors.toList());
	}
}
