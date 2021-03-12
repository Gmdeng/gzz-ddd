package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PTag;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 11:00:51
 */
public class PTagSqlProvider {

    // 选择性新增SQL
    public String insertSelective(PTag pTag) {
        return new SQL(){
            {
                INSERT_INTO("p_tag");
                if (pTag.getId() != null) {VALUES("id", "#{id}");}
                if (pTag.getCnName() != null) {VALUES("cn_name", "#{cnName}");}
                if (pTag.getEnName() != null) {VALUES("en_name", "#{enName}");}
                if (pTag.getLogo() != null) {VALUES("logo", "#{logo}");}
                if (pTag.getWebsite() != null) {VALUES("website", "#{website}");}
                if (pTag.getStroy() != null) {VALUES("stroy", "#{stroy}");}
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<PTag>> map) {
        List<PTag> list = (List<PTag>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].cnName}, #'{'list[{0}].enName}, #'{'list[{0}].logo}, #'{'list[{0}].website}, #'{'list[{0}].stroy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO p_tag (id, cn_name, en_name, logo, website, stroy) VALUES "
        + String.join(", ", rows);

    }
    // 选择性更新SQL
    public String update(PTag pTag) {
        return new SQL() {
            {
                UPDATE("p_tag");
                if (StringUtil.isNotEmpty(pTag.getCnName())) {SET("cn_name=#{cnName}");}
                if (pTag.getEnName() != null) {SET("en_name=#{enName}");}
                if (pTag.getLogo() != null) {SET("logo=#{logo}");}
                if (pTag.getWebsite() != null) {SET("website=#{website}");}
                if (pTag.getStroy() != null) {SET("stroy=#{stroy}");}
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(ParamMap param, Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_tag");
                if(param.get("name") !=null ) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_tag");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}