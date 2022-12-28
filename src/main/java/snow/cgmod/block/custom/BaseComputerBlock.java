package snow.cgmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snow.cgmod.block.custom.auxilliary.BaseResources;
import snow.cgmod.block.entity.BaseComputerBlockEntity;
import snow.cgmod.block.entity.ModBlockEntities;

public class BaseComputerBlock extends BaseEntityBlock {


    public BaseComputerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemInHand.getItem().equals(Items.AIR)) {
                BlockEntity entity = level.getBlockEntity(blockPos);
                if(entity instanceof BaseComputerBlockEntity) {
                    NetworkHooks.openScreen(((ServerPlayer) player), (BaseComputerBlockEntity) entity, blockPos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
                //player.sendSystemMessage(Component.literal(resources.toString()));
            }

        }
        return InteractionResult.sidedSuccess(level.isClientSide());
        //return super.use(blockState, level, blockPos, player, hand, hitResult);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BaseComputerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return createTickerHelper(entityType, ModBlockEntities.BASE_COMP.get(),
                BaseComputerBlockEntity::tick);
    }
}
