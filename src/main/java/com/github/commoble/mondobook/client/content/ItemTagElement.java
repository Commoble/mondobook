package com.github.commoble.mondobook.client.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.google.common.collect.ImmutableList;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/** Element that consists of a gridded list of all items in the tag **/
public class ItemTagElement extends Element
{
	private final CollectionElement itemCollection;

	public ItemTagElement(ElementPrimer primer)
	{
		super(primer);
		ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation(primer.getData()));
		List<String> itemClasses = Optional.ofNullable(primer.getAttributes().get("item_classes"))
			.map(classes -> Arrays.asList(classes.split(",")))
			.orElse(ImmutableList.of());
		List<RawElement> itemElements = tag.getAllElements().stream()
			.sorted(ItemTagElement::compareItems)
			.map(item -> new RawElement()
				.setType(new ResourceLocation("mondobook:item"))
				.setData(item.getRegistryName().toString())
				.setClasses(itemClasses))
			.collect(Collectors.toList());
		RawElement rawCollection = primer.getRawElement().copy()
			.setChildren(itemElements);
		
		// TODO can use attributes to add id/styles to individual item elements
		this.itemCollection = new CollectionElement(new ElementPrimer(rawCollection, primer.getRawStyles()));
		
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth, boolean shrinkwrap)
	{
		return this.itemCollection.getColumnOfDrawables(renderer, containerWidth, shrinkwrap);
	}

	/** Compares items for sorting -- sorts items in alphabetical order by translation key **/
	public static int compareItems(Item itemA, Item itemB)
	{
		String nameA = new TranslationTextComponent(itemA.getTranslationKey()).getString();
		String nameB = new TranslationTextComponent(itemB.getTranslationKey()).getString();
		return nameA.compareTo(nameB);
	}

}
