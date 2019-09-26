package com.test.model;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2019-06-04
*/
@Table(name="blog.message")
public class Message   {
	
	// alias
	public static final String ALIAS_id = "id";
	public static final String ALIAS_delete_flag = "delete_flag";
	public static final String ALIAS_blog_id = "blog_id";
	public static final String ALIAS_content = "content";
	public static final String ALIAS_nick_name = "nick_name";
	public static final String ALIAS_create_time = "create_time";
	public static final String ALIAS_update_time = "update_time";
	
	private Long id ;
	private Integer deleteFlag ;
	private Long blogId ;
	private String content ;
	private String nickName ;
	private Date createTime ;
	private Date updateTime ;
	
	public Message() {
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
	
	public Long getBlogId(){
		return  blogId;
	}
	public void setBlogId(Long blogId ){
		this.blogId = blogId;
	}
	
	public String getContent(){
		return  content;
	}
	public void setContent(String content ){
		this.content = content;
	}
	
	public String getNickName(){
		return  nickName;
	}
	public void setNickName(String nickName ){
		this.nickName = nickName;
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
