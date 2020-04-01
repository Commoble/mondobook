package com.github.commoble.mondobook;

import com.github.commoble.mondobook.network.OpenLoreBookS2CPacket;
import com.github.commoble.mondobook.network.PacketHandler;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonModEvents
{
	public static void subscribeEvents(IEventBus modBus)
	{
		modBus.addListener(CommonModEvents::onCommonSetup);
	}
	
	public static void onCommonSetup(FMLCommonSetupEvent event)
	{
		// register packets
		int packetID = 0;
		PacketHandler.INSTANCE.registerMessage(packetID++,
			OpenLoreBookS2CPacket.class,
			OpenLoreBookS2CPacket::write,
			OpenLoreBookS2CPacket::new,
			OpenLoreBookS2CPacket::handle);
	}
}
