package com.github.commoble.mondobook.client;

import java.util.ArrayList;
import java.util.List;

import com.github.commoble.mondobook.client.book.BakedBook;
import com.github.commoble.mondobook.client.book.BakedPage;
import com.github.commoble.mondobook.data.BookDataManager;
import com.github.commoble.mondobook.util.ListUtil;
import com.github.commoble.mondobook.util.MatchedPair;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LoreBookScreen extends Screen
{
	public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation("textures/gui/book.png");
	
	public static final int TEXT_START = 36;
	public static final int TEXT_END = 150;
	public static final int TEXT_WIDTH = TEXT_END - TEXT_START;
	public static final int BOOK_TEXTURE_START = 26;
	public static final int BOOK_TEXTURE_END = 166;
	public static final int BOOK_TEXTURE_WIDTH = BOOK_TEXTURE_END - BOOK_TEXTURE_START;
	public static final int PAGE_NUMBER_END = 148;
	public static final float PAGE_NUMBER_Y_START = 18F;
	public static final int LEFT_PAGE_OFFSET = BOOK_TEXTURE_WIDTH;
	public static final int RIGHTWARD_SHIFT = LEFT_PAGE_OFFSET/2;
	public static final int MAX_LINES_ON_PAGE = 14;
	public static final int PAGE_TEXT_START = 32;
	public static final int LINE_HEIGHT = 9;
	public static final int TEXT_OFFSET_FROM_BOOK_CENTER = 10;

	private int cachedPage = -1;	// the left page!
	private int currentPage = 0;	// also the left page

	private MatchedPair<List<ITextComponent>> cachedPageLines = MatchedPair.of(ArrayList::new);
	
	private final ResourceLocation bookID;
	private BakedBook book;
	
	public LoreBookScreen(ResourceLocation bookID)
	{
		super(new TranslationTextComponent(bookID.toString()));
		this.bookID = bookID;
	}
	
	// called by Minecraft after the screen is constructed and its font has been set
	@Override
	protected void init()
	{
		this.book = new BakedBook(BookDataManager.INSTANCE.getBook(this.bookID), MAX_LINES_ON_PAGE, TEXT_WIDTH, this.font);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BOOK_TEXTURES);
		int screenX = (this.width - BOOK_TEXTURE_WIDTH) / 2;
		int screenY = 2;
		int imageX = BOOK_TEXTURE_START;
		int imageY = 0;
		int screenWidth = BOOK_TEXTURE_WIDTH;
		int screenHeight = 192;
		
		int bookCenter = this.width / 2;
		
		// minecraft starts drawing text from x/y = 36/ to 148/
		int leftPageNumber = this.getLeftPageNumber();
		MatchedPair<Integer> pageNumbers = MatchedPair.of(leftPageNumber, leftPageNumber+1);
		MatchedPair<String> pageNumberStrings = pageNumbers.map(this::getPageNumberText);
		MatchedPair<Integer> pageNumberWidths = pageNumberStrings.map(this::getStringWidth);
		
		// refresh the cache if necessary
		if (this.cachedPage != this.currentPage)
		{
			this.cachedPageLines = pageNumbers.map(this::getPageText);
		}

		this.cachedPage = this.currentPage;
		
		// draw the left page
		RenderSystem.pushMatrix();
			RenderSystem.translatef(this.width - LEFT_PAGE_OFFSET, 0, 0);
			RenderSystem.scalef(-1F,1,1);
			this.blit(screenX-RIGHTWARD_SHIFT, screenY, imageX, imageY, screenWidth, screenHeight);
		RenderSystem.popMatrix();
		
		// draw the right page
		RenderSystem.pushMatrix();
			this.blit(screenX+RIGHTWARD_SHIFT, screenY, imageX, imageY, screenWidth, screenHeight);
		RenderSystem.popMatrix();
		
		// draw the page number displays
		MatchedPair<Integer> pageNumberOffsets = MatchedPair.of(
			screenX + RIGHTWARD_SHIFT - TEXT_WIDTH,
			screenX + RIGHTWARD_SHIFT/2 - pageNumberWidths.getRight() + PAGE_NUMBER_END);
		pageNumberStrings.consumeWith(pageNumberOffsets, (string, offset) -> this.font.drawString(string, offset, PAGE_NUMBER_Y_START, 0));

		MatchedPair<Integer> pageTextOffsets = MatchedPair.of(
			bookCenter - TEXT_WIDTH - TEXT_OFFSET_FROM_BOOK_CENTER,
			bookCenter + TEXT_OFFSET_FROM_BOOK_CENTER);// + RIGHTWARD_SHIFT/2 + PAGE_TEXT_START_X + 10);
		
		this.cachedPageLines.consumeWith(pageTextOffsets, this::drawPageLines);

		// this next part seems to be for rendering tooltips but it doesn't seem to work
//		ITextComponent itextcomponent2 = this.func_214154_c((double) p_render_1_, (double) p_render_2_);
//		if (itextcomponent2 != null)
//		{
//			this.renderComponentHoverEffect(itextcomponent2, p_render_1_, p_render_2_);
//		}

		super.render(mouseX, mouseY, partialTicks);
	}
	
	protected void drawPageLines(List<ITextComponent> lines, int xOffset)
	{
		ListUtil.forEachIndex(lines, (line, i) -> this.font.drawString(line.getFormattedText(), xOffset, PAGE_TEXT_START + LINE_HEIGHT*i, 0));
	}
	
//	public ITextComponent getPageText(int page)
//	{
//		return page == 0
//			? new StringTextComponent("§lLorem ipsum §ldolor sit amet, conse§rctetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in")
//			: new StringTextComponent("ᛚᛟᚱᛖᛗ ᛁᛈᛋᚢᛗ ᛞᛟᛚᛟᚱ ᛋᛁᛏ ᚨᛗᛖᛏ ᚲᛟᚾᛋᛖᚲᛏᛖᛏᚢᚱ ᚨᛞᛁᛈᛁᛋᚲᛁᚾᚷ ᛖᛚᛁᛏ ᛋᛖᛞ ᛞᛟ ᛖᛁᚢᛋᛗᛟᛞ ᛏᛖᛗᛈᛟᚱ ᛁᚾᚲᛁᛞᛁᛞᚢᚾᛏ ᚢᛏ ᛚᚨᛒᛟᚱᛖ ᛖᛏ ᛞᛟᛚᛟᚱᛖ ᛗᚨᚷᚾᚨ ᚨᛚᛁᛝᚢᚨ ᚢᛏ ᛖᚾᛁᛗ ᚨᛞ ᛗᛁᚾᛁᛗ ᛒᛖᚾᛁᚨᛗ ᛝᚢᛁᛋ ᚾᛟᛋᛏᚱᚢᛞ ᛖᚦᛖᚱᚲᛁᛏᚨᛏᛁᛟᚾ ᚢᛚᛚᚨᛗᚲᛟ ᛚᚨᛒᛟᚱᛁᛋ ᚾᛁᛋᛁ ᚢᛏ ᚨᛚᛁᛝᚢᛁᛈ ᛖᚦ ᛖᚨ ᚲᛟᛗᛗᛟᛞᛟ ᚲᛟᚾᛋᛖᛝᚢᚨᛏ ᛞᚢᛁᛋ ᚨᚢᛏᛖ ᛁᚱᚢᚱᛖ ᛞᛟᛚᛟᚱ ᛁᚾ ᚱᛖᛈᚱᛖᚻᛖᚾᛞᛖᚱᛁᛏ ᛁᚾ");
//	}
	
	public List<ITextComponent> getPageText(int page)
	{
		List<BakedPage> pages = this.book.getPages();
		return page < pages.size()
			? this.book.getPages().get(page).getLines()
			: Lists.newArrayList();
//		return RenderComponentsUtil.splitText(this.getPageText(page), TEXT_WIDTH, this.font, true, true);
	}
	
	// returns the number of pages with things on them, rounded up to an even number
	// this way the last page of a book with an odd number of "actual pages" doesn't display e.g. "page 2 of 1" on the last page
	// (slightly easier than just not rendering the last page number)
	public int getPageCountForPageNumberRendering()
	{
		int actualCount = this.getActualPageCount();
		return (actualCount % 2 == 0) ? actualCount : actualCount+1;
	}
	
	// returns the number of pages with things on them
	public int getActualPageCount()
	{
		return this.book.getPages().size();
	}
	
	public int getLeftPageNumber()
	{
		return 0;
	}
	
	public String getPageNumberText(int pageNumber)
	{
		return I18n.format("book.pageIndicator", pageNumber + 1, Math.max(this.getPageCountForPageNumberRendering(), 1));
	}
	
	public int getStringWidth(String s)
	{
		return this.font.getStringWidth(this.font.getBidiFlag() ? this.font.bidiReorder(s) : s);
	}
}
