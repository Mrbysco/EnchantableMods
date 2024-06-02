package com.mrbysco.enchantablemods.util;

import com.mrbysco.enchantablemods.EnchantableMods;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantableModTags {
	public static final TagKey<Enchantment> FORCE_FURNACE_APPLICABLE = modTag("applicable/force_furnace");
	public static final TagKey<Enchantment> WHITE_FORCE_FURNACE_APPLICABLE = modTag("applicable/white_force_furnace");
	public static final TagKey<Enchantment> ORANGE_FORCE_FURNACE_APPLICABLE = modTag("applicable/orange_force_furnace");
	public static final TagKey<Enchantment> MAGENTA_FORCE_FURNACE_APPLICABLE = modTag("applicable/magenta_force_furnace");
	public static final TagKey<Enchantment> LIGHT_BLUE_FORCE_FURNACE_APPLICABLE = modTag("applicable/light_blue_force_furnace");
	public static final TagKey<Enchantment> YELLOW_FORCE_FURNACE_APPLICABLE = modTag("applicable/yellow_force_furnace");
	public static final TagKey<Enchantment> LIME_FORCE_FURNACE_APPLICABLE = modTag("applicable/lime_force_furnace");
	public static final TagKey<Enchantment> PINK_FORCE_FURNACE_APPLICABLE = modTag("applicable/pink_force_furnace");
	public static final TagKey<Enchantment> GRAY_FORCE_FURNACE_APPLICABLE = modTag("applicable/gray_force_furnace");
	public static final TagKey<Enchantment> LIGHT_GRAY_FORCE_FURNACE_APPLICABLE = modTag("applicable/light_gray_force_furnace");
	public static final TagKey<Enchantment> CYAN_FORCE_FURNACE_APPLICABLE = modTag("applicable/cyan_force_furnace");
	public static final TagKey<Enchantment> PURPLE_FORCE_FURNACE_APPLICABLE = modTag("applicable/purple_force_furnace");
	public static final TagKey<Enchantment> BLUE_FORCE_FURNACE_APPLICABLE = modTag("applicable/blue_force_furnace");
	public static final TagKey<Enchantment> BROWN_FORCE_FURNACE_APPLICABLE = modTag("applicable/brown_force_furnace");
	public static final TagKey<Enchantment> GREEN_FORCE_FURNACE_APPLICABLE = modTag("applicable/green_force_furnace");
	public static final TagKey<Enchantment> RED_FORCE_FURNACE_APPLICABLE = modTag("applicable/red_force_furnace");
	public static final TagKey<Enchantment> BLACK_FORCE_FURNACE_APPLICABLE = modTag("applicable/black_force_furnace");

	private static TagKey<Enchantment> modTag(String path) {
		return TagKey.create(Registries.ENCHANTMENT, new ResourceLocation(EnchantableMods.MOD_ID, path));
	}
}
