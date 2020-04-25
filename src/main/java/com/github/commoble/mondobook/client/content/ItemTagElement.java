package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RawElement;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

/** Element that consists of a gridded list of all items in the tag **/
public class ItemTagElement extends Element
{
	private final CollectionElement itemCollection;

	public ItemTagElement(ElementPrimer primer)
	{
		super(primer);
		ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation(primer.getData()));
		List<RawElement> itemElements = tag.getAllElements().stream()
			.map(item -> new RawElement().setType(new ResourceLocation("mondobook:item")).setData(item.getRegistryName().toString()))
			.collect(Collectors.toList());
		RawElement rawCollection = primer.getRawElement().copy()
			.setChildren(itemElements);
		
		// TODO can use attributes to add id/styles to individual item elements
		this.itemCollection = new CollectionElement(new ElementPrimer(rawCollection, primer.getRawStyles()));
		
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth)
	{
		return this.itemCollection.getColumnOfDrawables(renderer, containerWidth);
	}

}
