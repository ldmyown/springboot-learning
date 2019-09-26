sample
===
* 注释

	select #use("cols")# from user  where  #use("condition")#

cols
===
	id,user_name,password,create_time,update_time,delete_flag

updateSample
===
	
	id=#id#,user_name=#userName#,password=#password#,create_time=#createTime#,update_time=#updateTime#,delete_flag=#deleteFlag#

condition
===

	1 = 1  
	@if(!isEmpty(id)){
	 and id=#id#
	@}
	@if(!isEmpty(userName)){
	 and user_name=#userName#
	@}
	@if(!isEmpty(password)){
	 and password=#password#
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
	
	