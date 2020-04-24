package com.github.commoble.mondobook.client.api.internal;

import java.util.Optional;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

// to be serialized via a standard GSON parser
public class ImageData
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
	
	public static class ImageDrawable implements Drawable
	{
		private final ImageData image;
		private final BookStyle style;
		
		public ImageDrawable(ImageData image, BookStyle style)
		{
			this.image = image;
			this.style = style;
		}
		

		@Override
		public void renderSelf(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
		{
			Minecraft.getInstance().getTextureManager().bindTexture(this.image.getTextureID());
			RenderSystem.pushMatrix();
			if (this.image.translucent)
			{
				RenderSystem.enableBlend();
			}
			renderer.getScreen().blit(startX, startY, this.image.u, this.image.v, this.image.width, this.image.height);
			if (this.image.translucent)
			{
				RenderSystem.disableBlend();
			}
			RenderSystem.popMatrix();
		}

		@Override
		public int getHeight()
		{
			return this.image.height;
		}

		@Override
		public int getWidth()
		{
			return this.image.width;
		}

		@Override
		public void renderTooltip(DrawableRenderer renderer, int startX, int startY, int maxWidth, int mouseX, int mouseY)
		{
			// TODO alt text?
		}

		@Override
		public Optional<BookStyle> getStyle()
		{
			return Optional.of(this.style);
		}
	}
}
