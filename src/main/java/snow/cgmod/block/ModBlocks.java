package snow.cgmod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.block.custom.BaseComputerBlock;
import snow.cgmod.block.custom.SpPlasFurnaceBlock;
import snow.cgmod.block.custom.WorkComputerBlock;
import snow.cgmod.item.ModItemGroup;
import snow.cgmod.item.ModItems;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CountriesGameMod.MODID);

    public static final RegistryObject<Block> SILCRYST_ORE = registerBlock("silicon_crystal_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
            ModItemGroup.COUNTRIES_GROUP);

    public static final RegistryObject<Block> SILCRYST_BLOCK = registerBlock("silicon_crystal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()),
            ModItemGroup.COUNTRIES_GROUP);

    public static final RegistryObject<Block> BASE_COMP = registerBlock("base_computer",
            () -> new BaseComputerBlock(BlockBehaviour.Properties.of(Material.METAL)),
            ModItemGroup.COUNTRIES_GROUP);

    public static final RegistryObject<Block> SP_PLS_FURNACE = registerBlock("sp_pls_furnace",
            () -> new SpPlasFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).lightLevel((s) -> 13)),
            ModItemGroup.COUNTRIES_GROUP);

    public static final RegistryObject<Block> WORK_COMP = registerBlock("work_computer",
            () -> new WorkComputerBlock(BlockBehaviour.Properties.of(Material.METAL)),
            ModItemGroup.COUNTRIES_GROUP);

    public static void register(IEventBus eb) {
        BLOCKS.register(eb);
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


}
