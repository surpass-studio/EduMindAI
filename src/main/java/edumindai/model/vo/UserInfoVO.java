package edumindai.model.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edumindai.model.dto.UserInfoRequest;
import edumindai.model.entity.UserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class UserInfoVO extends UserInfo {

    private static final ObjectMapper mapper = new ObjectMapper();

    // 辅助方法：逗号分隔字符串 转 List<String>
    private List<String> stringToList(String str) {
        return (str != null && !str.trim().isEmpty()) ?
                Arrays.stream(str.split(","))
                        .map(String::trim) // 去掉首尾空格
                        .collect(Collectors.toList()) :
                List.of();
    }

    // 复杂对象反序列化：JSON 转 List<AchievementDTO>
    private List<UserInfoRequest.AchievementDTO> jsonToAchievementList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return List.of();
        }
        try {
            return Arrays.asList(mapper.readValue(json, UserInfoRequest.AchievementDTO[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 反序列化失败: " + json, e);
        }
    }

    // 复杂对象反序列化：JSON 转 SocialMediaDTO
    private UserInfoRequest.SocialMediaDTO jsonToSocialMedia(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return mapper.readValue(json, UserInfoRequest.SocialMediaDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 反序列化失败: " + json, e);
        }
    }

    // 辅助方法：Date 转 LocalDate
    private LocalDate convertToLocalDate(Date date) {
        return date != null ?
                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() :
                null;
    }

    private List<String> interestsVo;


    private List<String> skillsVo;


    private List<UserInfoRequest.AchievementDTO> achievementsVo;

    private List<String> recentActivitiesVo;


    public UserInfoVO setUserInfoVO(UserInfo userInfo) {

        BeanUtils.copyProperties(userInfo,this);
        this.interestsVo = this.stringToList(userInfo.getInterests());
        this.skillsVo=this.stringToList(userInfo.getSkills());
        this.recentActivitiesVo=this.stringToList(userInfo.getRecentActivities());
        this.achievementsVo=this.jsonToAchievementList(userInfo.getAchievements());

        return this;
    }
}
