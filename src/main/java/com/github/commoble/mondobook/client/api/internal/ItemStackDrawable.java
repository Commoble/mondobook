package com.github.commoble.mondobook.client.api.internal;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class ItemStackDrawable implements Drawable
{
	private ItemStack stack;
	private BookStyle style;
	
	public ItemStackDrawable(ItemStack stack, BookStyle style)
	{
		this.stack = stack;
		this.style = style;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY, int maxWidth)
	{
		ItemRenderer itemRenderer = renderer.getItemRenderer();
		Minecraft minecraft = Minecraft.getInstance();
        itemRenderer.renderItemAndEffectIntoGUI(minecraft.player, this.stack, startX, startY);
        itemRenderer.renderItemOverlayIntoGUI(this.style.getFontRenderer(), this.stack, startX, startY, null);
	}

	@Override
	public int getHeight()
	{
		return 16;
	}

	@Override
	public int getWidth()
	{
		return 16;
	}

}
