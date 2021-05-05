package com.demo.dao;

import com.demo.entity.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPersonMapper {
    // 书写sql语句，没有分号
    @Select({"select *", "from person", "where id = #{id,jdbcType=BIGINT}"})
    @Results(id = "personMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "address", property = "address"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "hobby", property = "hobby")
    })
    Person selectByPrimaryKey(Long id);

    @ResultMap(value ="personMap" )
    List<Person> selectAll();
    // 增加数据
    @Update({"insert into person (name,age,address, hobby, sex)",
            "values(#{name},#{age}",
            ",#{address},#{hobby}",
            ",#{sex})"
    })
    int insertPerson(Person person);

    // 修改数据
    @Update({"update person set",
            "name = #{name},",
            "age=#{age},",
            "address=#{address},",
            "sex=#{sex},",
            "hobby=#{hobby}",
            "where id = #{id,jdbcType=BIGINT}" })
    int updatePerson(Person person);
    // 删除数据
    @Delete({ "delete", "from person", "where id = #{id,jdbcType=BIGINT}" })
    int deleteById(Long id);
}
