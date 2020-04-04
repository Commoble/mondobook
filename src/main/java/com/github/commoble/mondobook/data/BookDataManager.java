package com.github.commoble.mondobook.data;

import java.util.HashMap;
import java.util.Map;

import com.github.commoble.mondobook.data.raw.RawBook;
import com.github.commoble.mondobook.util.MapUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class BookDataManager extends JsonReloadListener
{
	public static final Gson GSON = new GsonBuilder().create();
	public static final BookDataManager INSTANCE = new BookDataManager();
	
	/** This is the name of the folders that the resource loader looks in, e.g. assets/modid/FOLDER **/
	public static final String FOLDER = "mondobooks";
	
	/** The book data that we parsed from json last time resources were reloaded **/
	private Map<ResourceLocation, RawBook> books = new HashMap<>();
	
	public RawBook getBook(ResourceLocation id)
	{
		return this.books.get(id);
	}

	public BookDataManager()
	{
		super(GSON, FOLDER);
	}

	/** Called on resource reload, the jsons have already been found for us and we just need to parse them in here **/
	@Override
	protected void apply(Map<ResourceLocation, JsonObject> jsons, IResourceManager manager, IProfiler profiler)
	{
		this.books = MapUtil.mapValues(jsons, this::getBookFromJson);
	}

	/** Use a json object (presumably one from an assets/modid/mondobooks folder) to generate a book object **/
	protected RawBook getBookFromJson(JsonObject json)
	{
		return GSON.fromJson(json, RawBook.class);
	}
}
