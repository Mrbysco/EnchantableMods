package com.mrbysco.enchantablemods.datagen.assets;

import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
	public ModLanguageProvider(PackOutput packOutput) {
		super(packOutput, EnchantableBlocks.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addBlock(ForceCompat.ENCHANTED_FORCE_FURNACE, "Enchanted Yellow Force Furnace");
		addBlock(ForceCompat.ENCHANTED_BLACK_FORCE_FURNACE, "Enchanted Black Force Furnace");
		addBlock(ForceCompat.ENCHANTED_BLUE_FORCE_FURNACE, "Enchanted Blue Force Furnace");
		addBlock(ForceCompat.ENCHANTED_BROWN_FORCE_FURNACE, "Enchanted Brown Force Furnace");
		addBlock(ForceCompat.ENCHANTED_CYAN_FORCE_FURNACE, "Enchanted Cyan Force Furnace");
		addBlock(ForceCompat.ENCHANTED_GRAY_FORCE_FURNACE, "Enchanted Gray Force Furnace");
		addBlock(ForceCompat.ENCHANTED_GREEN_FORCE_FURNACE, "Enchanted Green Force Furnace");
		addBlock(ForceCompat.ENCHANTED_LIGHT_BLUE_FORCE_FURNACE, "Enchanted Light Blue Force Furnace");
		addBlock(ForceCompat.ENCHANTED_LIGHT_GRAY_FORCE_FURNACE, "Enchanted Light Gray Force Furnace");
		addBlock(ForceCompat.ENCHANTED_LIME_FORCE_FURNACE, "Enchanted Lime Force Furnace");
		addBlock(ForceCompat.ENCHANTED_MAGENTA_FORCE_FURNACE, "Enchanted Magenta Force Furnace");
		addBlock(ForceCompat.ENCHANTED_ORANGE_FORCE_FURNACE, "Enchanted Orange Force Furnace");
		addBlock(ForceCompat.ENCHANTED_PINK_FORCE_FURNACE, "Enchanted Pink Force Furnace");
		addBlock(ForceCompat.ENCHANTED_PURPLE_FORCE_FURNACE, "Enchanted Purple Force Furnace");
		addBlock(ForceCompat.ENCHANTED_RED_FORCE_FURNACE, "Enchanted Red Force Furnace");
		addBlock(ForceCompat.ENCHANTED_WHITE_FORCE_FURNACE, "Enchanted White Force Furnace");
	}
}