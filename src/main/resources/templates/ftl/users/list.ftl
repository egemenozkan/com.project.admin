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
										<i class="fa fa-plus"></i> Add New User
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
				                       <th>Username</th>
				                       <th>Email</th>
				                       <th>FirstName</th>
				                       <th>LastName</th>
                                       <th>Gender</th>
				                       <th></th>
				                   </tr>
				               </thead>
				               <tbody>
				                   <#list users! as user>
				                   <tr id="" data-user-id="${ user.id! }">
				                       <td><a class="btn btn-sm btn-primary" href="/users/editor/${ user.id! }" title="Edit">${ user.id! }</a></td>
				                       <td class="v-align-middle font-weight-bold">${ user.username! }</td>
				                       <td class="v-align-middle">${ user.email! }</td>
				                       <td class="v-align-middle">${ user.firstName! }</td>
                                       <td class="v-align-middle">${ user.lastName! }</td>
                                       <td class="v-align-middle">${ user.gender! }</td>
                                       <td class="v-align-middle">${ user.birthDate! }</td>
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

</body>

</html>