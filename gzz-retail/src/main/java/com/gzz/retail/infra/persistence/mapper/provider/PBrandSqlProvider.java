package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.PBrand;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 11:03:12
 */
public class PBrandSqlProvider {

    // 选择性新增SQL
    public String insertSelective(PBrand pBrand) {
        return new SQL(){
            {
                INSERT_INTO("p_brand");
                if (pBrand.getId() != null) {VALUES("id", "#{id}");}
                if (pBrand.getCnName() != null) {VALUES("cn_name", "#{cnName}");}
                if (pBrand.getEnName() != null) {VALUES("en_name", "#{enName}");}
                if (pBrand.getLogo() != null) {VALUES("logo", "#{logo}");}
                if (pBrand.getWebsite() != null) {VALUES("website", "#{website}");}
                if (pBrand.getStroy() != null) {VALUES("stroy", "#{stroy}");}
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<PBrand>> map) {
        List<PBrand> list = (List<PBrand>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].cnName}, #'{'list[{0}].enName}, #'{'list[{0}].logo}, #'{'list[{0}].website}, #'{'list[{0}].stroy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO p_brand (id, cn_name, en_name, logo, website, stroy) VALUES "
        + String.join(", ", rows);

    }
    // 选择性更新SQL
    public String update(PBrand pBrand) {
        return new SQL() {
            {
                UPDATE("p_brand");
                if (pBrand.getCnName() != null) {SET("cn_name=#{cnName}");}
                if (pBrand.getEnName() != null) {SET("en_name=#{enName}");}
                if (pBrand.getLogo() != null) {SET("logo=#{logo}");}
                if (pBrand.getWebsite() != null) {SET("website=#{website}");}
                if (pBrand.getStroy() != null) {SET("stroy=#{stroy}");}
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(ParamMap param, Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_brand");
                if(param.get("name") !=null ) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_brand");
                if(param.get("name") !=null ) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}