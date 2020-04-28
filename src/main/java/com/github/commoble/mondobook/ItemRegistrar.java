package com.github.commoble.mondobook;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistrar
{
	public static final ItemGroup MONDOBOOK_TAB = new ItemGroup(MondobookMod.MODID)
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(Items.BOOK);
		}
	};

	private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MondobookMod.MODID);

	public static void registerRegistry(IEventBus modBus)
	{
		ITEMS.register(modBus);
	}
	public static final RegistryObject<MondobookItem> MONDOBOOK = ITEMS.register(ObjectNames.MONDO_BOOK, () ->
		new MondobookItem(new Item.Properties().group(MONDOBOOK_TAB)));
}
