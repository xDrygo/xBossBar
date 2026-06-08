package dev.drygo.xbossbar.command;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.drygo.xbossbar.manager.BossBarManager;
import dev.drygo.xbossbar.manager.ConfigManager;
import dev.drygo.xbossbar.model.BossBarModel;
import dev.drygo.xbossbar.util.ChatUtils;
import dev.drygo.xbossbar.XBossBar;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class XBossBarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatUtils.getMessage("commands.usage", null));
            return false;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "create" -> {
                if (!sender.hasPermission("xbossbar.create") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.create.usage", null));
                    return false;
                }

                String name = args[1];
                boolean personalized = false;
                BarColor color = BarColor.WHITE;
                BarStyle style = BarStyle.SOLID;

                int current = 2;

                try {
                    color = BarColor.valueOf(args[current].toUpperCase());
                    current++;
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {}

                try {
                    style = BarStyle.valueOf(args[current].toUpperCase());
                    current++;
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {}

                if (current < args.length && (args[current].equalsIgnoreCase("true") || args[current].equalsIgnoreCase("false"))) {
                    personalized = Boolean.parseBoolean(args[current]);
                    current++;
                }

                if (current >= args.length) {
                    sender.sendMessage(ChatUtils.getMessage("commands.create.usage", null));
                    return false;
                }

                String title = String.join(" ", java.util.Arrays.copyOfRange(args, current, args.length)).trim();

                BossBarModel model = new BossBarModel(title, color, style, 1.0, personalized);
                BossBarManager.createBossBar(name, model);

                sender.sendMessage(ChatUtils.getMessage("commands.create.success", null)
                        .replace("%name%", name)
                        .replace("%title%", ChatUtils.formatColor(title))
                        .replace("%personalized%", String.valueOf(personalized))
                        .replace("%color%", color.name().toLowerCase())
                        .replace("%style%", style.name().toLowerCase())
                );
                return true;
            }

            case "settitle" -> {
                if (!sender.hasPermission("xbossbar.settitle") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.settitle.usage", null));
                    return false;
                }
                String name = args[1];

                BossBarModel model = BossBarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                String title = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                BossBarManager.setTitle(name, title);

                sender.sendMessage(ChatUtils.getMessage("commands.settitle.success", null)
                        .replace("%name%", name)
                        .replace("%title%", ChatUtils.formatColor(title))
                );
                return true;
            }
            case "setstyle" -> {
                if (!sender.hasPermission("xbossbar.setstyle") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.setstyle.usage", null));
                    return false;
                }

                String name = args[1];
                String styleArg = args[2].toUpperCase();

                BossBarModel model = BossBarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                try {
                    BarStyle newStyle = BarStyle.valueOf(styleArg);
                    model.setStyle(newStyle);
                    sender.sendMessage(ChatUtils.getMessage("commands.setstyle.success", null)
                            .replace("%name%", name)
                            .replace("%style%", styleArg.toLowerCase())
                    );
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatUtils.getMessage("commands.setstyle.invalid_style", null));
                }

                return true;
            }
            case "setcolor" -> {
                if (!sender.hasPermission("xbossbar.setcolor") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.setcolor.usage", null));
                    return false;
                }

                String name = args[1];
                String colorArg = args[2].toUpperCase();

                BossBarModel model = BossBarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                try {
                    BarColor newColor = BarColor.valueOf(colorArg);
                    model.setColor(newColor);
                    sender.sendMessage(ChatUtils.getMessage("commands.setcolor.success", null)
                            .replace("%name%", name)
                            .replace("%color%", colorArg.toLowerCase())
                    );
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatUtils.getMessage("commands.setcolor.invalid_color", null));
                }

                return true;
            }
            case "addplayer" -> {
                if (!sender.hasPermission("xbossbar.addplayer") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.addplayer.usage", null));
                    return false;
                }

                String barName = args[1];
                String playerName = args[2];

                if (!barName.equals("*")) {
                    BossBarModel model = BossBarManager.getBossBarModel(barName);
                    if (model == null) {
                        sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                }

                Set<String> barTargets = barName.equals("*") ? BossBarManager.getBossBarNames() : Set.of(barName);
                Collection<? extends Player> playerTargets;

                Player player =  Bukkit.getPlayer(playerName);

                if (player == null) {
                    if (!playerName.equals("*")) {
                        sender.sendMessage(ChatUtils.getMessage("error.player_not_found", null)
                                .replace("%target%", playerName));
                        return false;
                    }
                    playerTargets = new ArrayList<>(Bukkit.getOnlinePlayers());
                } else {
                    playerTargets = playerName.equals("*") ? Bukkit.getOnlinePlayers() : Set.of(player);
                }
                for (String b : barTargets) {
                    for (Player p : playerTargets) {
                        if (p != null && p.isOnline()) {
                            BossBarManager.addPlayerToBossBar(b, p);
                        }
                    }
                }

                if (playerName.equals("*")) {
                    sender.sendMessage(ChatUtils.getMessage("commands.addplayer.success.all", null));
                } else {
                    sender.sendMessage(ChatUtils.getMessage("commands.addplayer.success.one", null)
                            .replace("%target%", playerName)
                    );
                }
                return true;
            }

            case "removeplayer" -> {
                if (!sender.hasPermission("xbossbar.removeplayer") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(ChatUtils.getMessage("commands.removeplayer.usage", null));
                    return false;
                }

                String barName = args[1];
                String playerName = args[2];

                if (!barName.equals("*")) {
                    BossBarModel model = BossBarManager.getBossBarModel(barName);
                    if (model == null) {
                        sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                }
                Set<String> barTargets = barName.equals("*") ? BossBarManager.getBossBarNames() : Set.of(barName);
                Collection<? extends Player> playerTargets;

                Player player =  Bukkit.getPlayer(playerName);

                if (player == null) {
                    if (!playerName.equals("*")) {
                        sender.sendMessage(ChatUtils.getMessage("error.player_not_found", null)
                                .replace("%target%", playerName));
                        return false;
                    }
                    playerTargets = new ArrayList<>(Bukkit.getOnlinePlayers());
                } else {
                    playerTargets = playerName.equals("*") ? Bukkit.getOnlinePlayers() : Set.of(player);
                }
                for (String b : barTargets) {
                    for (Player p : playerTargets) {
                        if (p != null && p.isOnline()) {
                            BossBarManager.removePlayerFromBossBar(b, p);
                        }
                    }
                }

                if (playerName.equals("*")) {
                    sender.sendMessage(ChatUtils.getMessage("commands.removeplayer.success.all", null));
                } else {
                    sender.sendMessage(ChatUtils.getMessage("commands.removeplayer.success.one", null)
                            .replace("%target%", playerName)
                    );
                }
                return true;
            }

            case "remove" -> {
                if (!sender.hasPermission("xbossbar.remove") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatUtils.getMessage("commands.remove.usage", null));
                    return false;
                }

                String name = args[1];
                if (name.equals("*")) {
                    BossBarManager.clearAllBossBars();
                    sender.sendMessage(ChatUtils.getMessage("commands.remove.success.all", null));
                } else {
                    BossBarModel model = BossBarManager.getBossBarModel(name);
                    if (model == null) {
                        sender.sendMessage(ChatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                    BossBarManager.removeBossBar(name);
                    sender.sendMessage(ChatUtils.getMessage("commands.remove.success.one", null)
                            .replace("%name%", name)
                    );
                }
                return true;
            }
            case "info" -> {
                if (!sender.hasPermission("xbossbar.info") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                infoXBossBar(sender);
            }
            case "help" -> {
                if (!sender.hasPermission("xbossbar.help") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                List<String> helpMessage = ConfigManager.getMessageConfig().getStringList("commands.help");
                for (String line : helpMessage) {
                    sender.sendMessage(ChatUtils.formatColor(line));
                }
                return true;
            }
            case "reload" -> {
                if (!sender.hasPermission("xbossbar.reload") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(ChatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                ConfigManager.reloadAll();
                sender.sendMessage(ChatUtils.getMessage("commands.reload.success", null));
            }

            default -> sender.sendMessage(ChatUtils.getMessage("commands.usage", null));
        }

        return false;
    }
    private void infoXBossBar(CommandSender sender) {
        String placeholderStatus = XBossBar.getInstance().enabledPAPI() ? "#a0ff72✔" : "#ff7272✖";

        List<String> info = List.of(
                "&7",
                "&7",
                "&8                            #ff4b18&lx&r&lBossBar &8» &r&fInfo",
                "&7",
                "#fff18d&l                           ᴍᴀᴅᴇ ʙʏ",
                "&f                   Drygo #707070» &7&o(@33drygo / drygo.dev)",
                "&7",
                "#fff18d&l                  ʀᴜɴɴɪɴɢ ᴘʟᴜɢɪɴ ᴠᴇʀꜱɪᴏɴ",
                "&f                                    " + XBossBar.getInstance().getPluginMeta().getVersion(),
                "&7",
                "#fff18d&l                      ꜰᴇᴀᴛᴜʀᴇꜱ ᴇɴᴀʙʟᴇᴅ",
                "&f                           ᴘʟᴀᴄᴇʜᴏʟᴅᴇʀᴀᴘɪ #707070» #FFFAAB" + placeholderStatus,
                "&7",
                "#fff18d&l               ᴅʀʏɢᴏ'ꜱ ɴᴏᴛᴇ ᴏꜰ ᴛʜᴇ ᴠᴇʀꜱɪᴏɴ",
                "&f  #FFFAAB      Damn, 2 versions in one day, this plugin is hard.",
                "&7",
                "&7"
        );

        for (String line : info) {
            sender.sendMessage(ChatUtils.formatColor(line));
        }
    }
}
