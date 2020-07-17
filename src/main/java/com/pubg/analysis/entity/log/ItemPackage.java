/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunpeikai
 * @version ItemPackage, v0.1 2020/7/14 11:15
 * @description
 */
@Data
public class ItemPackage implements Serializable {
    // 空投类型Carapackage_RedBox_C
    private String itemPackageId;
    // 空投着陆坐标
    private Location location;
    // 空投物品
    private List<Item> items;
}
