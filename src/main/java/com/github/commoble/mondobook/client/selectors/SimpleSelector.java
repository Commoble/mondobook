package com.github.commoble.mondobook.client.selectors;

import java.util.function.BiPredicate;
import java.util.function.Function;

import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;

public class SimpleSelector extends Selector
{
	BiPredicate<Element, Selector> predicate;
	
	public SimpleSelector(RawSelector raw, BiPredicate<Element, Selector> predicate)
	{
		super(raw);
		this.predicate = predicate;
	}

	@Override
	public boolean test(Element element)
	{
		return this.predicate.test(element, this);
	}

	public static Function<RawSelector, SimpleSelector> getFactory(BiPredicate<Element, Selector> predicate)
	{
		return raw -> new SimpleSelector(raw, predicate);
	}
}
