package com.github.commoble.mondobook;

import java.util.Optional;

import com.github.commoble.mondobook.network.OpenLoreBookS2CPacket;
import com.github.commoble.mondobook.network.PacketHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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
		ItemStack stack = new ItemStack(ItemRegistrar.LORE_BOOK.get());
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
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		if (player instanceof ServerPlayerEntity)
		{
			
			PacketHandler.INSTANCE.send(
				PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player),
				new OpenLoreBookS2CPacket(new ResourceLocation("mondobook:lorem_ipsum")));
		}	
		return ActionResult.resultConsume(player.getHeldItem(hand));
	}

}
