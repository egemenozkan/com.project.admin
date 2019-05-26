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
                            <div class="card-title"><a href="/events/list">Events</a>
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
                                    <a id="btn-add-place" href="/events/editor" class="btn btn-primary btn-cons">
                                        <i class="fa fa-plus"></i> Add New Event
                                    </a>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="card-block">
                            <form role="form" method="POST" action="/events/savep">
                                <input type="hidden" name="id" value="${ event.id! }">
                                <#if event.language??>
                                <div class="form-group">
                                    <label>[${ event.language }] Name </label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="name" placeholder="name" value="${ event.name! }" class="form-control">
                                        <input type="hidden" name="language" value="${ event.language! }" >
                                    </div>
                                </div> 
                                </#if>
                                    <#list languages as language>
                                        <#if language == event.language! || language == 'ALL' || language == 'NOTSET' >
                                          <#continue>
                                        </#if>
                                        <div class="form-group">
                                            <label>[${language}] NAME</label>
                                            <span class="help"></span>
                                            <div class="input-group">
                                                <span class="input-group-addon primary">
                                                   <i class="fa fa-instagram"></i>
                                                </span>
                                                <input type="text" name="localisation['${language}'].name" placeholder="name" value="<#if event.localisation?? && event.localisation[language]?has_content>${ event.localisation[language].name }</#if>" class="form-control">
        <!--                                        <input type="hidden" name="localisation[0].language" value="TURKISH" > -->
                                                      <input type="hidden" name="localisation['${language}'].language" value="${ language }">
        
                                            </div>
                                        </div>
                                    </#list>
                                <div class="form-group ">
                                    <div class="row">
                                        <div class="col-6">
		                                    <label>Event Type</label>
		                                    <select class="full-width" name="type" data-init-plugin="select2">
		                                        <#list eventTypes! as eventType>
		                                            <option ${ (event.type! == eventType)?then('selected','') }  value="${ eventType }">${ eventType }</option>
		                                        </#list>
		                                    </select>
	                                    </div>
	                                    <div class="col-6">
                                            <label>Event Period Type</label>
                                            <select class="full-width" name="periodType" data-init-plugin="select2">
                                                <#list periodTypes! as periodType>
                                                    <option ${ (event.periodType! == periodType)?then('selected','') }  value="${ periodType }">${ periodType }</option>
                                                </#list>
                                            </select>
                                        </div>
	                                 </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-12">
                                            <label>Place</label>
											<input type="text" name="query" class="form-control autocomplete-place" value="<#if event.place??>${ event.place.name! }</#if>">
											<input type="hidden" name="place.id" value="<#if event.place??>${ event.place.id! }</#if>">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-6">
                                            <label>Start Date</label>
                                            <input type="text" class="form-control my-DATE" name="startDate" value="<#if event.startDate?has_content>${ event.startDate.format('dd.MM.yyyy') }</#if>">
                                        </div>
                                        <div class="col-6">
                                            <label>Start Time</label>
											<div class="input-group">
												<div class="input-group-prepend">
													<div class="input-group-text">
														<input type="checkbox" name="showStartTime" ${ event.showStartTime?then('checked','') }>
													</div>
												</div>
												<input type="text" class="form-control my-TIME" name="startTime" value="<#if event.startTime?has_content>${ event.startTime.format('HH:mm') }</#if>">
											</div>
                                        </div>
                                   </div>
                                   <div class="row">
                                        <div class="col-6">
                                            <label>End Date</label>
                                            <input type="text" name="endDate" class="form-control my-DATE" value="<#if event.endDate?has_content>${ event.endDate.format('dd.MM.yyyy') }</#if>">
                                        </div>
                                        <div class="col-6">
                                            <label>End Time</label>
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <div class="input-group-text">
                                                        <input type="checkbox" name="showEndTime" ${ event.showEndTime?then('checked','') }
                                                            aria-label="Checkbox for following text input">
                                                    </div>
                                                </div>
                                                <input type="text" name="endTime" class="form-control my-TIME" value="<#if event.endTime?has_content>${ event.endTime.format('HH:mm') }</#if>">
                                            </div>
                                            
                                        </div>                                      
                                    </div>
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


$(function () {
    function split( val ) {
        return val.split( /,\s*/ );
      }
      function extractLast( term ) {
        return split( term ).pop();
      }
   
      $(".autocomplete-place").autocomplete({
          source: function( request, response ) {
            $.getJSON( "/places/autocomplete", {
              query: extractLast( request.term )
            }, response );
          },
          focus: function( event, ui ) {
              $( "#project" ).val( ui.item.label );
              return false;
            },
            select: function( event, ui ) {
              $('[name="place.id"]').val( ui.item.id );
              $(this).val( ui.item.name );      
              return false;
            }
          })
          .autocomplete( "instance" )._renderItem = function( ul, item ) {
          console.log(item);
            return $( "<li>" )
              .append( "<div>" + item.type + " > "+ item.name + "</div>" )
              .appendTo( ul );
          };
    } ); 

</script>


</body>

</html>