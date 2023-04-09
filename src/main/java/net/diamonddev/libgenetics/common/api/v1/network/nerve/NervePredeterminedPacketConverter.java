package net.diamonddev.libgenetics.common.api.v1.network.nerve;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NervePredeterminedPacketConverter {

    public static <
            S extends Serializer<D>,
            D extends Data>
    NerveCompatibleConvertedPacket<S, D>
    createConvertedPacket(Identifier channel, Pathway pathway, S serializer) {

        return new NerveCompatibleConvertedPacket<>(channel, pathway, serializer);
    }

    public static <S extends Serializer<D>, D extends Data> void registerClientPacketListener(NerveCompatibleConvertedPacket<S, D> convertedPacket) {
        if (convertedPacket.pathway == Pathway.S2C) {
            ClientPlayNetworking.registerGlobalReceiver(convertedPacket.channel, convertedPacket.receiveClient());
        } else NerveCompatibleConvertedPacket.throwPathwayException(Pathway.other(Pathway.S2C));
    }
    public static <S extends Serializer<D>, D extends Data> void registerServerPacketListener(NerveCompatibleConvertedPacket<S, D> convertedPacket) {
        if (convertedPacket.pathway == Pathway.C2S) {
            ServerPlayNetworking.registerGlobalReceiver(convertedPacket.channel, convertedPacket.receiveServer());
        } else NerveCompatibleConvertedPacket.throwPathwayException(Pathway.other(Pathway.C2S));
    }

    public static class NerveCompatibleConvertedPacket<S extends Serializer<D>, D extends Data> {


        private final Identifier channel;
        private final Pathway pathway;
        private final S serializer;

        private NerveCompatibleConvertedPacket(Identifier channel, Pathway pathway, S serializer) {
            this.channel = channel;
            this.pathway = pathway;
            this.serializer = serializer;
        }


        public Identifier getChannel() {
            return channel;
        }

        public Pathway getPathway() {
            return pathway;
        }

        public S getSerializer() {
            return serializer;
        }


        public ClientPlayNetworking.PlayChannelHandler receiveClient() {
            if (serializer instanceof S2CSerializer<?> s2c) {
                return s2c.receive();
            } else throwPathwayException(Pathway.other(Pathway.S2C));
            return null;
        }
        public ServerPlayNetworking.PlayChannelHandler receiveServer() {
            if (serializer instanceof C2SSerializer<?> c2s) {
                return c2s.receive();
            } else throwPathwayException(Pathway.other(Pathway.C2S));
            return null;
        }

        public static void throwPathwayException(Pathway pathway) {
            String s = pathway == Pathway.C2S ? "client" : "server";
            throw new NerveNetworkingException("This packet is not received on the " + s + "!");
        }
    }

    public interface S2CSerializer<D extends Data> extends Serializer<D> {
        ClientPlayNetworking.PlayChannelHandler receive();
    }

    public interface C2SSerializer<D extends Data> extends Serializer<D> {
        ServerPlayNetworking.PlayChannelHandler receive();
    }

    public interface Serializer<D extends Data> {
        PacketByteBuf write(D data);
        D read(PacketByteBuf buf);
    }

    public interface Data extends NervePacket.NervePacketData {}
}
