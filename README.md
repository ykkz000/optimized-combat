# Optimized Combat
[English](README.md) | [简体中文](README_zh-CN.md)
## Introduction
***Optimized Combat*** is a Mod for Minecraft to optimize the combat system.
## Features
1. Decrease the max health. Now $maxHealth=\min\{20-4*difficulty+2*\lfloor{expLevel/5}\rfloor,20\}$
2. Killing living entities will provide 1 health absorption(Max 2) and 2 food level.
3. If $foodLevel<0.3*maxFoodLevel$, player will get **Slowness** I, **Weakness** I, **Mining Fatigue** I status effect.
4. Now high food level will not provide healing.
5. Mining tool items now provide +1 *Entity Interaction Range* and +2 *Block Interaction Range*.
6. Sword items now provide +2 *Entity Interaction Range* and +1 *Block Interaction Range*.
7. Shield items can use continuously for only 40 ticks, and will cool down for $15\times{(1+useTime/40)}$ ticks.
8. Add new enchantment **Explosion**, which can be enchanted on bows. Projection entities which are shot from bows with **Explosion** enchantment will create explosions that not damage entities and blocks.
## Dependencies
1. Java 21
2. Minecraft(Java Edition)
3. [Fabric Loader](https://fabricmc.net/use/installer/)
4. [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
