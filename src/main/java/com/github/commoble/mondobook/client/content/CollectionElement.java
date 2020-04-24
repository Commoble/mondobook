package com.github.commoble.mondobook.client.content;

import java.util.List;

import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.api.Element;
import com.github.commoble.mondobook.client.api.internal.BookStyle;
import com.github.commoble.mondobook.client.api.internal.ColumnDrawable;
import com.github.commoble.mondobook.client.api.internal.ElementPrimer;
import com.github.commoble.mondobook.client.api.internal.RowDrawable;
import com.github.commoble.mondobook.util.ListUtil;
import com.google.common.collect.Lists;

public class CollectionElement extends Element
{

	public CollectionElement(ElementPrimer primer)
	{
		super(primer);
	}

	@Override
	public List<Drawable> getColumnOfDrawables(DrawableRenderer renderer, int containerWidth)
	{
		BookStyle style = this.getStyle();
		List<Element> children = this.getChildren();
		
		int interiorWidth = style.getInteriorWidth(containerWidth);
		
		List<Drawable> cells = ListUtil.map(children, (child -> new ColumnDrawable(child.getColumnOfDrawables(renderer, interiorWidth))));
		
		if (cells.isEmpty())
		{
			return cells;
		}
		
		// TODO surely we can do better than letting all cells be the weidth of the widest cell?
		int widestWidth = cells.stream()
			.map(Drawable::getWidth)
			.reduce(0, Math::max);
		
		int cellsPerRow = interiorWidth / widestWidth;
		
		if (cellsPerRow < 1)
		{
			cellsPerRow = 1;
		}
		
		return style.styleMultipleDrawables(Lists.partition(cells, cellsPerRow), (list, parentStyle) -> new RowDrawable(list));
	}

}
