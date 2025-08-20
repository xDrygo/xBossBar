# 📝 messages.yml

## 📝 messages.yml Documentation

### 📄 Introduction

The `messages.yml` file allows you to customize all messages that the **xBossBar** plugin sends to players, providing a tailored experience for your server. This file includes messages for command usage, creation, modification, player management, deletion, reload, help, and error handling. You can also customize error messages and command outputs to improve clarity and user experience.

***

### 💡 Breakdown of the `messages.yml` file

#### 🔖 `prefix`

```yaml
prefix: "#ff4b18&lx&r&f&lBossBar &8»&r"
```

Defines the main prefix for all messages. Supports legacy color codes `&` and hex `#RRGGBB` / `&x&R&R&G&G&B&B`. (Placeholders: —)

***

### 💻 Commands return messages

#### `command.usage`

```yaml
command:
  usage: "%prefix% #FF0000🚫 Unknown Command. &7Use &f/xbb help &7to get command list."
```

**usage:** Shown when an invalid command is executed. _(Placeholders: `%prefix%`)_

***

#### `command.create`

```yaml
command:
  create:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb create <name> <color> <style> <per-player> <title>"
    success: "%prefix% #a0ff72✔ Successfully created '%name%' bossbar. [%personalized%, %color%, %style%, %title%&r#a0ff72]"
    invalid_color: "%prefix% #FF0000🚫 Invalid Color. &7Available colors: &fblue&7, &fgreen&7, &fpink&7, &fpurple&7, &fred&7, &fwhite&7, &fyellow"
    invalid_style: "%prefix% #FF0000🚫 Invalid Style. &7Available styles: &fsegmented_6&7, &fsegmented_10&7, &fsegmented_12&7, &fsegmented_20&7, &fsolid"
```

**usage:** Invalid command usage. **success:** Shown when a bossbar is successfully created. _(Placeholders: `%prefix%`, `%name%`, `%personalized%`, `%color%`, `%style%`, `%title%`)_ **invalid\_color / invalid\_style:** Shown when wrong values are entered. _(Placeholders: `%prefix%`)_

***

#### `command.settitle`

```yaml
command:
  settitle:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb settitle <bossbar> <title>"
    success: "%prefix% #a0ff72✔ Successfully set '%name%' bossbar title to %title%."
```

**usage:** Invalid usage message. **success:** Title successfully set. _(Placeholders: `%prefix%`, `%name%`, `%title%`)_

***

#### `command.setstyle`

```yaml
command:
  setstyle:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb setstyle <bossbar> <style>"
    success: "%prefix% #a0ff72✔ Successfully set '%name%' bossbar style to %style%."
    invalid_style: "%prefix% #FF0000🚫 Invalid Style. &7Available styles: &fsegmented_6&7, &fsegmented_10&7, &fsegmented_12&7, &fsegmented_20&7, &fsolid"
```

**success:** Style set. _(Placeholders: `%prefix%`, `%name%`, `%style%`)_

***

#### `command.setcolor`

```yaml
command:
  setcolor:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb setcolor <bossbar> <color>"
    success: "%prefix% #a0ff72✔ Successfully set '%name%' bossbar color to %color%."
    invalid_color: "%prefix% #FF0000🚫 Invalid Color. &7Available colors: &fblue&7, &fgreen&7, &fpink&7, &fpurple&7, &fred&7, &fwhite&7, &fyellow"
```

**success:** Color set. _(Placeholders: `%prefix%`, `%name%`, `%color%`)_

***

#### `command.addplayer` / `removeplayer`

```yaml
command:
  addplayer:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb addplayer <id / *> <player / *>"
    success:
      one: "%prefix% #a0ff72✔ Successfully added player '%target%' to the bossbar."
      all: "%prefix% #a0ff72✔ Successfully added all players to the bossbar."

  removeplayer:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb removeplayer <id / *> <player / *>"
    success:
      one: "%prefix% #ff7272❌ Successfully removed player '%target%' from the bossbar."
      all: "%prefix% #ff7272❌ Successfully removed all players from the bossbar."
```

**success/one/all:** Shown when players are added or removed. _(Placeholders: `%prefix%`, `%target%`)_

***

#### `command.remove`

```yaml
command:
  remove:
    usage: "%prefix% #FF0000🚫 Invalid Usage. &7Use &f/xbb delete <id / *>"
    success:
      one: "%prefix% #ff7272❌ Successfully removed the bossbar '%name%'"
      all: "%prefix% #ff7272❌ Successfully removed all bossbars."
```

**success:** Bossbar(s) removed. _(Placeholders: `%prefix%`, `%name%`)_

***

#### `command.reload`

```yaml
command:
  reload:
    error: "%prefix% #FF0000🚫 Error reloading xBossBar plugin."
    success: "%prefix% #a0ff72✔ xBossBar plugin reloaded."
```

**success / error:** Plugin reload status messages. _(Placeholders: `%prefix%`)_

***

#### `command.help`

Multi-line help page:

```yaml
command:
  help:
    - " "
    - " "
    - "                            #ff4b18&lx&r&f&lBossBar &8» &r&fHelp"
    - " "
    - "#fff18d&l                    ᴘʟᴜɢɪɴ ᴄᴏᴍᴍᴀɴᴅꜱ"
    - "&f  /xʙʙ ʜᴇʟᴘ #707070» #ccccccShows this help message"
    - "&f  /xʙʙ ʀᴇʟᴏᴀᴅ #707070» #ccccccReloads the plugin configuration"
    - " "
    - "                #fff18d&lᴄʀᴇᴀᴛɪᴏɴ & ᴇᴅɪᴛ ᴄᴏᴍᴍᴀɴᴅꜱ"
    - "&f  /xʙʙ ᴄʀᴇᴀᴛᴇ <name> [color] [style] [personalized] <title> #707070» #ccccccCreate a bossbar"
    - "&f  /xʙʙ ꜱᴇᴛᴛɪᴛʟᴇ <name> <title> #707070» #ccccccSet bossbar title"
    - "&f  /xʙʙ ꜱᴇᴛꜱᴛʏʟᴇ <name> <style> #707070» #ccccccSet bossbar style"
    - "&f  /xʙʙ ꜱᴇᴛᴄᴏʟᴏʀ <name> <color> #707070» #ccccccSet bossbar color"
    - " "
    - "                 #fff18d&lᴘʟᴀʏᴇʀ ᴍᴀɴᴀɢᴇᴍᴇɴᴛ"
    - "&f  /xʙʙ ᴀᴅᴅᴘʟᴀʏᴇʀ <bar> <player> #707070» #ccccccShow bar to player (or * for all)"
    - "&f  /xʙʙ ʀᴇᴍᴏᴠᴇᴘʟᴀʏᴇʀ <bar> <player> #707070» #ccccccRemove bar from player"
    - " "
    - "                     #fff18d&lʙᴏꜱꜱʙᴀʀ ᴅᴇʟᴇᴛɪᴏɴ"
    - "&f  /xʙʙ ʀᴇᴍᴏᴠᴇ <name> #707070» #ccccccDelete a bossbar"
    - "&f  /xʙʙ ʀᴇᴍᴏᴠᴇ * #707070» #ccccccRemove all bossbars"
    - " "
    - " "
```

**help:** Lines shown for the `/xbb help` command.

***

### ❌ Error Messages

```yaml
error:
  no_permission: "%prefix% #FF0000🚫 You don't have permission to use this command."
  bossbar_not_found: "%prefix% #FF0000🚫 BossBar not found."
```

**no\_permission / bossbar\_not\_found:** Shown for permission errors or invalid bossbar references. _(Placeholders: `%prefix%`)_

***

### 🧩 Placeholders

* `%prefix%` — Main message prefix.
* `%name%` — BossBar name.
* `%target%` — Player name.
* `%color%` — Color of the BossBar.
* `%style%` — Style of the BossBar.
* `%title%` — Title of the BossBar.
* `%personalized%` — Indicates if the BossBar is per-player.

> Tip: Supports hex and legacy Minecraft color codes. Placeholders can be dynamically replaced in plugin messages.
