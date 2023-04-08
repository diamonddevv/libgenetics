package net.diamonddev.libgenetics.common.api.v1.network.nerve;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class NerveConnectionEvents {
    private NerveConnectionEvents() {
    }

    public static final Event<ClientConnect> CLIENT_CONNECT = EventFactory.createArrayBacked(ClientConnect.class, callbacks -> (handler, client) -> {
        for (ClientConnect callback : callbacks) {
            callback.onClientConnectsToServer(handler, client);
        }
    });
    public static final Event<ClientDisconnect> CLIENT_DISCONNECT = EventFactory.createArrayBacked(ClientDisconnect.class, callbacks -> (handler, client) -> {
        for (ClientDisconnect callback : callbacks) {
            callback.onClientDisconnectsFromServer(handler, client);
        }
    });
    public static final Event<ServerConnect> SERVER_CONNECT = EventFactory.createArrayBacked(ServerConnect.class, callbacks -> (handler, client) -> {
        for (ServerConnect callback : callbacks) {
            callback.onClientConnectsToServer(handler, client);
        }
    });
    public static final Event<ServerDisconnect> SERVER_DISCONNECT = EventFactory.createArrayBacked(ServerDisconnect.class, callbacks -> (handler, client) -> {
        for (ServerDisconnect callback : callbacks) {
            callback.onClientDisconnectsFromServer(handler, client);
        }
    });


    @FunctionalInterface
    public interface ClientConnect {
        void onClientConnectsToServer(ClientPlayNetworkHandler handler, MinecraftClient client);
    }

    @FunctionalInterface
    public interface ClientDisconnect {
        void onClientDisconnectsFromServer(ClientPlayNetworkHandler handler, MinecraftClient client);
    }

    @FunctionalInterface
    public interface ServerConnect {
        void onClientConnectsToServer(ClientPlayNetworkHandler handler, MinecraftClient client);
    }

    @FunctionalInterface
    public interface ServerDisconnect {
        void onClientDisconnectsFromServer(ClientPlayNetworkHandler handler, MinecraftClient client);
    }
}
