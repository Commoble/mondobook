package com.github.commoble.mondobook.util;

import java.util.Optional;
import java.util.concurrent.Callable;

public class ExceptionUtil
{
	public static <T> Optional<T> getUnlessThrow(Callable<T> getter)
	{
		try
		{
			T output = getter.call();
			return Optional.ofNullable(output);
		}
		catch(Exception e)
		{
			return Optional.empty();
		}
	}
}
