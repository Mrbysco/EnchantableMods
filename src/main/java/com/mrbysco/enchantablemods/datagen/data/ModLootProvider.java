package com.mrbysco.enchantablemods.datagen.data;

import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import com.mrbysco.forcecraft.registry.ForceRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModLootProvider extends LootTableProvider {
	public ModLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(EnchantableBlockLootSubProvider::new, LootContextParamSets.BLOCK)
		));
	}

	private static class EnchantableBlockLootSubProvider extends BlockLootSubProvider {

		protected EnchantableBlockLootSubProvider() {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		protected void generate() {
			this.add(ForceCompat.ENCHANTED_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_BLACK_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.BLACK_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_BLUE_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.BLUE_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_BROWN_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.BROWN_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_CYAN_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.CYAN_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_GRAY_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.GRAY_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_GREEN_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.GREEN_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_LIGHT_BLUE_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.LIGHT_BLUE_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_LIGHT_GRAY_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.LIGHT_GRAY_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_LIME_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.LIME_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_MAGENTA_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.MAGENTA_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_ORANGE_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.ORANGE_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_PINK_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.PINK_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_PURPLE_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.PURPLE_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_RED_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.RED_FORCE_FURNACE.get()));
			this.add(ForceCompat.ENCHANTED_WHITE_FORCE_FURNACE.get(), (block) -> createEnchantableBlockEntityTable(block, ForceRegistry.WHITE_FORCE_FURNACE.get()));
		}

		protected LootTable.Builder createEnchantableBlockEntityTable(Block block, Block originalBlock) {
			return LootTable.lootTable()
					.withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
									.add(LootItem.lootTableItem(originalBlock)
											.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
											.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Enchantments", "Enchantments"))
									)
							)
					);
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			List<DeferredHolder<Block, ? extends Block>> blocks = new ArrayList<>();
			//Forcecraft Compat
			blocks.addAll(ForceCompat.BLOCKS.getEntries());

			return blocks.stream().map((holder) -> (Block) holder.value())::iterator;
		}
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationContext) {
		map.forEach((location, lootTable) -> lootTable.validate(validationContext));
	}
}
