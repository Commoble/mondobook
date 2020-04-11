package com.github.commoble.mondobook.client.content;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.Selector;
import com.github.commoble.mondobook.client.api.Specificity;
import com.github.commoble.mondobook.client.book.RawSelector;

public class SimpleSelector extends Selector
{
	BiPredicate<Element, Selector> predicate;
	BiFunction<Element, Selector, Specificity> specificityGetter;
	
	public SimpleSelector(RawSelector raw, BiPredicate<Element, Selector> predicate, BiFunction<Element, Selector, Specificity> specificityGetter)
	{
		super(raw);
		this.predicate = predicate;
		this.specificityGetter = specificityGetter;
	}

	@Override
	public boolean test(Element element)
	{
		return this.predicate.test(element, this);
	}

	public static Function<RawSelector, SimpleSelector> getFactory(BiPredicate<Element, Selector> predicate, BiFunction<Element, Selector, Specificity> specificityGetter)
	{
		return raw -> new SimpleSelector(raw, predicate, specificityGetter);
	}

	@Override
	public Specificity getSpecificity(Element element)
	{
		return this.specificityGetter.apply(element, this);
	}
}
