# xBossBar 🎯
A powerful, lightweight, and highly customizable BossBar manager for Spigot & Paper Minecraft servers.

---

## ✨ Features

- 🎨 Create personalized or global BossBars.
- 🔧 Modify title, color, style, and progress dynamically.
- 👥 Add or remove players from bars — individually or all at once.
- 📌 Placeholder support for dynamic titles (e.g. `%player_name%`).
- 🧠 Smart tab-completion for all commands.
- ♻️ Live updates that don’t interrupt players not currently viewing the bar.
- 📂 Fully customizable messages in `messages.yml`.
- 🔒 Granular permission support.

---

## 📦 Installation

1. Download the latest release from the [Releases](https://github.com/xDrygo/xBossBar/releases/latest) page.
2. Drop `xBossBar-1.0.1.jar` into your server's `plugins` folder.
3. Restart your server.
4. Customize `messages.yml` if needed. *(Currently `config.yml` has no settings to change)*

---

## 💻 Commands

| Command                                                        | Description                                        |
|----------------------------------------------------------------|----------------------------------------------------|
| `/xbossbar create <id> <color> <style> <personalized> <title>` | Create a new BossBar                               |
| `/xbossbar remove <id or *>`                                   | Delete a BossBar                                   |
| `/xbossbar settitle <id> <title>`                              | Change the title                                   |
| `/xbossbar setcolor <id> <color>`                              | Change the color                                   |
| `/xbossbar setstyle <id> <style>`                              | Change the style                                   |
| `/xbossbar addplayer <id> <player or *>`                       | Show BossBar to player(s)                          |
| `/xbossbar removeplayer <id> <player or *>`                    | Hide BossBar from player(s)                        |
| `/xbossbar reload`                                             | Reload the config and messages                     |
| `/xbossbar info`                                               | Get plugin information like version, creator, etc. |
| `/xbossbar help`                                               | Show help menu                                     |

> Use `*` to apply an action to all players or all BossBars.

---

## 🛡 Permissions

| Node                            | Description                  |
|---------------------------------|------------------------------|
| `xbossbar.command.create`       | Use `/xbossbar create`       |
| `xbossbar.command.remove`       | Use `/xbossbar remove`       |
| `xbossbar.command.settitle`     | Use `/xbossbar settitle`     |
| `xbossbar.command.setcolor`     | Use `/xbossbar setcolor`     |
| `xbossbar.command.setstyle`     | Use `/xbossbar setstyle`     |
| `xbossbar.command.addplayer`    | Use `/xbossbar addplayer`    |
| `xbossbar.command.removeplayer` | Use `/xbossbar removeplayer` |
| `xbossbar.command.reload`       | Use `/xbossbar reload`       |
| `xbossbar.command.info`         | Use `/xbossbar info`         |
| `xbossbar.admin`                | Access all commands          |

---

## 🛠 Configuration

### `messages.yml`

You can fully customize all plugin messages using placeholders like:
- `%name%` → BossBar name
- `%target%` → Player name
- And any PlaceholderAPI placeholders!

---

## 🧩 PlaceholderAPI Support

If you have [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) installed, `xBossBar` automatically registers its own placeholders!

### 🔍 Available Placeholders

You can use the following placeholders to display information about a BossBar:

| Placeholder                | Description                                                                             |
|----------------------------|-----------------------------------------------------------------------------------------|
| `%xbossbar_title_<id>%`    | Shows the title of the specified BossBar. If personalized, it resolves for each player. |
| `%xbossbar_progress_<id>%` | Shows the progress as a percentage (e.g., `45.00%`).                                    |
| `%xbossbar_color_<id>%`    | Displays the color of the BossBar (e.g., `RED`, `BLUE`).                                |
| `%xbossbar_style_<id>%`    | Displays the style (e.g., `SEGMENTED_10`, `SOLID`).                                     |

> Replace `<id>` with the name of your BossBar.

---

## 📦 API Usage (Developers)

xBossBar provides a static API to control BossBars from other plugins.

### Requirements
- Add xBossBar as a dependency or softdepend in your plugin.yml.

### Add Maven dependency
```xml
<dependencies>
  <dependency>
    <groupId>org.eldrygo</groupId>
    <artifactId>XBossbar</artifactId>
    <version>1.0.1</version>
    <scope>provided</scope>
  </dependency>
</dependencies>

<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
### Add Gradle dependency (settings.gradle)
```gradle
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.xDrygo:xBossBar:1.0.1'
}
```
### Add Gradle dependency (settings.gradle.kts)
```gradle.kts
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
}

dependencies {
  implementation("com.github.xDrygo:xBossBar:1.0.1")
}
```
### Available Methods

| Method                  | Parameters                                                                 | Description                                                                                                                            |
|-------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| createBossBar           | String id, String title, BarColor color, BarStyle style, boolean perplayer | Creates a new BossBar with the specified id, title, color, style, and whether it is personalized per player. Progress defaults to 1.0. |
| removeBossBar           | String id                                                                  | Removes the BossBar with the given id.                                                                                                 |
| clearAllBossBars        | —                                                                          | Removes all registered BossBars.                                                                                                       |
| addPlayerToBossBar      | String id, Player player                                                   | Adds the specified player to view the BossBar with the given id.                                                                       |
| removePlayerFromBossBar | String id, Player player                                                   | Removes the specified player from viewing the BossBar with the given id.                                                               |
| setTitle                | String id, String title                                                    | Sets the title text of the BossBar with the given id.                                                                                  |
| setProgress             | String id, double progress                                                 | Sets the progress (0.0 to 1.0) of the BossBar with the given id.                                                                       |
| setStyle                | String id, BarStyle style                                                  | Sets the style of the BossBar with the given id.                                                                                       |
| setColor                | String id, BarColor color                                                  | Sets the color of the BossBar with the given id.                                                                                       |
| getBossBar              | String id                                                                  | Returns the Bukkit BossBar instance for the given id.                                                                                  |
| getBossBarModel         | String id                                                                  | Returns the BossBarModel instance for the given id.                                                                                    |
| getBossBarNames         | —                                                                          | Returns a Set of all registered BossBar IDs.                                                                                           |

---
