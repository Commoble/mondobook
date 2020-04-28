package com.github.commoble.mondobook.client.jei;

import com.github.commoble.mondobook.ItemRegistrar;
import com.github.commoble.mondobook.MondobookItem;
import com.github.commoble.mondobook.MondobookMod;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIProxy implements IModPlugin
{
	public static final ResourceLocation ID = new ResourceLocation(MondobookMod.MODID, MondobookMod.MODID);

	@Override
	public ResourceLocation getPluginUid()
	{
		return ID;
	}
	
	/**
	 * If your item has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
	 */
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration)
	{
		registration.registerSubtypeInterpreter(ItemRegistrar.MONDOBOOK.get(), stack -> MondobookItem.getBookIDFromItemStack(stack).toString());
	}
}
