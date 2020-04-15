package com.github.commoble.mondobook.client;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import com.github.commoble.mondobook.MondobookMod;
import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;
import com.github.commoble.mondobook.client.api.Specificity;
import com.github.commoble.mondobook.client.api.internal.SimpleSelector;
import com.github.commoble.mondobook.client.content.ImageElement;
import com.github.commoble.mondobook.client.content.NewPageElement;
import com.github.commoble.mondobook.client.content.Selectors;
import com.github.commoble.mondobook.client.content.TextElement;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
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
		}
	}

	private static void onClientSetup(FMLClientSetupEvent event)
	{
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "new_page", NewPageElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "text", TextElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "image", ImageElement::new);
		
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
}
