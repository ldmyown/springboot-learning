package com.test.model;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2019-06-04
*/
@Table(name="blog.user")
public class User   {
	
	// alias
	public static final String ALIAS_id = "id";
	public static final String ALIAS_delete_flag = "delete_flag";
	public static final String ALIAS_password = "password";
	public static final String ALIAS_user_name = "user_name";
	public static final String ALIAS_create_time = "create_time";
	public static final String ALIAS_update_time = "update_time";
	
	private Long id ;
	private Integer deleteFlag ;
	private String password ;
	private String userName ;
	private Date createTime ;
	private Date updateTime ;
	
	public User() {
	}
	
	public Long getId(){
		return  id;
	}
	public void setId(Long id ){
		this.id = id;
	}
	
	public Integer getDeleteFlag(){
		return  deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag ){
		this.deleteFlag = deleteFlag;
	}
	
	public String getPassword(){
		return  password;
	}
	public void setPassword(String password ){
		this.password = password;
	}
	
	public String getUserName(){
		return  userName;
	}
	public void setUserName(String userName ){
		this.userName = userName;
	}
	
	public Date getCreateTime(){
		return  createTime;
	}
	public void setCreateTime(Date createTime ){
		this.createTime = createTime;
	}
	
	public Date getUpdateTime(){
		return  updateTime;
	}
	public void setUpdateTime(Date updateTime ){
		this.updateTime = updateTime;
	}
	

}
