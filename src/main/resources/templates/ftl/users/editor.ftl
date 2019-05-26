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
                            <div class="card-title"><a href="/users/list">Users</a>
                            <h1>
<!--                             <#assign dynamic_url = "active_" + "places_" + "editor"> -->
                            
                            
                            </h1>
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
							<form role="form" method="POST" action="users/saveu">
							    <input type="hidden" name="id" value="${ user.id! }">
				                <div class="form-group">
                                    <label>Username </label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="username" placeholder="username" value="${ user.username! }" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Email </label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="email" placeholder="E-Mail" value="${ user.email! }" class="form-control">
                                    </div>
                                </div>                               
                                <div class="form-group">
                                    <label>FirstName </label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="firstName" placeholder="firstName" value="${ user.firstName! }" class="form-control">
                                    </div>
                                </div>
<!--                                 <div class="form-group"> -->
<!--                                     <label>LastName </label> -->
<!--                                     <span class="help"></span> -->
<!--                                     <div class="input-group"> -->
<!--                                         <span class="input-group-addon primary"> -->
<!--                                            <i class="fa fa-instagram"></i> -->
<!--                                         </span> -->
<!--                                         <input type="text" name="LastName" placeholder="LastName" value="${ user.LastName! }" class="form-control"> -->
<!--                                     </div> -->
<!--                                 </div>    -->
								<div class="form-group ">
								    <label>Place Type</label>
								    <select class="full-width" name="type" data-init-plugin="select2">
								        <#list placeTypes! as placeType>
								            <option ${ (place.type! == placeType)?then('selected','') }  value="${ placeType }">${ placeType }</option>
								        </#list>
								    </select>
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
<script>
$(function() {

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
    
    $('[name="address.lat"]').bind("paste", function(e){
        // access the clipboard using the api
        var pastedData = e.originalEvent.clipboardData.getData('text');
        var delimiterIndex = pastedData.indexOf(',');
        var done = false;
        if (delimiterIndex != -1) {
            setTimeout(function(){
                if (!done) {
                    $('[name="address.lat"]').val(pastedData.substr(0,delimiterIndex));
                    done = true;
                }
            },120);
            $('[name="address.lng"]').val(pastedData.substr(delimiterIndex + 1));
        }
    } );
    
})
</script>


</body>

</html>