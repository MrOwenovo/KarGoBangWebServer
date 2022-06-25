package dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select password from user.user where username=#{username}")
    String getPasswordByUsername(String username);
}
