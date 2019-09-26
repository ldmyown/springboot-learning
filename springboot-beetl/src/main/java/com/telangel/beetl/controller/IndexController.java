package com.telangel.beetl.controller;

import com.telangel.beetl.model.Blog;
import com.telangel.beetl.service.BlogService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author lid
 * @date   2019/4/17 10:14
 * @version 1.0.0
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(required = false) String category,
                        HttpServletRequest request){
        PageQuery<Blog> pageQuery = blogService.pageBlog(pageNum, pageSize, keyword, category);
        request.setAttribute("page", pageQuery);
        request.setAttribute("keyword", keyword);
        request.setAttribute("category", category);
        return "index.html";
    }


    @GetMapping
    public String tags( HttpServletRequest request){
        List<String> tags = blogService.listCategory();
        request.setAttribute("categorys", tags);
        return "common/layout.html#tags";
    }


//    @GetMapping("/listCategory")
//    public List<String> listCategory(
//                                     HttpServletRequest request){
//    }
}
