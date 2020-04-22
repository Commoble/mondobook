package com.github.commoble.mondobook.client.api;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;

public interface DrawableRenderer
{
	public Screen getScreen();
	public ItemRenderer getItemRenderer();
}
