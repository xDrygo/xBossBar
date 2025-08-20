# 🔒 Permissions

### 📄 **Introduction**

Permissions are essential to control who can access specific features and commands in the **xBossBar** plugin. By managing permissions, you can ensure that only authorized players can create, delete, or modify BossBars. Below is a table listing all the permissions available in **xBossBar**, their descriptions, and examples of usage.

### **🔒 Plugin Permissions**

| 🔒 Permission                     | 📄 Description                                                                                                              |
| --------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| **xbossbar.command.help**         | Grants access to the /xbossbar help command. Players with this permission can view all available commands.                  |
| **xbossbar.command.reload**       | Allows access to the /xbossbar reload command. Players with this permission can reload plugin messages.                     |
| **xbossbar.command.info**         | Grants access to the /xbossbar info command. Allows the player to view plugin details.                                      |
| **xbossbar.command.create**       | Grants permission to create new BossBars with the /xbossbar create command. Admins or trusted users only.                   |
| **xbossbar.command.remove**       | Allows the player to delete a BossBar using the /xbossbar remove command. Can remove one or all BossBars.                   |
| **xbossbar.command.settitle**     | Grants the ability to change a BossBar's title with /xbossbar settitle.                                                     |
| **xbossbar.command.setcolor**     | Grants the ability to change a BossBar's color with /xbossbar setcolor.                                                     |
| **xbossbar.command.setstyle**     | Grants the ability to change a BossBar's style with /xbossbar setstyle.                                                     |
| **xbossbar.command.addplayer**    | Allows the player to show a BossBar to others using /xbossbar addplayer. Can target individual players or `*` for all.      |
| **xbossbar.command.removeplayer** | Allows the player to hide a BossBar from others using /xbossbar removeplayer. Can target individual players or `*` for all. |
| **xbossbar.admin**                | Full admin rights for all **xBossBar** commands and features. Gives the player complete control over the plugin.            |

***

### 📜 **Explanation**

* **Command Permissions**: Each command has an associated permission controlling who can use it. For example, to allow a player to create BossBars, assign `xbossbar.command.create`. To allow deletion, assign `xbossbar.command.remove`.
* **Global Actions & Caution**: Commands using `*` (all players or all bars) can have server-wide effects. Only grant these permissions to trusted admins.
* **Admin Permission**: The `xbossbar.admin` permission grants full access to all plugin features and commands. This should be reserved for server administrators to prevent unauthorized changes.
