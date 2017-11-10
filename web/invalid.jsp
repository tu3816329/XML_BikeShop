<%-- 
    Document   : Homepage
    Created on : Oct 26, 2017, 6:10:04 PM
    Author     : tudt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="pageCss.css" />
        <script type="text/javascript" src="pageScript.js"></script>
        <title>Trang chủ</title>

    </head>
    <body>

        <div class="hidden" id="loginDiv">
            <form id="myForm" action="ProcessServlet">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Tài khoản: </td>
                            <td> <input type="text" name="txtUsername" value="${txtUsername}" /> </td>
                        </tr>
                        <tr>
                            <td>Mật mã &nbsp;&nbsp;:</td>
                            <td> <input type="password" name="txtPassword" value="${txtPassword}" /> </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td> <input type="submit" value="Đăng nhập" name="btAction" />  <input type="reset" value="Hủy" style="float: right" onclick="backToPage()"/> </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="hidden" id="registerDiv">            
            hello word;
        </div>            
        <div class="body" id="bodyDiv">
            <div class="header" >
                <div class="headerCenter">                    
                    <div class="logo">                    
                        <a href="homepage.jsp" class="logo" style="float:left" ></a>
                    </div> 
                    <div class="links" style="float: left;">
                        <span style="color: red;font-size: 20px"><b> TIỆM XE THIÊN TỨ</b> </span>
                    </div> 
                    <c:if test="${empty sessionScope.user}">
                        <div class="links">
                            <a class="login" onclick="showLogin()">Đăng nhập&nbsp; / </a> &nbsp;<a class="login" onclick="showLogin()">Đăng ký </a>                        
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.user}">
                        Chào <h5>${sessionScope.user}</h5>/&nbsp;<a class="login" onclick="<c:remove var="user" scope="session"/>">Thoát</a>          
                    </c:if>
                </div>
                <div class="title">
                    <ul class="titleBar">
                        <div style="display: inline-block;float: left;width: 12%"><li> <a href="homepage.jsp">Trang chủ</a> </li></div>
                        <div style="display: inline-block;float: left;width: 12%" class="titleProduct">
                            <li>
                                <a href="#">Sản phẩm</a>
                                <x:parse var="doc" xml="${sessionScope.doc}"/>
                                <x:set var="brand" select="out" ></x:set>
                                <c:set var="typeName" value="out"/>
                                <div class="menu-brandName">
                                    <div class="center-text"> 
                                        <c:set var="i" value="1"/>
                                        <x:forEach var="result" select="$doc//*[local-name()='MotorType']" varStatus="counter">                                                                  
                                            <x:set var="brandName" select="string($result//*[local-name()='BrandName'])"/>
                                            <c:if test="${brandName!= brand}">
                                                <div class="titleBrand_${i}">
                                                    <div><a href="#"><p><c:out value="${brandName}" /></a></p></div>
                                                    <div class="menu-item_${i}">
                                                        <c:set var="brand" value="${brandName}"/>                                                    
                                                        <x:forEach var="type" select="$doc//*[local-name()='MotorType'][*[local-name()='BrandName']=$brand]">
                                                            <x:set var="nodeTypeName" select="string($type//*[local-name()='Type'])"/>
                                                            <c:if test="${typeName!=nodeTypeName}">
                                                                <c:set var="typeName" value="${nodeTypeName}"/>
                                                                <div class="item-wrap"> 
                                                                    <a href="#"><p>${nodeTypeName}</p></a>
                                                                    <table>
                                                                        <tbody>
                                                                            <x:forEach var="detail" select="$doc//*[local-name()='MotorType'][*[local-name()='BrandName']=$brand and *[local-name()='Type']=$typeName]" varStatus="count">
                                                                                <c:if test="${((count.count-1) mod 2) == 0}">
                                                                                    <tr>
                                                                                    </c:if>
                                                                                    <td>
                                                                                        <div class="item">                                             
                                                                                            <x:set var="id" select="string($detail//*[local-name()='Id'])"/>
                                                                                            <x:set var="previewImage" select="string($detail//*[local-name()='PreviewImage'])"/>
                                                                                            <a href="ProcessServlet?btAction=Detail&&id=${id}">
                                                                                                <img src="ReviewImages/${previewImage}"/>
                                                                                            </a>
                                                                                            <p class="itemName"><a href="ProcessServlet?btAction=Detail&&id=${id}"><x:out select="$detail//*[local-name()='Name']" /> </a></p>
                                                                                        </div>
                                                                                    </td>
                                                                                    <c:if test="${count.count mod 2 == 0}">
                                                                                    </tr>
                                                                                </c:if>
                                                                            </x:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </c:if>
                                                        </x:forEach>
                                                    </div>
                                                </div>
                                                <c:set var="i" value="${i+1}"/>
                                            </div>
                                        </c:if>
                                    </x:forEach>
                                </div>
                            </li>
                        </div>
                        <div style="display: inline-block;float: left;width: 10%" ><li><a href="#">Tin tức</a></li></div>
                        <div style="display: inline-block;float: left;width: 10%" ><li><a href="#">Tìm kiếm</a></li></div>
                        <form action="ProcessServlet">
                            <div style="display: inline-block;width: 10%"><li><select name="txtSearchOption" size="1">
                                        <option>Theo tin tức</option>
                                        <option>Theo sản phẩm</option>
                                    </select>  </li></div>
                            <div style="display: inline-block;width: 10%">
                                <li>
                                    <input type="search" name="txtSearch" value="" placeholder="Tìm kiếm" required="required"/>
                                    <input style="display: none" type="submit" name="btAction" value="Search"/>
                                    <!--<span class="validity" style="color: red"></span>-->
                                </li>
                            </div>
                            <div style="display: inline-block;"><li>
                                    <!--<input type="submit" value="Search" name="btAction">-->
                                    <!--<input type="text" name="btAction" value="" />-->
                                    <input type="image" src="PageImages/SearchLogo.png" alt="Search" width="15px" height="20px" style="margin-bottom: -7px;" />
                                    <!--<a href="ProcessServlet?btAction=Search&&txtSearch=${param.txtSearch}&&txtSearchOption=${param.txtSearchOption}" >-->
                                    <!--<img src="PageImages/SearchLogo.png" width="15px" height="20px" style="margin-bottom: -6px;"/>-->
                                    <!--</a>-->
                                    <!--</input>-->
                                </li></div>
                        </form>
                    </ul>
                </div>
            </div>
            <c:set var="newsList" value="${sessionScope.news}" />
            <div class="contentDiv">
                <h1>${requestScope.error}</h1>
            </div>
        </div>
    </body>
</html>
