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
                            <div class="card-title">Places  - ${ place.name! } []
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
                                   </tr>
                               </thead>
                               <tbody>
                                <#list files! as file>
                                    <tr>
                                        <td>${ file.id } </td>
                                        <td><img src="${ file.url }" width="120" /></td>
                                        <td><#if file.createDateTime?has_content>${ file.createDateTime }</#if></td>
                                        <td><#if file.createDateTime?has_content>${ file.createDateTime }</#if></td>
                                        <td><#if file.user?has_content>${ file.user.id }</#if></td>
                                        <td><#if file.status?has_content>${ file.status }</#if></td>
                                    </tr>
                                </#list> 
                               </tbody>
                               </table>
                            </div>
							<div class="multiple-upload">
			                    <h3>Upload Multiple Files</h3>
			                    <form id="multipleUploadForm" name="multipleUploadForm">
			                        <input type="file" name="files" class="file-input" multiple required class="btn btn-secondary" />
			                        <input type="hidden" name="userId" value="1" />
			                        <input type="hidden" name="pageType" value="1" />
			                        <input type="hidden" name="pageId" value="${ place.id! }" />
			                        <button type="submit" class="btn btn-success">Submit</button>
			                    </form>
			                    <div class="upload-response">
			                        <div id="multipleFileUploadError"></div>
			                        <div id="multipleFileUploadSuccess"></div>
			                    </div>
			                </div>
						</div>
                    </div>

                </div>

            </div>
            <#include "/common/footer.ftl"> 
        </div>
        <script type="text/javascript">
//         var singleUploadForm = document.querySelector('#singleUploadForm');
//         var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
//         var singleFileUploadError = document.querySelector('#singleFileUploadError');
//         var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

        var multipleUploadForm = document.querySelector('#multipleUploadForm');
        var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
        var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
        var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

        function uploadSingleFile(file) {
            var formData = new FormData();
            formData.append("file", file);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/uploadFile");

            xhr.onload = function() {
                console.log(xhr.responseText);
                var response = JSON.parse(xhr.responseText);
                if(xhr.status == 200) {
                    singleFileUploadError.style.display = "none";
                    singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
                    singleFileUploadSuccess.style.display = "block";
                } else {
                    singleFileUploadSuccess.style.display = "none";
                    singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
                }
            }

            xhr.send(formData);
        }

        function uploadMultipleFiles(files, userId, pageType, pageId) {
            var formData = new FormData();
            for(var index = 0; index < files.length; index++) {
                formData.append("files", files[index]);
            }
            console.log(userId, pageType, pageId);
            formData.append("userId", userId);
            formData.append("pageType", pageType);
            formData.append("pageId", pageId);
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/uploadMultipleFiles");

            xhr.onload = function() {
                console.log(xhr.responseText);
                var response = JSON.parse(xhr.responseText);
                if(xhr.status == 200) {
                    multipleFileUploadError.style.display = "none";
                    var content = "<p>All Files Uploaded Successfully</p>";
                    for(var i = 0; i < response.length; i++) {
                        content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
                    }
                    multipleFileUploadSuccess.innerHTML = content;
                    multipleFileUploadSuccess.style.display = "block";
                } else {
                    multipleFileUploadSuccess.style.display = "none";
                    multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
                }
            }

            xhr.send(formData);
        }


//         singleUploadForm.addEventListener('submit', function(event){
//             var files = singleFileUploadInput.files;
//             if(files.length === 0) {
//                 singleFileUploadError.innerHTML = "Please select a file";
//                 singleFileUploadError.style.display = "block";
//             }
//             uploadSingleFile(files[0]);
//             event.preventDefault();
//         }, true);


        multipleUploadForm.addEventListener('submit', function(event){
            var files = document.getElementsByName("files")[0].files;
            var userId = document.getElementsByName("userId")[0].value;
            var pageType = document.getElementsByName("pageType")[0].value;
            var pageId = document.getElementsByName("pageId")[0].value;
console.log(files);
            if(files.length === 0) {
                multipleFileUploadError.innerHTML = "Please select at least one file";
                multipleFileUploadError.style.display = "block";
            }
            uploadMultipleFiles(files, userId, pageType, pageId);
            event.preventDefault();
        }, true);        
        
        </script>
    </div>


    <#include "/common/quickview.ftl">

    <#include "/common/builder.ftl">

    <#include "/common/search.ftl">
    

<#include "/common/javascripts.ftl" >


</body>

</html>