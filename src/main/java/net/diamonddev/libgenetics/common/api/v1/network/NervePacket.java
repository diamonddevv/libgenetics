package net.diamonddev.libgenetics.common.api.v1.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

/**
 * Don't use this - use NerveS2CPacket or NerveC2SPacket instead.
 *
 * @see NerveS2CPacket
 * @see NerveC2SPacket
 */
public interface NervePacket<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> {

    default PacketByteBuf getEmptyBuf() {
        return PacketByteBufs.empty();
    }

    PacketByteBuf write(D data);
    D read(PacketByteBuf buf);

    NerveNetworker.Pathway getPathway();

    class NervePacketData {}
}
