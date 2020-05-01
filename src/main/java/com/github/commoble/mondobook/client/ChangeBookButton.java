package com.github.commoble.mondobook.client;

import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.SoundEvents;

public class ChangeBookButton extends Button
{
	public ChangeBookButton(int widthIn, int heightIn, int width, int height, String text, IPressable onPress)
	{
		super(widthIn, heightIn, width, height, text, onPress);
	}
	
	@Override
	public void playDownSound(SoundHandler soundHandler)
	{
        soundHandler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
	}
}
