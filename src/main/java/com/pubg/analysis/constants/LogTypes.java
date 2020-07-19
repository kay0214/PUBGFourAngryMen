package com.pubg.analysis.constants;

/**
 * 日志类型
 *
 * @author yangy
 * @date 2020/7/16 15:26
 */
public enum LogTypes {
	/**
	 * 护甲破坏
	 */
	LogArmorDestroy,

	/**
	 * 空投落地
	 */
	LogCarePackageLand,

	/**
	 * 空投生成
	 */
	LogCarePackageSpawn,

	/**
	 * 安全区、毒圈、轰炸区、黑圈日志
	 */
	LogGameStatePeriodic,

	/**
	 * 治疗
	 */
	LogHeal,

	/**
	 * 附加配件
	 */
	LogItemAttach,

	/**
	 * 移除配件
	 */
	LogItemDetach,

	/**
	 * 抛弃物品
	 */
	LogItemDrop,

	/**
	 * 装备物品
	 */
	LogItemEquip,

	/**
	 * 拾取物品
	 */
	LogItemPickup,

	/**
	 * 添空投
	 */
	LogItemPickupFromCarepackage,

	/**
	 * 舔包
	 */
	LogItemPickupFromLootBox,

	/**
	 * 取消装备
	 */
	LogItemUnequip,

	/**
	 * 使用物品
	 */
	LogItemUse,

	/**
	 * 比赛定义
	 */
	LogMatchDefinition,

	/**
	 * 比赛结束
	 */
	LogMatchEnd,

	/**
	 * 比赛开始
	 */
	LogMatchStart,

	/**
	 * 物品破坏
	 */
	LogObjectDestroy,

	/**
	 * 物品交互
	 */
	LogObjectInteraction,

	/**
	 * 降落伞落地
	 */
	LogParachuteLanding,

	/**
	 * 阶段改变
	 */
	LogPhaseChange,

	/**
	 * 玩家攻击
	 */
	LogPlayerAttack,

	/**
	 * 玩家创建
	 */
	LogPlayerCreate,

	/**
	 * 玩家击杀
	 */
	LogPlayerKill,

	/**
	 * 玩家登录
	 */
	LogPlayerLogin,

	/**
	 * 玩家登出
	 */
	LogPlayerLogout,

	LogPlayerMakeGroggy,

	/**
	 * 玩家位置
	 */
	LogPlayerPosition,

	/**
	 * 玩家复活
	 */
	LogPlayerRevive,

	/**
	 * 承受伤害
	 */
	LogPlayerTakeDamage,

	/**
	 * 使用信号枪
	 */
	LogPlayerUseFlareGun,

	/**
	 * 使用投掷物
	 */
	LogPlayerUseThrowable,

	/**
	 * 轰炸结束
	 */
	LogRedZoneEnded,

	/**
	 * 游泳结束
	 */
	LogSwimEnd,

	/**
	 * 开始游泳
	 */
	LogSwimStart,

	LogVaultStart,

	/**
	 * 载具破坏
	 */
	LogVehicleDestroy,

	/**
	 * 离开载具
	 */
	LogVehicleLeave,

	/**
	 * 乘坐？驾驶？载具
	 */
	LogVehicleRide,

	/**
	 * 开火计数
	 */
	LogWeaponFireCount,

	/**
	 * 打胎
	 */
	LogWheelDestroy
}
