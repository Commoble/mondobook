package com.github.commoble.mondobook;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class CommonForgeEvents
{
	public static void subscribeEvents(IEventBus forgeBus)
	{
		forgeBus.addListener(CommonForgeEvents::onServerAboutToStart);
	}
	
	public static void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
	}
}
