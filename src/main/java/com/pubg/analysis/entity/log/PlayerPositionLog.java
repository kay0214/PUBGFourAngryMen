package com.pubg.analysis.entity.log;

import lombok.*;

import java.io.Serializable;

/**
 * @author yangy
 * @date 2020/7/12 10:18
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PlayerPositionLog extends BaseLog implements Serializable {

	/**
	 * character : {"name":"Jump-","teamId":14,"health":100,"location":{"x":261146.421875,"y":247191.234375,"z":1423.52001953125},"ranking":0,"accountId":"account.b7148a387d924e509796aff52978f303","isInBlueZone":false,"isInRedZone":false,"zone":["quarry"]}
	 * vehicle : null
	 * elapsedTime : 149
	 * numAlivePlayers : 90
	 * common : {"isGame":1}
	 */

	// private Vehicle vehicle;
	// private int elapsedTime;
	// private int numAlivePlayers;
}
