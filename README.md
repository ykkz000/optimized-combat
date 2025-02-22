# Optimized Combat
[English](README.md) | [简体中文](README_zh-CN.md)
## Introduction
***Optimized Combat*** is a Mod for Minecraft to optimize the combat system.
## Features
1. Decrease the max health. Now $maxHealth=\min\{20-4*difficulty+2*\lfloor{expLevel/5}\rfloor,20\}$
2. Killing living entities will provide 1 health absorption(Max 4) and 2 food levels.
3. If $foodLevel<6$, player will get **Slowness** I, **Weakness** I, **Mining Fatigue** I status effect.
4. High food level will not provide healing.
5. The player has a -1 *entity interaction distance* and a -1 *block interaction distance*.
6. Mining tool items provide +1 *Entity Interaction Range* and +2 *Block Interaction Range*.
7. Sword items provide +2 *Entity Interaction Range* and +1 *Block Interaction Range*.
8. Shield items can use continuously for only 40 ticks, and will cool down for $15\times{(1+useTime/40)}$ ticks.
9. The maximum number of items stacked has been adjusted to 256.
10. The player receives damage reduction. $damageReductionPercentage=0.3+0.4\times{lostHealthPercentage}$.
## Dependencies
1. Java 21
2. Minecraft(Java Edition)
3. [Fabric Loader](https://fabricmc.net/use/installer/)
4. [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
5. [Lombok](https://projectlombok.org/)(Compile only)
