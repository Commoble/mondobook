package com.github.commoble.mondobook.client.api;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;

public interface DrawableRenderer
{
	public FontRenderer getFont();
	
	public AbstractGui getGUI();
}
