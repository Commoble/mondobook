package com.github.commoble.mondobook.client.util;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;

public class KeyUtil
{
	public static PageDirection getKeyDirection(int key)
	{
		if (isLeft(key))
		{
			return PageDirection.LEFT;
		}
		else if (isRight(key))
		{
			return PageDirection.RIGHT;
		}
		else
		{
			return PageDirection.NONE;
		}
		
	}
	
	// TODO make more configurable
	public static boolean isLeft(int key)
	{
		// currently returns true if the the key is page up, left arrow, or the player's keybind for left
		return key == Minecraft.getInstance().gameSettings.keyBindLeft.getKey().getKeyCode()
			|| key == GLFW.GLFW_KEY_PAGE_UP
			|| key == GLFW.GLFW_KEY_LEFT;
	}
	
	// TODO make more configurable
	public static boolean isRight(int key)
	{
		// currently returns true if the the key is page up, left arrow, or the player's keybind for right
		return key == Minecraft.getInstance().gameSettings.keyBindRight.getKey().getKeyCode()
			|| key == GLFW.GLFW_KEY_PAGE_DOWN
			|| key == GLFW.GLFW_KEY_RIGHT;
	}
}
