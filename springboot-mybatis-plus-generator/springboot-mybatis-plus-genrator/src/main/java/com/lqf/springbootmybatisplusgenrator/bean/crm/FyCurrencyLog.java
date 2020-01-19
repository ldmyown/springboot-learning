package com.lqf.springbootmybatisplusgenrator.bean.crm;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lqf
 * @since 2018-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FyCurrencyLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作人id
     */
    private Long userId;

    /**
     * 被操作人id
     */
    private Long opUid;

    /**
     * 1 用户, 2代理, 3玩家
     */
    private Integer userType;

    /**
     * 1 增加, 0扣除
     */
    private Integer status;

    /**
     * 001 金币 002房卡 
     */
    private String currencyType;

    private Boolean isGive;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 金额
     */
    private BigDecimal cash;

    /**
     * 原始数量
     */
    private Long originalNum;

    /**
     * 操作理由
     */
    private Integer reasonType;

    /**
     * 操作类型 001代理加卡，002玩家加卡
     */
    private String opType;

    /**
     * 地区
     */
    private String area;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
