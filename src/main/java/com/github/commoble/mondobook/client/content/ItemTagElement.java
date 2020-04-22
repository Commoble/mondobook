package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.Borders;
import com.github.commoble.mondobook.client.api.internal.ItemStackDrawable;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.client.api.internal.RowDrawable;
import com.github.commoble.mondobook.client.api.internal.SideSizes;
import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
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
		List<Drawable> itemDrawables = this.tag.getAllElements().stream()
			.map(item -> new ItemStackDrawable(new ItemStack(item), style))
			.collect(Collectors.toList());
		
		// if we don't have any items to draw, just return an empty list
		if (itemDrawables.isEmpty())
		{
			return itemDrawables;
		}
		
		SideSizes margins = style.getMargins();
		Borders borders = style.getBorders();
		SideSizes borderWidths = borders.getSizes();
		int widthForItems = containerWidth - margins.left - margins.right - borderWidths.left - borderWidths.right;
		int itemsPerRow = widthForItems / 16;
		if (itemsPerRow < 1)
		{
			itemsPerRow = 1;
		}
		
		int items = itemDrawables.size();
		
		int rows = (int) Math.ceil((double)items / (double)itemsPerRow);

		return style.styleMultipleDrawables(Lists.partition(itemDrawables, itemsPerRow), (list, someStyle) -> new RowDrawable(list));
		
	}

}
