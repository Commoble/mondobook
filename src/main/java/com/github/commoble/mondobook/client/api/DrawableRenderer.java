package com.github.commoble.mondobook.client.api;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface DrawableRenderer
{
	public Screen getScreen();
	public ItemRenderer getItemRenderer();
	public void renderItemTooltip(ItemStack stack, int mouseX, int mouseY);
	public void newBook(ResourceLocation bookID);
}
