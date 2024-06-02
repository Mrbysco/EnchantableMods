package com.mrbysco.enchantablemods.datagen;

import com.mrbysco.enchantablemods.datagen.assets.ModBlockstateProvider;
import com.mrbysco.enchantablemods.datagen.assets.ModLanguageProvider;
import com.mrbysco.enchantablemods.datagen.data.ModBlockTagsProvider;
import com.mrbysco.enchantablemods.datagen.data.ModEnchantmentTagsProvider;
import com.mrbysco.enchantablemods.datagen.data.ModLootProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(true, new ModLootProvider(packOutput));
			generator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new ModLanguageProvider(packOutput));
			generator.addProvider(true, new ModBlockstateProvider(packOutput, existingFileHelper));
			generator.addProvider(true, new ModEnchantmentTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));
		}
	}
}
