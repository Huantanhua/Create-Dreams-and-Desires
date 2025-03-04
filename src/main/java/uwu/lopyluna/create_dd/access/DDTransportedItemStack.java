package uwu.lopyluna.create_dd.access;

import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.FanProcessing;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_dd.recipes.BakingFanProcessing;

import java.util.Random;

public class DDTransportedItemStack extends TransportedItemStack {

    public FanProcessing.Type processedBy;

    private static Random R = new Random();

    public ItemStack stack;
    public float beltPosition;
    public float sideOffset;
    public int angle;
    public int insertedAt;
    public Direction insertedFrom;
    public boolean locked;
    public boolean lockedExternally;

    public float prevBeltPosition;
    public float prevSideOffset;

    public int processingTime;

    public DDTransportedItemStack(ItemStack stack) {
        super(stack);
        this.stack = stack;
        boolean centered = BeltHelper.isItemUpright(stack);
        angle = centered ? 180 : R.nextInt(360);
        sideOffset = prevSideOffset = getTargetSideOffset();
        insertedFrom = Direction.UP;
    }

    public float getTargetSideOffset() {
        return (angle - 180) / (360 * 3f);
    }

    public int compareTo(DDTransportedItemStack o) {
        return beltPosition < o.beltPosition ? 1 : beltPosition > o.beltPosition ? -1 : 0;
    }

    public DDTransportedItemStack getSimilar() {
        DDTransportedItemStack copy = new DDTransportedItemStack(stack.copy());
        copy.beltPosition = beltPosition;
        copy.insertedAt = insertedAt;
        copy.insertedFrom = insertedFrom;
        copy.prevBeltPosition = prevBeltPosition;
        copy.prevSideOffset = prevSideOffset;
        copy.processedBy = processedBy;
        copy.processingTime = processingTime;
        return copy;
    }

    public DDTransportedItemStack copy() {
        DDTransportedItemStack copy = getSimilar();
        copy.angle = angle;
        copy.sideOffset = sideOffset;
        return copy;
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Item", stack.serializeNBT());
        nbt.putFloat("Pos", beltPosition);
        nbt.putFloat("PrevPos", prevBeltPosition);
        nbt.putFloat("Offset", sideOffset);
        nbt.putFloat("PrevOffset", prevSideOffset);
        nbt.putInt("InSegment", insertedAt);
        nbt.putInt("Angle", angle);
        nbt.putInt("InDirection", insertedFrom.get3DDataValue());
        if (locked)
            nbt.putBoolean("Locked", locked);
        if (lockedExternally)
            nbt.putBoolean("LockedExternally", lockedExternally);
        return nbt;
    }

    public static DDTransportedItemStack read(CompoundTag nbt) {
        DDTransportedItemStack stack = new DDTransportedItemStack(ItemStack.of(nbt.getCompound("Item")));
        stack.beltPosition = nbt.getFloat("Pos");
        stack.prevBeltPosition = nbt.getFloat("PrevPos");
        stack.sideOffset = nbt.getFloat("Offset");
        stack.prevSideOffset = nbt.getFloat("PrevOffset");
        stack.insertedAt = nbt.getInt("InSegment");
        stack.angle = nbt.getInt("Angle");
        stack.insertedFrom = Direction.from3DDataValue(nbt.getInt("InDirection"));
        stack.locked = nbt.getBoolean("Locked");
        stack.lockedExternally = nbt.getBoolean("LockedExternally");
        return stack;
    }
}
