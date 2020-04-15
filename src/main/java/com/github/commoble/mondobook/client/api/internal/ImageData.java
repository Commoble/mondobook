package com.github.commoble.mondobook.client.content;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

// to be serialized via a standard GSON parser
public class ImageData implements Drawable
{
	private String texture; // the ResourceLocation of a texture file
	private int u; // the x of the top-left coordinate to read the texture from, defaults to 0
	private int v; // the y of the top-left coordinate to read the texture from, defaults to 0
	private int width; // how much of the texture to draw in width, defaults to 0
	private int height; // how much of the texture to draw in height, defaults to 0
	private boolean translucent; // false if omitted, allows translucency in textures

	private transient ResourceLocation textureID;

	public ResourceLocation getTextureID()
	{
		if (this.textureID == null)
		{
			this.textureID = new ResourceLocation(this.getTextureString());
		}
		return this.textureID;
	}

	private String getTextureString()
	{
		if (this.texture == null)
		{
			this.texture = "";
		}
		return this.texture;
	}

	@Override
	public void render(DrawableRenderer renderer, int startX, int startY)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(this.getTextureID());
		RenderSystem.pushMatrix();
		if (this.translucent)
		{
			RenderSystem.enableBlend();
		}
		renderer.getGUI().blit(startX, startY, this.u, this.v, this.width, this.height);
		if (this.translucent)
		{
			RenderSystem.disableBlend();
		}
		RenderSystem.popMatrix();
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}
}
