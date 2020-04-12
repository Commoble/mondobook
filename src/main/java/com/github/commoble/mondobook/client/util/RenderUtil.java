package com.github.commoble.mondobook.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderUtil
{
	public static FontRenderer getFontRenderer(ResourceLocation id)
	{
		return Minecraft.getInstance().getFontResourceManager().getFontRenderer(id);
	}
}
