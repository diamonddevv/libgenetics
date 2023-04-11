package net.diamonddev.libgenetics.common.api.v1.network.nerve;

import net.minecraft.network.PacketByteBuf;

/**
 * Don't use this - use NerveS2CPacket or NerveC2SPacket instead.
 *
 * @see NerveS2CPacket
 * @see NerveC2SPacket
 */
interface NervePacket<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> {

    PacketByteBuf write(D data);
    D read(PacketByteBuf buf);

    Pathway getPathway();

    interface NervePacketData {}
}
