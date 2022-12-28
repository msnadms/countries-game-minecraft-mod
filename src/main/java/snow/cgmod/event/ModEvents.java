package snow.cgmod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.coord.PlayerCoordinates;
import snow.cgmod.coord.PlayerCoordinatesProvider;

@Mod.EventBusSubscriber(modid = CountriesGameMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerCoordinatesProvider.COORDS).isPresent()) {
                event.addCapability(new ResourceLocation(CountriesGameMod.MODID, "properties"),
                        new PlayerCoordinatesProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerCoordinatesProvider.COORDS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerCoordinatesProvider.COORDS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerCoordinates.class);
    }




}
