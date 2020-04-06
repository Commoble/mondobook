package com.github.commoble.mondobook.client.api;

import com.github.commoble.mondobook.client.elements.RawElement;
import com.github.commoble.mondobook.client.selectors.RawSelector;
import com.github.commoble.mondobook.client.selectors.Selectors;
import com.github.commoble.mondobook.client.selectors.SimpleSelector;

public class AssetFactories
{
	public static final AssetFactoryRegistry<RawElement, Element> ELEMENTS = new AssetFactoryRegistry<>(RawElement::getTypeID, element -> Element.NONE);
	public static final AssetFactoryRegistry<RawSelector, Selector> SELECTORS = new AssetFactoryRegistry<>(RawSelector::getTypeID, raw -> new SimpleSelector(raw, Selectors::neverMatch));
}
