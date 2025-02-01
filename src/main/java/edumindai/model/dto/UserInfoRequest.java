package edumindai.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edumindai.model.entity.UserInfo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Data
public class UserInfoRequest {

    private String fullName;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;


    private String gender;


    private String location;


    private String occupation;


    @Size(max = 500, message = "个人简介最多500字")
    private String bio;


    private List<String> interests;


    private String avatar;


    private String education;


    private String phone;


    private String email;


    private List<String> skills;


    private List<AchievementDTO> achievements;


    private SocialMediaDTO socialMedia;


    private List<String> recentActivities;

    @Data
    public static class AchievementDTO {
        @NotBlank(message = "成就图标不能为空")
        private String icon;
        
        @NotBlank(message = "成就描述不能为空")
        private String text;
    }

    @Data
    public static class SocialMediaDTO {

        private String twitter;
        

        private String instagram;
        

        private String github;
    }


    // entity 新增转换方法
    public UserInfo toEntity(UserInfo entity) {
        ObjectMapper mapper = new ObjectMapper();
//
        // 基础字段映射（非空且非空字符串时设置）
        if (isNotEmpty(this.fullName)) {
            entity.setFullName(this.fullName);
        }
        if (this.birthDate != null) { // LocalDate没有空字符串问题
            entity.setBirthDate(convertToDate(this.birthDate));
        }
        if (isNotEmpty(this.gender)) {
            entity.setGender(this.gender);
        }
        if (isNotEmpty(this.location)) {
            entity.setLocation(this.location);
        }
        if (isNotEmpty(this.occupation)) {
            entity.setOccupation(this.occupation);
        }
        if (isNotEmpty(this.bio)) {
            entity.setBio(this.bio);
        }
        if (isNotEmpty(this.education)) {
            entity.setEducation(this.education);
        }


        if (isNotEmpty(this.email)) {
            entity.setEmail(this.email);
        }
        if (isNotEmpty(this.phone)) {
            entity.setPhone(this.phone);
        }
        if (isNotEmpty(this.avatar)) {
            entity.setAvatar(this.avatar);
        }

        // 集合类型转换（非空且非空集合时处理）
        if (isNotEmpty(this.interests)) {
            entity.setInterests(listToString(this.interests));
        }
        if (isNotEmpty(this.skills)) {
            entity.setSkills(listToString(this.skills));
        }
        if (isNotEmpty(this.recentActivities)) {
            entity.setRecentActivities(listToString(this.recentActivities));
        }

        // 复杂对象序列化（非空且非空集合时处理）
        try {
            if (isNotEmpty(this.achievements)) {
                entity.setAchievements(mapper.writeValueAsString(this.achievements));
            }
            if (this.socialMedia != null && isSocialMediaNotEmpty()) {
                entity.setSocialMedia(mapper.writeValueAsString(this.socialMedia));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON序列化失败", e);
        }

        return entity;
    }

    // 通用空值检查方法
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // 集合空值检查
    private boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    // 复杂对象空值检查
    private boolean isSocialMediaNotEmpty() {
        return isNotEmpty(this.socialMedia.getTwitter()) ||
                isNotEmpty(this.socialMedia.getInstagram()) ||
                isNotEmpty(this.socialMedia.getGithub());
    }


    // 辅助方法：LocalDate转Date
    private Date convertToDate(LocalDate localDate) {
        return localDate != null ?
                Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) :
                null;
    }

    // 辅助方法：List转逗号分隔字符串
    private String listToString(List<?> list) {
        return list != null ?
                list.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")) :
                null;
    }

}