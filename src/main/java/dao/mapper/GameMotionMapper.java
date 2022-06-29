package dao.mapper;

import entity.Move;
import entity.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

@Mapper
public interface GameMotionMapper{

    //将移动的棋子插入表中
    @Insert("insert into user.roommove values(#{poster},#{locationX},#{locationY},#{type},#{locationZ})")
    public int addMove(@Param("poster")String poster,@Param("locationX") String locationX, @Param("locationY") String locationY, @Param("type") String type, @Param("locationZ")String locationZ);

    //查出某一房间号的所有行动
    @Select("select * from user.roommove where roomNumber=#{roomNumber}")
    Move[] getMoves(String roomNumber);
}
