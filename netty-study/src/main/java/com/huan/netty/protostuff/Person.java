package com.huan.netty.protostuff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人员信息类
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月13日 - 下午10:08:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private int id;
	private String name;
}
