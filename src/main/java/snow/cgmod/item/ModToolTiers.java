package snow.cgmod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import snow.cgmod.CountriesGameMod;

import java.util.List;

public class ModToolTiers {
    public static Tier DIA_ALLOY = TierSortingRegistry.registerTier(
                new ForgeTier(5, 15000, 2.0F, 7.0F, 24,
                        ModTags.Blocks.NEEDS_DIA_ALLOY_TOOL, () -> Ingredient.of(ModItems.DIAMOND_ALLOY.get())),
                new ResourceLocation(CountriesGameMod.MODID, "diamond_alloy"), List.of(Tiers.NETHERITE), List.of());
}