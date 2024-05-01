package edumindai.service.impl;

import edumindai.common.Response;
import edumindai.enums.db.UserStatusEnum;
import edumindai.enums.db.UserTypeEnum;
import edumindai.enums.exception.RegisterExceptionEnum;
import edumindai.exception.RegisterServiceException;
import edumindai.exception.ServiceException;
import edumindai.mapper.UserMapper;
import edumindai.model.dto.LoginRequest;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.entity.User;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import edumindai.service.RegisterContext;
import edumindai.service.UserService;
import edumindai.utils.ContextHolder;
import edumindai.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
* @author ljz20
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-04-30 16:14:17
*/
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public Response<LoginVO> login(LoginRequest loginRequest) {


//        // 传入信息进行认证 魔改选择,加个选择器(context)然后枚举选择然后传入传出
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getStudentNo(),loginRequest.getPassword());
//
//
//        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //security校验成功不会返回null,校验失败会返回null

//        if(Objects.isNull(authenticate)){
//
//            //密码错误
//           // throw new ServiceException(UserServiceErrorEnum.USER_LOGIN_ERROR.getMessage(),UserServiceErrorEnum.USER_LOGIN_ERROR.getCode());
//        }

        //authenticate通过验证已经将用户信息保存在线程中,SecurityContextHolder,自己用直接的信息,
        //注意更新信息

        User user = ContextHolder.getUser();

        //TODO 生成token信息
      //  String token = JwtUtil.createJWT(user);

//        return new Response<>(1,new LoginVO(token),"登陆成功");

        return null;
    }

    @Override
    public Response<RegisterVO> register(RegisterRequest registerRequest) {



        //选择策略,验证并获取用户对象
        User user = new RegisterContext(registerRequest.getRegisterPattern()).registerOperation(registerRequest);

        //得到用户对象,保存在数据库
        saveUser(user);

        //创建token返回token

        //TODO 登陆结合
//        JwtUtil.createJWT()

        return null;
    }

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return user
     */
    @Override
    public User getUserByPhone(String phone) {

        try {

            return userMapper.findUserByPhone(phone);

        }catch (Exception e){

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.QUERY_USER_ERROR);
        }

    }

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return user
     */
    @Override
    public User getUserByEmail(String email) {

        try {

            return userMapper.findUserByEmail(email);

        }catch (Exception e){

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.QUERY_USER_ERROR);
        }

    }


    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user){

        try {

            userMapper.insertUser(user);

        }catch (Exception e){

            log.error(e.getMessage());

            throw new RegisterServiceException(RegisterExceptionEnum.SQL_INSERT_ERROR);
        }
    }

    /**
     * 创建用户,用于提供给策略模式接口实现类创建用户,因为大部分代码重复,所以邮箱注册实现类和手机注册实现类一致
     * @param registerRequest
     * @return
     */
    public static User createUser(RegisterRequest registerRequest){
        User user = new User();

        BeanUtils.copyProperties(registerRequest,user);

        //用户密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encode);

        user.setStatus(UserStatusEnum.NORMAL);

        user.setId(UUID.randomUUID().toString());

        user.setTypes(UserTypeEnum.ORDINARY);

        return user;
    }
}




