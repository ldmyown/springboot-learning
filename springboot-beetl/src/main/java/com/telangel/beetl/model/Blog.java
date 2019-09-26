package com.telangel.beetl.model;

import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 *
 * @author lid
 * @date   2019/5/29 11:18
 * @version 1.0.0
 */
@Table(name = "blog")
@Data
public class Blog {

    private Long id;

    private String content;

    private Boolean deleteFlag;

    private String img;

    private String category;

    private String title;

    private Date createTime;

    private Date updateTime;
}
