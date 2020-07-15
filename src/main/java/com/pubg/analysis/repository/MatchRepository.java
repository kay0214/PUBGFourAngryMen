/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.constants.ApiConstant;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sunpeikai
 * @version MatchRepository, v0.1 2020/7/14 17:29
 * @description
 */
@Repository
public class MatchRepository extends MongoBaseDao<Match>{

    @Override
    protected Class<Match> getEntityClass() {
        return Match.class;
    }

    public Match findByMatchId(String matchId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId));
        return findOne(query);
    }

    public List<Match> findMatchToFetch(){
        Query query = new Query();
        query.addCriteria(new Criteria().and("fetchLog").is(ApiConstant.MATCH_FETCH_LOG_NO)).limit(ApiConstant.MATCH_FETCH_LOG_COUNT).with(new Sort(Sort.Direction.DESC,"createTime"));
        return find(query);
    }

    public void updateFetchLogStatus(String matchId){
        Query query = new Query();
        // 添加查询条件
        query.addCriteria(new Criteria().and("matchId").is(matchId));
        Update update = new Update();
        // 1已拉取
        update.set("fetchLog", ApiConstant.MATCH_FETCH_LOG_YES);
        // mongo更新,将满足条件的消息全部更新[updateAll(query,update);]。[update(query,update);]只能更新一条
        update(query,update);
    }
}
