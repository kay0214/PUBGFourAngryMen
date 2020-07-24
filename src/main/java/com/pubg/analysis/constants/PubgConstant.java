package com.pubg.analysis.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pubg 常量
 *
 * @author yangy
 * @date 2020/7/12 0:48
 */
public class PubgConstant {

	/**
	 * 8x8地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_8X8_CM = 8 * 1000 * 100;

	/**
	 * 6x6地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_6X6_CM = 6 * 1000 * 100;

	/**
	 * 4x4地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_4X4_CM = 4 * 1000 * 100;

    /**
     * 2x2地图边长
     * 厘米
     */
    private static final long MAP_SIDE_LENGTH_2X2_CM = 2 * 1000 * 100;

	/**
	 * 地图
	 */
	@Getter
	@AllArgsConstructor
	public enum Maps {

		/**
		 * 艾伦格 - 原版
		 */
		ERANGEL(1, "艾伦格", "Erangel_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

        /**
         * 艾伦格 - 重制版
         */
        BALTIC(2, "艾伦格", "Baltic_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

        /**
		 * 米拉玛
		 */
		MIRAMAR(3, "米拉玛", "Desert_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

		/**
		 * 萨诺
		 */
		SANHOK(4, "萨诺", "Savage_Main", MAP_SIDE_LENGTH_4X4_CM, MAP_SIDE_LENGTH_4X4_CM),

		/**
		 * 维寒迪
		 */
		VIKENDI(5, "维寒迪", "DihorOtok_Main", MAP_SIDE_LENGTH_6X6_CM, MAP_SIDE_LENGTH_6X6_CM),

        /**
         * 卡拉金
         */
        KARAKIN(6, "卡拉金", "Summerland_Main", MAP_SIDE_LENGTH_2X2_CM, MAP_SIDE_LENGTH_2X2_CM),

        /**
         * 训练场
         * */
		TRAIN(7, "训练场", "Range_Main", MAP_SIDE_LENGTH_2X2_CM, MAP_SIDE_LENGTH_2X2_CM);

		/**
		 * 地图编号
		 */
		private final int code;

		/**
         * 地图中文名
         * */
		private final String shortName;

		/**
         * 地图日志名
         * */
		private final String longName;

		/**
		 * 宽
		 * 厘米
		 */
		private final long width;

		/**
		 * 高
		 * 厘米
		 */
		private final long height;

        /**
         * 根据长地图名映射中文地图名
         *
         * @param longName 长地图名
         * @return 地图类型
         */
        public static String getShortNameByLongName(String longName){
            String result = "";
            for (Maps map : Maps.values()) {
                if(map.longName.equals(longName)){
                    result = map.shortName;
                }
            }
            return result;
        }

		/**
		 * 根据长地图名映射地图类型
		 *
		 * @param longName 长地图名
		 * @return 地图类型
		 */
		public static Maps getByLongName(String longName) {
            for (Maps map : Maps.values()) {
                if(map.longName.equals(longName)){
                    return map;
                }
            }
            throw new IllegalArgumentException("Unrecognized map name: " + longName);
		}

	}

    /**
     * 小队模式
     */
    @Getter
    @AllArgsConstructor
    public enum TeamMode {

        /**
         * 单排
         */
        SOLO(1, "单排", "solo"),

        /**
         * 第一人称单排
         */
        SOLO_FPP(2, "第一人称单排", "solo-fpp"),

        /**
         * 双排
         */
        DUO(3, "双排", "duo"),

        /**
         * 第一人称双排
         */
        DUO_FPP(4, "第一人称双排", "duo-fpp"),

        /**
         * 四排
         * */
        SQUAD(5, "四排", "squad"),

        /**
         * 第一人称四排
         * */
        SQUAD_FPP(6, "第一人称四排", "squad-fpp"),

        /**
         * 普通模式 - 单排
         */
        NORMAL_SOLO(7, "普通-单排", "normal-solo"),

        /**
         * 普通模式 - 第一人称单排
         */
        NORMAL_SOLO_FPP(8, "普通-第一人称单排", "normal-solo-fpp"),

        /**
         * 普通模式 - 双排
         */
        NORMAL_DUO(9, "普通-双排", "normal-duo"),

        /**
         * 普通模式 - 第一人称双排
         */
        NORMAL_DUO_FPP(10, "普通-第一人称双排", "normal-duo-fpp"),

        /**
         * 普通模式 - 四排
         * */
        NORMAL_SQUAD(11, "普通-四排", "normal-squad"),

        /**
         * 普通模式 - 第一人称四排
         * */
        NORMAL_SQUAD_FPP(12, "普通-第一人称四排", "normal-squad-fpp"),

        /**
         * 感染者(僵尸)模式 - 单排
         */
        ZOMBIE_SOLO(13, "感染者-单排", "zombie-solo"),

        /**
         * 感染者(僵尸)模式 - 第一人称单排
         */
        ZOMBIE_SOLO_FPP(14, "感染者-第一人称单排", "zombie-solo-fpp"),

        /**
         * 感染者(僵尸)模式 - 双排
         */
        ZOMBIE_DUO(15, "感染者-双排", "zombie-duo"),

        /**
         * 感染者(僵尸)模式 - 第一人称双排
         */
        ZOMBIE_DUO_FPP(16, "感染者-第一人称双排", "zombie-duo-fpp"),

        /**
         * 感染者(僵尸)模式 - 四排
         * */
        ZOMBIE_SQUAD(17, "感染者-四排", "zombie-squad"),

        /**
         * 感染者(僵尸)模式 - 第一人称四排
         * */
        ZOMBIE_SQUAD_FPP(18, "感染者-第一人称四排", "zombie-squad-fpp"),

        /**
         * 战争模式 - 单排
         */
        WAR_SOLO(19, "战争-单排", "war-solo"),

        /**
         * 战争模式 - 第一人称单排
         */
        WAR_SOLO_FPP(20, "战争-第一人称单排", "war-solo-fpp"),

        /**
         * 战争模式 - 双排
         */
        WAR_DUO(21, "战争-双排", "war-duo"),

        /**
         * 战争模式 - 第一人称双排
         */
        WAR_DUO_FPP(22, "战争-第一人称双排", "war-duo-fpp"),

        /**
         * 战争模式 - 四排
         * */
        WAR_SQUAD(23, "战争-四排", "war-squad"),

        /**
         * 战争模式 - 第一人称四排
         * */
        WAR_SQUAD_FPP(24, "战争-第一人称四排", "war-squad-fpp"),

        /**
         * 对抗模式占领 - 单排
         */
        CONQUEST_SOLO(25, "占领-单排", "conquest-solo"),

        /**
         * 对抗模式占领 - 第一人称单排
         */
        CONQUEST_SOLO_FPP(26, "占领-第一人称单排", "conquest-solo-fpp"),

        /**
         * 对抗模式占领 - 双排
         */
        CONQUEST_DUO(27, "占领-双排", "conquest-duo"),

        /**
         * 对抗模式占领 - 第一人称双排
         */
        CONQUEST_DUO_FPP(28, "占领-第一人称双排", "conquest-duo-fpp"),

        /**
         * 对抗模式占领 - 四排
         * */
        CONQUEST_SQUAD(29, "占领-四排", "conquest-squad"),

        /**
         * 对抗模式占领 - 第一人称四排
         * */
        CONQUEST_SQUAD_FPP(30, "占领-第一人称四排", "conquest-squad-fpp"),

        /**
         * 电竞模式 - 单排
         */
        ESPORTS_SOLO(31, "电竞-单排", "esports-solo"),

        /**
         * 电竞模式 - 第一人称单排
         */
        ESPORTS_SOLO_FPP(32, "电竞-第一人称单排", "esports-solo-fpp"),

        /**
         * 电竞模式 - 双排
         */
        ESPORTS_DUO(33, "电竞-双排", "esports-duo"),

        /**
         * 电竞模式 - 第一人称双排
         */
        ESPORTS_DUO_FPP(34, "电竞-第一人称双排", "esports-duo-fpp"),

        /**
         * 电竞模式 - 四排
         * */
        ESPORTS_SQUAD(35, "电竞-四排", "esports-squad"),

        /**
         * 电竞模式 - 第一人称四排
         * */
        ESPORTS_SQUAD_FPP(36, "电竞-第一人称四排", "esports-squad-fpp"),

        /**
         * 实验室模式 - 第三人称
         * */
        LAB_TPP(37, "实验室-第三人称", "lab-tpp"),

        /**
         * 实验室模式 - 第一人称四排
         * */
        LAB_FPP(38, "实验室-第一人称", "lab-fpp"),

        /**
         * 街机模式 - 8V8
         */
        TEAM_DEATH_MATCH(39, "街机模式-团队淘汰竞赛", "tdm");

        /**
         * 地图编号
         */
        private final int code;

        /**
         * 地图中文名
         * */
        private final String shortName;

        /**
         * 地图日志名
         * */
        private final String longName;

        /**
         * 根据长地图名映射中文地图名
         *
         * @param longName 长地图名
         * @return 地图类型
         */
        public static String getShortNameByLongName(String longName){
            String result = "";
            for (TeamMode teamMode : TeamMode.values()) {
                if(teamMode.longName.equals(longName)){
                    result = teamMode.shortName;
                }
            }
            return result;
        }

    }

    /**
     * 小队模式
     */
    @Getter
    @AllArgsConstructor
    public enum DeathType {

        /**
         * 存活
         */
        ALIVE(1, "存活", "alive"),

        /**
         * 玩家击杀
         */
        BYPLAYER(2, "玩家击杀", "byplayer"),

        /**
         * 被毒身亡
         */
        TEAM_DEATH_MATCH(3, "被毒身亡", "byzone"),

        /**
         * 自杀
         */
        SUICIDE(4, "自杀", "suicide"),

        /**
         * 退出
         */
        LOGOUT(5, "退出", "logout"),

        /**
         * 未知原因
         */
        UNKNOWN(6, "未知原因", "");

        /**
         * 地图编号
         */
        private final int code;

        /**
         * 地图中文名
         * */
        private final String shortName;

        /**
         * 地图日志名
         * */
        private final String longName;

        /**
         * 根据长地图名映射中文地图名
         *
         * @param longName 长地图名
         * @return 地图类型
         */
        public static String getShortNameByLongName(String longName){
            String result = "";
            for (DeathType deathType : DeathType.values()) {
                if(deathType.longName.equals(longName)){
                    result = deathType.shortName;
                }
            }
            return result;
        }

    }
}
