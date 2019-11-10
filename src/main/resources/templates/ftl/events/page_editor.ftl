<#ftl  encoding="utf-8" output_format="XML" auto_esc=true>
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
	<!-- Page Properties -->
<#assign title>Events</#assign>
<#assign description></#assign>
<#assign category="home">
<#assign PAGE="index">
<#assign 	styles=[]>
<#assign javascripts=[]>
<#assign bundle="index"> 

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
                            <div class="card-title">Events  - ${page.event.name!} [${page.event.language!}]
                            </div>
                            <div class="pull-right">
                                <div class="col-xs-12">
                                    <input type="text" id="search-table" class="form-control pull-right" placeholder="Search">
                                </div>
                            </div>
							<div class="pull-right">
								<div class="col-xs-12">
                                    <a id="btn-add-place" href="/events/editor" class="btn btn-primary btn-cons">
                                        <i class="fa fa-plus"></i> Add New Event
                                    </a>
								</div>
							</div>
							<div class="clearfix"></div>
                        </div>
                        <div class="card-block">
							<form role="form" method="POST" action="/events/pages">
							    <input type="hidden" name="id" value="${ page.id! }"> 
							    <input type="hidden" name="event.id" value="${ page.event.id! }"> 
							    <input type="hidden" name="language" value="${ page.language! }"> 
							    <div class="form-group">
                                    <label>Meta Title </label>
                                    <span class="help"></span>
								    <div class="input-group">
										<span class="input-group-addon primary">
										   <i class="fa fa-instagram"></i>
										</span>
										<input type="text" name="title" placeholder="Title" value="${ page.title! }" class="form-control">
									</div>
								</div>
                                <div class="form-group">
                                    <label>Meta Keywords</label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="keywords" placeholder="Keywords" value="${ page.keywords! }" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Meta Description</label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="description" placeholder="Description" value="${ page.description! }" class="form-control">
                                    </div>
                                </div>
                                <div class="summernote-wrapper">
                                    <input type="hidden" name="contents[0].id" value="<#if page.contents?? && page.contents[0]?has_content>${ page.contents[0].id! }</#if>">
                                    <textarea id="summernote" name="contents[0].text"><#if page.contents?? && page.contents[0]?has_content>${ page.contents[0].text! }</#if></textarea>
								</div>
							    <div>
							         <button class="btn btn-success" type="submit">Submit</button>
							    </div>
							</form>
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
<script src="/assets/plugins/summernote/js/summernote.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
    $('#summernote').summernote({
        height : 200,
        onfocus : function (e) {
            $('body').addClass('overlay-disabled');
        },
        onblur : function (e) {
            $('body').removeClass('overlay-disabled');
        }
    });
    var dateTimeParser = function (datetime) {
        if (typeof datetime !== "undefined" && datetime.length > 10) {
            datetime = datetime.trim();
            var splitted = datetime.split(' ');
            return {
                    date: splitted[0],
                    time: splitted[1]
            }
            
        } else {
            console.warn("fn:dateTimeParser - param datetime is undefined");
            return {
                date: "",
                time: ""
            }
        }
    }


    $.postJSON = function(url, data, callback) {
        return jQuery.ajax({
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        'type': 'POST',
        'url': url,
        'data': JSON.stringify(data),
        'dataType': 'json',
        xhrFields: { withCredentials: true }, 
        'success': callback
        });
    };

    var apiFeedByParent = function (parentSelector, url) {
        var $parent = $(parentSelector);
        var childSelectorName = $parent.attr("data-childSelector");
        
      //  console.log($parent, childSelectorName, url);
        var selectedId = $parent.val();
        var requestUrl = url.replace("{param}", selectedId);
            

        var selectCallback = function(response) {
            var list = "";
            if(!response) {
                list = '<option value="0">Veri yok</option>';
            }
            else {
                list = '<option value="0">Seçiniz</option>';
            }
            console.log("test-->",response);
            $.each(response, function(index){
                var content = '<option value="' + response[index].id + '"' + '>' + response[index].name + '</option>';
                list += content
            });
            $(childSelectorName).html(list);
            
            var grandChildSelectorName = $(childSelectorName).attr("data-childSelector");
            
            if (typeof grandChildSelectorName != 'undefined') {
                $(childSelectorName).change();
            }
      
        }
        
        $.ajax({url: requestUrl, xhrFields: { withCredentials: true }, 
  success: selectCallback
  });
        
        
    }
    
    $("#sel-city").change(function () {
        apiFeedByParent(this, "/cities/{param}/regions");
    });
    $("#sel-region").change(function () {
        apiFeedByParent(this, "/regions/{param}/subregions");
    });
    
})
</script>


</body>

</html>