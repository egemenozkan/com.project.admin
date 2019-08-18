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
                            <div class="card-title"><a href="/places/list">Places</a>
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
								<div class="col-lg-12">
                                    <a id="btn-add-place" href="/places/editor" class="btn btn-primary btn-cons">
                                        <i class="fa fa-plus"></i> Add New Place
                                    </a>
								</div>
							</div>
							<div class="clearfix"></div>
                        </div>
                        <div class="card-block">
							<form role="form" method="POST" action="/places/savep">
							    <input id="placeId" type="hidden" name="id" value="${ place.id?long?c! }">
							    <#if place.language??>
				                <div class="form-group">
                                    <label>[${ place.language }] Name </label>
                                    <span class="help"></span>
                                    <div class="input-group">
                                        <span class="input-group-addon primary">
                                           <i class="fa fa-instagram"></i>
                                        </span>
                                        <input type="text" name="name" placeholder="name" value="${ place.name! }" class="form-control">
                                        <input type="hidden" name="language" value="${ place.language! }" >
                                    </div>
                                </div> 
                                </#if>
								    <#list languages as language>
								        <#if language == place.language! || language == 'ALL' || language == 'NOTSET' >
									      <#continue>
										</#if>
									    <div class="form-group">
		                                    <label>[${language}] NAME</label>
		                                    <span class="help"></span>
										    <div class="input-group">
												<span class="input-group-addon primary">
												   <i class="fa fa-instagram"></i>
												</span>
												<input type="text" name="localisation['${language}'].name" placeholder="name" value="<#if place.localisation?? && place.localisation[language]?has_content>${ place.localisation[language].name }</#if>" class="form-control">
		<!-- 									    <input type="hidden" name="localisation[0].language" value="TURKISH" > -->
		                                              <input type="hidden" name="localisation['${language}'].language" value="${ language }">
		
											</div>
										</div>
									</#list>
								<div class="form-group ">
								    <label>Place Type</label>
								    <select class="full-width" name="type" data-init-plugin="select2">
								        <#list placeTypes! as placeType>
								            <option ${ (place.type! == placeType)?then('selected','') }  value="${ placeType }">${ placeType }</option>
								        </#list>
								    </select>
								</div>
                                <div class="form-group ">
                                    <div class="row">
                                        <div class="col-4">
		                                    <label>City</label>
		                                    <select id="sel-city" class="full-width" name="address.cityId" data-childSelector="#sel-region" data-init-plugin="select2">
		                                        <option>Seçiniz</option>
		                                        <#list cities! as city>
		                                            <option ${ (place.address.cityId! == city.id)?then('selected','') }  value="${ city.id }">${ city.name }</option>
		                                        </#list>
		                                    </select>                                        
                                        </div>
                                        <div class="col-4">
                                            <label>Region</label>
                                            <select id="sel-region" class="full-width" name="address.regionId" data-childSelector="#sel-subregion" data-init-plugin="select2">
                                                <#list regions! as region>
                                                    <option ${ (place.address.regionId! == region.id)?then('selected','') }  value="${ region.id }">${ region.name }</option>
                                                </#list>
                                            </select>                                        
                                        </div>
                                        <div class="col-4">
                                            <label>Subregion</label>
                                            <select id="sel-subregion" class="full-width" name="address.subregionId" data-init-plugin="select2">
                                                <#list subregions! as subregion>
                                                    <option ${ (place.address.subregionId! == subregion.id)?then('selected','') }  value="${ subregion.id }">${ subregion.name }</option>
                                                </#list>
                                            </select>                                        
                                        </div>                                                                                
                                    </div>

                                </div>
							    <div class="form-group">
							        <label>Address</label>
<!-- 							        <span class="help">e.g. "some@example.com"</span> -->
                                    <input type="hidden" name="address.id" value="${ place.address.id?long?c! }">
							        <input type="text" name="address.address" class="form-control" value="${ place.address.address! }" placeholder="" required="">
							    </div>
							    <div class="form-group">
							        <label>Post Code</label>
<!-- 							        <span class="help">e.g. "some@example.com"</span> -->
                                    <input type="text" name="address.postCode" class="form-control" value="${ place.address.postCode! }" placeholder="" required="">
							    </div>
							    <div class="form-group">
							        <div class="row">
							             <div class="col-6">
	                                        <label>Lattitude</label>
	    <!--                                    <span class="help">e.g. "some@example.com"</span> -->
	                                        <input type="text" name="address.lat" class="form-control" value="<#if place.address.lat?has_content>#{place.address.lat}</#if>">							             
							             </div>
                                         <div class="col-6">
                                            <label>Longitude</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="text" name="address.lng" class="form-control" value="<#if place.address.lng?has_content>#{place.address.lng}</#if>">                                        
                                         </div>
							        </div>
							    </div>
							     <div class="form-group">
                                    <div class="row">
                                         <div class="col-6">
                                            <label>Phone</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="hidden" name="contact.id" value="<#if place.contact?has_content>${ place.contact.id! }<#else>0</#if>">                                        
                                            <input type="text" name="contact.phone" class="form-control my-PHONE" value="<#if place.contact?has_content>${ place.contact.phone! }</#if>">                                        
                                         </div>
                                         <div class="col-6">
                                            <label>Call Center</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="text" name="contact.callCenter" class="form-control my-PHONE7" value="<#if place.contact?has_content>${ place.contact.callCenter! }</#if>">                                
                                         </div>
                                    </div>
                                </div>
                                 <div class="form-group">
                                    <div class="row">
                                         <div class="col-6">
                                            <label>WhatsApp</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="text" name="contact.whatsapp" class="form-control my-PHONE" value="<#if place.contact?has_content>${ place.contact.whatsapp! }</#if>">                                        
                                         </div>
                                         <div class="col-6">
                                            <label>Web</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="text" name="contact.web" class="form-control" value="<#if place.contact?has_content>${ place.contact.web! }</#if>">                                
                                         </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                         <div class="col-6">
                                            <label>E-mail</label>
        <!--                                    <span class="help">e.g. "some@example.com"</span> -->
                                            <input type="text" name="contact.email" class="form-control" value="<#if place.contact?has_content>${ place.contact.email! }</#if>">                                        
                                         </div>
                                         <div class="col-6">                             
                                         </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <!-- #appTimeTable -->
                                        <div id="appTimeTable">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th colspan="10">Place Rows 1</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr v-for="(tm, index) in rows">
                                                        <td>{{ tm.id }}</td>
                                                        <td>
                                                            <select class="form-control" v-model="tm.periodType">
                                                                <#list periodTypes! as periodType>
                                                                    <option>${ periodType }</option>
                                                                </#list>
                                                            </select>
                                                        </td>
                                                        <td>
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="custom-control custom-checkbox">
                                                                        <input type="checkbox" class="custom-control-input" id="customCheck{{index}}" v-model="tm.twentyFourSeven">
                                                                        <label class="custom-control-label" for="customCheck{{index}}">7/24</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <input type="checkbox" v-model="tm.showStartDate">
                                                                    </div>
                                                                </div>
                                                                <input type="text" class="form-control my-DATE" v-model="tm.startDate">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <input type="checkbox" v-model="tm.showEndDate">
                                                                    </div>
                                                                </div>
                                                                <input type="text" class="form-control my-DATE" v-model="tm.endDate">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <input type="checkbox" v-model="tm.showStartTime">
                                                                    </div>
                                                                </div>
                                                                <input type="text" class="form-control my-TIME" v-model="tm.startTime">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <input type="checkbox" v-model="tm.showEndTime">
                                                                    </div>
                                                                </div>
                                                                <input type="text" class="form-control my-TIME" v-model="tm.endTime">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-sm btn-success" @click="save(index)">Save</button>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-sm btn-danger" @click="remove(index)">Delete</button>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-sm btn-warning" @click="add">New</button>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- END of #appTimeTable -->
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
</script>
<script>
        var blankTimeTable = {"id":0,"pageId":document.getElementById("placeId").value,"pageType":"PLACE","periodType":"MONDAYS","startDate":"","startTime":"","endDate":"","endTime":"","showStartTime":true,"showEndTime":true, "twentyFourSeven": false, "showStartDate":true,"showEndDate":true};

        function dateFormat(date) {
            if (typeof date != 'undefined' && date != null && date.length == 10) {
                return date.split("-")[2] + '.' + date.split("-")[1] + '.' + date.split("-")[0]
            }
            return "";
        }

        function filterTimeTable(data) {
            if (typeof data == 'undefined' || data == null || data.length == 0) {
                return [blankTimeTable];
            }

            return data.map(item => {
                item.startDate = dateFormat(item.startDate);
                item.endDate = dateFormat(item.endDate);
                return item;
            })
        }

        var app = new Vue({
                el: '#appTimeTable',
                data () {
                    return {
                        placeId: '',
                        rows: []
                    }
                },
                mounted () {
                   var self = this;
                   self.placeId = document.getElementById("placeId").value;
                   const requestUrl = '/places/' +  self.placeId + '/time-table';
                    axios
                    .get(requestUrl)
                    .then(response => (this.rows = filterTimeTable(response.data)))
                },
                methods: {
                    save: function (index) {
                        const requestUrl = "/places/time-table";
                        console.log(index);
                        axios.post(requestUrl, this.rows[index])
                        .then(function (response) {
                            console.log(response);
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    },
                    add : function () {
                        this.rows.push(blankTimeTable);
                    },
                    remove : function (index) {
                        
                        var self = this;
                        const requestUrl = '/places/time-table/' +  self.rows[index].id;
                        axios.delete(requestUrl).then(function (response) {
                            self.rows.splice(index, 1);
                            console.log(response);
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    }

                }
            }) 
        </script>

</body>

</html>