<!DOCTYPE html>
<html>

<#include "/common/head.ftl">

<body class="fixed-header ">

<#include "/common/nav.ftl">
    <div class="page-container ">
        <#include "/common/top.ftl">
        <div class="page-content-wrapper ">

            <div class="content ">
                <div class=" container-fluid   container-fixed-lg bg-white">
                    <div class="card card-transparent">
                        <div class="card-header ">
                            <div class="card-title">Users
                            </div>
                            <div class="pull-right">
                                <div class="col-xs-12">
                                    <input type="text" id="search-table" class="form-control pull-right" placeholder="Search">
                                </div>
                            </div>
							<div class="pull-right">
								<div class="col-xs-12">
									<a id="btn-add-place" href="/users/editor" class="btn btn-primary btn-cons">
										<i class="fa fa-plus"></i> Add New Comment
									</a>
								</div>
							</div>
							<div class="clearfix"></div>
                        </div>
                        <div class="card-block">
                            <table class="table table-hover demo-table-search table-responsive-block" id="">
				               <thead>
				                   <tr>
				                       <th>#</th>
				                       <th>Place</th>
				                       <th>Title</th>
				                       <th>Message</th>
				                       <th>User</th>
				                       <th>Language</th>
				                       <th>Status</th>
                                       <th></th>
				                       <th></th>
				                   </tr>
				               </thead>
				               <tbody>
				                   <#list comments! as comment>
				                   <tr id="" data-user-id="${ comment.id! }">
				                       <td><a class="btn btn-sm btn-primary" href="/comments/places/editor/${ comment.id! }" title="Edit">${ comment.id! }</a></td>
				                       <td class="v-align-middle font-weight-bold"><#if  comment.place?has_content>${ comment.place.name! }</#if></td>
				                       <td class="v-align-middle font-weight-bold">${ comment.title! }</td>
				                       <td class="v-align-middle">${ comment.message! }</td>
				                       <td class="v-align-middle">${ comment.status! }</td>
                                       <td class="v-align-middle">${ comment.language! }</td>
                                       <td class="v-align-middle"><#if comment.user?has_content>${ comment.user.id! }</#if></td>
                                       <td class="v-align-middle">
                                            <#list statuses as status>
                                                <#if status != comment.status! && status != 'NOTSET' &&  status != 'ALL'>
	                                            <button class="btn btn-xs btn-danger" onclick="updateCommentStatus(${ comment.id! },'${ status }');">${ status }</button>
                                                </#if>
                                            </#list>
                                       </td>
                                       <td class="v-align-middle"></td>                                      
                                       
<!-- 				                       <td class="v-align-middle"><a href="#" class="btn btn-tag">1</a></td> -->
                                       <td></td>
				                   </tr>
				                   </#list>
				               </tbody>
				           </table>
                        </div>
                    </div>

                </div>

            </div>
            <#include "/common/footer.ftl"> 
        </div>

    </div>


    <#include "/common/quickview.ftl">

    <#include "/common/builder.ftl">

    <#include "/common/search.ftl">
    

<#include "/common/javascripts.ftl" >
<script type="text/javascript">
function updateCommentStatus(id,status) {
    console.log(id, status);
    $.post( "/places/comments/" + id + "/status", { status: status } );
}

</script>
</body>

</html>