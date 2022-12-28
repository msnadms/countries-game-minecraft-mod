package snow.cgmod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import snow.cgmod.CountriesGameMod;
import snow.cgmod.networking.packet.ClaimStickPacket;

public class ModPackets {

    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CountriesGameMod.MODID, "packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ClaimStickPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClaimStickPacket::new)
                .encoder(ClaimStickPacket::toBytes)
                .consumerMainThread(ClaimStickPacket::handle)
                .add();
    }

    public static <PKT> void sendToServer(PKT packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PKT> void sendToPlayer(PKT packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

}