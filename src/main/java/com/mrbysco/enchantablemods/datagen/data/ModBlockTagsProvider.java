package com.mrbysco.enchantablemods.datagen.data;

import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
	public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EnchantableBlocks.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				ForceCompat.ENCHANTED_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_BLACK_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_BLUE_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_BROWN_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_CYAN_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_GRAY_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_GREEN_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_LIGHT_BLUE_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_LIGHT_GRAY_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_LIME_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_MAGENTA_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_ORANGE_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_PINK_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_PURPLE_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_RED_FORCE_FURNACE.get(),
				ForceCompat.ENCHANTED_WHITE_FORCE_FURNACE.get()
		);
	}
}