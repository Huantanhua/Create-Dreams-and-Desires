package uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import uwu.lopyluna.create_dd.block.YIPPEEEntityTypes;

public class HydraulicPressBlock extends MechanicalPressBlock {
    public HydraulicPressBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends HydraulicPressBlockEntity> getBlockEntityType() {
        return YIPPEEEntityTypes.hydraulic_press.get();
    }
}
