package com.telangel.test.service.impl;

import com.telangel.test.entity.TUser;
import com.telangel.test.mapper.TUserMapper;
import com.telangel.test.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lid
 * @since 2019-09-26
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
