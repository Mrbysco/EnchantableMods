package com.mrbysco.enchantablemods.datagen.data;

import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantableblocks.registry.ModEnchantments;
import com.mrbysco.enchantablemods.util.EnchantableModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends TagsProvider<Enchantment> {
	public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, Registries.ENCHANTMENT, completableFuture, EnchantableBlocks.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(EnchantableModTags.FORCE_FURNACE_APPLICABLE).add(
				ModEnchantments.SPEED.getKey(),
				ModEnchantments.FUEL_EFFICIENCY.getKey(),
				ModEnchantments.YIELD.getKey(),
				ModEnchantments.PRESERVATION.getKey(),
				ModEnchantments.SOLAR_RADIANCE.getKey(),
				ModEnchantments.CONCEALED.getKey(),
				ModEnchantments.GLINTLESS.getKey(),
				BuiltInRegistries.ENCHANTMENT.getResourceKey(Enchantments.VANISHING_CURSE).orElseThrow(),
				BuiltInRegistries.ENCHANTMENT.getResourceKey(Enchantments.BLAST_PROTECTION).orElseThrow()
		).addOptional(new ResourceLocation("stickerframes", "foiled"));
		this.tag(EnchantableModTags.WHITE_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.ORANGE_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.MAGENTA_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.LIGHT_BLUE_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.YELLOW_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.LIME_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.PINK_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.GRAY_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.LIGHT_GRAY_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.CYAN_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.PURPLE_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.BLUE_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.BROWN_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.GREEN_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.RED_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
		this.tag(EnchantableModTags.BLACK_FORCE_FURNACE_APPLICABLE).addTag(EnchantableModTags.FORCE_FURNACE_APPLICABLE);
	}
}