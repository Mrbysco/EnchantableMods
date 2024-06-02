package com.mrbysco.enchantablemods;

import com.mojang.logging.LogUtils;
import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantableblocks.util.BlockReplacement;
import com.mrbysco.enchantablemods.client.ClientHandler;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import com.mrbysco.enchantablemods.util.EnchantableModTags;
import com.mrbysco.forcecraft.registry.ForceRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(EnchantableMods.MOD_ID)
public class EnchantableMods {
	public static final String MOD_ID = "enchantablemods";
	private static final Logger LOGGER = LogUtils.getLogger();

	public EnchantableMods(IEventBus eventBus) {
		eventBus.addListener(this::sendImc);

		if (ModList.get().isLoaded("forcecraft")) {
			ForceCompat.BLOCKS.register(eventBus);
			ForceCompat.BLOCK_ENTITY_TYPES.register(eventBus);
		}

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerRenderers);
		}
	}

	public void sendImc(InterModEnqueueEvent event) {
		List<BlockReplacement> replacements = new ArrayList<>();
		if (ModList.get().isLoaded("forcecraft")) {
			replacements.add(new BlockReplacement(ForceRegistry.FORCE_FURNACE.get(), ForceCompat.ENCHANTED_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.BLACK_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_BLACK_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.BLUE_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_BLUE_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.BROWN_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_BROWN_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.CYAN_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_CYAN_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.GRAY_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_GRAY_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.GREEN_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_GREEN_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.LIGHT_BLUE_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_LIGHT_BLUE_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.LIGHT_GRAY_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_LIGHT_GRAY_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.LIME_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_LIME_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.MAGENTA_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_MAGENTA_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.ORANGE_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_ORANGE_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.PINK_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_PINK_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.PURPLE_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_PURPLE_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.RED_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_RED_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
			replacements.add(new BlockReplacement(ForceRegistry.WHITE_FORCE_FURNACE.get(), ForceCompat.ENCHANTED_WHITE_FORCE_FURNACE, EnchantableModTags.FORCE_FURNACE_APPLICABLE));
		}
		if (replacements.isEmpty()) {
			LOGGER.warn("No replacements found. You don't have any compatible mods installed.");
			return;
		}
		for (BlockReplacement replacement : replacements) {
			InterModComms.sendTo(EnchantableBlocks.MOD_ID, "register_replacement", () -> replacement);
		}
	}
}
