package edumindai.model.vo;

import edumindai.model.entity.UserTopicAssociation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicsVO {
    /*
    用户id
     */
    private String userId;
    /**
     * 我的topic 列表
     */
    private List<UserTopicAssociation> list;
}
