package com.github.commoble.mondobook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.github.commoble.mondobook.client.ClientEventHandler;
import com.github.commoble.mondobook.network.OpenLoreBookS2CPacket;
import com.github.commoble.mondobook.network.PacketHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.PacketDistributor;

public class MondobookItem extends Item
{
	public static String BOOK = "book";

	public MondobookItem(Properties properties)
	{
		super(properties);
	}

	public static ItemStack makeItemStackForBookTitle(ResourceLocation id)
	{
		return makeItemStackForBookTitle(id.toString());
	}

	public static ItemStack makeItemStackForBookTitle(String title)
	{
		ItemStack stack = new ItemStack(ItemRegistrar.MONDOBOOK.get());
		stack.setTagInfo(BOOK, StringNBT.valueOf(title));
		return stack;
	}

	public static Optional<ResourceLocation> getBookIDFromItemStack(ItemStack stack)
	{
		if (stack.getCount() > 0)
		{
			CompoundNBT nbt = stack.getTag();
			if (nbt != null && nbt.contains(BOOK))
			{
				return Optional.of(new ResourceLocation(nbt.getString(BOOK)));
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack
	 * so different stacks can have different names based on their damage or NBT.
	 */
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		return getBookIDFromItemStack(stack)
			.map(id -> String.format("item.mondobook.title.%s", id.toString().replace(":", ".")))
			.orElse(this.getTranslationKey());
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns
	 * 16 items)
	 */
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		Consumer<ResourceLocation> addBook = bookID -> items.add(makeItemStackForBookTitle(bookID));
		
		if (group == ItemGroup.SEARCH)
		{
			DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEventHandler.fillGroupWithAllMondobooks(items));
		}
		else if (this.isInGroup(group))
		{
			DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEventHandler.fillGroupWithRelevantMondobooks(items, group));
		}

	}
	
	/**
     * Gets a list of tabs that items belonging to this class can display on,
     * combined properly with getSubItems allows for a single item to span many
     * sub-items across many tabs.
     *
     * @return A list of all tabs that this item could possibly be one.
     */
	@Override
    public java.util.Collection<ItemGroup> getCreativeTabs()
    {
		List<ItemGroup> groups = new ArrayList<>();
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientEventHandler.fillMondobookCreativeTabs(groups));
        return groups;
    }

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		if (player instanceof ServerPlayerEntity)
		{
			getBookIDFromItemStack(player.getHeldItem(hand)).ifPresent(id ->
				PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new OpenLoreBookS2CPacket(id)));
		}
		return ActionResult.resultConsume(player.getHeldItem(hand));
	}

}
