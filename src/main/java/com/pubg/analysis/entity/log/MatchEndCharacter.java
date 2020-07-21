/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

/**
 * @author sunpeikai
 * @version MatchEndCharacter, v0.1 2020/7/21 9:39
 * @description
 */
@Data
public class MatchEndCharacter {
    private Character character;
    private String primaryWeaponFirst;
    private String primaryWeaponSecond;
    private String secondaryWeapon;
    private Integer spawnKitIndex;
}
