package com.github.commoble.mondobook;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistrar
{

	private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MondobookMod.MODID);

	public static void registerRegistry(IEventBus modBus)
	{
		ITEMS.register(modBus);
	}
	public static final RegistryObject<MondobookItem> LORE_BOOK = ITEMS.register(ObjectNames.MONDO_BOOK, () ->
		new MondobookItem(new Item.Properties().group(ItemGroup.MISC)));
}
