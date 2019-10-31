package com.telangel.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.telangel.mybatisplus.entity.User;
import com.telangel.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisPlusApplicationTests {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 测试插入数据
	 */
	@Test
	public void testInsert(){
		User user = new User();
		user.setUsername("aa");
		user.setEmail("aa@test.com");
		user.setAge(12);
		userMapper.insert(user);
	}

	/**
	 * 测试更新单个数据
	 */
	@Test
	public void testUpdate() {
		User user = new User();
		user.setId(1l);
		user.setUsername("ceshi1");
		userMapper.updateById(user);
	}

	/**
	 * 测试根据条件更新数据
	 */
	@Test
	public void testUpdate1() {
		User user = new User();
		user.setEmail("ceshi1@test.com");
		QueryWrapper<User> eq = Wrappers.<User>query().eq("username", "ceshi1");
		userMapper.update(user, eq);
	}

	/**
	 * 测试根据id删除数据
	 */
	@Test
	public void testDel() {
		// 根据单个id删除
		userMapper.deleteById(1);
	}

	/**
	 * 测试根据id批量删除数据
	 */
	@Test
	public void testDel1() {
		// 根据单个id删除
		userMapper.deleteBatchIds(Arrays.asList(1, 2, 3 ));
	}

	/**
	 * 测试根据id查询数据
	 */
	@Test
	public void testSelect() {
		// 根据id查询单个记录
		User user = userMapper.selectById(1);
		// 根据id批量查询数据
		List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
	}

	/**
	 * 根据条件查询
	 */
	@Test
	public void testSelect1() {
		QueryWrapper<User> eq = Wrappers.<User>query().eq("username", "ceshi1");
		List<User> users = userMapper.selectList(eq);
	}
}
