<div align="center"><img src="docs/title.png">
<h5></h5>
</div>

**BetterDonate** - плагин для Spigot, который добавляет корзину покупок для игроков

<hr>

# ![English version](README.md)

<hr>

# ✅ Что уже сделано:

- [x] Поддержка конфига, сохранения корзины в YAML-файл и нескольких языков (на данный момент RU/EN)
- [x] Перезагрузка с помощью /betterdonate reload
- [x] Поддержка разных видов товаров с настройкой команд для выдачи:
  - привилегия (например, LuckPerms)
  - деньги (например, Vault)
  - токены (например, PlayerPoints)
  - своих команд
- [x] Настройка команд при выдаче товара
- [x] Оповещение игрока о наличии покупок в корзине

# 📋 Планируется

- [ ] Webhook-сообщение в Discord при покупке
- [ ] Плейсхолдер с количеством покупок

<hr>
<br>

# 🚀 Использование

## Команды:

### `/betterdonate` - основная команда плагина

> Право: **betterdonate.admin**

Список команд:

- `/betterdonate help` - показать список команд
- `/betterdonate reload` - перезагрузить плагин
- `/betterdonate give <игрок> <тип товара> <аргумент>` - выдать товар игроку
  - Пример использования: `/betterdonate give Player123 donate vip`

### `/cart` - команда для игроков

> Право: **betterdonate.cart**

Список команд:

- `/cart help` - показать список команд
- `/cart get` - получить купленные товары

<br>

## 🛒 Товары:

### Список доступных товаров для выдачи:

- **привилегия (например, LuckPerms)**
  - Пример использования: `/betterdonate give Player123 donate vip`
- **деньги (например, Vault)**
  - Пример использования: `/betterdonate give Player123 money 1000`
- **токены (например, PlayerPoints)**
  - Пример использования: `/betterdonate give Player123 tokens 1`
- **любые свои команды**
  - Пример использования: `/betterdonate give Player123 give Player123 minecraft:diamond 64`

> [!TIP]
> Команды для выдачи настраиваются в [config.yml](src/main/resources/config.yml)

<hr>
<br>

# ⚙ Остальная информация

- Если вы нашли баг или хотите помочь в разработке - не стесняйтесь обращаться ко мне
  - Ссылки на контакты [тут](https://slv.nshard.fun/) 
- Также (по желанию) вы можете [дать мне денег](https://www.donationalerts.com/r/mrdrag0nxyt)
