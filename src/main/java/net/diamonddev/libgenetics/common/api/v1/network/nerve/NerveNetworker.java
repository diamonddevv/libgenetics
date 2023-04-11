package net.diamonddev.libgenetics.common.api.v1.network.nerve;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class NerveNetworker {
    /**
     * LibGenetics Networking API.
     * I called it Nerve, after the Nervous System in living creatures, much how the whole API is named after the foundations of life.
     */
    public static PacketByteBuf getNewBuf() {
        return PacketByteBufs.create();
    }

    private static void send(Pathway pathway, @Nullable ServerPlayerEntity serverPlayer, Identifier channel, PacketByteBuf buf) {
        switch (pathway) {
            case C2S -> ClientPlayNetworking.send(channel, buf);
            case S2C -> ServerPlayNetworking.send(serverPlayer, channel, buf);
        }
    }

    public static <T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> void send(@Nullable ServerPlayerEntity serverPlayer,
                                                                                                 NervePacketRegistry.NervePacketRegistryEntry<T, D> entry,
                                                                                                 D data) {
        send(entry.packet().getPathway(), serverPlayer, entry.channel(), entry.packet().write(data));
    }


    public static <S extends NervePredeterminedPacketConverter.Serializer<D>,
            D extends NervePredeterminedPacketConverter.Data> void send(@Nullable ServerPlayerEntity serverPlayer,
                                                                        NervePredeterminedPacketConverter.NerveCompatibleConvertedPacket<S, D> convertedPacket,
                                                                        D data) {
        send(convertedPacket.getPathway(), serverPlayer, convertedPacket.getChannel(), convertedPacket.getSerializer().write(data));
    }

}