package net.diamonddev.libgenetics.common.api.v1.block.model;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class HorizontalRotationalBlock extends HorizontalFacingBlock implements HorizontalRotationModelShape {
    protected HorizontalRotationalBlock(Settings settings) {
        super(settings);
    }

    public boolean shouldPlaceOpposite() {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case SOUTH -> getSouthShape();
            case WEST -> getWestShape();
            case EAST -> getEastShape();
            default -> getNorthShape();
        };
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, shouldPlaceOpposite() ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerFacing());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
