sample
===
* 注释

	select #use("cols")# from message  where  #use("condition")#

cols
===
	id,blog_id,nick_name,content,create_time,update_time,delete_flag

updateSample
===
	
	id=#id#,blog_id=#blogId#,nick_name=#nickName#,content=#content#,create_time=#createTime#,update_time=#updateTime#,delete_flag=#deleteFlag#

condition
===

	1 = 1  
	@if(!isEmpty(id)){
	 and id=#id#
	@}
	@if(!isEmpty(blogId)){
	 and blog_id=#blogId#
	@}
	@if(!isEmpty(nickName)){
	 and nick_name=#nickName#
	@}
	@if(!isEmpty(content)){
	 and content=#content#
	@}
	@if(!isEmpty(createTime)){
	 and create_time=#createTime#
	@}
	@if(!isEmpty(updateTime)){
	 and update_time=#updateTime#
	@}
	@if(!isEmpty(deleteFlag)){
	 and delete_flag=#deleteFlag#
	@}
	
	