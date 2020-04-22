package com.github.commoble.mondobook.client.content;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.commoble.mondobook.SimpleJsonDataManager;
import com.github.commoble.mondobook.client.api.internal.RawElement;
import com.github.commoble.mondobook.util.ExceptionUtil;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

public class RawElementTests
{
	
	public static RawElement getRaw(String json)
	{
		return SimpleJsonDataManager.GSON.fromJson(json, RawElement.class);
	}
	
	@Test
	void should_ParseItemName_When_ReadingFromJSON()
	{
		RawElement element = getRaw("{\"type\": \"mondobook:item\", \"data\": \"minecraft:potion\"}");
		
		String expected = "minecraft:potion";
		String actual = element.getData();
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	void should_ParseNBTAttribute_When_ReadingFromJSON()
	{
		// given this big json string
		String input = "		{\r\n" + 
			"			\"type\": \"mondobook:item\",\r\n" + 
			"			\"data\": \"minecraft:potion\",\r\n" + 
			"			\"attributes\": {\"tag\": \"{Potion:\\\"minecraft:fire_resistance\\\"}\"}\r\n" + 
			"		}";
		RawElement element = getRaw(input);
		
		// the NBT tag should have the key-value pair {"Potion": "minecraft:fire_resistance"}
		
		String key = "Potion";
		String expected = "minecraft:fire_resistance";
		
		CompoundNBT tag = ExceptionUtil.getUnlessThrow(() -> JsonToNBT.getTagFromJson(element.getAttributes().getOrDefault("tag", "{}")))
			.orElse(new CompoundNBT());
		
		String actual = tag.getString(key);
		
		Assertions.assertEquals(expected, actual);
	}
}
