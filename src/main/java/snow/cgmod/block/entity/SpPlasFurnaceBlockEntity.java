package snow.cgmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SpPlasFurnaceBlockEntity extends AbstractFurnaceBlockEntity {


    public SpPlasFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SP_PLAS_FURNACE.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.literal("Superheated Plasma Furnace");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int n, @NotNull Inventory inv) {
        return new FurnaceMenu(n, inv, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) * 5;
        }
    }
}
