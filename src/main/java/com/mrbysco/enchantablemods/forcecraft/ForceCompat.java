package com.mrbysco.enchantablemods.forcecraft;

import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantablemods.forcecraft.block.EnchantedForceFurnaceBlock;
import com.mrbysco.enchantablemods.forcecraft.blockentity.EnchantedForceFurnaceBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ForceCompat {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EnchantableBlocks.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, EnchantableBlocks.MOD_ID);

	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_FORCE_FURNACE = BLOCKS.register("enchanted_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(4.0F, 18.0F).lightLevel(EnchantedForceFurnaceBlock.getLightValueLit(13))));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_BLACK_FORCE_FURNACE = BLOCKS.register("enchanted_black_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_BLUE_FORCE_FURNACE = BLOCKS.register("enchanted_blue_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_BROWN_FORCE_FURNACE = BLOCKS.register("enchanted_brown_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_CYAN_FORCE_FURNACE = BLOCKS.register("enchanted_cyan_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_GRAY_FORCE_FURNACE = BLOCKS.register("enchanted_gray_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_GREEN_FORCE_FURNACE = BLOCKS.register("enchanted_green_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_LIGHT_BLUE_FORCE_FURNACE = BLOCKS.register("enchanted_light_blue_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_LIGHT_GRAY_FORCE_FURNACE = BLOCKS.register("enchanted_light_gray_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_LIME_FORCE_FURNACE = BLOCKS.register("enchanted_lime_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_MAGENTA_FORCE_FURNACE = BLOCKS.register("enchanted_magenta_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_ORANGE_FORCE_FURNACE = BLOCKS.register("enchanted_orange_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_PINK_FORCE_FURNACE = BLOCKS.register("enchanted_pink_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_PURPLE_FORCE_FURNACE = BLOCKS.register("enchanted_purple_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_RED_FORCE_FURNACE = BLOCKS.register("enchanted_red_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));
	public static final DeferredBlock<EnchantedForceFurnaceBlock> ENCHANTED_WHITE_FORCE_FURNACE = BLOCKS.register("enchanted_white_force_furnace", () -> new EnchantedForceFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(ENCHANTED_FORCE_FURNACE.get())));

	public static final Supplier<BlockEntityType<EnchantedForceFurnaceBlockEntity>> ENCHANTED_FORCE_FURNACE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("enchanted_force_furnace", () ->
			BlockEntityType.Builder.of(EnchantedForceFurnaceBlockEntity::new, ENCHANTED_FORCE_FURNACE.get(),
					ENCHANTED_BLACK_FORCE_FURNACE.get(), ENCHANTED_BLUE_FORCE_FURNACE.get(),
					ENCHANTED_BROWN_FORCE_FURNACE.get(), ENCHANTED_CYAN_FORCE_FURNACE.get(),
					ENCHANTED_GRAY_FORCE_FURNACE.get(), ENCHANTED_GREEN_FORCE_FURNACE.get(),
					ENCHANTED_LIGHT_BLUE_FORCE_FURNACE.get(), ENCHANTED_LIGHT_GRAY_FORCE_FURNACE.get(),
					ENCHANTED_LIME_FORCE_FURNACE.get(), ENCHANTED_MAGENTA_FORCE_FURNACE.get(),
					ENCHANTED_ORANGE_FORCE_FURNACE.get(), ENCHANTED_PINK_FORCE_FURNACE.get(),
					ENCHANTED_PURPLE_FORCE_FURNACE.get(), ENCHANTED_RED_FORCE_FURNACE.get(),
					ENCHANTED_WHITE_FORCE_FURNACE.get()).build(null));

}
