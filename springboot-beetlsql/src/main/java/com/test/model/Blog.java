package com.test.model;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2019-06-04
*/
@Table(name="blog.blog")
public class Blog   {
	
	// alias
	public static final String ALIAS_id = "id";
	public static final String ALIAS_delete_flag = "delete_flag";
	public static final String ALIAS_category = "category";
	public static final String ALIAS_content = "content";
	public static final String ALIAS_img = "img";
	public static final String ALIAS_title = "title";
	public static final String ALIAS_create_time = "create_time";
	public static final String ALIAS_update_time = "update_time";
	
	private Long id ;
	private Integer deleteFlag ;
	private String category ;
	private String content ;
	private String img ;
	private String title ;
	private Date createTime ;
	private Date updateTime ;
	
	public Blog() {
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
	
	public String getCategory(){
		return  category;
	}
	public void setCategory(String category ){
		this.category = category;
	}
	
	public String getContent(){
		return  content;
	}
	public void setContent(String content ){
		this.content = content;
	}
	
	public String getImg(){
		return  img;
	}
	public void setImg(String img ){
		this.img = img;
	}
	
	public String getTitle(){
		return  title;
	}
	public void setTitle(String title ){
		this.title = title;
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
