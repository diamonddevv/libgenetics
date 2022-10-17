package net.diamonddev.libgenetics.common.api.v1.block.model;

import net.minecraft.util.shape.VoxelShape;

public interface HorizontalRotationModelShape {
    VoxelShape getNorthShape();
    VoxelShape getEastShape();
    VoxelShape getSouthShape();
    VoxelShape getWestShape();
}
