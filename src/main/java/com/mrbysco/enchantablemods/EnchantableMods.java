package com.mrbysco.enchantablemods;

import com.mojang.logging.LogUtils;
import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantableblocks.util.BlockReplacement;
import com.mrbysco.enchantablemods.client.ClientHandler;
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
			com.mrbysco.enchantablemods.forcecraft.ForceCompat.BLOCKS.register(eventBus);
			com.mrbysco.enchantablemods.forcecraft.ForceCompat.BLOCK_ENTITY_TYPES.register(eventBus);
		}

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerRenderers);
		}
	}

	public void sendImc(InterModEnqueueEvent event) {
		List<BlockReplacement> replacements = new ArrayList<>();
		if (ModList.get().isLoaded("forcecraft")) {
			com.mrbysco.enchantablemods.forcecraft.ForceCompat.populateReplacements(replacements);
		}
		if (replacements.isEmpty()) {
			LOGGER.warn("No replacements found. You don't have any compatible mods installed.");
		} else {
			for (BlockReplacement replacement : replacements) {
				InterModComms.sendTo(EnchantableBlocks.MOD_ID, "register_replacement", () -> replacement);
			}
		}
	}
}
