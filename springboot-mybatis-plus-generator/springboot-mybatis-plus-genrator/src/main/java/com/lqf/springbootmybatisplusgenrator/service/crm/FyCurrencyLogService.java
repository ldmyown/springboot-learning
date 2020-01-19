package com.lqf.springbootmybatisplusgenrator.service.crm;

import com.lqf.springbootmybatisplusgenrator.bean.crm.FyCurrencyLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lqf
 * @since 2018-09-29
 */
public interface FyCurrencyLogService extends IService<FyCurrencyLog> {

    List<FyCurrencyLog> list();
}
