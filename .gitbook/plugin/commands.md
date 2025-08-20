# ⌨️ Commands

### 📄 **Introduction**

In this section, you’ll find the list of commands available in **xBossBar**. These commands allow you to manage personalized and global BossBars, add or remove players, and modify bar settings dynamically.

### **⌨️ Management and information commands**

| 💬 Command                                                   | 💡 Example                                             | 📄 Description                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------ | ---------------------------------------------------------------- |
| **/xbossbar help**                                           | /xbossbar help                                         | Displays a list of all available commands in **xBossBar**.       |
| **/xbossbar reload**                                         | /xbossbar reload                                       | Reloads the plugin’s configuration and messages.                 |
| **/xbossbar info**                                           | /xbossbar info                                         | Shows plugin information like version, creator, etc.             |
| **/xbossbar create**                                         | /xbossbar create boss1 RED SOLID true "\&cBoss Fight!" | Creates a new BossBar with the specified settings.               |
| **/xbossbar remove&#x20;**<kbd>**\<id or >**</kbd>           | /xbossbar remove boss1                                 | Deletes a BossBar by ID or `*` to delete all bars.               |
| **/xbossbar settitle**                                       | /xbossbar settitle boss1 "\&aVictory!"                 | Changes the title of the specified BossBar.                      |
| **/xbossbar setcolor**                                       | /xbossbar setcolor boss1 BLUE                          | Changes the color of the BossBar.                                |
| **/xbossbar setstyle**                                       | /xbossbar setstyle boss1 SEGMENTED\_10                 | Changes the style of the BossBar.                                |
| **/xbossbar addplayer&#x20;**<kbd>**\<player or >**</kbd>    | /xbossbar addplayer boss1 xDrygo                       | Shows the BossBar to a specific player or `*` for all players.   |
| **/xbossbar removeplayer&#x20;**<kbd>**\<player or >**</kbd> | /xbossbar removeplayer boss1 xDrygo                    | Hides the BossBar from a specific player or `*` for all players. |

***

### 📜 Explanation

* **General Use**: Commands are designed to give admins control over BossBars, both personal and global.
* **Creating and Removing Bars**: Use `/xbossbar create` to make new bars and `/xbossbar remove` to delete them.
* **Title, Color, Style**: Commands like `/xbossbar settitle`, `/setcolor`, and `/setstyle` let you customize the appearance of each bar.
* **Player Management**: Add or remove players with `/xbossbar addplayer` and `/xbossbar removeplayer` for individual or global updates.
* **Plugin Information & Reload**: `/xbossbar info` shows version and status, and `/xbossbar reload` applies changes without restarting the server.
