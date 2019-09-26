package com.telangel.beetlsql.dao;

import com.telangel.beetlsql.model.Blog;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author lid
 * @date 2019/6/4 14:09
 */
@SqlResource("core.blog")
public interface BlogDao extends BaseMapper<Blog> {

    List<Blog> selectBlogByTitile(String title);
}
