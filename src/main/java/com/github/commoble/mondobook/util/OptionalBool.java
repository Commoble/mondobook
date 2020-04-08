package com.github.commoble.mondobook.util;

import java.util.Optional;
import java.util.function.Supplier;

import com.google.gson.annotations.SerializedName;

/** Used primarily for some of the objects-parsed-from-jsons where "false" needs to be different from "not present" **/
public enum OptionalBool implements Supplier<Optional<Boolean>>
{
	@SerializedName("none")	NONE(Optional.empty()),
	@SerializedName("false")FALSE(Optional.of(false)),
	@SerializedName("true")	TRUE(Optional.of(true));
	
	private Optional<Boolean> optional;
	
	OptionalBool(Optional<Boolean> optional)
	{
		this.optional = optional;
	}

	@Override
	public Optional<Boolean> get()
	{
		return this.optional;
	}
}
