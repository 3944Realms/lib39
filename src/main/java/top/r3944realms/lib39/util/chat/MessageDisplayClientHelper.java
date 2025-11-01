package top.r3944realms.lib39.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class MessageDisplayClientHelper {
    public static void sendSystemMessage(Component component) {
        sendSystemMessage(component, false);
    }
    public static void sendSystemMessage(Component component, boolean isOverlay) {
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().getChatListener().handleSystemMessage(component, isOverlay));
    }
}
