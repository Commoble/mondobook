package com.github.commoble.mondobook.client;

import java.util.List;

import com.github.commoble.mondobook.client.api.AssetManagers;
import com.github.commoble.mondobook.client.api.Drawable;
import com.github.commoble.mondobook.client.api.DrawableRenderer;
import com.github.commoble.mondobook.client.util.KeyUtil;
import com.github.commoble.mondobook.util.MatchedPair;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class MondobookScreen extends Screen implements DrawableRenderer
{
	public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation("textures/gui/book.png");

	public static final int TEXT_START = 36;
	public static final int TEXT_END = 150;
	public static final int TEXT_WIDTH = TEXT_END - TEXT_START;
	public static final int BOOK_TEXTURE_START_X = 26;
	public static final int BOOK_TEXTURE_END_X = 166;
	public static final int BOOK_TEXTURE_START_Y = 1;
	public static final int BOOK_TEXTURE_END_Y = 180;
	public static final int BOOK_TEXTURE_WIDTH = BOOK_TEXTURE_END_X - BOOK_TEXTURE_START_X;
	public static final int BOOK_TEXTURE_HEIGHT = BOOK_TEXTURE_END_Y - BOOK_TEXTURE_START_Y;
	public static final int PAGE_NUMBER_END = 148;
	public static final float PAGE_NUMBER_Y_START = 18F;
	public static final int LEFT_PAGE_OFFSET = BOOK_TEXTURE_WIDTH;
	public static final int RIGHTWARD_SHIFT = LEFT_PAGE_OFFSET / 2;
//	public static final int MAX_LINES_ON_PAGE = 14;
	public static final int PAGE_TEXT_START = 32;
//	public static final int LINE_HEIGHT = 9;
	public static final int PAGE_HEIGHT_IN_PIXELS = 128;	// the space we can put page content into
	public static final int TEXT_OFFSET_FROM_BOOK_CENTER = 10;
	public static final int BLACK = 0x0;

	private ChangePageButton buttonNextPage;
	private ChangePageButton buttonPreviousPage;

	private int cachedPage = -1; // the left page!
	private int currentPage = 0; // also the left page

	private MatchedPair<Drawable> cachedPageDrawables = MatchedPair.of(Drawable.NONE);

	private final ResourceLocation bookID;
	private BakedBook book;

	public MondobookScreen(ResourceLocation bookID)
	{
		super(new TranslationTextComponent(bookID.toString()));
		this.bookID = bookID;
	}

	// called by Minecraft after the screen is constructed and its font has been set
	@Override
	protected void init()
	{
		super.init();
		this.book = new BakedBook(AssetManagers.BOOK_DATA.getData(this.bookID), PAGE_HEIGHT_IN_PIXELS, TEXT_WIDTH, this);
		this.addDoneButton();
		this.addChangePageButtons();
	}

	// from readBookScreen
	protected void addDoneButton()
	{	// the done button closes the screen when you click it
		this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done"), (button) -> this.minecraft.displayGuiScreen((Screen) null)));
	}

	// from readBookScreen, mostly
	protected void addChangePageButtons()
	{
		int i = (this.width - 192) / 2;
		this.buttonNextPage = this.addButton(new ChangePageButton(i + 116, 159, true, (p_214159_1_) -> {
			this.nextPage();
		}, true));
		this.buttonPreviousPage = this.addButton(new ChangePageButton(i + 43, 159, false, (p_214158_1_) -> {
			this.previousPage();
		}, true));
		this.updateButtons();
	}

	/**
	 * Moves the display back (two pages)
	 */
	public void previousPage()
	{
		this.showPage(this.currentPage - 2);

		this.updateButtons();
	}

	/**
	 * Moves the display forward (two pages)
	 */
	public void nextPage()
	{
		this.showPage(this.currentPage + 2);

		this.updateButtons();
	}

	// from readBookScreen
	protected void updateButtons()
	{
		// let's say we have three pages (left, right, left), pageCount is 3
		// if we're on the last pair of pages, currentPage is 2
		// if we have four pages (left,right,left,right), pageCount is 4, currentPage is 2
		// we allow the next page button to be visible if currentPage < (pageCount - 2)
		this.buttonNextPage.visible = this.currentPage < this.getActualPageCount() - 2;
		this.buttonPreviousPage.visible = this.currentPage > 0;
	}

	// from readBookScreen
	@Override
	public boolean keyPressed(int key, int scanCode, int modifiers)
	{
		if (super.keyPressed(key, scanCode, modifiers))
		{
			return true;
		}
		else
		{
			switch (KeyUtil.getKeyDirection(key))
			{
				case LEFT:
					this.buttonPreviousPage.onPress();
					return true;
				case RIGHT:
					this.buttonNextPage.onPress();
					return true;
				default:
					return false;
			}
		}
	}

	/**
	 * Moves the book to the specified page. If the requested page was not in the
	 * book, move to the next best page (i.e. the first or the last page). Returns
	 * true if the book's currently displayed page changed, false if not.
	 */
	public boolean showPage(final int requestedPageNumber)
	{
		final int pageCount = this.getActualPageCount();
		final int pageNumber = MathHelper.clamp(requestedPageNumber, 0, pageCount - 1);
		final int leftPageNumber = pageNumber % 2 == 0 ? pageNumber : pageNumber - 1; // if we're not already on a left page, round down to an even number
		if (leftPageNumber != this.currentPage)
		{
			this.currentPage = pageNumber;
			this.updateButtons();
			this.cachedPage = -1;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		int screenX = (this.width - BOOK_TEXTURE_WIDTH) / 2;
		int screenY = 2;
		int imageX = BOOK_TEXTURE_START_X;
		int imageY = BOOK_TEXTURE_START_Y;
		int screenWidth = BOOK_TEXTURE_WIDTH;
		int screenHeight = BOOK_TEXTURE_HEIGHT;

		int bookCenter = this.width / 2;

		// minecraft starts drawing text from x/y = 36/ to 148/
		int leftPageNumber = this.getLeftPageNumber();
		MatchedPair<Integer> pageNumbers = MatchedPair.of(leftPageNumber, leftPageNumber + 1);
		MatchedPair<String> pageNumberStrings = pageNumbers.map(this::getPageNumberText);
		MatchedPair<Integer> pageNumberWidths = pageNumberStrings.map(this::getStringWidth);

		// refresh the cache if necessary
		if (this.cachedPage != this.currentPage)
		{
			this.cachedPageDrawables = pageNumbers.map(this::getPageDrawables);
		}

		this.cachedPage = this.currentPage;

		this.minecraft.getTextureManager().bindTexture(BOOK_TEXTURES);
		// draw the left page
		RenderSystem.pushMatrix();
		RenderSystem.translatef(this.width - LEFT_PAGE_OFFSET, 0, 0);
		RenderSystem.scalef(-1F, 1, 1);
		this.blit(screenX - RIGHTWARD_SHIFT, screenY, imageX, imageY, screenWidth, screenHeight);
		RenderSystem.popMatrix();

		// draw the right page
		RenderSystem.pushMatrix();
		this.blit(screenX + RIGHTWARD_SHIFT, screenY, imageX, imageY, screenWidth, screenHeight);
		RenderSystem.popMatrix();

		// draw the page number displays
		MatchedPair<Integer> pageNumberOffsets = MatchedPair.of(screenX + RIGHTWARD_SHIFT - TEXT_WIDTH,
			screenX + RIGHTWARD_SHIFT / 2 - pageNumberWidths.getRight() + PAGE_NUMBER_END);
		pageNumberStrings.consumeWith(pageNumberOffsets, (string, offset) -> this.font.drawString(string, offset, PAGE_NUMBER_Y_START, BLACK));

		MatchedPair<Integer> pageTextOffsets = MatchedPair.of(bookCenter - TEXT_WIDTH - TEXT_OFFSET_FROM_BOOK_CENTER, bookCenter + TEXT_OFFSET_FROM_BOOK_CENTER);// +
																																									// RIGHTWARD_SHIFT/2
																																									// +
																																									// PAGE_TEXT_START_X
																																									// + 10);

		this.cachedPageDrawables.consumeWith(pageTextOffsets, this::drawPageDrawables);

		// this next part seems to be for rendering tooltips but it doesn't seem to work
		// ITextComponent itextcomponent2 = this.func_214154_c((double) p_render_1_,
		// (double) p_render_2_);
		// if (itextcomponent2 != null)
		// {
		// this.renderComponentHoverEffect(itextcomponent2, p_render_1_, p_render_2_);
		// }

		super.render(mouseX, mouseY, partialTicks);
	}

	protected void drawPageDrawables(Drawable drawables, int xOffset)
	{
//		int yStart = PAGE_TEXT_START;
		drawables.render(this, xOffset, PAGE_TEXT_START);
//		for (Drawable drawable : drawables)
//		{
//			drawable.render(this, xOffset, yStart);
//			yStart += drawable.getHeight();
//		}
		//ListUtil.forEachIndex(lines, (line, i) -> this.font.drawString(line.getFormattedText(), xOffset, PAGE_TEXT_START + LINE_HEIGHT * i, 0));
	}

	// public ITextComponent getPageText(int page)
	// {
	// return page == 0
	// ? new StringTextComponent("§lLorem ipsum §ldolor sit amet, conse§rctetur
	// adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
	// aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
	// nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
	// reprehenderit in")
	// : new StringTextComponent("ᛚᛟᚱᛖᛗ ᛁᛈᛋᚢᛗ ᛞᛟᛚᛟᚱ ᛋᛁᛏ ᚨᛗᛖᛏ ᚲᛟᚾᛋᛖᚲᛏᛖᛏᚢᚱ ᚨᛞᛁᛈᛁᛋᚲᛁᚾᚷ
	// ᛖᛚᛁᛏ ᛋᛖᛞ ᛞᛟ ᛖᛁᚢᛋᛗᛟᛞ ᛏᛖᛗᛈᛟᚱ ᛁᚾᚲᛁᛞᛁᛞᚢᚾᛏ ᚢᛏ ᛚᚨᛒᛟᚱᛖ ᛖᛏ ᛞᛟᛚᛟᚱᛖ ᛗᚨᚷᚾᚨ ᚨᛚᛁᛝᚢᚨ ᚢᛏ
	// ᛖᚾᛁᛗ ᚨᛞ ᛗᛁᚾᛁᛗ ᛒᛖᚾᛁᚨᛗ ᛝᚢᛁᛋ ᚾᛟᛋᛏᚱᚢᛞ ᛖᚦᛖᚱᚲᛁᛏᚨᛏᛁᛟᚾ ᚢᛚᛚᚨᛗᚲᛟ ᛚᚨᛒᛟᚱᛁᛋ ᚾᛁᛋᛁ ᚢᛏ
	// ᚨᛚᛁᛝᚢᛁᛈ ᛖᚦ ᛖᚨ ᚲᛟᛗᛗᛟᛞᛟ ᚲᛟᚾᛋᛖᛝᚢᚨᛏ ᛞᚢᛁᛋ ᚨᚢᛏᛖ ᛁᚱᚢᚱᛖ ᛞᛟᛚᛟᚱ ᛁᚾ ᚱᛖᛈᚱᛖᚻᛖᚾᛞᛖᚱᛁᛏ ᛁᚾ");
	// }

	public Drawable getPageDrawables(int page)
	{
		List<BakedPage> pages = this.book.getPages();
		return page < pages.size() ? this.book.getPages().get(page) : Drawable.NONE;
		// return RenderComponentsUtil.splitText(this.getPageText(page), TEXT_WIDTH,
		// this.font, true, true);
	}

	// returns the number of pages with things on them, rounded up to an even number
	// this way the last page of a book with an odd number of "actual pages" doesn't
	// display e.g. "page 2 of 1" on the last page
	// (slightly easier than just not rendering the last page number)
	public int getPageCountForPageNumberRendering()
	{
		int actualCount = this.getActualPageCount();
		return (actualCount % 2 == 0) ? actualCount : actualCount + 1;
	}

	// returns the number of pages with things on them
	public int getActualPageCount()
	{
		return this.book.getPages().size();
	}

	public int getLeftPageNumber()
	{
		return this.currentPage;
	}

	public String getPageNumberText(int pageNumber)
	{
		return I18n.format("book.pageIndicator", pageNumber + 1, Math.max(this.getPageCountForPageNumberRendering(), 1));
	}

	public int getStringWidth(String s)
	{
		return this.font.getStringWidth(this.font.getBidiFlag() ? this.font.bidiReorder(s) : s);
	}

	@Override
	public AbstractGui getGUI()
	{
		return this;
	}
}
