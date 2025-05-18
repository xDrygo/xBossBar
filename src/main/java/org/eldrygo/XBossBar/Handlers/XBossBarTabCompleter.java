package org.eldrygo.XBossBar.Handlers;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class XBossBarTabCompleter implements TabCompleter {
    private final BossBarManager bossbarManager;

    public XBossBarTabCompleter(BossBarManager bossbarManager) {
        this.bossbarManager = bossbarManager;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("create", "settitle", "setstyle", "setcolor", "addplayer", "removeplayer", "remove", "help", "info", "reload"));
        } else {
            String sub = args[0].toLowerCase();

            switch (sub) {
                case "create" -> {
                    if (args.length == 2) {
                        completions.add("id");
                    } else if (args.length == 3) {
                        completions.addAll(Arrays.stream(BarColor.values())
                                .map(color -> color.name().toLowerCase())
                                .toList());
                    } else if (args.length == 4) {
                        completions.addAll(Arrays.stream(BarStyle.values())
                                .map(style -> style.name().toLowerCase())
                                .toList());
                    } else if (args.length == 5) {
                        completions.addAll(List.of("true", "false"));
                    } else if (args.length == 6) {
                        completions.add("title");
                    }
                }
                case "settitle", "setstyle", "setcolor", "addplayer", "removeplayer", "remove" -> {
                    if (args.length == 2) {
                        completions.addAll(bossbarManager.getBossBarNames());
                        if (sub.equals("remove") || sub.endsWith("player")) {
                            completions.add("*");
                        }
                    } else if ((sub.equals("setcolor") || sub.equals("setstyle")) && args.length == 3) {
                        if (sub.equals("setcolor")) {
                            completions.addAll(Arrays.stream(BarColor.values())
                                    .map(color -> color.name().toLowerCase())
                                    .toList());
                        } else {
                            completions.addAll(Arrays.stream(BarStyle.values())
                                    .map(style -> style.name().toLowerCase())
                                    .toList());
                        }
                    } else if ((sub.equals("addplayer") || sub.equals("removeplayer")) && args.length == 3) {
                        for (Player player : sender.getServer().getOnlinePlayers()) {
                            completions.add(player.getName());
                        }
                        completions.add("*");
                    }
                }
            }
        }
        return completions.stream()
                .filter(s -> args[args.length - 1].isEmpty() || s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
