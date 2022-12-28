package snow.cgmod.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import snow.cgmod.coord.PlayerCoordinatesProvider;
import snow.cgmod.item.custom.ClaimStickItem;

import java.util.function.Supplier;

public class ClaimStickPacket {

    public ClaimStickPacket() {

    }

    public ClaimStickPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            BlockPos pos = player.blockPosition();
            player.getCapability(PlayerCoordinatesProvider.COORDS).ifPresent(coords -> {
                coords.setCorner(pos);
            });
            player.sendSystemMessage(Component.literal("Position: " + pos.toString()));

        });
        return true;
    }

}
