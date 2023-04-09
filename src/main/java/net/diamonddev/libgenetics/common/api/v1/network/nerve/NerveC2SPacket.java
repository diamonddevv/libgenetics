package net.diamonddev.libgenetics.common.api.v1.network.nerve;


import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public interface NerveC2SPacket<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> extends NervePacket<T, D> {
    @Override
    default Pathway getPathway() {
        return Pathway.C2S;
    }

    ServerPlayNetworking.PlayChannelHandler receive(Identifier channel);
}
