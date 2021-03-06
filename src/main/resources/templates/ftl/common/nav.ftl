    <nav class="page-sidebar" data-pages="sidebar">

        <div class="sidebar-overlay-slide from-top" id="appMenu">
            <div class="row">
                <div class="col-xs-6 no-padding">
                    <a href="datatables.html#" class="p-l-40"><img src="/assets/img/demo/social_app.svg" alt="socail">
                    </a>
                </div>
                <div class="col-xs-6 no-padding">
                    <a href="datatables.html#" class="p-l-10"><img src="/assets/img/demo/email_app.svg" alt="socail">
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6 m-t-20 no-padding">
                    <a href="datatables.html#" class="p-l-40"><img src="/assets/img/demo/calendar_app.svg" alt="socail">
                    </a>
                </div>
                <div class="col-xs-6 m-t-20 no-padding">
                    <a href="datatables.html#" class="p-l-10"><img src="/assets/img/demo/add_more.svg" alt="socail">
                    </a>
                </div>
            </div>
        </div>


        <div class="sidebar-header">
            <#--  <img src="/assets/img/logo_white.png" alt="logo" class="brand" data-src="/assets/img/logo_white.png" data-src-retina="/assets/img/logo_white_2x.png"
             width="78" height="22">  -->
            <div class="sidebar-header-controls">
                <button type="button" class="btn btn-xs sidebar-slide-toggle btn-link m-l-20 hidden-md-down" data-pages-toggle="#appMenu"><i
                     class="fa fa-angle-down fs-16"></i>
                </button>
                <button type="button" class="btn btn-link hidden-md-down" data-toggle-pin="sidebar"><i class="fa fs-12"></i>
                </button>
            </div>
        </div>


        <div class="sidebar-menu">

            <ul class="menu-items">
                <li class="m-t-30 ">
                    <a href="/" class="detailed">
                        <span class="title">Dashboard</span>
                        <#--  <span class="details">12 New Updates</span>  -->
                    </a>
                    <span class="bg-success icon-thumbnail"><i class="pg-home"></i></span>
                </li>
                <li class="">
                    <a href="/places/list" class="detailed">
                        <span class="title">Places</span>
                    </a>
                    <span class="icon-thumbnail"><i class="pg-map"></i></span>
                </li>
                <li class="">
                    <a href="/events/list" class="detailed">
                        <span class="title">Events</span>
                    </a>
                    <span class="icon-thumbnail"><i class="pg-calendar"></i></span>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="title">Places  List</span>
                        <span class=" arrow"></span>
                    </a>
                    <span class="icon-thumbnail"><i class="pg-map"></i></span>
                    <ul class="sub-menu">
                    <#list placeMainTypes as placeMainType>
                        <#if placeMainType != 'NOTSET'>
                        <li class="${ ("active_" + "places_" + "list_"+ placeMainType?lower_case + "")?eval!}">
                            <a href="javascript:;">
	                            <span class="title">${ placeMainType }</span>
                            </a>
                        <ul class="sub-menu">
                                                        <li class="${ ("active_" + "places_" + "list_"+ placeMainType?lower_case + "")?eval!}">
                                <a href="/places/list/${ placeMainType?lower_case }">All</a></li>
                        <#list placeTypes as placeType>

                            <#if placeType.mainType! == placeMainType! && placeType != 'ALL' && placeMainType != 'NOTSET'>
                                <li class="${ ("active_" + "places_" + "list_"+ placeMainType?lower_case + "_" + placeType?lower_case +"")?eval!}">
                                <a href="/places/list/${ placeMainType?lower_case }/${ placeType?lower_case }">${ placeType }</a></li>
                            </#if>
                        </#list>
                        </ul>
                        </li>
                        </#if>
                    </#list>
                    </ul>
               </li>
               <li class="">
                   <a href="/places/comments/list" class="detailed">
                       <span class="title">Comments</span>
                   </a>
                   <span class="icon-thumbnail"><i class="pg-comment"></i></span>
               </li>
               <li class="">
                   <a href="/users/list" class="detailed">
                       <span class="title">Users</span>
                   </a>
                   <span class="icon-thumbnail"><i class="pg-tables"></i></span>
               </li>
               <li class="">
                   <a href="/multimedia/list" class="detailed">
                       <span class="title">Multimedia</span>
                   </a>
                   <span class="icon-thumbnail"><i class="pg-tables"></i></span>
               </li>
            </ul>
            <div class="clearfix"></div>
        </div>

    </nav>