package com.gzz.retail.infra.persistence.mapper;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.ZModuleSqlProvider;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IZModuleMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * from Z_MODULE  where id = #{id}"})
    @Results(id = "zModuleMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "name", column = "name"),
            @Result(property = "code", column = "code"),
            @Result(property = "operate", column = "operate"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "url", column = "url"),
            @Result(property = "idx", column = "idx"),
            @Result(property = "status", column = "status"),
            @Result(property = "updateOn", column = "update_on"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "createOn", column = "create_on"),
            @Result(property = "createBy", column = "create_by"),
    })
    ZModulePo getById(Long id);

    // 列表
    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findList")
    List<ZModulePo> findLists(@Param("param") ParamMap param);

    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findList")
    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findListByPage")
    List<ZModulePo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findListByPage")
    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @Insert({"insert into Z_MODULE(id, parent_id, type, name, code, operate, icon, url, idx,",
            " status, update_on, update_by, create_on, create_by) ",
            "values(",
            "#{id,jdbcType=BIGINT}, #{parentId,jdbcType=BIGINT}, #{type,jdbcType=CHAR}, ",
            "#{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR},",
            "#{operate, jdbcType=TINYINT, typeHandler = com.gzz.retail.infra.persistence.handler.OperateSetTypeHandler}, ",
            "#{icon,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{idx,jdbcType=TINYINT},",
            "#{status,jdbcType=TINYINT}, #{updateOn,jdbcType=TIMESTAMP}, ",
            "#{updateBy,jdbcType=VARCHAR}, #{createOn,jdbcType=TIMESTAMP}, ",
            "#{createBy,jdbcType=VARCHAR}",
            ")"})
    int insert(ZModulePo zModule);

    @InsertProvider(type = ZModuleSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(ZModulePo zModule);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = ZModuleSqlProvider.class, method = "batchInsert")
    int batchInsert(@Param("list") List<ZModulePo> zModuleList);

    // 删除
    @Delete("delete from Z_MODULE where id = #{id}")
    int delete(Long id);

    // 修改
    @UpdateProvider(type = ZModuleSqlProvider.class, method = "dynamicUpdate")
    int update(ZModulePo zModule);

}