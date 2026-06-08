package dev.drygo.xbossbar.util;

import dev.drygo.xbossbar.XBossBar;

import java.util.LinkedHashMap;
import java.util.Map;

public class DebugUtils {
    private static final int MAX_DEBUG_ENTRIES = 256;
    private static final Map<String, Long> lastDebugTimes = new LinkedHashMap<>(64, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Long> eldest) {
            return size() > MAX_DEBUG_ENTRIES;
        }
    };
    private static final long COOLDOWN_MILLIS = 250;

    public static void debug(String message) {
        if (!XBossBar.getInstance().getConfig().getBoolean("settings.debug", false)) return;

        long now = System.currentTimeMillis();
        long last = lastDebugTimes.getOrDefault(message, 0L);

        if (now - last >= COOLDOWN_MILLIS) {
            lastDebugTimes.put(message, now);
            XBossBar.getInstance().getLogger().info("[DEBUG] " + message);
        }
    }

    public static boolean isDebug() {
        return XBossBar.getInstance().getConfig().getBoolean("settings.debug", false);
    }
}