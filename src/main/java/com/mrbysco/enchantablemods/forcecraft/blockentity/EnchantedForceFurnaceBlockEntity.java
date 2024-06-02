package com.mrbysco.enchantablemods.forcecraft.blockentity;

import com.mrbysco.enchantableblocks.block.blockentity.IEnchantable;
import com.mrbysco.enchantableblocks.registry.ModEnchantments;
import com.mrbysco.enchantableblocks.registry.ModTags;
import com.mrbysco.enchantableblocks.util.TagHelper;
import com.mrbysco.enchantablemods.forcecraft.ForceCompat;
import com.mrbysco.forcecraft.blockentities.furnace.AbstractForceFurnaceBlockEntity;
import com.mrbysco.forcecraft.blockentities.furnace.ForceFurnaceBlockEntity;
import com.mrbysco.forcecraft.config.ConfigHandler;
import com.mrbysco.forcecraft.recipe.MultipleOutputFurnaceRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EnchantedForceFurnaceBlockEntity extends ForceFurnaceBlockEntity implements IEnchantable {
	private static final List<ResourceLocation> hopperBlacklist = List.of(new ResourceLocation("hopper"), new ResourceLocation("cyclic", "hopper"), new ResourceLocation("cyclic", "hopper_gold"), new ResourceLocation("cyclic", "hopper_fluid"), new ResourceLocation("uppers", "upper"), new ResourceLocation("goldenhopper", "golden_hopper"), new ResourceLocation("woodenhopper", "wooden_hopper"));
	private RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;

	protected boolean hideGlint = false;
	protected ListTag enchantmentTag = null;
	protected final Object2IntMap<Enchantment> enchantments = new Object2IntOpenHashMap<>();

	public EnchantedForceFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ForceCompat.ENCHANTED_FORCE_FURNACE_BLOCK_ENTITY.get();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, EnchantedForceFurnaceBlockEntity blockEntity) {
		if (level != null) {
			boolean wasBurning = blockEntity.isLit();
			boolean solar = blockEntity.hasEnchantment(ModEnchantments.SOLAR_RADIANCE.get());
			boolean solarRequirements = level.isDay() && level.canSeeSky(pos.above());
			boolean hasInput = !blockEntity.getItem(0).isEmpty();

			if (blockEntity.isLit()) {
				int speed = blockEntity.getEnchantmentSpeed();
				if (solar) {
					if (solarRequirements) {
						blockEntity.litTime = 200;
					} else {
						blockEntity.litTime -= speed;
					}
				} else {
					boolean preservation = blockEntity.hasEnchantment(ModEnchantments.PRESERVATION.get());
					if (preservation) {
						RecipeHolder<?> recipe = hasInput ? blockEntity.getRecipe() : null;
						if (recipe != null)
							blockEntity.litTime -= speed;

					} else {
						blockEntity.litTime -= speed;
					}
				}
			}

			if (blockEntity.isLit() && blockEntity.canBurn(blockEntity.currentRecipe)) {
				int speed = blockEntity.getEnchantmentSpeed();
				if (blockEntity.burnSpeed != speed) {
					blockEntity.burnSpeed = speed;
				}
				blockEntity.litTime -= blockEntity.burnSpeed;
			}

			boolean dirty = false;
			ItemStack fuel = blockEntity.handler.getStackInSlot(FUEL_SLOT);
			if (blockEntity.isLit() || !fuel.isEmpty() && !blockEntity.handler.getStackInSlot(INPUT_SLOT).isEmpty()) {
				RecipeHolder<? extends AbstractCookingRecipe> recipeHolder = blockEntity.getRecipe();
				AbstractCookingRecipe cookingRecipe = recipeHolder != null ? recipeHolder.value() : null;
				if (!blockEntity.isLit() && blockEntity.canBurn(recipeHolder)) {
					blockEntity.litTime = blockEntity.getBurnDuration(fuel);
					blockEntity.litDuration = blockEntity.litTime;
					if (blockEntity.isLit()) {
						dirty = true;
						if (fuel.hasCraftingRemainingItem())
							blockEntity.handler.setStackInSlot(FUEL_SLOT, fuel.getCraftingRemainingItem());
						else if (!fuel.isEmpty()) {
							fuel.shrink(1);
							if (fuel.isEmpty()) {
								blockEntity.handler.setStackInSlot(FUEL_SLOT, fuel.getCraftingRemainingItem());
							}
						}
					}
				}

				if (blockEntity.isLit() && blockEntity.canBurn(recipeHolder)) {
					int currentSpeed = blockEntity.getEnchantmentSpeed();
					int speed = blockEntity.isEfficient() ? currentSpeed * 4 : currentSpeed;
					if (blockEntity.cookingSpeed != speed) {
						blockEntity.cookingSpeed = speed;
					}

					if (cookingRecipe != null && blockEntity.cookingTotalTime != cookingRecipe.getCookingTime())
						blockEntity.cookingTotalTime = cookingRecipe.getCookingTime();

					blockEntity.cookingProgress += blockEntity.cookingSpeed;
					if (blockEntity.cookingProgress >= blockEntity.cookingTotalTime) {
						blockEntity.cookingProgress = 0;
						blockEntity.cookingTotalTime = blockEntity.getCookingProgress();
						if (blockEntity.enchantedBurn(recipeHolder)) {
							blockEntity.setRecipeUsed(recipeHolder);
						}
						dirty = true;
					}
				} else {
					blockEntity.cookingProgress = 0;
				}
			} else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
				blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
			}

			if (wasBurning != blockEntity.isLit()) {
				dirty = true;
				level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit()), 3);
			}

			if (dirty) {
				setChanged(level, pos, state);
			}
		}
	}

	private boolean enchantedBurn(@Nullable RecipeHolder<?> holder) {
		if (holder != null && this.canBurn(holder) && level != null) {
			Recipe<?> recipe = holder.value();
			ItemStack inputStack = this.handler.getStackInSlot(INPUT_SLOT);
			List<? extends String> additionalBlacklist = new ArrayList<>();
			if (!ConfigHandler.COMMON.furnaceOutputBlacklist.get().isEmpty() && !ConfigHandler.COMMON.furnaceOutputBlacklist.get().get(0).isEmpty()) {
				additionalBlacklist = ConfigHandler.COMMON.furnaceOutputBlacklist.get();
			}

			if (recipe instanceof MultipleOutputFurnaceRecipe multipleRecipe) {
				NonNullList<ItemStack> outputStacks = multipleRecipe.getRecipeOutputs();
				if (hasEnchantment(ModEnchantments.YIELD.get())) {
					int enchantmentLevel = getEnchantmentLevel(ModEnchantments.YIELD.get());
					for (ItemStack craftedStack : outputStacks) {
						if (craftedStack.getCount() < craftedStack.getMaxStackSize() && !craftedStack.is(ModTags.Items.YIELD_BLACKLIST)) {
							//Adjust the craftedStack based on the level of the enchantment
							int count = 1 + enchantmentLevel;
							craftedStack.setCount(Mth.clamp(count, 1, craftedStack.getMaxStackSize()));
						}
					}
				}
				for (int i = 0; i < outputStacks.size(); i++) {
					ItemStack craftedStack = outputStacks.get(i).copy();

					if (i > 0) {
						if (multipleRecipe.getSecondaryChance() != 1.0F || level.random.nextFloat() > multipleRecipe.getSecondaryChance()) {
							//Early break if change didn't work out on second output
							break;
						}
					}

					List<com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory> inventoryList = new ArrayList<>();
					for (Direction dir : Direction.values()) {
						BlockPos offPos = worldPosition.relative(dir);
						if (this.level.isAreaLoaded(worldPosition, 1)) {
							BlockEntity foundTile = this.level.getBlockEntity(offPos);
							IItemHandler itemHandler = this.level.getCapability(Capabilities.ItemHandler.BLOCK, offPos, dir.getOpposite());
							if (foundTile != null && itemHandler != null) {
								ResourceLocation typeLocation = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(foundTile.getType());
								boolean flag = foundTile instanceof Hopper || foundTile instanceof AbstractFurnaceBlockEntity || foundTile instanceof AbstractForceFurnaceBlockEntity;
								boolean flag2 = typeLocation != null && (!hopperBlacklist.contains(typeLocation) && (additionalBlacklist.isEmpty() || !additionalBlacklist.contains(typeLocation.toString())));
								if (!flag && flag2 && !foundTile.isRemoved() && foundTile.hasLevel() && itemHandler != null) {
									inventoryList.add(new com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory(offPos, itemHandler.getSlots(), dir.getOpposite()));
								}
							}
						}
					}
					inventoryList.sort(Collections.reverseOrder());
					for (com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory inventory : inventoryList) {
						IItemHandler itemHandler = inventory.getIItemHandler(this.level, this.worldPosition);
						craftedStack = ItemHandlerHelper.insertItem(itemHandler, craftedStack, false);
						if (craftedStack.isEmpty()) {
							break;
						}
					}

					if (i > 0 && !craftedStack.isEmpty()) {
						((ServerLevel) level).sendParticles(ParticleTypes.POOF, (double) worldPosition.getX(), (double) worldPosition.getY() + 1, (double) worldPosition.getZ(), 1, 0, 0, 0, 0.0D);
						ItemEntity itemEntity = new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), craftedStack);
						level.addFreshEntity(itemEntity);
					} else {
						ItemStack currentOutputStack = this.handler.getStackInSlot(OUTPUT_SLOT);
						if (currentOutputStack.isEmpty() && !craftedStack.isEmpty()) {
							this.handler.setStackInSlot(OUTPUT_SLOT, craftedStack);
						} else if (currentOutputStack.getItem() == outputStacks.get(i).getItem()) {
							currentOutputStack.grow(craftedStack.getCount());
						}
					}
				}
			} else {
				ItemStack itemstack1 = recipe.getResultItem(level.registryAccess());
				ItemStack outputStack = itemstack1.copy();

				List<com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory> inventoryList = new ArrayList<>();
				for (Direction dir : Direction.values()) {
					BlockPos offPos = worldPosition.relative(dir);
					if (this.level.isAreaLoaded(worldPosition, 1)) {
						BlockEntity foundTile = this.level.getBlockEntity(offPos);
						IItemHandler itemHandler = this.level.getCapability(Capabilities.ItemHandler.BLOCK, offPos, dir.getOpposite());
						if (foundTile != null && itemHandler != null) {
							ResourceLocation typeLocation = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(foundTile.getType());
							boolean flag = foundTile instanceof Hopper || foundTile instanceof AbstractFurnaceBlockEntity || foundTile instanceof AbstractForceFurnaceBlockEntity;
							boolean flag2 = typeLocation != null && (!hopperBlacklist.contains(typeLocation) && (additionalBlacklist.isEmpty() || !additionalBlacklist.contains(typeLocation.toString())));
							if (!flag && flag2 && !foundTile.isRemoved() && foundTile.hasLevel() && foundTile != null) {
								inventoryList.add(new com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory(offPos, itemHandler.getSlots(), dir.getOpposite()));
							}
						}
					}
				}
				inventoryList.sort(Collections.reverseOrder());
				for (com.mrbysco.forcecraft.blockentities.furnace.BiggestInventory inventory : inventoryList) {
					IItemHandler itemHandler = inventory.getIItemHandler(this.level, this.worldPosition);
					outputStack = ItemHandlerHelper.insertItem(itemHandler, outputStack, false);
					if (outputStack.isEmpty()) {
						break;
					}
				}

				ItemStack itemstack2 = this.handler.getStackInSlot(OUTPUT_SLOT);
				if (itemstack2.isEmpty() && !outputStack.isEmpty()) {
					this.handler.setStackInSlot(OUTPUT_SLOT, outputStack);
				} else if (itemstack2.getItem() == itemstack1.getItem()) {
					itemstack2.grow(outputStack.getCount());
				}
			}

			if (!this.level.isClientSide) {
				this.setRecipeUsed(holder);
			}

			if (inputStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.handler.getStackInSlot(FUEL_SLOT).isEmpty() && this.handler.getStackInSlot(FUEL_SLOT).getItem() == Items.BUCKET) {
				this.handler.setStackInSlot(FUEL_SLOT, new ItemStack(Items.WATER_BUCKET));
			}

			inputStack.shrink(1);
			return true;
		}
		return false;
	}

	@Override
	protected int getBurnDuration(ItemStack fuel) {
		int burnDuration = super.getBurnDuration(fuel);
		if (burnDuration != 0 && this.hasEnchantment(ModEnchantments.FUEL_EFFICIENCY.get())) {
			int enchantmentLevel = this.getEnchantmentLevel(ModEnchantments.FUEL_EFFICIENCY.get());
			//Adjust the burnDuration based on the level of the enchantment
			burnDuration = Mth.ceil(burnDuration * (1F + (enchantmentLevel * 0.2F)));
		}
		return burnDuration;
	}

	private int getEnchantmentSpeed() {
		int speed = this.getSpeed();
		if (hasEnchantment(ModEnchantments.SPEED.get())) {
			int enchantmentLevel = getEnchantmentLevel(ModEnchantments.SPEED.get());
			//Adjust the speed based on the level of the enchantment
			speed += enchantmentLevel;
		}
		return speed;
	}

	@Override
	public Map<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	public boolean hasEnchantment(Enchantment enchantment) {
		return this.enchantments.containsKey(enchantment);
	}

	@Override
	public int getEnchantmentLevel(Enchantment enchantment) {
		if (this.hasEnchantment(enchantment))
			return this.enchantments.get(enchantment);
		return -1;
	}

	@Override
	public boolean hasEnchantment(TagKey<Enchantment> enchantmentTag) {
		for (Enchantment enchantment : this.enchantments.keySet()) {
			if (TagHelper.matchesTag(enchantment, enchantmentTag)) {
				return true;
			}
		}
		return this.enchantments.containsKey(enchantmentTag);
	}

	@Override
	public int getEnchantmentLevel(TagKey<Enchantment> enchantmentTag) {
		for (Enchantment enchantment : this.enchantments.keySet()) {
			if (TagHelper.matchesTag(enchantment, enchantmentTag)) {
				return this.enchantments.get(enchantment);
			}
		}
		return -1;
	}

	@Override
	public void setEnchantments(ListTag enchantmentTags) {
		this.enchantmentTag = enchantmentTags;
		this.updateEnchantmentMap();
	}

	@Override
	public ListTag getEnchantmentsTag() {
		return this.enchantmentTag;
	}

	@Override
	public void updateEnchantmentMap() {
		this.enchantments.clear();
		if (this.enchantmentTag != null) {
			EnchantmentHelper.deserializeEnchantments(this.enchantmentTag).forEach((enchantment, integer) -> {
				if (enchantment != null) {
					this.enchantments.put(enchantment, integer);
				}
			});
			this.hideGlint = this.hasEnchantment(ModEnchantments.GLINTLESS.get());
		}
	}

	@Override
	public boolean hideGlint() {
		return this.hideGlint;
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Enchantments")) {
			this.enchantmentTag = tag.getList("Enchantments", Tag.TAG_COMPOUND);
			this.updateEnchantmentMap();
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (this.enchantmentTag != null)
			tag.put("Enchantments", enchantmentTag);
	}

	//Sync stuff
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
		var tag = packet.getTag();
		if (tag != null) {
			handleUpdateTag(tag);

			BlockState state = level.getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition, state, state, 3);
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (level != null) {
			BlockState state = level.getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition, state, state, 3);
		}
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		load(tag);
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}
}
