<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
	<!-- Page Properties -->
<#assign title>Events</#assign>
<#assign description></#assign>
<#assign category="home">
<#assign page="index">
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
                <div class=" container-fluid container-fixed-lg bg-white">
                    <div class="card card-transparent">
                        <div class="card-header ">
                            <div class="card-title">Events
                            </div>
                            <div class="pull-right">
                                <div class="col-12">
                                    <input type="text" id="search-table" class="form-control pull-right" placeholder="Search">
                                </div>
                            </div>
							<div class="pull-right">
								<div class="col-12">
									<a id="btn-add-event" href="/events/editor" class="btn btn-primary btn-cons">
										<i class="fa fa-plus"></i> Add New Event
									</a>
								</div>
							</div>
							<div class="clearfix"></div>
                            <form>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="form-group col-4">
                                                <label for="sel-eventType">Type</label>
                                                <select id="sel-eventType" name="types" class="full-width" data-init-plugin="select2" multiple placeholder="Choose">
                                                    <#list eventTypes as eventType>
                                                        <option value="${ eventType.id }" <#if (eventRequest?? && eventRequest.types?seq_contains(eventType.id?c)) ||  (!eventRequest?? && eventType == 'ALL')>selected</#if>>${ eventType }</option>
                                                    </#list>
                                                </select>
                                            </div>
                                            <div class="form-group col-4">
                                                <label for="sel-eventStatus">Status</label>
                                                <select id="sel-eventStatus" name="status" class="full-width" data-init-plugin="select2">
                                                    <#list eventStatuses as eventStatus>
                                                        <option value="${ eventStatus.id }" <#if eventRequest?? && eventRequest.status.id == eventStatus.id>selected</#if>>${ eventStatus }</option>
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
				                   <#list events! as event>
				                   <tr id="" data-event-id="${ event.id }">
				                       <td><a class="btn btn-sm btn-primary" href="/events/editor/${ event.id }" title="Edit">${ event.id }</a></td>
				                       <td class="v-align-middle font-weight-bold"><#if event.localisation?? && event.localisation['RUSSIAN']?has_content><a href="/events/${ event.id }/pages/ru" target="_blank">${ event.localisation['RUSSIAN'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if event.localisation?? && event.localisation['TURKISH']?has_content><a href="/events/${ event.id }/pages/tr" target="_blank">${ event.localisation['TURKISH'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if event.localisation?? && event.localisation['ENGLISH']?has_content><a href="/events/${ event.id }/pages/en" target="_blank">${ event.localisation['ENGLISH'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if event.localisation?? && event.localisation['GERMAN']?has_content><a href="/events/${ event.id }/pages/de" target="_blank">${ event.localisation['GERMAN'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if event.localisation?? && event.localisation['FARSI']?has_content><a href="/events/${ event.id }/pages/fa" target="_blank">${ event.localisation['FARSI'].name! }</a></#if></td>
                                       <td class="v-align-middle"><#if event.localisation?? && event.localisation['DUTCH']?has_content><a href="/events/${ event.id }/pages/nl" target="_blank">${ event.localisation['DUTCH'].name! }</a></#if></td>
				                       <td class="v-align-middle">${ event.type! }</td>
<!-- 				                       <td class="v-align-middle"><a href="#" class="btn btn-tag">1</a></td> -->
                                       <td></td>
				                   </tr>
<!-- 				                   <tr> -->
<!-- 				                        <td colspan="7"> -->
<!--                                             <div class="row"> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/events/${ event.id }/pages/tr" class="btn btn-tag">TR</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/events/${ event.id }/pages/ru" class="btn btn-tag">RU</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/events/${ event.id }/pages/en" class="btn btn-tag">EN</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/events/${ event.id }/pages/de" class="btn btn-tag">DE</a> -->
<!--                                                 </div> -->
<!--                                                 <div class="col-1"> -->
<!--                                                     <a href="/events/${ event.id }/pages/de" class="btn btn-tag">FA</a> -->
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