package net.diamonddev.libgenetics.common.api.v1.network.nerve;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public interface NerveS2CPacket<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> extends NervePacket<T, D> {
    @Override
    default Pathway getPathway() {
        return Pathway.S2C;
    }

    ClientPlayNetworking.PlayChannelHandler receive(Identifier channel);
}
