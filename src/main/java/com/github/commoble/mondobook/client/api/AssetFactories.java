package com.github.commoble.mondobook.client.api;

import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RawSelector;
import com.github.commoble.mondobook.client.api.internal.SimpleSelector;
import com.github.commoble.mondobook.client.api.internal.Specificity;
import com.github.commoble.mondobook.client.content.Selectors;

public class AssetFactories
{
	public static final AssetFactoryRegistry<ElementPrimer, Element> ELEMENTS = new AssetFactoryRegistry<>(ElementPrimer::getTypeID, element -> Element.NONE);
	public static final AssetFactoryRegistry<RawSelector, Selector> SELECTORS = new AssetFactoryRegistry<>(RawSelector::getTypeID, raw -> new SimpleSelector(raw, Selectors::neverMatch, (element, selector) -> Specificity.NONE));
}
