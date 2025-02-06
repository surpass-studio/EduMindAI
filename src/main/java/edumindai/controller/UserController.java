package edumindai.controller;

import edumindai.common.Response;
import edumindai.mapper.UserInfoMapper;
import edumindai.model.dto.*;
import edumindai.model.entity.User;
import edumindai.model.entity.UserInfo;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import edumindai.model.vo.UserInfoVO;
import edumindai.service.UserService;
import edumindai.utils.ContextHolder;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Resource
    UserInfoMapper userInfoMapper;

    /**
     * 登陆
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public Response<LoginVO> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    /**
     * 注册
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public Response<RegisterVO> register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    /**
     * 查找密码
     * @param findPasswordRequest
     * @return
     */
    @PostMapping("/findPassword")
    public Response<String> findPassword(@RequestBody FindPasswordRequest findPasswordRequest) {
        String passWord = userService.findPassWord(findPasswordRequest.getEmail(), findPasswordRequest.getNewPassword(), findPasswordRequest.getCode());

        return new Response<>(200,"重置密码成功",passWord);
    }

    /**
     * 获取我的会话id
     * @return
     */
    @GetMapping("/topics")
    public Response getTopics() {
        return userService.getTopics();
    }


    /**
     * 增加信息
     * @param userInfoDTO
     * @return
     */
    @PostMapping("/info/add")
    public Response<UserInfo> createUser( @RequestBody UserInfoRequest userInfoDTO) {
        // 转换DTO为实体
        UserInfo userInfo = new UserInfo();
        User user = ContextHolder.getUser();
        userInfo.setUserId(user.getId());
        userInfo = userInfoDTO.toEntity(userInfo);

        userInfoMapper.insertUser(userInfo);

        UserInfoVO userInfoVO = new UserInfoVO().setUserInfoVO(userInfo);

        return new Response<>(200, "成功返回", userInfoVO); // 返回转换后的实体
    }

    /**
     * 改信息
     * @param userInfoUpdateRequest
     * @return
     */
    @PostMapping("/info/update")
    public Response<UserInfo> updateUser( @RequestBody UserInfoUpdateRequest userInfoUpdateRequest) {
        // 获取当前用户
        User user = ContextHolder.getUser();

        userInfoUpdateRequest.setUserId(user.getId());
        //更改信息
        userInfoMapper.updateUserInfo(userInfoUpdateRequest);
        //查找userInfo

        UserInfo userInfo = userInfoMapper.getUserInfoById(user.getId());



        UserInfoVO userInfoVO = new UserInfoVO().setUserInfoVO(userInfo);



        return new Response<>(200, "更新成功", userInfoVO);
    }

    /**
     * 查信息
     * @return
     */
    @GetMapping("/info/find")
    public Response<UserInfo> updateUser() {
        // 获取当前用户
        User user = ContextHolder.getUser();
        UserInfo userInfoById = userInfoMapper.getUserInfoById(user.getId());


        UserInfoVO userInfoVO = new UserInfoVO().setUserInfoVO(userInfoById);


        return new Response<>(200, "更新成功",userInfoVO );
    }






}
