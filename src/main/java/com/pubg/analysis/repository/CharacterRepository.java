package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.Character;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色仓库
 *
 * @author yangy
 * @date 2020/7/11 22:27
 */
@Repository
@Slf4j
public class CharacterRepository extends MongoBaseDao<Character> {

	@Override
	protected Class<Character> getEntityClass() {

		return Character.class;
	}

	/**
	 * 获取比赛中全部角色记录
	 *
	 * @param matchId 比赛id
	 * @return 角色记录列表
	 */
	public List<Character> getCharacters(String matchId) {
		//拆分
		UnwindOperation unwindOperation = Aggregation.unwind("$events");

		//匹配
		MatchOperation match1 = Aggregation.match(new Criteria()
				.and("matchId").is(matchId)
				.and("events.character").exists(true));

		//根据实体类字段设置投影规则
		java.lang.reflect.Field[] entityFields = getEntityClass().getDeclaredFields();
		Field[] fieldArray = new Field[entityFields.length];
		for (int i = 0; i < entityFields.length; i++) {
			String name = entityFields[i].getName();
			fieldArray[i] = Fields.field(name, "$events.character." + name);
		}
		Fields fields = Fields.from(fieldArray);
		ProjectionOperation projectionOperation = Aggregation.project(fields).andExclude("_id");

		Aggregation aggregation = Aggregation.newAggregation(unwindOperation, match1, projectionOperation);
		return aggregate(aggregation, "telemetry");
	}
}
