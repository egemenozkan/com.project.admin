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
                            <div class="card-title">Multimedia
                            </div>
                            <div class="pull-right">
                                <div class="col-xs-12">
                                    <input type="text" id="search-table" class="form-control pull-right" placeholder="Search">
                                </div>
                            </div>
							<div class="pull-right">
								<div class="col-xs-12">
                                    <a id="btn-add-place" href="/places/editor" class="btn btn-primary btn-cons">
                                        <i class="fa fa-plus"></i> Add New Place
                                    </a>
								</div>
							</div>
							<div class="clearfix"></div>
                        </div>
                        <div class="card-block">
                            <div>
                               <table class="table table-hover demo-table-search table-responsive-block" id="">
                               <thead>
                                   <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                   </tr>
                               </thead>
                               <tbody>
                                <#list files! as file>
                                    <tr>
                                        <td>${ file.id } </td>
                                        <td><img src="${ file.url }" width="160" /></td>
                                        <td>${ file.pageId }</td>
                                        <td>${ file.pageType }</td>
                                        <td><#if file.createDateTime?has_content>${ file.createDateTime }</#if></td>
                                        <td><#if file.createDateTime?has_content>${ file.updateDateTime }</#if></td>
                                        <td><#if file.user?has_content>${ file.user.id }</#if></td>
                                        <td><#if file.status?has_content>${ file.status }</#if></td>
                                        <td><button class="btn-set-main-image" data-file-id="${ file.id }" data-page-id="${ file.pageId }" data-page-type="${ file.pageType }">Set Main Image</button></td>
                                    </tr>
                                </#list> 
                               </tbody>
                               </table>
                            </div>
						</div>
                    </div>

                </div>

            </div>
            <#include "/common/footer.ftl"> 
        </div>
        <script type="text/javascript">
        $(function () {
            $(".btn-set-main-image").click(function() {
                var fileId = $(this).data("file-id");
                var pageId = $(this).data("page-id");
                var pageType = 1;
                var url = "/places/" + pageId + "/main-image" 
                $.ajax({
                    'type': 'POST',
                    'url': url,
                    'data': {fileId : fileId},
                    'dataType': 'json',
                    'success': function (response) {
                        console.log(response);
                    }
                });
            })
        });

        </script>
    </div>


    <#include "/common/quickview.ftl">

    <#include "/common/builder.ftl">

    <#include "/common/search.ftl">
    

<#include "/common/javascripts.ftl" >


</body>

</html>