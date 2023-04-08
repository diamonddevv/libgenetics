package net.diamonddev.libgentest;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacket;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TestPacket implements NerveS2CPacket<TestPacket, TestPacket.TestPacketData> {

    @Override
    public PacketByteBuf write(TestPacketData data) {
        PacketByteBuf buf = NervePacket.getNewBuf();

        buf.writeString(data.payload);

        return buf;
    }

    @Override
    public TestPacketData read(PacketByteBuf buf) {
        return new TestPacketData(buf.readString());
    }

    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return (client, handler, buf, responseSender) -> {
            System.out.println("Recieved Packet from '" + channel + "'! Payload String: ");
            System.out.println("Value: " + read(buf).payload);
        };
    }

    public static class TestPacketData extends NervePacketData {
        public TestPacketData(String data) {
            this.payload = data;
        }
        public String payload;
    }
}
