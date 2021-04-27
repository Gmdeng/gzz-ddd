package com.gzz.boot.event.impl.mybatis;

import com.gzz.boot.event.StoredEvent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreEventMapper {
    @Select("select * from dom_stored_event " +
            "where event_id between #{lowStoredEventId} and #{highStoredEventId} " +
            "order by event_id")
    List<StoredEvent> allStoredEventsBetween(@Param("lowStoredEventId") long lowStoredEventId, @Param("highStoredEventId") long highStoredEventId);

    @Select("select * from dom_stored_event where event_id > #{storedEventId} order by event_id")
    List<StoredEvent> allStoredEventsSince(@Param("storedEventId") long storedEventId);

    @Options(useGeneratedKeys = true, keyProperty = "eventId")
    @Insert("insert into dom_stored_event" +
            "(type_name, occurred_on, event_body) " +
            "values (#{typeName}, #{occurredOn}, #{eventBody})")
    void create(StoredEvent event);

    @Select("select count(*) from tbl_stored_event")
    long countStoredEvents();
}
