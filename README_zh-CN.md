# Optimized Combat
[English](README.md) | [简体中文](README_zh-CN.md)
## 简介
***Optimized Combat*** 是一个优化《我的世界》的战斗系统的模组。
## 特性
1. 降低最大生命值。 现在 $最大生命值=\min\{20-4*难度+2*\lfloor{经验等级/5}\rfloor,20\}$
2. 击杀生物会获得1点生命吸收（最高4点）和2点饥饿等级。
3. 如果 $饥饿等级<6$，玩家会获得 **迟缓** I, **虚弱** I, **挖掘疲劳** I。
4. 高的饥饿等级不会提供生命恢复。
5. 挖掘工具提供+1*实体交互距离*和+2*方块交互距离*。
6. 剑提供+2*实体交互距离*和+1*方块交互距离*。
7. 盾只能连续使用40个tick，并会冷却$15\times{(1+使用时长/40)}$个tick.
8. 添加可以被赋在弓上的新附魔**爆炸**。从赋有**爆炸**附魔的弓射出的投射物会产生不造成实体和方块伤害的爆炸。
9. 物品最大堆叠数调整为256。
## 依赖
1. Java 21
2. Minecraft(Java Edition)
3. [Fabric Loader](https://fabricmc.net/use/installer/)
4. [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
5. [Lombok](https://projectlombok.org/)(Compile only)
