package com.github.commoble.mondobook.client;

import java.util.Collections;

import javax.annotation.Nullable;

import com.github.commoble.mondobook.MondobookItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BakedModelWrapper;

public class MondobookModel extends BakedModelWrapper<IBakedModel>
{
	private final ItemOverrideList overrides;

	public MondobookModel(IBakedModel baseModel)
	{
		super(baseModel);
		this.overrides = new MondobookItemOverrideList();
	}

    @Override
    public ItemOverrideList getOverrides()
    {
        return this.overrides;
    }
	
	public static class MondobookItemOverrideList extends ItemOverrideList
	{
		public MondobookItemOverrideList()
		{
			// if the list is empty, then the override list doesn't actually use any of these other args
			super(null, null, null, null, Collections.emptyList());
		}
		
		/**
		 * If the item is a mondobook item and has a valid item ID stored in its NBT correctly, returns the model for that item
		 * If a model cannot be found, uses the base mondobook model
		 */
		@Override
		public IBakedModel getModelWithOverrides(IBakedModel baseModel, ItemStack stack, @Nullable World world, @Nullable LivingEntity entity)
		{
			return MondobookItem.getBookIDFromItemStack(stack)
				.map(MondobookItemOverrideList::getItemModelFromBookID)
				.filter(id -> id != Minecraft.getInstance().getModelManager().getMissingModel())
				.orElse(baseModel);
		}
		
		public static IBakedModel getItemModelFromBookID(ResourceLocation id)
		{
			return Minecraft.getInstance().getModelManager()
				.getModel(new ModelResourceLocation(new ResourceLocation(id.getNamespace(), "mondobooks/" + id.getPath()), "inventory"));
		}
	}
}
