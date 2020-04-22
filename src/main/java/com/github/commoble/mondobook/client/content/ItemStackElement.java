package com.github.commoble.mondobook.client.content;

import java.util.List;
import java.util.Map;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.ItemStackDrawable;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.util.ExceptionUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackElement extends Element
{
	public ItemStack stack;
	
	public ItemStackElement(RawElement raw)
	{
		super(raw);
		Map<String, String> attributes = raw.getAttributes();
		int stackSize = Integer.parseInt(attributes.getOrDefault("size", "1"));
		CompoundNBT tag = ExceptionUtil.getUnlessThrow(() -> JsonToNBT.getTagFromJson(attributes.getOrDefault("tag", "{}")))
			.orElse(new CompoundNBT());
		this.stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(raw.getData())), stackSize);
		this.stack.setTag(tag);
		
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		return style.getSingleStyledDrawable(new ItemStackDrawable(this.stack, style));
	}

}
