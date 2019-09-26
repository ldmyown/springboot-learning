package com.telangel.beetlsql.util;

import com.alibaba.fastjson.JSON;
import com.telangel.beetlsql.model.Blog;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DebugInterceptor;

import java.util.Date;
import java.util.List;

/**
 *
 * @author lid
 * @date   2019/6/4 12:59
 * @version 1.0.0
 */
public class SqlUtil {
    public static void main(String[] args) throws Exception {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false&useInformationSchema=true";
        String user = "root";
        String passwd = "root123";
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, user, passwd);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
        UnderlinedNameConversion nc = new  UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql,loader,source,nc,new Interceptor[]{new DebugInterceptor()});

        Blog blog = new Blog();
        blog.setTitle("aaa");
        blog.setImg("cccc");
        blog.setDeleteFlag(true);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());

        blog.setContent("ccccccccccc");
        sqlManager.insert(blog);

        //使用内置sql查询用户
        int id = 1;
        blog = sqlManager.unique(Blog.class,id);
        System.out.println(JSON.toJSONString(blog));
        //模板更新,仅仅根据id更新值不为null的列
        Blog newBlog = new Blog();
        newBlog.setId(1l);
        newBlog.setTitle("ccc");
        sqlManager.updateTemplateById(newBlog);

        // 模板查询
        Blog query = new Blog();
        query.setTitle("888");
        query.setContent("内容1");
        List<Blog> list = sqlManager.template(query);
        System.out.println(JSON.toJSONString(list));

        //Query查询
        LambdaQuery blogQuery = sqlManager.lambdaQuery(Blog.class);
        List<Blog> blogs = blogQuery.andEq("title","aaa").select();
        System.out.println(JSON.toJSONString(blogs));

        // 使用user.md 文件里的select语句，参考下一节。
        Blog query2 = new Blog();
        query.setTitle("ccc");
        List<Blog> list2 = sqlManager.select("blog.select",Blog.class, query2);
        System.out.println(JSON.toJSONString(list2));
//        // 这一部分需要参考mapper一章
//        BlogDao dao = sqlManager.getMapper(BlogDao.class);
//        List<User> list3 = dao.select(query2);

        sqlManager.genPojoCodeToConsole("blog");
        sqlManager.genSQLTemplateToConsole("blog");

    }
}
