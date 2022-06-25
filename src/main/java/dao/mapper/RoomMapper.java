package dao.mapper;

import entity.Room;
import org.apache.ibatis.annotations.*;
import org.springframework.web.context.annotation.RequestScope;

@Mapper
public interface RoomMapper {

    @Insert("insert into user.roomlist(roomNumber, isReady,Password) VALUES(#{roomNumber},#{isReady},#{password})")
    int createRoomNumber(@Param("roomNumber")String roomNumber, @Param("isReady")String isReady, @Param("password")String password);

    @Insert("insert into user.roomlist(roomNumber, isReady,Password) VALUES(#{roomNumber},#{isReady},#{password})")
    int createRoomNumberSecret(@Param("roomNumber")String roomNumber, @Param("isReady")String isReady, @Param("password")String password);

    @Delete("delete from user.roomlist where roomNumber=#{roomNumber}")
    int deleteRoomNumber(String roomNumber);

    @Select("select * from user.roomlist where roomNumber=#{roomNumber}")
    Room getRoomNumber(String roomNumber);

    //修改状态
    @Update("update user.roomlist set isReady=#{isReady} where roomNumber=#{roomNumber} ")
    int updateIsReady(@Param("isReady") int isReady,@Param("roomNumber")String roomNumber);

}
