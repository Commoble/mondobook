package com.github.commoble.mondobook.client.api.internal;

import net.minecraft.util.ResourceLocation;

/**
 * To be deserialized via GSON.
 * Contains data for the background and shape of a book,
 * describing how and where the book is to be rendered by the gui screen.
 */
public class RawFormat
{
	/** Uses texture resourcelocation format, e.g. "minecraft:textures/gui/book.png" **/
	private String texture;
	
	public int texture_u;
	public int texture_v;
	public int texture_width;
	public int texture_height;
	public int content_start_x;
	public int content_start_y;
	public int content_width;
	public int content_height;
	public int book_start_on_screen_y;
	public int content_offset_from_center;
	public boolean show_page_numbers;
	public int page_number_y;
	public int page_number_end_x;
	private String page_number_color;
	
	private transient ResourceLocation textureRL;
	private Integer parsedPageNumberColor;
	
	public ResourceLocation getTexture()
	{
		if (this.textureRL == null)
		{
			if (this.texture == null)
			{
				this.texture = "minecraft:textures/gui/book.png";
			}
			
			this.textureRL = new ResourceLocation(this.texture);
		}
		
		return this.textureRL;
	}
	
	public int getPageNumberColor()
	{
		if (this.parsedPageNumberColor == null)
		{
			if (this.page_number_color == null)
			{
				this.page_number_color = "0";
			}
			
			this.parsedPageNumberColor = RawStyle.StyleBuilder.parseColorString(this.page_number_color) & 0xFF000000;
		}
		
		return this.parsedPageNumberColor;
	}
}
