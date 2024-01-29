<div align="center"><img src="docs/title.png">
<h5></h5>
</div>

**BetterDonate** - A plugin for Spigot that adds a shopping cart for players

<hr>

# ![Версия на русском](README-RU.md)

<hr>

# ✅ What's already done:
- [x] Config support, saving cart to YAML file and several languages (currently RU/EN)
- [x] Reload with /betterdonate reload
- [x] Support for different types of products with customization of commands for issuing:
   - donate-group (e.g. LuckPerms)
   - money (e.g. Vault)
   - tokens (e.g. PlayerPoints)
   - own commands
- [x] Customize commands that executed on getting purchases by player.
- [x] Notification of the player about not picked up purchases when logging in to the server

# 📋 Future plans:
- [ ] Webhook message on giving product to Discord
- [ ] Placeholder with the number of purchases

<hr>
<br>

# 🚀 Usage

## Commands:

### `/betterdonate` - main command of this plugin

> Permisson: **betterdonate.admin**

Commands list:

- `/betterdonate help` - show command list
- `/betterdonate reload` - reload plugin
- `/betterdonate give <игрок> <тип товара> <аргумент>` - give a purchase to player
  - Example of use: `/betterdonate give Player123 donate vip`

### `/cart` - command for players

> Permisson: **betterdonate.cart**

Commands list:

- `/cart help` - show command list
- `/cart get` - get purchases

<br>

## 🛒 Products:

### List of available products for dispensing:

- **donate-group (e.g., LuckPerms)**
  - Example of use: `/betterdonate give Player123 donate vip`
- **money (e.g., Vault)**
  - Example of use: `/betterdonate give Player123 money 1000`
- **tokens (e.g., PlayerPoints)**
  - Example of use: `/betterdonate give Player123 tokens 1`
- **any commands executed by console**
  - Example of use: `/betterdonate give Player123 give Player123 minecraft:diamond 64`

> [!TIP]
> The commands to issue are configured in [config.yml](src/main/resources/config.yml)

<hr>
<br>

# ⚙ Other information

- If you find a bug or want to help with development, feel free to contact me
  - Contact links [here](https://slv.nshard.fun/) 
- You can also (optionally) [donate me](https://www.donationalerts.com/r/mrdrag0nxyt)
