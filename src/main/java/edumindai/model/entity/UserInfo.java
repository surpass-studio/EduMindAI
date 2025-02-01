package edumindai.model.entity;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName user_info
 */

@Data
@Getter
@Setter
public class UserInfo implements Serializable {
    /**
     * 
     */

    private String userId;

    /**
     * 
     */

    private String fullName;

    /**
     * 
     */

    private Date birthDate;

    /**
     * 
     */

    private String gender;

    /**
     * 
     */

    private String location;

    /**
     * 
     */

    private String occupation;

    /**
     * 
     */

    private String bio;

    /**
     * 数组用,隔开
     */

    private String interests;

    /**
     * 
     */

    private String education;

    /**
     * 
     */

    private String skills;

    /**
     * 
     */

    private String achievements;

    /**
     * 
     */

    private String socialMedia;

    /**
     * 
     */

    private String recentActivities;


    private static final long serialVersionUID = 1L;

    private String phone;


    private String email;
    private String avatar;

}