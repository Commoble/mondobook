package com.github.commoble.mondobook.client;

import java.util.function.BiPredicate;

import com.github.commoble.mondobook.MondobookMod;
import com.github.commoble.mondobook.client.api.AssetFactories;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;
import com.github.commoble.mondobook.client.book.elements.ImageElement;
import com.github.commoble.mondobook.client.book.elements.NewPageElement;
import com.github.commoble.mondobook.client.book.elements.TextElement;
import com.github.commoble.mondobook.client.selectors.Selectors;
import com.github.commoble.mondobook.client.selectors.SimpleSelector;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler
{
	public static void subscribeClientEvents(IEventBus modBus, IEventBus forgeBus)
	{
		modBus.addListener(ClientEventHandler::onClientSetup);

		AssetManagers.onClientInit();
	}

	private static void onClientSetup(FMLClientSetupEvent event)
	{
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "new_page", NewPageElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "text", TextElement::new);
		AssetFactories.ELEMENTS.register(MondobookMod.MODID, "image", ImageElement::new);
		
		registerSimpleSelector("all", Selectors::alwaysMatch);
		registerSimpleSelector("element", Selectors::matchElement);
		registerSimpleSelector("class", Selectors::matchClass);
		registerSimpleSelector("or", Selectors::matchAnyChildren);
		registerSimpleSelector("and", Selectors::matchAllChildren);
		
	}
	
	public static void registerSimpleSelector(String name, BiPredicate<Element, Selector> predicate)
	{
		AssetFactories.SELECTORS.register(MondobookMod.MODID, name, SimpleSelector.getFactory(predicate));
	}
}
