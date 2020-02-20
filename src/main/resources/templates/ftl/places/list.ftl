<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
	<!-- Page Properties -->
<#assign title>Places</#assign>
<#assign description></#assign>
<#assign category="home">
<#assign page="index">
<#assign 	styles=[]>
<#assign javascripts=[]>
<#assign bundle="page_list"> 

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
                            <div class="card-title">Places
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
                            <form>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="form-group col-3">
                                                <label for="sel-placeType">Type</label>
                                                <select id="sel-placeType" name="type" class="full-width" data-init-plugin="select2" placeholder="Choose">
                                                    <#list placeTypes as placeType>
                                                        <option value="${ placeType.id }" <#if placeRequest.type?? && placeRequest.type == placeType>selected</#if>>${ placeType }</option>
                                                    </#list>
                                                </select>
                                            </div>
                                            <div class="form-group col-3">
                                                <label for="sel-city">City</label>
                                                <select id="sel-city" name="city" class="full-width" data-init-plugin="select2"  placeholder="Choose">
                                                    <option value="0">Choose</option>
                                                    <#list cities as city>
                                                        <option value="${ city.id }" <#if placeRequest.cityId == city.id>selected</#if>>${ city.name }</option>
                                                    </#list>
                                                </select>
                                            </div>
                                            <div class="form-group col-3">
                                                <label for="sel-district">District</label>
                                                <select id="sel-district" name="district" class="full-width" data-init-plugin="select2"  placeholder="Choose">
                                                    <option value="0">Choose</option>
                                                    <#list districts! as district>
                                                        <#--  <option value="${ district.id }"  <#if placeRequest.districts!?seq_index_of(district.id) != -1>selected</#if>>${ district.name }</option>  -->
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <button type="submit" name="filter" value="true" class="btn-success btn-sm">Filter</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-block">
                            <table class="table table-hover demo-table-search table-responsive-block" id="">
				               <thead>
				                   <tr>
				                       <th>#</th>
				                       <td>Tools</td>
				                       <th>Russian</th>
				                       <th>Turkish</th>
				                       <th>English</th>
				                       <th>German</th>
                                       <th>Farsi</th>
                                       <th>Dutch</th>
				                       <th>Type</th>
				                       <th></th>
				                   </tr>
				               </thead>
				               <tbody>
				                   <#list places! as place>
				                   <tr id="" data-place-id="${ place.id?long?c }">
				                       <td><a class="btn btn-sm btn-primary" href="/places/editor/${ place.id?long?c }" title="Edit">${ place.id?long?c}</a></td>
                                       <td><a class="btn btn-sm btn-secondary" href="/places/multimedia/${ place.id?long?c }" title="Multimedia">Multimedia</a></td>
				                       <td class="v-align-middle font-weight-bold"><#if place.localisation?? && place.localisation['RUSSIAN']?has_content><a href="/places/${ place.id?long?c }/pages/ru" target="_blank">${ place.localisation['RUSSIAN'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if place.localisation?? && place.localisation['TURKISH']?has_content><a href="/places/${ place.id?long?c }/pages/tr" target="_blank">${ place.localisation['TURKISH'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if place.localisation?? && place.localisation['ENGLISH']?has_content><a href="/places/${ place.id?long?c }/pages/en" target="_blank">${ place.localisation['ENGLISH'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if place.localisation?? && place.localisation['GERMAN']?has_content><a href="/places/${ place.id?long?c }/pages/de" target="_blank">${ place.localisation['GERMAN'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if place.localisation?? && place.localisation['FARSI']?has_content><a href="/places/${ place.id?long?c }/pages/fa" target="_blank">${ place.localisation['FARSI'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if place.localisation?? && place.localisation['DUTCH']?has_content><a href="/places/${ place.id?long?c }/pages/nl" target="_blank">${ place.localisation['DUTCH'].name! }</a></#if></td>
				                       <td class="v-align-middle">${ place.type! }</td>
<!-- 				                       <td class="v-align-middle"><a href="#" class="btn btn-tag">1</a></td> -->
                                       <td></td>
				                   </tr>
<!-- 				                   <tr> -->
<!-- 				                        <td colspan="7"> -->
<!--                                             <div class="row"> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/places/${ place.id }/pages/tr" class="btn btn-tag">TR</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/places/${ place.id }/pages/ru" class="btn btn-tag">RU</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/places/${ place.id }/pages/en" class="btn btn-tag">EN</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/places/${ place.id }/pages/de" class="btn btn-tag">DE</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/places/${ place.id }/pages/de" class="btn btn-tag">FA</a> -->
<!--                                                 </div> -->
<!--                                             </div> -->
<!-- 				                        </td> -->
<!-- 				                   </tr> -->
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