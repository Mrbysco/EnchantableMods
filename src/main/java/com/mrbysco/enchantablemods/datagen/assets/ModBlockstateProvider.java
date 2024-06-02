package com.mrbysco.enchantablemods.datagen.assets;

import com.mrbysco.enchantableblocks.EnchantableBlocks;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import com.mrbysco.forcecraft.registry.ForceRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockstateProvider extends BlockStateProvider {
	public ModBlockstateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, EnchantableBlocks.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		abstractFurnace(ForceCompat.ENCHANTED_FORCE_FURNACE, ForceRegistry.FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_BLACK_FORCE_FURNACE, ForceRegistry.BLACK_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_BLUE_FORCE_FURNACE, ForceRegistry.BLUE_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_BROWN_FORCE_FURNACE, ForceRegistry.BROWN_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_CYAN_FORCE_FURNACE, ForceRegistry.CYAN_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_GRAY_FORCE_FURNACE, ForceRegistry.GRAY_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_GREEN_FORCE_FURNACE, ForceRegistry.GREEN_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_LIGHT_BLUE_FORCE_FURNACE, ForceRegistry.LIGHT_BLUE_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_LIGHT_GRAY_FORCE_FURNACE, ForceRegistry.LIGHT_GRAY_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_LIME_FORCE_FURNACE, ForceRegistry.LIME_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_MAGENTA_FORCE_FURNACE, ForceRegistry.MAGENTA_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_ORANGE_FORCE_FURNACE, ForceRegistry.ORANGE_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_PINK_FORCE_FURNACE, ForceRegistry.PINK_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_PURPLE_FORCE_FURNACE, ForceRegistry.PURPLE_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_RED_FORCE_FURNACE, ForceRegistry.RED_FORCE_FURNACE.getId().getPath());
		abstractFurnace(ForceCompat.ENCHANTED_WHITE_FORCE_FURNACE, ForceRegistry.WHITE_FORCE_FURNACE.getId().getPath());
	}

	private void abstractFurnace(DeferredBlock<? extends Block> block, String originalBlock) {
		ModelFile normal = getExistingFile(new ResourceLocation("forcecraft", originalBlock));
		ModelFile lit = getExistingFile(new ResourceLocation("forcecraft", originalBlock + "_on"));
		this.getVariantBuilder(block.get())
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.NORTH).with(AbstractFurnaceBlock.LIT, false)
				.modelForState().modelFile(normal).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.EAST).with(AbstractFurnaceBlock.LIT, false)
				.modelForState().modelFile(normal).rotationY(90).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.SOUTH).with(AbstractFurnaceBlock.LIT, false)
				.modelForState().modelFile(normal).rotationY(180).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.WEST).with(AbstractFurnaceBlock.LIT, false)
				.modelForState().modelFile(normal).rotationY(270).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.NORTH).with(AbstractFurnaceBlock.LIT, true)
				.modelForState().modelFile(lit).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.EAST).with(AbstractFurnaceBlock.LIT, true)
				.modelForState().modelFile(lit).rotationY(90).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.SOUTH).with(AbstractFurnaceBlock.LIT, true)
				.modelForState().modelFile(lit).rotationY(180).addModel()
				.partialState().with(AbstractFurnaceBlock.FACING, Direction.WEST).with(AbstractFurnaceBlock.LIT, true)
				.modelForState().modelFile(lit).rotationY(270).addModel();
	}

	public ModelFile.UncheckedModelFile getExistingFile(ResourceLocation path) {
		return new ModelFile.UncheckedModelFile(extendWithFolder(path));
	}

	private ResourceLocation extendWithFolder(ResourceLocation path) {
		return new ResourceLocation(path.getNamespace(), "block/" + path.getPath());
	}

	private ResourceLocation suffix(ResourceLocation location, String suffix) {
		return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
	}
}
