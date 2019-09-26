select
===
select * from blog where 1=1
@if(!isEmpty(title)){
and title = #title#
@}
@if(!isEmpty(content)){
and content = #content#
@}

selectBlogByTitile
===
select * from blog where title = #title#