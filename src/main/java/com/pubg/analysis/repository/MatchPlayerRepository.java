package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 日志仓库
 *
 * @author yangy
 * @date 2020/7/12 11:27
 */
@Repository
public class MatchPlayerRepository extends MongoBaseDao<MatchPlayer> {

	@Override
	protected Class<MatchPlayer> getEntityClass() {
		return MatchPlayer.class;
	}

	public List<MatchPlayer> findByIdOrName(String accountId, String playerName){
        Query query = new Query();
        // 构建查询条件
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(accountId)){
            criteria.and("accountId").is(accountId);
        }
        if(!StringUtils.isEmpty(playerName)){
            criteria.and("playerName").is(playerName);
        }
        query.addCriteria(criteria);
        return find(query);
    }
}
