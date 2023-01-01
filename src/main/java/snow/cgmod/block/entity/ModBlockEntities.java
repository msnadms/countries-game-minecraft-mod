package snow.cgmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CountriesGameMod.MODID);

    public static final RegistryObject<BlockEntityType<BaseComputerBlockEntity>> BASE_COMP =
            BLOCK_ENTITIES.register("base_computer", () ->
                    BlockEntityType.Builder.of(BaseComputerBlockEntity::new,
                            ModBlocks.BASE_COMP.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpPlasFurnaceBlockEntity>> SP_PLAS_FURNACE =
            BLOCK_ENTITIES.register("sp_pls_furnace", () ->
                    BlockEntityType.Builder.of(SpPlasFurnaceBlockEntity::new,
                            ModBlocks.SP_PLS_FURNACE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}