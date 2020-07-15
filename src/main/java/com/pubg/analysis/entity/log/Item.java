/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

import java.util.List;

/**
 * @author sunpeikai
 * @version Item, v0.1 2020/7/14 10:03
 * @description
 */
@Data
public class Item {

    /*
    		"itemId": "Item_Weapon_UMP_C",
			"stackCount": 1,
			"category": "Weapon",
			"subCategory": "Main",
			"attachedItems": [
				"Item_Attach_Weapon_Magazine_Extended_Medium_C"
			]
     */
    private String itemId;
    private Integer stackCount;
    private String category;
    private String subCategory;
    private List<String> attachedItems;
}
