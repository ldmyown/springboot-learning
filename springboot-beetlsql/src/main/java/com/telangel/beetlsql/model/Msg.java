package com.telangel.beetlsql.model;

import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 *
 * @author lid
 * @date   2019/5/31 14:26
 * @version 1.0.0
 */
@Table(name = "message")
@Data
public class Msg {
    private Long id;

    private long bolgId;

    private String nickName;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Boolean deleteFlag;
}
