package snow.cgmod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import snow.cgmod.CountriesGameMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_DIA_ALLOY_TOOL
                = tag("needs_dia_alloy_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(CountriesGameMod.MODID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}