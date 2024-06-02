package com.mrbysco.enchantablemods.client;

import com.mrbysco.enchantableblocks.client.renderer.EnchantedBlockEntityRenderer;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		if (ModList.get().isLoaded("forcecraft")) {
			event.registerBlockEntityRenderer(ForceCompat.ENCHANTED_FORCE_FURNACE_BLOCK_ENTITY.get(), EnchantedBlockEntityRenderer::new);
		}
	}
}
