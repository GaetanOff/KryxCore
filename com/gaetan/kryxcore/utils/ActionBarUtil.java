package com.gaetan.kryxcore.utils;

import com.gaetan.api.plugin.SimplePlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/*
 *  Util class for creating actionbars.
 *  Compatibility 1.7 - 1.16
 *
 * Forked from: https://github.com/ConnorLinfoot/ActionBarAPI/blob/master/src/main/java/com/connorlinfoot/actionbarapi/ActionBarAPI.java
 *
 */
public final class ActionBarUtil {
    public static SimplePlugin gCore;

    private static String nmsver;
    private static boolean useOldMethods = false;
    private static boolean isSuperior1_16Ver = false;

    public ActionBarUtil(final SimplePlugin gCore) {
        ActionBarUtil.gCore = gCore;
        nmsver = gCore.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_"))
            useOldMethods = true;

        if (nmsver.equalsIgnoreCase("v1_16_R1"))
            isSuperior1_16Ver = true;
    }

    public void sendActionBar(final Player player, final String message) {
        if (!player.isOnline()) {
            return;
        }
        try {
            final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            final Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            final Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            final Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            if (useOldMethods) {
                final Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                final Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                final Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                final Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                final Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                final Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                try {
                    final Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
                    final Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                    Object chatMessageType = null;
                    for (final Object obj : chatMessageTypes) {
                        if (obj.toString().equals("GAME_INFO")) {
                            chatMessageType = obj;
                        }
                    }
                    final Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    if (isSuperior1_16Ver) {
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass, UUID.class}).newInstance(chatCompontentText, chatMessageType, player.getUniqueId());
                    } else {
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                    }
                } catch (final ClassNotFoundException cnfe) {
                    final Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                }
            }
            final Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            final Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            try {
                final Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                final Object playerConnection = playerConnectionField.get(craftPlayerHandle);
                final Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
                sendPacketMethod.invoke(playerConnection, packet);
            } catch (final Exception e) {
                //e.printStackTrace();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void sendActionBar(final Player player, final String message, int duration) {
        this.sendActionBar(player, message);

        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ActionBarUtil.this.sendActionBar(player, "");
                }
            }.runTaskLater(gCore, duration + 1);
        }

        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    ActionBarUtil.this.sendActionBar(player, message);
                }
            }.runTaskLater(gCore, (long) duration);
        }
    }

    public void sendActionBarToAllPlayers(final String message) {
        this.sendActionBarToAllPlayers(message, -1);
    }

    public void sendActionBarToAllPlayers(final String message, final int duration) {
        for (final Player p : gCore.getServer().getOnlinePlayers()) {
            this.sendActionBar(p, message, duration);
        }
    }
}
