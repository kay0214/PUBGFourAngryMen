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
		ERANGEL(0, "艾伦格", "Erangel_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

        /**
         * 艾伦格 - 重制版
         */
        BALTIC(0, "艾伦格", "Baltic_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

        /**
		 * 米拉玛
		 */
		MIRAMAR(1, "米拉玛", "Desert_Main", MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

		/**
		 * 萨诺
		 */
		SANHOK(2, "萨诺", "Savage_Main", MAP_SIDE_LENGTH_4X4_CM, MAP_SIDE_LENGTH_4X4_CM),

		/**
		 * 维寒迪
		 */
		VIKENDI(3, "维寒迪", "DihorOtok_Main", MAP_SIDE_LENGTH_6X6_CM, MAP_SIDE_LENGTH_6X6_CM),

		/**
         * 练功房
         * */
		TRAIN(9, "练功房", "RANGE_Main", MAP_SIDE_LENGTH_2X2_CM, MAP_SIDE_LENGTH_2X2_CM);

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
        SOLO(0, "单排", "solo"),

        /**
         * 第一人称单排
         */
        SOLO_FPP(0, "第一人称单排", "solo-fpp"),

        /**
         * 双排
         */
        DUO(1, "双排", "duo"),

        /**
         * 第一人称双排
         */
        DUO_FPP(1, "第一人称双排", "duo-fpp"),

        /**
         * 四排
         * */
        SQUAD(1, "四排", "squad"),

        /**
         * 第一人称四排
         * */
        SQUAD_FPP(1, "第一人称四排", "squad-fpp"),

        /**
         * 街机模式 - 8V8
         */
        TEAM_DEATH_MATCH(2, "街机模式-团队淘汰竞赛", "tdm");

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
         * 被毒身亡
         */
        UNKNOWN(4, "未知原因", "");

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
