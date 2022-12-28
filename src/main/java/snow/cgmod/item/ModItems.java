package snow.cgmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.item.custom.ClaimStickItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CountriesGameMod.MODID);

    public static void register(IEventBus eb) {
        ITEMS.register(eb);
    }

    public static final RegistryObject<Item> SILCRYST = ITEMS.register("silicon_crystal",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> CHARGED_SIL_CHIP = ITEMS.register("charged_silicon_chip",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> CLAIM_STICK = ITEMS.register("claim_stick",
            () -> new ClaimStickItem(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP).stacksTo(1)));


}
