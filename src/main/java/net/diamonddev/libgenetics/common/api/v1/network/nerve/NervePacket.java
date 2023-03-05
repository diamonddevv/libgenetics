package net.diamonddev.libgenetics.common.api.v1.network.nerve;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

/**
 * Don't use this - use NerveS2CPacket or NerveC2SPacket instead.
 *
 * @see NerveS2CPacket
 * @see NerveC2SPacket
 */
public interface NervePacket<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> {

    default PacketByteBuf getNewBuf() {
        return PacketByteBufs.create();
    }

    PacketByteBuf write(D data);
    D read(PacketByteBuf buf);

    NerveNetworker.Pathway getPathway();

    class NervePacketData {}
}
