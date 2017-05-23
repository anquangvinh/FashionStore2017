<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Blogs</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Blog Statistics</span>
                </h1>
            </div>
            <div class="col-lg-12">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th colspan="2" class="text-center fs-valign-middle" style="background: white"><h3 style="color: #337ab7"><b> Table statistics the number of blogs by category</b> </h3></th>
                        </tr>
                        <tr>
                            <th class="text-center fs-valign-middle" >Category</th>
                            <th class="text-center fs-valign-middle" >Number of blogs in the category</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${getAllNumberBlogsInCate}" var="blogscate">
                            <tr>
                                <td class="text-center fs-valign-middle" >
                                    ${blogscate.label}
                                </td>  
                                <td class="text-center fs-valign-middle" >
                                    ${blogscate.value}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <br>

            <div class="text-center">
                <h2> <span class="label label-info"> Total views in a category</span> </h2>
                <div id="donut-blog-chart" ></div>
            </div>
        </div>
    </div>
</div>


