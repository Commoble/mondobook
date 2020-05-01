package com.github.commoble.mondobook.client;

import com.github.commoble.mondobook.client.api.internal.RawFormat;
import com.github.commoble.mondobook.client.util.PageDirection;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

public class FormattedChangePageButton extends Button
{
	private ResourceLocation texture;
	private final int u;
	private final int v;
	private final int hover_u;
	private final int hover_v;
	
	public FormattedChangePageButton(RawFormat format, int x, int y, PageDirection direction, IPressable onPress)
	{
		super(x, y, format.page_button_width, format.page_button_height, "", onPress);
		this.texture = format.getTexture();
		if (direction == PageDirection.RIGHT)
		{
			this.u = format.next_page_button_u;
			this.v = format.next_page_button_v;
			this.hover_u = format.next_page_button_hover_u;
			this.hover_v = format.next_page_button_hover_v;
		}
		else
		{
			this.u = format.previous_page_button_u;
			this.v = format.previous_page_button_v;
			this.hover_u = format.previous_page_button_hover_u;
			this.hover_v = format.previous_page_button_hover_v;
		}
	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(this.texture);
		boolean hover = this.isHovered();
		int u = hover ? this.hover_u : this.u;
		int v = hover ? this.hover_v : this.v;

		this.blit(this.x, this.y, u, v, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void playDownSound(SoundHandler soundHandler)
	{
		soundHandler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
	}
}
