package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.RawElement;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

/** Element that consists of a gridded list of all items in the tag **/
public class ItemTagElement extends Element
{
	private final ItemTags.Wrapper tag;

	public ItemTagElement(RawElement raw)
	{
		super(raw);
		this.tag = new ItemTags.Wrapper(new ResourceLocation(raw.getData()));
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		return null;
	}

}
