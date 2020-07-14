package com.pubg.analysis.entity.log;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 日志基类
 *
 * @author yangy
 * @date 2020/7/12 9:58
 */
@Data
@Document(collection = "pubg_match_log")
@CompoundIndexes({
        @CompoundIndex(name = "matchId_T",def = "{'matchId':1,'_T':1}")
})
public class BaseLog {
    // 对局ID
    private String matchLogId;
    // 时间
    @DateTimeFormat(pattern = "dd-MMM-yyyyTHH:mm:ss:SSSz")
    private String _D;
    // 事件类型
    private String _T;
    private Common common;

    /**********          对局          **********/
    // 对局ID
    private String MatchId;
    // ping质量
    private String PingQuality;
    // 赛季状态 - progress
    private String SeasonState;
    // 对局运行时间
    private Integer elapsedTime;
    // 剩余存活人数
    private Integer numAlivePlayers;
    // 阶段?
    private Integer phase;
    // LogPlayerKill事件
    private Integer dBNOId;

    /**********          对局设置          **********/
    // 地图名称 - Savage_Main
    private String mapName;
    // 天气 - Clear
    private String weatherId;
    // 镜头活动 - FpsAndTps
    private String cameraViewBehaviour;
    // 每队人数 - 4
    private Integer teamSize;
    // 是否自定义游戏
    private boolean isCustomGame;
    // 是否娱乐模式
    private boolean isEventMode;
    // 毒圈自定义
    private String blueZoneCustomOptions;
    // 游戏状态
    private GameState gameState;

    /**********          对局人物          **********/
    // 所有人物属性
    private List<Character> characters;
    // LogMatchEnd事件 - 获胜者数据
    private GameResultOnFinished gameResultOnFinished;

    /**********          ID          **********/
    // 账户ID
    private String accountId;
    // 攻击ID
    private String attackId;

    /**********          各类人物属性          **********/
    // 攻击者
    private Character attacker;
    // 受害者
    private Character victim;
    // 击杀者
    private Character killer;
    // 助攻者
    private Character assistant;
    // 当前角色属性
    private Character character;
    // 救人者
    private Character reviver;

    /**********          各类伤害属性          **********/
    // 伤害量
    private Integer damage;
    // 伤害原因 - HeadShot
    private String damageReason;
    // 伤害类型 - Damage_Gun
    private String damageTypeCategory;
    // 伤害物品名称 - WeapQBZ95_C
    private String damageCauserName;
    // 伤害原因附加信息
    private List<String> damageCauserAdditionalInfo;
    // 穿透墙壁?
    private boolean isThroughPenetrableWall;
    // 是否车上攻击
    private boolean isAttackerInVehicle;
    // 受害武器
    private String victimWeapon;
    // 受害附加信息
    private List<String> victimWeaponAdditionalInfo;
    // 受害者结果
    private GameResult victimGameResult;
    // 开火武器计数
    private Integer fireWeaponStackCount;
    // 攻击类型 - Weapon
    private String attackType;
    // 开火计数
    private Integer fireCount;

    /**********          各类物品属性          **********/
    // 物品
    private Item item;
    // 物品 - 父类 - 武器上配件 - 武器
    private Item parentItem;
    // 物品 - 子类 - 武器上配件 - 武器配件
    private Item childItem;
    // 攻击武器
    private Item weapon;
    // 武器名称
    private String weaponId;
    // 空投
    private Integer carePackageUniqueId;
    // 空投着陆
    private ItemPackage itemPackage;
    // 战利品箱归属team
    private Integer ownerTeamId;
    // 战利品箱归属人
    private String creatorAccountId;


    /**********          各类车辆属性          **********/
    // 车辆
    private Vehicle vehicle;
    // 开车距离
    private double rideDistance;
    // 座位索引
    private Integer seatIndex;
    // 最大速度
    private double maxSpeed;
    // 乘客
    private List<Character> fellowPassengers;


    /**********          各类地图固定物品属性          **********/
    // 摧毁对象类型 - Door
    private String objectType;
    // 对象类型状态 - Opening
    private String objectTypeStatus;
    // 摧毁对象坐标
    private Location objectLocation;
    // 对象附加信息 - 暂时没看到里面的结构
    // private List<String> objectTypeAdditionalInfo;
    // LogVaultStart事件 - 是否抓墙壁
    private boolean isLedgeGrab;


    // 跑动距离?
    private double distance;
    // 游泳距离
    private double swimDistance;
    // 潜水深度
    private double maxSwimDepthOfWater;
    // 治疗量
    private Integer healAmount;
}


