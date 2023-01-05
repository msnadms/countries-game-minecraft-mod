package snow.cgmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorkComputerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public final ContainerData data;

    private int population;
    private int industrialJobs;
    private int farmingJobs;
    private int forestryJobs;
    private int technicalJobs;
    private int sorcererJobs;
    private int jobs;

    public WorkComputerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WORK_COMP.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WorkComputerBlockEntity.this.population;
                    case 1 -> WorkComputerBlockEntity.this.industrialJobs;
                    case 2 -> WorkComputerBlockEntity.this.farmingJobs;
                    case 3 -> WorkComputerBlockEntity.this.forestryJobs;
                    case 4 -> WorkComputerBlockEntity.this.technicalJobs;
                    case 5 -> WorkComputerBlockEntity.this.sorcererJobs;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WorkComputerBlockEntity.this.population = value;
                    case 1 -> WorkComputerBlockEntity.this.industrialJobs = value;
                    case 2 -> WorkComputerBlockEntity.this.farmingJobs = value;
                    case 3 -> WorkComputerBlockEntity.this.forestryJobs = value;
                    case 4 -> WorkComputerBlockEntity.this.technicalJobs = value;
                    case 5 -> WorkComputerBlockEntity.this.sorcererJobs = value;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
        jobs = industrialJobs + farmingJobs + forestryJobs + technicalJobs + sorcererJobs;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Work Computer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return null;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
    }

    public void changeByIndex(int index, boolean inc) {
        if (population == 0) {
            return;
        }
        if (jobs == population && inc) {
            return;
        }
        if (jobs == 0 && !inc) {
            return;
        }
        this.data.set(index, this.data.get(index) + (inc ? 1 : -1));
    }
}