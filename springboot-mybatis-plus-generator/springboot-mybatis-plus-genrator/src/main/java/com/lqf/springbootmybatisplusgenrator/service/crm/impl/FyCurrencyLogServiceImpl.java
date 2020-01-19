package com.lqf.springbootmybatisplusgenrator.service.crm.impl;

import com.lqf.springbootmybatisplusgenrator.bean.crm.FyCurrencyLog;
import com.lqf.springbootmybatisplusgenrator.dao.crm.FyCurrencyLogMapper;
import com.lqf.springbootmybatisplusgenrator.service.crm.FyCurrencyLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lqf
 * @since 2018-09-29
 */
@Service
public class FyCurrencyLogServiceImpl extends ServiceImpl<FyCurrencyLogMapper, FyCurrencyLog> implements FyCurrencyLogService {

    @Autowired
    private FyCurrencyLogMapper mapper;
    @Override
    public List<FyCurrencyLog> list() {
        return mapper.selectList(null);
    }
}
