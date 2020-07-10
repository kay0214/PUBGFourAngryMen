/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author sunpeikai
 * @version MongoTest, v0.1 2020/7/10 10:42
 * @description
 */
@Data
@Document(collection = "pubg_class_teacher")
@CompoundIndexes({
        @CompoundIndex(name = "teacher_headmaster",def = "{'teacher':1,'headmaster':1}")
})
public class MongoTest {

    // 班级
    @Indexed
    private Integer classId;

    // 老师
    private String teacher;

    // 是否班主任
    private Integer headmaster;

    // 所授课程
    private String course;
}
