/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.response;

import com.pubg.analysis.entity.log.Location;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunpeikai
 * @version CharacterPositionResponse, v0.1 2020/7/21 16:54
 * @description
 */
@Data
public class CharacterPositionResponse implements Serializable {

    private String name;
    private Location location;
    private String accountId;
}
