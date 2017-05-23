<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>

<div class="space10"></div>

<!-- BLOG CONTENT -->
<div class="blog-content">
    <div class="container">
        <div class="row">
            <!-- Sidebar -->
            <aside class="col-md-3 col-sm-4">
                <div class="side-widget space50">
                    <h3><span>Search</span></h3>
                    <form role="form" class="search-widget" method="POST" action="blog-categories.html">
                        <input class="form-control" type="text" name="searchBlog"/>
                        <button type="submit"><i class="fa fa-search"></i></button>
                    </form>
                </div>
                <div class="side-widget space50">
                    <h3><span>Popular Post</span></h3>
                    <ul class="list-unstyled popular-post">
                        <c:forEach items="${PopularPosts}" var="blogpopularclient" begin="0" end="2" varStatus="no">
                            <li>
                                <div class="popular-img">
                                    <a href="blog-detail/${blogpopularclient.blogID}.html"> <img src="assets/images/blog/1/${blogpopularclient.blogImg}" class="img-responsive" alt=""></a>
                                </div>
                                <div class="popular-desc">
                                    <h5> <a href="blog-detail/${blogpopularclient.blogID}.html">${blogpopularclient.blogTitle}</a></h5>
                                    <span>By ${blogpopularclient.user.lastName} ${blogpopularclient.user.firstName}</span>
                                </div>
                            </li>
                        </c:forEach>

                    </ul>
                </div>
<!--                <div class="side-widget space50">
                    <h3><span>Archives</span></h3>
                    <ul class="list-unstyled cat-list">
                        <li> <a href="#">June 2015</a> <i class="icon-plus2"></i></li>
                        <li> <a href="#">May 2015</a> <i class="icon-plus2"></i></li>
                        <li> <a href="#">April 2015</a> <i class="icon-plus2"></i></li>
                    </ul>
                </div>-->
<!--                <div class="side-widget">
                    <h3><span>Tags</span></h3>
                    <ul class="widget-tags">
                        <li><a href="#">fashion</a></li>
                        <li><a href="#">sports</a></li>
                        <li><a href="#">business</a></li>
                        <li><a href="#">news</a></li>
                        <li><a href="#">night</a></li>
                        <li><a href="#">freedom</a></li>
                        <li><a href="#">design</a></li>
                        <li><a href="#">miracle</a></li>
                        <li><a href="#">gallery</a></li>
                        <li><a href="#">collection</a></li>
                        <li><a href="#">pen</a></li>
                        <li><a href="#">pants</a></li>
                        <li><a href="#">jeans</a></li>
                        <li><a href="#">photos</a></li>
                        <li><a href="#">oscar</a></li>
                        <li><a href="#">smile</a></li>
                        <li><a href="#">love</a></li>
                        <li><a href="#">sunshine</a></li>
                        <li><a href="#">luxury</a></li>
                        <li><a href="#">forever</a></li>
                        <li><a href="#">inlove</a></li>
                    </ul>
                </div>-->
            </aside>
            <div class="col-md-9 col-sm-8 blog-content">
                <div class="blog-single">
                    <article class="blogpost">
                        <h2 class="post-title">${getShowAllBlogsDetail.blogTitle}</h2>
                        <div class="post-meta">
                            <span><i class="fa fa-calendar"></i>&nbsp; ${getShowAllBlogsDetail.postedDate}</span>
                            <span><i class="fa fa-user"></i>&nbsp; ${getShowAllBlogsDetail.user.lastName} ${getShowAllBlogsDetail.user.firstName}</span>
                            <span><i class="fa fa-folder"></i><a href="blog-categories/${getShowAllBlogsDetail.blogCategory.blogCateID}.html">&nbsp; ${getShowAllBlogsDetail.blogCategory.blogCateName}</a></span>
                              <span><i class="fa fa-eye"></i>&nbsp; ${getShowAllBlogsDetail.blogViews}</span>
                        </div>
                        <div class="space30"></div>
                        <!-- Media Gallery -->
                        <!--                            <div class="post-media">
                                                        <div class="blog-slider">
                                                            <div class="item">						
                                                                <img src="assets/images/blog/1.jpg" class="img-responsive" alt="">
                                                            </div>
                                                            <div class="item">						
                                                                <img src="assets/images/blog/2.jpg" class="img-responsive" alt="">
                                                            </div>
                                                            <div class="item">						
                                                                <img src="assets/images/blog/3.jpg"  class="img-responsive" alt="">
                                                            </div>
                                                            <div class="item">						
                                                                <img src="assets/images/blog/4.jpg"  class="img-responsive" alt="">
                                                            </div>
                                                        </div>
                                                    </div>-->
                        <div class="item">						
                            <img src="assets/images/blog/1/${getShowAllBlogsDetail.blogImg}"  class="img-responsive" alt="">
                        </div>
                        <div class="space30"></div>
                        <p>
                            ${getShowAllBlogsDetail.blogSummary}
                        </p>
                        <p>
                            ${getShowAllBlogsDetail.content}
                        </p>
                        <!--                            <blockquote class="style1">
                                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Lorem ipsum dolor sit amet. Integer posuere erat a ante.</p>
                                                        <small><b>Author Name</b></small>
                                                    </blockquote>-->
                        <!--                            <p>
                                                        Praesent ultricies ut ipsum non laoreet. Nunc ac <a href="#">ultricies</a> leo. Nulla ac ultrices arcu. Nullam adipiscing lacus in consectetur posuere. Nunc malesuada tellus turpis, ac pretium orci molestie vel.
                                                        Morbi lacus massa, euismod ut turpis molestie, tristique sodales est. Integer sit amet mi id sapien tempor molestie in nec massa.
                                                        Fusce non ante sed lorem rutrum feugiat.
                                                    </p>
                                                    <p>
                                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris non laoreet dui. Morbi lacus massa, euismod ut turpis molestie, tristique sodales est. Integer sit amet mi id sapien tempor molestie in nec massa.
                                                        Fusce non ante sed lorem rutrum feugiat. Vestibulum pellentesque, purus ut&nbsp;dignissim consectetur, nulla erat ultrices purus, ut&nbsp;consequat sem elit non sem.
                                                    </p>-->
                    </article>
                </div>
                <!--                <div class="padding10">
                                    <h4 class="uppercase space30">Comments&nbsp;&nbsp;<span>(3)</span></h4>
                                    <ul class="comment-list">
                                        <li>
                                            <a class="pull-left" href="#"><img class="comment-avatar" src="assets/images/quote/1.png" alt="" height="50" width="50"></a>
                                            <div class="comment-meta">
                                                <a href="#">John Doe</a>
                                                <span>
                                                    <em>Feb 17, 2015, at 11:34</em>
                                                    <a href="#" class="button btn-xs reply"><i class="fa fa-comment"></i>&nbsp;Reply</a>
                                                </span>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor sit amet urna nec tempor. Nullam pellentesque in orci in luctus. Sed convallis tempor tellus a faucibus. Suspendisse et quam eu velit commodo tempus.
                                            </p>
                                        </li>
                                        <li class="comment-sub">
                                            <a class="pull-left" href="#"><img class="comment-avatar" src="assets/images/quote/2.png" alt="" height="50" width="50"></a>
                                            <div class="comment-meta">
                                                <a href="#">John Doe</a>
                                                <span>
                                                    <em>March 08, 2015, at 03:34</em>
                                                    <a href="#" class="button btn-xs reply"><i class="fa fa-comment"></i>&nbsp;Reply</a>
                                                </span>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor sit amet urna nec tempor. Nullam pellentesque in orci in luctus. Sed convallis tempor tellus a faucibus. Suspendisse et quam eu velit commodo tempus.
                                            </p>
                                        </li>
                                        <li>
                                            <a class="pull-left" href="#"><img class="comment-avatar" src="assets/images/quote/1.png" alt="" height="50" width="50"></a>
                                            <div class="comment-meta">
                                                <a href="#">John Doe</a>
                                                <span>
                                                    <em>June 11, 2015, at 07:34</em>
                                                    <a href="#" class="button btn-xs reply"><i class="fa fa-comment"></i>&nbsp;Reply</a>
                                                </span>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor sit amet urna nec tempor. Nullam pellentesque in orci in luctus. Sed convallis tempor tellus a faucibus. Suspendisse et quam eu velit commodo tempus.
                                            </p>
                                        </li>
                                    </ul>
                                </div>-->
                <!--                <div class="space30"></div>
                                <h4 class="uppercase space30">Leave a comment</h4>
                                <form method="post" action="#" id="form" role="form" class="form">
                                    <div class="row">
                                        <div class="col-md-6 space20">
                                            <input name="name" id="name" class="input-md form-control" placeholder="Name *" maxlength="100" required="" type="text">
                                        </div>
                                        <div class="col-md-6 space20">
                                            <input name="email" id="email" class="input-md form-control" placeholder="Email *" maxlength="100" required="" type="email">
                                        </div>
                                    </div>
                                    <div class="space20">
                                        <input name="website" id="website" class="input-md form-control" placeholder="Website" maxlength="100" required="" type="text">
                                    </div>
                                    <div class="space20">
                                        <textarea name="text" id="text" class="input-md form-control" rows="6" placeholder="Comment" maxlength="400"></textarea>
                                    </div>
                                    <button type="submit" class="btn-black">
                                        Submit Comment
                                    </button>
                                </form>-->
                <div class="space60"></div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix space20"></div>