package com.github.commoble.mondobook.client;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import com.github.commoble.mondobook.MondobookItem;
import com.github.commoble.mondobook.MondobookMod;
import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;
import com.github.commoble.mondobook.client.api.internal.SimpleSelector;
import com.github.commoble.mondobook.client.api.internal.Specificity;
import com.github.commoble.mondobook.client.content.CollectionElement;
import com.github.commoble.mondobook.client.content.ColumnElement;
import com.github.commoble.mondobook.client.content.ImageElement;
import com.github.commoble.mondobook.client.content.ItemStackElement;
import com.github.commoble.mondobook.client.content.ItemTagElement;
import com.github.commoble.mondobook.client.content.NewPageElement;
import com.github.commoble.mondobook.client.content.RowElement;
import com.github.commoble.mondobook.client.content.Selectors;
import com.github.commoble.mondobook.client.content.TextElement;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.NonNullList;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler
{
	public static void subscribeClientEvents(IEventBus modBus, IEventBus forgeBus)
	{
		modBus.addListener(ClientEventHandler::onClientSetup);

		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if (manager instanceof IReloadableResourceManager)
		{
			IReloadableResourceManager reloader = (IReloadableResourceManager)manager;
			reloader.addReloadListener(AssetManagers.BOOK_DATA);
			reloader.addReloadListener(AssetManagers.IMAGE_DATA);
			reloader.addReloadListener(AssetManagers.STYLE_DATA);
			reloader.addReloadListener(AssetManagers.TABS);
			reloader.addReloadListener(AssetManagers.BOOK_FORMATS);
		}
	}

	private static void onClientSetup(FMLClientSetupEvent event)
	{
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "new_page", NewPageElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "row", RowElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "column", ColumnElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "collection", CollectionElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "text", TextElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "image", ImageElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "item", ItemStackElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "item_tag", ItemTagElement::new);
		
		registerSimpleSelector("all", Selectors::alwaysMatch, (element, selector) -> Specificity.NONE);
		registerSimpleSelector("element", Selectors::matchElement, (element, selector) -> Specificity.ONE_ELEMENT);
		registerSimpleSelector("class", Selectors::matchClass, (element, selector) -> Specificity.ONE_CLASS);
		registerSimpleSelector("id", Selectors::matchID, (element, selector) -> Specificity.ONE_ID);
		registerSimpleSelector("or", Selectors::matchAnyChildren, Selectors::getOrSpecificity);
		registerSimpleSelector("and", Selectors::matchAllChildren, Selectors::getAndSpecificity);
		
	}
	
	public static void registerSimpleSelector(String name, BiPredicate<Element, Selector> predicate, BiFunction<Element, Selector, Specificity> specificityGetter)
	{
		AssetFactories.SELECTORS.register(MondobookMod.MODID, name, SimpleSelector.getFactory(predicate, specificityGetter));
	}
	
	public static void fillGroupWithAllMondobooks(NonNullList<ItemStack> items)
	{
		AssetManagers.TABS.map.values().stream()
			.reduce(new HashSet<>(), Sets::union)
			.forEach(bookID -> items.add(MondobookItem.makeItemStackForBookTitle(bookID)));
	}
	
	public static void fillGroupWithRelevantMondobooks(NonNullList<ItemStack> items, ItemGroup group)
	{
		AssetManagers.TABS.map.getOrDefault(group,ImmutableSet.of())
			.forEach(bookID -> items.add(MondobookItem.makeItemStackForBookTitle(bookID)));
	}
	
	/**
     * Gets a list of tabs that items belonging to this class can display on,
     * combined properly with getSubItems allows for a single item to span many
     * sub-items across many tabs.
     */
    public static void fillMondobookCreativeTabs(List<ItemGroup> mutableList)
    {
        AssetManagers.TABS.map.keySet().forEach(mutableList::add);
    }
}
