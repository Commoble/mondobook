package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.ItemStackDrawable;
import com.github.commoble.mondobook.client.api.internal.RawElement;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackElement extends Element
{
	public ItemStack stack;
	
	public ItemStackElement(RawElement raw)
	{
		super(raw);
		this.stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(raw.getData())));
		
		// TODO allow stack sizes, maybe NBT?
	}

	@Override
	public List<Drawable> getAsDrawables(DrawableRenderer renderer, BookStyle style, int containerWidth)
	{
		return style.getSingleStyledDrawable(new ItemStackDrawable(this.stack, style));
	}

}
