package com.github.commoble.mondobook.client.elements;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;

import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.text.StringTextComponent;

public class TextElement extends Element
{
	private final String text;
	
	public TextElement(RawElement raw)
	{
		super(raw);
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
