package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.utils.MongoUtil;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日志仓库
 *
 * @author yangy
 * @date 2020/7/12 11:27
 */
@Repository
public class LogRepository extends MongoBaseDao<BaseLog> {

	@Override
	protected Class<BaseLog> getEntityClass() {

		return BaseLog.class;
	}

	/**
	 * 取得日志列表
	 *
	 * @param clazz       输出类
	 * @param matchId     比赛id
	 * @param logTypeList 日志类型列表, null为全部输出
	 * @param <T>         输出类型
	 * @return 日志列表
	 */
	public <T> List<T> getLog(Class<T> clazz, String matchId, List<String> logTypeList) {
		//拆分
		UnwindOperation unwindOperation = Aggregation.unwind("$logs");

		//匹配
		Criteria criteria = new Criteria();
		criteria.and("matchId").is(matchId);
		if (logTypeList != null) {
			criteria.and("logs._T").in(logTypeList);
		}
		MatchOperation match1 = Aggregation.match(criteria);

		//根据实体类字段设置投影规则
		Fields fields = Fields.from(MongoUtil.getFieldsByClass(clazz, "$logs.", true));
		ProjectionOperation projectionOperation = Aggregation.project(fields).andExclude("_id");

		Aggregation aggregation = Aggregation.newAggregation(unwindOperation, match1, projectionOperation);
		return aggregate(clazz, aggregation, "telemetry");
	}
}