package com.github.commoble.mondobook;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.commoble.mondobook.client.ClientEventHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MondobookMod.MODID)
public class MondobookMod
{
	public static final String MODID = "mondobook";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public MondobookMod()
    {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		
		subscribeDeferredRegisters(modBus,
			ItemRegistrar::registerRegistry);
		
		
		CommonModEvents.subscribeEvents(modBus);
		CommonForgeEvents.subscribeEvents(forgeBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEventHandler.subscribeClientEvents(modBus, forgeBus));
    }
	
	@SafeVarargs
	public static void subscribeDeferredRegisters(IEventBus modBus, Consumer<IEventBus>... subscribers)
	{
		for (Consumer<IEventBus> subscriber : subscribers)
		{
			subscriber.accept(modBus);
		}
	}
}
