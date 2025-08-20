# 🧩 Placeholders

### 📄 **Introduction**

Placeholders allow you to display dynamic information about BossBars and players in messages, titles, or any supported formats. These placeholders can be used in `messages.yml` or other compatible configuration files to customize the experience.

### 🧩 Supported Placeholders

| ⚙️ Placeholder         | 📄 Description                                                                             | 🔄 Returned Format                               |
| ---------------------- | ------------------------------------------------------------------------------------------ | ------------------------------------------------ |
| %xbossbar\_title\_%    | Displays the title of the specified BossBar. If personalized, it resolves for each player. | **String**: BossBar title (e.g., \&cBoss Fight!) |
| %xbossbar\_progress\_% | Shows the progress of the BossBar as a percentage.                                         | **Double**: Progress percentage (e.g., 45.0%)    |
| %xbossbar\_color\_%    | Displays the color of the BossBar.                                                         | **String**: Color name (e.g., RED)               |
| %xbossbar\_style\_%    | Displays the style of the BossBar.                                                         | **String**: Style name (e.g., SEGMENTED\_10)     |

### ❓ How to Use These Placeholders

To use these placeholders, insert them into `messages.yml` or any supported file where dynamic BossBar data is needed. For example, in a message to show the title of a BossBar:

```yaml
bossbar_start_message: "The BossBar %xbossbar_title_boss1% has started!"
```

### 💡 Example of Use in a Server

With PlaceholderAPI installed, these placeholders will automatically update for each player:

* `%xbossbar_title_<id>%` will display the title of the specified BossBar.
* `%xbossbar_progress_<id>%` will show the progress of the BossBar.
* `%xbossbar_color_<id>%` will show the color.
* `%xbossbar_style_<id>%` will show the style.

#### **⚙️ Example Configuration**

```yaml
bossbar_start_message: "%xbossbar_title_boss1% is active with %xbossbar_progress_boss1% progress!"
bossbar_color_info: "The BossBar color is %xbossbar_color_boss1% and style is %xbossbar_style_boss1%"
```
