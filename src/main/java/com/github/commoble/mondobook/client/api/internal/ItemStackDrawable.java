package com.github.commoble.mondobook.client.api.internal;

import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;

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
	public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		ItemRenderer itemRenderer = renderer.getItemRenderer();
		itemRenderer.renderItemIntoGUI(this.stack, startX+1, startY+1);
        itemRenderer.renderItemOverlayIntoGUI(this.style.getFontRenderer(), this.stack, startX, startY, null);
	}

	@Override
	public int getHeight()
	{
		return 18;
	}

	@Override
	public int getWidth()
	{
		return 18;
	}

	@Override
	public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
	{
		if (MathUtil.isWithin(mouseX, mouseY, startX, startY, this.getWidth(), this.getHeight()))
		{
			RenderSystem.pushMatrix();
			RenderSystem.translated(0D, 0D, 600D);
			renderer.renderItemTooltip(this.stack, mouseX, mouseY);
			RenderSystem.popMatrix();
		}
	}

	@Override
	public Optional<BookStyle> getStyle()
	{
		return Optional.of(this.style);
	}
}
