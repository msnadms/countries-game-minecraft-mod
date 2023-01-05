package snow.cgmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.item.custom.ClaimStickItem;
import snow.cgmod.item.custom.UnstableCobaltItem;

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

    public static final RegistryObject<Item> DIAMOND_ALLOY = ITEMS.register("diamond_alloy",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> CHARGED_DIAMOND_ALLOY_SWORD = ITEMS.register("ch_dia_alloy_sword",
            () -> new SwordItem(ModToolTiers.DIA_ALLOY, 10, 5f,
                    new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP).durability(15000)));

    public static final RegistryObject<Item> CHARGED_NETHERITE_HANDLE = ITEMS.register("ch_netherite_handle",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> SUPERHEATED_PLASMA = ITEMS.register("superheated_plasma",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> UNSTABLE_COBALT = ITEMS.register("unstable_cobalt",
            () -> new UnstableCobaltItem(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));

    public static final RegistryObject<Item> COBALT_ALLOY = ITEMS.register("cobalt_alloy",
            () -> new Item(new Item.Properties().tab(ModItemGroup.COUNTRIES_GROUP)));


}
