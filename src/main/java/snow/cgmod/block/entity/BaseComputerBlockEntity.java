package snow.cgmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snow.cgmod.block.ModBlocks;
import snow.cgmod.item.ModItems;
import snow.cgmod.screen.BaseComputerMenu;

import java.util.Arrays;

public class BaseComputerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> BaseComputerBlockEntity.isValidMaterial(stack.getItem());
                case 1, 2, 3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public final ContainerData data;

    // Persist across server shutdowns
    private int population; // Number of pops
    private int food; // Represents how much food base has, in units of hunger points restored by any food item
    private int water; // Is the base connected to a water source?
    private int diamonds; // Diamonds will be used as wealth, impacts happiness
    private int structureBlocks; // Used by pops to build structures
    private int happiness; // How happy the population is, impacts growth speed or decline speed
    // Recalculate upon entity tick
    private boolean firstTick = true;
    private int cityLevel;
    private int schoolLevel;
    private int forgeLevel;


    int[] allArr = new int[]{population, food, water, diamonds, structureBlocks, happiness};

    public BaseComputerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BASE_COMP.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BaseComputerBlockEntity.this.population;
                    case 1 -> BaseComputerBlockEntity.this.food;
                    case 2 -> BaseComputerBlockEntity.this.water;
                    case 3 -> BaseComputerBlockEntity.this.diamonds;
                    case 4 -> BaseComputerBlockEntity.this.structureBlocks;
                    case 5 -> BaseComputerBlockEntity.this.happiness;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BaseComputerBlockEntity.this.population = value;
                    case 1 -> BaseComputerBlockEntity.this.food = value;
                    case 2 -> BaseComputerBlockEntity.this.water = value;
                    case 3 -> BaseComputerBlockEntity.this.diamonds = value;
                    case 4 -> BaseComputerBlockEntity.this.structureBlocks = value;
                    case 5 -> BaseComputerBlockEntity.this.happiness = value;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Base Computer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BaseComputerMenu(id, inv, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putIntArray("base_computer.stats", allArr);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        loadFromArr(nbt.getIntArray("base_computer.stats"));

    }

    private static boolean isValidMaterial(Item item) {
        String name = item.toString();
        return name.contains("planks") ||
                (name.contains("stone") && !name.contains("cobble")) ||
                name.contains("diamond") ||
                name.contains("concrete") ||
                name.contains("terracotta") ||
                item.isEdible();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BaseComputerBlockEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        if (entity.firstTick) {
            entity.updateArr();
            assignLevels(entity, level);
            entity.firstTick = false;
        }
        ItemStack items = entity.itemHandler.getStackInSlot(0);
        boolean hasValidMaterialInSlot = isValidMaterial(items.getItem());
        if (hasValidMaterialInSlot) {
            entity.addResources(items.getItem(), items.getCount());
            entity.itemHandler.extractItem(0, items.getCount(), false);
            entity.updateArr();
            level.players().get(0).sendSystemMessage(Component.literal(Arrays.toString(entity.allArr)));
            level.players().get(0).sendSystemMessage(Component.literal("" + entity.cityLevel + " " + entity.schoolLevel + " " + entity.forgeLevel));
            assignLevels(entity, level);
        }
        boolean aroundWater = level.getBlockStates(entity.getRenderBoundingBox().inflate(20)).
                filter(s -> s.is(Blocks.WATER)).toArray().length > 10;
        if (aroundWater) {
            entity.waterAdded();
        } else {
            entity.waterRemoved();
        }
        if (entity.tickAllRandom()) {
            if (entity.forgeLevel >= 10) {
                entity.itemHandler.setStackInSlot(1, new ItemStack(ModItems.DIAMOND_ALLOY.get(),
                        entity.itemHandler.getStackInSlot(1).getCount() + entity.forgeLevel / 5));
                entity.diamonds -= entity.itemHandler.getStackInSlot(1).getCount();
            }
            if (entity.schoolLevel >= 5) {
                entity.itemHandler.setStackInSlot(2, new ItemStack(Items.EXPERIENCE_BOTTLE,
                        entity.itemHandler.getStackInSlot(1).getCount() + entity.schoolLevel * 2));
            }
        }
        setChanged(level, pos, state);
    }

    private void loadFromArr(int[] arr) {
        this.population = arr[0];
        this.food = arr[1];
        this.water = arr[2];
        this.diamonds = arr[3];
        this.structureBlocks = arr[4];
        this.happiness = arr[5];
    }

    private void updateArr() {
        allArr = new int[]{population, food, water, diamonds, structureBlocks, happiness};
    }

    private void recalculateHappiness() {
        if (water == 0 || food == 0) {
            happiness = 0;
            return;
        }
        happiness = food + structureBlocks + (64 * diamonds);
    }

    private void waterAdded() {
        water = 1;
    }

    private void waterRemoved() {
        water = 0;
    }

    private boolean tickAllRandom() {
        int hourModifier = 5;
        int rnd = (int) (20 * 60 * hourModifier * Math.random());
        int moreRnd = (int) (4 * Math.random());
        if (rnd < 10) {
            diamonds = Math.max(diamonds - 1, 0);
            food = Math.max(food - 5, 0);
            structureBlocks = Math.max(structureBlocks - 32, 0);
            recalculateHappiness();
            changePopulation();
        } else if (rnd == 11 && moreRnd == 1) {
            return true;
        } else if (rnd > (20 * 60 * hourModifier) - 5) {
            changePopulation();
        }
        return false;
    }

    private void changePopulation() {
        recalculateHappiness();
        if (happiness < 50) {
            population -= 5;
        } else if (happiness < 100) {
            population -= 1;
        } else if (happiness < 200) {
            population += 1;
        } else {
            population += 5;
        }
        if (population < 0) {
            population = 0;
        }
    }

    private <T> void addResources(T resources, int count) {

        if (resources instanceof Item item) {
            String name = item.toString();
            ItemStack stack = new ItemStack(item);
            if (name.contains("diamond")) {
                diamonds += count;
            } else if (item.isEdible()) {
                FoodProperties fp = stack.getFoodProperties(null);
                if (fp != null) {
                    food += (fp.getNutrition() * count);
                }
            } else if (name.contains("planks")) {
                structureBlocks += count;
            } else if (name.contains("stone") && !name.contains("cobble")) {
                structureBlocks += (2 * count);
            } else if (name.contains("stripped")) {
                structureBlocks += (3 * count);
            } else if (name.contains("concrete") || name.contains("terracotta")) {
                structureBlocks += (10 * count);
            } else {
                return;
            }
        }
        recalculateHappiness();
    }

    private static int roundTargetNorm(int num, int n, int norm) {
        return (((num + (n / 2)) / n) * n) / norm;
    }

    //Recalculate whenever needed, data not stored
    private static void assignLevels(BaseComputerBlockEntity entity, Level level) {
        // City Level
        entity.cityLevel = Math.max(1, roundTargetNorm(entity.population, 25, 25));
        // Max school level is 20
        int bookshelvesInProximityModifier = Math.min(10,
                level.getBlockStates(entity.getRenderBoundingBox().inflate(Math.pow(entity.cityLevel, 2))).
                        filter(s -> s.is(Blocks.BOOKSHELF)).toArray().length / 5);
        entity.schoolLevel = Math.min(10, roundTargetNorm(entity.structureBlocks, 64, 64 * 3)) +
                bookshelvesInProximityModifier;
        // Max forge level is 20
        int blastFurnaceModifier = Math.min(10,
                level.getBlockStates(entity.getRenderBoundingBox().inflate(Math.pow(entity.cityLevel, 2))).
                        filter(s -> s.is(ModBlocks.SP_PLS_FURNACE.get())).toArray().length);
        entity.forgeLevel = Math.min(10, roundTargetNorm(entity.diamonds, 32, 32)) + blastFurnaceModifier;

    }
}