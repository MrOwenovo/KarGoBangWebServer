//package dao.mapper;
//
//import entity.Room;
//import org.apache.ibatis.annotations.*;
//import org.springframework.web.context.annotation.RequestScope;
//
//@Mapper
//@RequestScope
//public interface RoomMapper {
//
//    @Insert("insert into user.roomlist(roomNumber, isReady,Password) VALUES(#{roomNumber},#{isRdy},},#{passord}})")
//    int createRoomNumber(@Param("roomNumber")String roomNumber, @Param("isReady")String isReady, @Param("password")String password);
//
//    @Delete("delete from user.roomlist where roomNumber=#{roomNumber}")
//    int deleteRoomNumber(String roomNumber);
//
//    @Select("select * from user.roomlist where roomNumber=#{roomNumber}")
//    Room getRoomNumber(String roomNumber);
//
//}
