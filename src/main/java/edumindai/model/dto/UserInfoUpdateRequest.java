package edumindai.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoUpdateRequest {

    private String fullName;


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


    private List<UserInfoRequest.AchievementDTO> achievements;


    private UserInfoRequest.SocialMediaDTO socialMedia;


    private List<String> recentActivities;

    private String userId;

    @Data
    public static class AchievementDTO {
        @NotBlank(message = "成就图标不能为空")
        private String icon;

        @NotBlank(message = "成就描述不能为空")
        private String text;
    }

    @Data
    public static class SocialMediaDTO {
        @NotBlank(message = "Twitter不能为空")
        private String twitter;

        @NotBlank(message = "Instagram不能为空")
        private String instagram;

        @NotBlank(message = "GitHub不能为空")
        private String github;
    }


}
