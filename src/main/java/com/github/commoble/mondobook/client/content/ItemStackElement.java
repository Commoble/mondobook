package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.Map;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.ItemStackDrawable;
import com.github.commoble.mondobook.util.ExceptionUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackElement extends Element
{
	public ItemStack stack;
	
	public ItemStackElement(ElementPrimer primer)
	{
		super(primer);
		Map<String, String> attributes = primer.getAttributes();
		int stackSize = Integer.parseInt(attributes.getOrDefault("size", "1"));
		CompoundNBT tag = ExceptionUtil.getUnlessThrow(() -> JsonToNBT.getTagFromJson(attributes.getOrDefault("tag", "{}")))
			.orElse(new CompoundNBT());
		this.stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(primer.getData())), stackSize);
		this.stack.setTag(tag);
		
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth, boolean shrinkwrap)
	{
		return this.getStyle().getSingleStyledDrawable(new ItemStackDrawable(this.stack, this.getStyle()), containerWidth, shrinkwrap);
	}

}
