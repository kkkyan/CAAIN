package org.bupt.caain.model;

import org.bupt.caain.pojo.po.Entry;
import org.bupt.caain.pojo.po.EntryExpert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryExpertModel {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(EntryExpertModel.class);

    @Autowired
    public EntryExpertModel(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 插入专家评奖信息
     *
     * @param entryExpert 评奖内容
     */
    public void add(EntryExpert entryExpert) {
        jdbcTemplate.update("INSERT INTO entry_expert(entry_id, expert_id, level1, level2, level3) VALUES (?, ?, ?, ?, ?)",
                entryExpert.getEntry_id(), entryExpert.getExpert_id(), entryExpert.getLevel1(), entryExpert.getLevel2(),
                entryExpert.getLevel3());
    }

    /**
     * 根据专家和作品查询评奖结果
     *
     * @param entryExpert 查询条件
     * @return 评奖结果
     */
    public EntryExpert queryByEntryAndExpert(EntryExpert entryExpert) {
        List<EntryExpert> entryExperts = jdbcTemplate.query("SELECT entry_id, expert_id, level1, level2, level3 FROM entry_expert WHERE entry_id = ? AND expert_id = ?",
                new BeanPropertyRowMapper<>(EntryExpert.class),
                entryExpert.getEntry_id(), entryExpert.getExpert_id());
        if (null != entryExperts && entryExperts.size() > 0) {
            return entryExperts.get(0);
        }
        return null;
    }

    /**
     * 删除所有评奖结果
     */
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM  entry_expert");
    }

    /**
     * 删除参评指定奖项所有作品的投票信息
     *
     * @param entryIds 作品IDs
     */
    public void deleteByEntryIds(List<Integer> entryIds) {
        entryIds.stream().forEach(entryId->jdbcTemplate.update("DELETE FROM entry_expert WHERE entry_id = ?", entryId));
    }

    public EntryExpert queryByEntryIdAndExpertId(int entryId, int expertId){
        List<EntryExpert> entryExperts = jdbcTemplate.query("SELECT level1, level2, level3 FROM entry_expert WHERE entry_id = ? AND expert_id = ?",
                new BeanPropertyRowMapper<>(EntryExpert.class), entryId, expertId);
        if(entryExperts!=null&&entryExperts.size()>0){
            return entryExperts.get(0);
        }
        return null;
    }
}
