package snow.cgmod.coord;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class PlayerCoordinates {

    private BlockPos corner1;
    private BlockPos corner2;
    private BlockPos centerBlock;
    private boolean hasClicked = false;

    public PlayerCoordinates() {
        corner1 = new BlockPos(0, 0, 0);
        corner2 = new BlockPos(0, 0, 0);
    }

    public BlockPos getCorner1() {
        return corner1;
    }

    public BlockPos getCorner2() {
        return corner2;
    }

    private void reset() {
        hasClicked = false;
    }

    private int calcMidpoint(int a, int b) {
        return Math.abs((a - b) / 2) + Math.min(a, b);
    }
    private void calculateCenterPoint() {
        int x = calcMidpoint(corner1.getX(), corner2.getX());
        int y = calcMidpoint(corner1.getY(), corner2.getY());
        int z = calcMidpoint(corner1.getZ(), corner2.getZ());
        this.centerBlock = new BlockPos(x, y, z);
    }

    public void setCorner(BlockPos pos) {
        if (!hasClicked) {
            corner1 = pos;
            hasClicked = true;
        } else {
            corner2 = pos;
            calculateCenterPoint();
            reset();
        }
    }

    //Conversions from int[] to blockpos and vice versa
    private int[] bpToIntArr(BlockPos pos) {
        return new int[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    private BlockPos intArrToBp(int[] arr) {
        return new BlockPos(arr[0], arr[1], arr[2]);
    }

    public int getLength() {
        return Math.abs(corner1.getX() - corner2.getX());
    }

    public int getWidth() {
        return Math.abs(corner1.getZ() - corner2.getZ());
    }

    public void saveNBTData(CompoundTag nbt) {
        int[] c1Arr = bpToIntArr(corner1);
        int[] c2Arr = bpToIntArr(corner2);
        nbt.putIntArray("corner1", c1Arr);
        nbt.putIntArray("corner2", c2Arr);
    }

    public void loadNBTData(CompoundTag nbt) {
        int[] p1 = nbt.getIntArray("corner1");
        int[] p2 = nbt.getIntArray("corner2");
        corner1 = intArrToBp(p1);
        corner2 = intArrToBp(p2);
    }

    public void copyFrom(PlayerCoordinates source) {
        this.corner1 = source.corner1;
        this.corner2 = source.corner2;
        this.hasClicked = source.hasClicked;
    }
}