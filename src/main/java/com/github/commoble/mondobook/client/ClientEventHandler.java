package com.github.commoble.mondobook.client;

import com.github.commoble.mondobook.MondobookMod;
import com.github.commoble.mondobook.client.api.DrawableRegistry;
import com.github.commoble.mondobook.client.book.elements.ImageElement;
import com.github.commoble.mondobook.client.book.elements.NewPageElement;
import com.github.commoble.mondobook.client.book.elements.TextElement;

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
		DrawableRegistry.register(MondobookMod.MODID, "new_page", element -> NewPageElement::getNewPage);
		DrawableRegistry.register(MondobookMod.MODID, "text", TextElement::new);
		DrawableRegistry.register(MondobookMod.MODID, "image", ImageElement::new);
	}
}
