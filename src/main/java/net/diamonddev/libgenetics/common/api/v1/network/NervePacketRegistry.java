package net.diamonddev.libgenetics.common.api.v1.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class NervePacketRegistry<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> {
    private static final HashMap<Identifier, NervePacketRegistryEntry<?, ?>> REGISTRY_HASH = new HashMap<>();


    public static <T extends NervePacket<T, D>, D extends NervePacket.NervePacketData> NervePacketRegistryEntry<T, D> register(Identifier reference, NervePacketRegistryEntry<T, D> entry) {
        REGISTRY_HASH.put(reference, entry);
        return entry;
    }
    public static Identifier getChannelId(Identifier reference) {
        return REGISTRY_HASH.get(reference).channel;
    }

    /**
     * Should be called on Client Initialization.
     * @param reference PacketRegistry ID
     */
    public static void initClientS2CReciever(Identifier reference) {
        Identifier channel = getChannelId(reference);
        NervePacket<?, ?> packet = REGISTRY_HASH.get(reference).packet();
        if (packet instanceof NerveS2CPacket<?,?> s2cPacket) {
            ClientPlayNetworking.registerGlobalReceiver(channel, s2cPacket.receive(channel));
        }
    }

    /**
     * Should be called on Server Initialization.
     * @param reference PacketRegistry ID
     */
    public static void initServerC2SReciever(Identifier reference) {
        Identifier channel = getChannelId(reference);
        NervePacket<?, ?> packet = REGISTRY_HASH.get(reference).packet();
        if (packet instanceof NerveC2SPacket<?,?> c2sPacket) {
            ServerPlayNetworking.registerGlobalReceiver(channel, c2sPacket.receive(channel));
        }
    }

    public record NervePacketRegistryEntry<T extends NervePacket<T, D>, D extends NervePacket.NervePacketData>(Identifier channel, NervePacket<T, D> packet) {
    }
}
