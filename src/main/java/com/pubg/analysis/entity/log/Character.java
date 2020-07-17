package com.pubg.analysis.entity.log;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 角色实体类
 *
 * @author yangy
 * @date 2020/7/10 9:08
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "accountId")
public class Character implements Serializable {


	/**
	 * name : gem_3xD
	 * teamId : 19
	 * health : 42
	 * location : {"x":123399.4375,"y":121450.4765625,"z":1358.9000244140625}
	 * ranking : 0
	 * accountId : account.2a7eecc6bab647218c0af862207e5d50
	 * isInBlueZone : false
	 * isInRedZone : false
	 * zone : ["hatinh"]
	 */

	private String name;
	private Integer teamId;
	private Integer health;
	private Location location;
	private Integer ranking;
	private String accountId;
	private Boolean isInBlueZone;
	private Boolean isInRedZone;
	private List<String> zone;

}
