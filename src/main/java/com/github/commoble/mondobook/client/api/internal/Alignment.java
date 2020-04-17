package com.github.commoble.mondobook.client.api.internal;

import com.google.gson.annotations.SerializedName;

public enum Alignment
{
	@SerializedName("left") LEFT (Alignment::getLeftAlignmentStart),
	@SerializedName("center") CENTER(Alignment::getCenterAlignmentStart),
	@SerializedName("right") RIGHT(Alignment::getRightAlignmentStart);
	
	private Aligner aligner;
	
	Alignment(Aligner aligner)
	{
		this.aligner = aligner;
	}
	
	public int getLeft(int startX, int endX, int elementWidth)
	{
		return this.aligner.getLeft(startX, endX, elementWidth);
	}
	
	private static int getLeftAlignmentStart(int startX, int endX, int elementWidth)
	{
		return startX;
	}
	
	private static int getCenterAlignmentStart(int startX, int endX, int elementWidth)
	{
		int maxWidth = endX - startX;
		int fillerWidth = maxWidth - elementWidth;
		return startX + fillerWidth / 2;
	}
	
	private static int getRightAlignmentStart(int startX, int endX, int elementWidth)
	{
		return endX - elementWidth;
	}
	
	@FunctionalInterface
	private static interface Aligner
	{
		/**
		 * Gets the x-position in pixels to align the left side of something to
		 * @param startX The leftmost possible position the left side of something can be aligned to
		 * @param endX The rightmost possible position the right side of something can be aligned to
		 * @param elementWidth The width of the thing we're aligning
		 * @return The x-position we should align the left side of the thing to
		 */
		int getLeft(int startX, int endX, int elementWidth);
	}
}
