# SurvivalPlugin

A Spigot plugin that provides random spawn functionality for survival servers.

## Features

- **First Join Random Spawn**: New players are automatically teleported to a random safe location when they first join the server
- **Wild Command**: Players can use `/wild` to teleport to random locations (with cooldown)
- **Safe Location Detection**: Ensures players spawn on solid ground in clear areas, avoiding dangerous blocks like lava, fire, and cactus
- **World Border Support**: Respects world border settings for teleportation range

## Commands

- `/wild` - Teleport to a random location (60 second cooldown)

## Permissions

- `survival.wild` - Allows use of the /wild command (default: true)

## Installation

1. Download the latest release JAR
2. Place it in your server's `plugins` folder
3. Restart your server

## Building

```bash
mvn clean package
```

The compiled JAR will be in the `target` folder.

## Requirements

- Spigot/Paper 1.20.1 or higher
- Java 17 or higher
