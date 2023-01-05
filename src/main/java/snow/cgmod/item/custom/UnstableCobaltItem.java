package snow.cgmod.item.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import snow.cgmod.item.ModItems;

public class UnstableCobaltItem extends Item {

    public UnstableCobaltItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        entity.setSecondsOnFire(10);
        entity.hurt(DamageSource.LAVA, 5);
        stack.shrink(1);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int num, boolean flag) {
        entity.setSecondsOnFire(1);
        super.inventoryTick(stack, level, entity, num, flag);
    }
}
