package org.eldrygo.XBossBar.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Managers.ConfigManager;
import org.eldrygo.XBossBar.Models.BossBarModel;
import org.eldrygo.XBossBar.Utils.ChatUtils;
import org.eldrygo.XBossBar.Utils.LoadUtils;
import org.eldrygo.XBossBar.XBossBar;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class XBossBarCommand implements CommandExecutor {
    private final ChatUtils chatUtils;
    private final BossBarManager bossbarManager;
    private final ConfigManager configManager;
    private final XBossBar plugin;
    private final LoadUtils loadUtils;

    public XBossBarCommand(ChatUtils chatUtils, BossBarManager bossbarManager, ConfigManager configManager, XBossBar plugin, LoadUtils loadUtils) {
        this.chatUtils = chatUtils;
        this.bossbarManager = bossbarManager;
        this.configManager = configManager;
        this.plugin = plugin;
        this.loadUtils = loadUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("command.usage", null));
            return false;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "create" -> {
                if (!sender.hasPermission("xbossbar.command.create") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.create.usage", null));
                    return false;
                }

                String name = args[1];
                boolean personalized = false;
                BarColor color = BarColor.WHITE;
                BarStyle style = BarStyle.SOLID;

                int current = 2;

                // Detectar color si es válido
                try {
                    color = BarColor.valueOf(args[current].toUpperCase());
                    current++;
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {}

                // Detectar estilo si es válido
                try {
                    style = BarStyle.valueOf(args[current].toUpperCase());
                    current++;
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {}

                // Detectar personalizado si es booleano válido
                if (current < args.length && (args[current].equalsIgnoreCase("true") || args[current].equalsIgnoreCase("false"))) {
                    personalized = Boolean.parseBoolean(args[current]);
                    current++;
                }

                // Verificar que quede al menos un argumento para el título
                if (current >= args.length) {
                    sender.sendMessage(chatUtils.getMessage("command.create.usage", null));
                    return false;
                }

                String title = String.join(" ", java.util.Arrays.copyOfRange(args, current, args.length)).trim();

                BossBarModel model = new BossBarModel(title, color, style, 1.0, personalized);
                bossbarManager.createBossBar(name, model);

                sender.sendMessage(chatUtils.getMessage("command.create.success", null)
                        .replace("%name%", name)
                        .replace("%title%", ChatUtils.formatColor(title))
                        .replace("%personalized%", String.valueOf(personalized))
                        .replace("%color%", color.name().toLowerCase())
                        .replace("%style%", style.name().toLowerCase())
                );
                return true;
            }

            case "settitle" -> {
                if (!sender.hasPermission("xbossbar.command.settitle") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.settitle.usage", null));
                    return false;
                }
                String name = args[1];

                BossBarModel model = bossbarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                String title = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                bossbarManager.setTitle(name, title);

                sender.sendMessage(chatUtils.getMessage("command.settitle.success", null)
                        .replace("%name%", name)
                        .replace("%title%", ChatUtils.formatColor(title))
                );
                return true;
            }
            case "setstyle" -> {
                if (!sender.hasPermission("xbossbar.command.setstyle") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.setstyle.usage", null));
                    return false;
                }

                String name = args[1];
                String styleArg = args[2].toUpperCase();

                BossBarModel model = bossbarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                try {
                    BarStyle newStyle = BarStyle.valueOf(styleArg);
                    model.setStyle(newStyle);
                    sender.sendMessage(chatUtils.getMessage("command.setstyle.success", null)
                            .replace("%name%", name)
                            .replace("%style%", styleArg.toLowerCase())
                    );
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(chatUtils.getMessage("command.setstyle.invalid_style", null));
                }

                return true;
            }
            case "setcolor" -> {
                if (!sender.hasPermission("xbossbar.command.setcolor") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.setcolor.usage", null));
                    return false;
                }

                String name = args[1];
                String colorArg = args[2].toUpperCase();

                BossBarModel model = bossbarManager.getBossBarModel(name);
                if (model == null) {
                    sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                    return false;
                }

                try {
                    BarColor newColor = BarColor.valueOf(colorArg);
                    model.setColor(newColor);
                    sender.sendMessage(chatUtils.getMessage("command.setcolor.success", null)
                            .replace("%name%", name)
                            .replace("%color%", colorArg.toLowerCase())
                    );
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(chatUtils.getMessage("command.setcolor.invalid_color", null));
                }

                return true;
            }
            case "addplayer" -> {
                if (!sender.hasPermission("xbossbar.command.addplayer") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.addplayer.usage", null));
                    return false;
                }

                String barName = args[1];
                String playerName = args[2];

                if (!barName.equals("*")) {
                    BossBarModel model = bossbarManager.getBossBarModel(barName);
                    if (model == null) {
                        sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                }

                Set<String> barTargets = barName.equals("*") ? bossbarManager.getBossBarNames() : Set.of(barName);
                Collection<? extends Player> playerTargets = playerName.equals("*") ? Bukkit.getOnlinePlayers() : Set.of(Bukkit.getPlayerExact(playerName));

                for (String b : barTargets) {
                    for (Player p : playerTargets) {
                        if (p != null && p.isOnline()) {
                            bossbarManager.addPlayerToBossBar(b, p);
                        }
                    }
                }

                if (playerName.equals("*")) {
                    sender.sendMessage(chatUtils.getMessage("command.addplayer.success.all", null));
                } else {
                    sender.sendMessage(chatUtils.getMessage("command.addplayer.success.one", null)
                            .replace("%target%", Bukkit.getPlayerExact(playerName).getName())
                    );
                }
                return true;
            }

            case "removeplayer" -> {
                if (!sender.hasPermission("xbossbar.command.removeplayer") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(chatUtils.getMessage("command.removeplayer.usage", null));
                    return false;
                }

                String barName = args[1];
                String playerName = args[2];

                if (!barName.equals("*")) {
                    BossBarModel model = bossbarManager.getBossBarModel(barName);
                    if (model == null) {
                        sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                }
                Set<String> barTargets = barName.equals("*") ? bossbarManager.getBossBarNames() : Set.of(barName);
                Collection<? extends Player> playerTargets = playerName.equals("*") ? Bukkit.getOnlinePlayers() : Set.of(Bukkit.getPlayerExact(playerName));

                for (String b : barTargets) {
                    for (Player p : playerTargets) {
                        if (p != null && p.isOnline()) {
                            bossbarManager.removePlayerFromBossBar(b, p);
                        }
                    }
                }

                if (playerName.equals("*")) {
                    sender.sendMessage(chatUtils.getMessage("command.removeplayer.success.all", null));
                } else {
                    sender.sendMessage(chatUtils.getMessage("command.removeplayer.success.one", null)
                            .replace("%target%", Bukkit.getPlayerExact(playerName).getName())
                    );
                }
                return true;
            }

            case "remove" -> {
                if (!sender.hasPermission("xbossbar.command.remove") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(chatUtils.getMessage("command.remove.usage", null));
                    return false;
                }

                String name = args[1];
                if (name.equals("*")) {
                    bossbarManager.clearAllBossBars();
                    sender.sendMessage(chatUtils.getMessage("command.remove.success.all", null));
                } else {
                    BossBarModel model = bossbarManager.getBossBarModel(name);
                    if (model == null) {
                        sender.sendMessage(chatUtils.getMessage("error.bossbar_not_found", null));
                        return false;
                    }
                    bossbarManager.removeBossBar(name);
                    sender.sendMessage(chatUtils.getMessage("command.remove.success.one", null)
                            .replace("%name%", name)
                    );
                }
                return true;
            }
            case "info" -> {
                if (!sender.hasPermission("xbossbar.command.info") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                infoXBossBar(sender);
            }
            case "help" -> {
                if (!sender.hasPermission("xbossbar.command.help") && !sender.hasPermission("xbossbar.admin") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", null));
                    return true;
                }
                List<String> helpMessage = configManager.getMessageConfig().getStringList("command.help");
                for (String line : helpMessage) {
                    sender.sendMessage(ChatUtils.formatColor(line));
                }
                return true;
            }
            case "reload" -> {
                Player target = (sender instanceof Player) ? (Player) sender : null;
                try {
                    loadUtils.loadConfigFiles();
                } catch (Exception e) {
                    sender.sendMessage(chatUtils.getMessage("command.reload.error", target));
                    return false;
                }
                sender.sendMessage(chatUtils.getMessage("command.reload.success", target));
            }

            default -> sender.sendMessage(chatUtils.getMessage("command.usage", null));
        }

        return false;
    }
    private void infoXBossBar(CommandSender sender) {
        String placeholderStatus = plugin.enabledPAPI() ? "#a0ff72✔" : "#ff7272✖";

        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&8                            #ff4b18&lx&r&lBossBar &8» &r&fInfo"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                           ᴍᴀᴅᴇ ʙʏ"));
        sender.sendMessage(ChatUtils.formatColor("&f                           xDrygo #707070» &7&o(@eldrygo)"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                  ʀᴜɴɴɪɴɢ ᴘʟᴜɢɪɴ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f                                    " + plugin.version));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                      ꜰᴇᴀᴛᴜʀᴇꜱ ᴇɴᴀʙʟᴇᴅ"));
        sender.sendMessage(ChatUtils.formatColor("&f                           ᴘʟᴀᴄᴇʜᴏʟᴅᴇʀᴀᴘɪ #707070» #FFFAAB" + placeholderStatus));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l               ᴅʀʏɢᴏ'ꜱ ɴᴏᴛᴇ ᴏꜰ ᴛʜᴇ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB       Welcome to xBossBar! This plugin was a function I"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB         made for a project, so I decided to make it an"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB      independent plugin with an API. A WIKI is coming soon!"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
    }
}
