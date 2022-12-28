package snow.cgmod.coord;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCoordinatesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerCoordinates> COORDS = CapabilityManager.get(new CapabilityToken<PlayerCoordinates>(){});
    private PlayerCoordinates pcoords = null;
    private final LazyOptional<PlayerCoordinates> optional = LazyOptional.of(this::createPlayerCoords);

    private @NotNull PlayerCoordinates createPlayerCoords() {
        if (this.pcoords == null) {
            this.pcoords = new PlayerCoordinates();
        }
        return this.pcoords;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == COORDS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCoords().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCoords().loadNBTData(nbt);
    }
}
