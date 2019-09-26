package com.telangel.beetl.service.impl;

import com.telangel.beetl.dao.BlogDao;
import com.telangel.beetl.dao.MsgDao;
import com.telangel.beetl.model.Blog;
import com.telangel.beetl.model.Msg;
import com.telangel.beetl.service.BlogService;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author lid
 * @date   2019/5/29 11:30
 * @version 1.0.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private MsgDao msgDao;

    @Override
    public PageQuery<Blog> pageBlog(long pageNumber, long pageSize, String keyword, String category) {
        LambdaQuery<Blog> query = blogDao.createLambdaQuery().andEq(Blog::getDeleteFlag, false);
        if (!StringUtils.isEmpty(keyword)) {
            query.andLike(Blog::getTitle, "%" + keyword.trim() + "%");
        }
        if (!StringUtils.isEmpty(category)) {
            query.andEq(Blog::getCategory, category);
        }
        if (pageNumber > 0 && pageSize > 0) {
            return query.desc(Blog::getCreateTime).page(pageNumber, pageSize);
        }
        return null;
    }

    @Override
    public PageQuery<Msg> pageMsg(long bolgId, long pageNumber, long pageSize) {
        return msgDao.createLambdaQuery().andEq(Msg::getBolgId, bolgId)
                .andEq(Msg::getDeleteFlag, false)
                .page(pageNumber, pageSize);
    }

    @Override
    public List<String> listCategory() {
        List<Blog> select = blogDao.createLambdaQuery().groupBy(Blog::getCategory).select(Blog::getCategory);
       return select.stream().map(Blog::getCategory).filter(o -> o != null).collect(Collectors.toList());
    }


}
