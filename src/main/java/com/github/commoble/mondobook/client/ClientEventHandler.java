package com.github.commoble.mondobook.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler
{
	public static void subscribeClientEvents(IEventBus modBus, IEventBus forgeBus)
	{
		modBus.addListener(ClientEventHandler::onClientSetup);
	}

	private static void onClientSetup(FMLClientSetupEvent event)
	{
	}
}
