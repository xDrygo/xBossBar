# 📖 Getting Started

### 1️⃣ **Installation**

To get started with **xBossBar**, you first need to install the plugin on your Minecraft server. Follow these steps for proper installation:

* **Requirements**: You will need Spigot or Paper, as **xBossBar** is not compatible with CraftBukkit.
* **Optional Dependencies**: **PlaceholderAPI** is optional but recommended to enable dynamic placeholders in BossBar titles.

Once downloaded, place the plugin in your server's `plugins` folder and restart or reload the server. For the latest releases and updates, visit the [GitHub Repository](https://github.com/xDrygo/xBossBar).

***

### 2️⃣ **Create and Manage BossBars**

Now that you have **xBossBar** installed, let's learn how to create and manage BossBars on your server.

#### ✨ **Create a BossBar**

To create a new BossBar, use the following command:

```
/xbossbar create <bar_name> <title> <color> <style>
```

Replace the placeholders accordingly:

* `<bar_name>` — The internal codename for the BossBar.
* `<title>` — The display title of the BossBar.
* `<color>` — The color of the bar (e.g., RED, BLUE, GREEN, etc.).
* `<style>` — The style of the bar (e.g., SOLID, SEGMENTED\_6, SEGMENTED\_10, etc.).

Example: /xbossbar create eventBar "\&aEvent Starting!" GREEN SOLID

#### 🛠️ **Managing BossBars**

* **Assign BossBar to a Player**

```
/xbossbar add <bar_name> <player_name>
```

Example: /xbossbar add eventBar Steve

* **Remove BossBar from a Player**

```
/xbossbar remove <bar_name> <player_name>
```

Example: /xbossbar remove eventBar Steve

* **Update BossBar Title**

```
/xbossbar settitle <bar_name> <new_title>
```

Example: /xbossbar settitle eventBar "\&cEvent Started!"

* **Update BossBar Progress**

```
/xbossbar setprogress <bar_name> <progress>
```

Example: /xbossbar setprogress eventBar 0.75 sets the bar to 75%.

* **Delete a BossBar**

```
/xbossbar delete <bar_name>
```

Example: /xbossbar delete eventBar

***

### 3️⃣ **More Resources**

For more information on commands, placeholders, and API usage, check out the following sections:

* **Plugin Commands**
  * [⌨️ Commands](../plugin/commands.md)
  * [🔒 Permissions](../plugin/permissions.md)
  * [🧩 Placeholders](../plugin/placeholders.md)
* **Configuration Files**
  * [📝 config.yml](../configuration-files/config.yml.md)
  * [📝 messages.yml](../configuration-files/messages.yml.md)
* **API**
  * [🔗 xBossBar API](../api/xbossbar-api.md)

***

Now you're ready to start creating and managing BossBars in **xBossBar**. Explore the API and commands to fully customize your server's experience. 🚀
