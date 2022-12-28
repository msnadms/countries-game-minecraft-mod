package snow.cgmod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import snow.cgmod.networking.ModPackets;
import snow.cgmod.networking.packet.ClaimStickPacket;

public class ClaimStickItem extends Item {

    public ClaimStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            ModPackets.sendToServer(new ClaimStickPacket());
        }
        return super.use(level, player, hand);
    }

}
