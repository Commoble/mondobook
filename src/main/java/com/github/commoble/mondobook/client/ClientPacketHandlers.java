package com.github.commoble.mondobook.client;

import java.util.function.Supplier;

import com.github.commoble.mondobook.client.book.raw.RawBook;
import com.github.commoble.mondobook.data.BookDataManager;
import com.github.commoble.mondobook.network.OpenLoreBookS2CPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

/** Utility class for adding a degree of separation between packet classes and things that happen on the client only **/
public class ClientPacketHandlers
{
	public static void handlePacket(Supplier<NetworkEvent.Context> context, Runnable runnable)
	{
		context.get().enqueueWork(runnable);
		context.get().setPacketHandled(true);
	}
	
	public static void handleOpenLoreBookPacket(OpenLoreBookS2CPacket packet, Supplier<NetworkEvent.Context> context)
	{
		RawBook book = BookDataManager.INSTANCE.getBook(new ResourceLocation("mondobook:lorem_ipsum"));
		handlePacket(context, () ->
		{
			Minecraft.getInstance().displayGuiScreen(new LoreBookScreen(packet.getBookID()));
		});
	}
}
