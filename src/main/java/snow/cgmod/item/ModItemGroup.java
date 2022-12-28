package snow.cgmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab COUNTRIES_GROUP = new CreativeModeTab("countriesTab") {
        @Override
        public ItemStack makeIcon() {
            return ModItems.SILCRYST.get().getDefaultInstance();
        }
    };

}
