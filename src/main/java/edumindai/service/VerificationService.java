package edumindai.service;

import edumindai.common.Response;

/**
 * 验证接口
 * 验证码生成以及发送
 * 验证码校验
 */
public interface VerificationService {

    /**
     * 发送验证码
     * @param phoneNumber 手机号
     * @param email 邮箱
     *              可选字段
     * @return 发送成功告诉给用户
     */
    Response verificationCode(String phoneNumber, String email);

    //验证码校验

    /**
     * 验证码校验
     * @param key 手机号或者邮箱
     * @param verificationCode 验证码
     * @return 验证过了返回true 验证失败是抛出异常
     */
    boolean verificationCodeCheck(String key, String verificationCode) throws Exception;
}
