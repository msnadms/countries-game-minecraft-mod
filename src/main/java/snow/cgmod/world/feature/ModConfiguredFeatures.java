package snow.cgmod.world.feature;

import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.block.ModBlocks;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ModConfiguredFeatures {

    public static DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, CountriesGameMod.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OW_SILICON_CRYSTAL_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.SILCRYST_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SILCRYST_ORE = CONFIGURED_FEATURES.register("silicon_crystal_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Objects.requireNonNull(OW_SILICON_CRYSTAL_ORE.get()),7)));

    public static void register(IEventBus eb) {
        CONFIGURED_FEATURES.register(eb);
    }

}
