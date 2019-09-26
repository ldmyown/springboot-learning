package com.telangel.beetlsql;

import com.alibaba.fastjson.JSON;
import com.telangel.beetlsql.dao.BlogDao;
import com.telangel.beetlsql.model.Blog;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.GenFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootBeetlSqlApplicationTests {

	private SQLManager sqlManager;

	@Before
	public void setup(){
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/cloud?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false&useInformationSchema=true";
		String user = "root";
		String passwd = "root123";
		ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, user, passwd);
		DBStyle mysql = new MySqlStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("/sql");
		// 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
		UnderlinedNameConversion nc = new  UnderlinedNameConversion();
		// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
		sqlManager = new SQLManager(mysql,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
	}

	/**
	 * 简单查询
	 */
	@Test
	public void test1() {
		// 根据主键查询，如果未找到，抛出异常
		Blog unique = sqlManager.unique(Blog.class, 1);
		printObj(unique);

		// 根据主键查询，如果未找到，返回null.
		Blog single = sqlManager.single(Blog.class, 1);
		printObj(single);

		// 查询所有结果集
		List<Blog> all = sqlManager.all(Blog.class);
		printObj(all);

		// 查询所有结果集 增加翻页
		List<Blog> all1 = sqlManager.all(Blog.class, 1, 10);
		printObj(all1);

		// 查询总数
		long l = sqlManager.allCount(Blog.class);
		printObj(l);

	}

	/**
	 * template查询
	 */
	@Test
	public void test2(){
		// 根据模板查询，返回所有符合这个模板的数据,没有则返回null
		Blog blog = new Blog();
		blog.setTitle("ccc");
		blog.setContent("内容1");
		List<Blog> template = sqlManager.template(blog);
		printObj(template);

		// 根据模板查询，返回所有符合这个模板的数据 分页
		List<Blog> template1 = sqlManager.template(blog, 1, 10);
		printObj(template1);

		// 参数是paras，可以是Map或者普通对象
		List<Blog> template2 = sqlManager.template(Blog.class, blog, 1, 10);
		printObj(template2);

		// 根据模板查询，返回一条结果，如果没有找到，返回null
		Blog blog1 = sqlManager.templateOne(blog);
		printObj(blog1);

		// 根据模板查询符合条件的个数
		long l = sqlManager.templateCount(blog);

	}

	/**
	 * 通过sqlid查询
	 */
	@Test
	public void test3(){
		// 根据sqlid来查询，参数是个pojo
		Blog blog = new Blog();
		blog.setTitle("ccc");
		List<Blog> select = sqlManager.select("blog.selectBlogByTitile", Blog.class, blog);
		printObj(select);

//		public List select(String sqlId, Class clazz, Map<String, Object> paras) 根据sqlid来查询，参数是个map
//		public List select(String sqlId, Class clazz) 根据sqlid来查询，无参数
//
//		public T selectSingle(String id,Object paras, Class target) 根据sqlid查询，输入是Pojo，将对应的唯一值映射成指定的target对象，如果未找到，则返回空。需要注意的时候，有时候结果集本身是空，这时候建议使用unique
//
//		public T selectSingle(String id,Map<String, Object> paras, Class target) 根据sqlid查询，输入是Map，将对应的唯一值映射成指定的target对象，如果未找到，则返回空。需要注意的时候，有时候结果集本身是空，这时候建议使用unique
//
//		public T selectUnique(String id,Object paras, Class target) 根据sqlid查询，输入是Pojo或者Map，将对应的唯一值映射成指定的target对象,如果未找到，则抛出异常
//
//		public T selectUnique(String id,Map<String, Object> paras, Class target) 根据sqlid查询，输入是Pojo或者Map，将对应的唯一值映射成指定的target对象,如果未找到，则抛出异常
//
//		public Integer intValue(String id,Object paras) 查询结果映射成Integer，如果找不到，返回null，输入是object
//
//		public Integer intValue(String id,Map paras) 查询结果映射成Integer，如果找不到，返回null，输入是map，其他还有 longValue，bigDecimalValue
	}

	/**
	 * 插入数据
	 */
	@Test
	public void test4(){
		// 插入paras到paras关联的表
		Blog blog = new Blog();
		blog.setTitle("aaa");
		blog.setImg("cccc");
		blog.setDeleteFlag(true);
		blog.setCreateTime(new Date());
		blog.setUpdateTime(new Date());
		blog.setContent("ccccccccccc");
		sqlManager.insert(blog);
		// 将数据库主键赋值到paras里
		sqlManager.insert(blog, true);

		// 插入paras到paras关联的表,忽略为null值或者为空值的属性
		sqlManager.insertTemplate(blog);
		sqlManager.insertTemplate(blog, true);

		// 插入paras到clazz关联的表
		sqlManager.insert(Blog.class, blog);
		sqlManager.insert(Blog.class, blog, true);

		// 批量插入数据
		sqlManager.insertBatch(Blog.class, Arrays.asList(blog));
	}

	/**
	 * 更新数据
	 */
	@Test
	public void test5(){
		Blog blog = new Blog();
		blog.setId(1l);
		blog.setTitle("ccccccc");
		// 根据主键更新，所有值参与更新
		sqlManager.updateById(blog);

		// 根据主键更新，属性为null的不会更新
		sqlManager.updateTemplateById(blog);

		// 批量根据主键更新,属性为null的不会更新
		sqlManager.updateBatchTemplateById(Blog.class, Arrays.asList(blog));

	}

	public void printObj(Object object){
		System.out.println("-------------------------------------");
		System.out.println("结果为");
		System.out.println(JSON.toJSONString(object));
		System.out.println("-------------------------------------");
	}

	/**
	 * 插入或者更新数据
	 */
	@Test
	public void test6(){
		// 更新或者插入一条。先判断是否主键为空，如果为空，则插入，如果不为空，则从数据库 按照此主健取出一条，如果未取到，则插入一条，其他情况按照主键更新。插入后的自增或者序列主健
		Blog blog = new Blog();
		blog.setId(1l);
		blog.setTitle("ccccccc");
		sqlManager.upsert(blog);
		sqlManager.upsertByTemplate(blog);
	}

	/**
	 * 生成pojo代码和相应的sql文件
	 */
	@Test
	public void test7() throws Exception {
		// 根据表名生成pojo类，输出到控制台
		sqlManager.genPojoCodeToConsole("blog");

		// 生成查询，条件，更新sql模板，输出到控制台
		sqlManager.genSQLTemplateToConsole("blog");

		// 生成所有的模板 使用完后立即注释，会覆盖自己的代码
		sqlManager.genALL("com.test.model", new GenConfig(), new GenFilter() {
			@Override
			public boolean accept(String s) {
				return true;
			}
		});
	}

	/**
	 * 通过mapper查询
	 */
	@Test
	public void test8(){
		BlogDao mapper = sqlManager.getMapper(BlogDao.class);
		List<Blog> ccc = mapper.selectBlogByTitile("ccc");
		printObj(ccc);
	}
	@Test
	public void test9() throws Exception {
		sqlManager.genPojoCodeToConsole("tb_cloud_menu");
	}

}
