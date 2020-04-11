package com.github.commoble.mondobook.client.api;

/**
 * CSS-like style selector specificity. Selectors are to be compared based on the
 * number of ids, classes, and element types they are matching. A selector that matches
 * one id has a greater specificity than a selector that matches any number of classes (but
 * no ids), a selector that matches one class has a greater specificity than a selector that matches
 * any number of elements (but no ids or classes).
 */
public class Specificity implements Comparable<Specificity>
{
	public static final Specificity NONE = new Specificity(0,0,0);
	public static final Specificity ONE_ELEMENT = new Specificity(0,0,1);
	public static final Specificity ONE_CLASS = new Specificity(0,1,0);
	public static final Specificity ONE_ID = new Specificity(1,0,0);
	
	private final int ids;
	private final int classes;
	private final int elements;
	
	public Specificity(int ids, int classes, int elements)
	{
		this.ids = ids;
		this.classes = classes;
		this.elements = elements;
	}
	
	public Specificity add(Specificity other)
	{
		return new Specificity(this.ids + other.ids, this.classes + other.classes, this.elements + other.elements);
	}

	@Override
	public int compareTo(Specificity other)
	{
		if (this.ids != other.ids)
		{
			return this.ids - other.ids;
		}
		else if (this.classes != other.classes)
		{
			return this.classes - other.classes;
		}
		else
		{
			return this.elements - other.elements;
		}
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof Specificity)
		{
			Specificity other = (Specificity)object;
			return this.ids == other.ids && this.classes == other.classes && this.elements == other.elements;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int hashCode()
	{
		return 31*(31*this.ids + this.classes) + this.elements;
	}
}
