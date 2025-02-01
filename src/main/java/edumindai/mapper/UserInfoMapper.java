package edumindai.mapper;


import edumindai.model.dto.UserInfoUpdateRequest;
import edumindai.model.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ljz20
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2025-01-31 18:43:51
* @Entity edumindai.model.entity.UserInfo
*/
@Mapper
public interface UserInfoMapper {

    int insertUser(UserInfo userInfo);

    @Select("select * from user_info where user_id=#{userId}")
    UserInfo getUserInfoById(@Param("userId")String userId);

    int updateUserInfo(UserInfoUpdateRequest userInfoUpdateRequest);

}




