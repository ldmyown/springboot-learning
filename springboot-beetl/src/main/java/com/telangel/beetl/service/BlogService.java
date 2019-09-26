package com.telangel.beetl.service;

import com.telangel.beetl.model.Blog;
import com.telangel.beetl.model.Msg;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * @author lid
 * @date 2019/5/29 11:27
 */
public interface BlogService {

    PageQuery<Blog> pageBlog(long pageNumber, long pageSize, String keyword, String category);

    PageQuery<Msg> pageMsg(long bolgId, long pageNumber, long pageSize);

    List<String> listCategory();
}
