package net.diamonddev.libgenetics.core.mixin;

import net.diamonddev.libgenetics.common.api.v1.network.NerveNetworker;
import net.diamonddev.libgentest.GeneticsTest;
import net.diamonddev.libgentest.TestPacket;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    public void libgentest$sendTestPacket(SentMessage message, boolean filterMaskEnabled, MessageType.Parameters params, CallbackInfo ci) {
        if (message.getContent().getString().equals("send")) {
            NerveNetworker.send((ServerPlayerEntity)(Object)this, GeneticsTest.testPacketRegistryEntry, new TestPacket.TestPacketData("hello world"));
        }
    }
}
