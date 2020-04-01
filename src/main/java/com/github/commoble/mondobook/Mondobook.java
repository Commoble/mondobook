package com.github.commoble.mondobook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.commoble.mondobook.client.ClientEventHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mondobook.MODID)
public class Mondobook
{
	public static final String MODID = "mondobook";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public Mondobook()
    {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		CommonModEvents.subscribeEvents(modBus);
		CommonForgeEvents.subscribeEvents(forgeBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEventHandler.subscribeClientEvents(modBus, forgeBus));
    }
}
