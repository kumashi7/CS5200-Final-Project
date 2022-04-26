<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/name_style.css"%></style>
<html>
<head>
    <title>Movie Rank by Group 2</title>
    <script type="text/javaScript">
        function showForm(title_id){
            document.getElementById("postUpdateEndYear" + title_id).style.display="block";
            document.getElementById("updateBtn" + title_id).style.display="none";
        }
        function createMovie(){
            document.getElementById("createMovieBtn").style.display="none";
            document.getElementById("createMovieTable").style.display="block";
        }
        function createReview(){
            document.getElementById("createReviewBtn").style.display="none";
            document.getElementById("createReviewTable").style.display="block";
        }
        function deleteConfirm(){
            if(confirm("Are you sure to delete this movie?")){
                document.getElementById("TrueDelete").click();
            }
        }
    </script>
</head>
<body>
	<nav>
	<a href="./index.jsp">Back to home</a>
	</nav>
	<div class="intro">
	"I wanna watch Scarlett Johansson's movie."
	</div>
	<form action="personName" method="get">
<%-- 	<h2>Search for Movies by Person's Name</h2>																																							   --%>
	    <div class="input">
	    <p>
	        <label for="person">Person's Name</label>
	        <input type="text" id="person" name="person" value="${fn:escapeXml(param.person)}"></input>
	    </p>
	    </div>
	    <p>
	        <input type="submit">
	        <br/><br/><br/>
	        <span id="successMessage"><b>${Message.Success}</b></span>
	    </p>
	</form>
		
    <h2>Movies with person ${param.person}:</h2>
    <table border="1">
        <tr>
            <th>TitleID</th>
            <th>PrimaryTitle</th>
            <th>PersonName</th>
            <th>StartYear</th>
            <th>EndYear</th>
            <th>Update End Year</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${movies}" var="movie" >
            <tr>
                <td><c:out value="${movie.getTitle_id()}" /></td>
                <td><c:out value="${movie.getPrimary_title()}" /></td>
                <td><c:out value="${movie.getPersonName()}" /></td>
                <td><c:out value="${movie.getStart_year()}" /></td>
                <td><c:out value="${movie.getEnd_year()}" /></td>
<%--                <td><fmt:formatDate value="${blogUser.getDob()}" pattern="yyyy-MM-dd"/></td>--%>
<%--                <td><a href="movies?update_end_year=true&title_id=<c:out value="${movie.getTitle_id()}"/>">Update End Year</a></td>--%>
<%--                <td><a href="blogcomments?username=<c:out value="${blogUser.getUserName()}"/>">BlogComments</a></td>--%>
                <td>
                    <input type="button" id="updateBtn${movie.getTitle_id()}" value="Update" onclick="showForm('${movie.getTitle_id()}')">
                    <form action="movies" method="post">
                        <p id="postUpdateEndYear${movie.getTitle_id()}" style="display: none;">
                            <label for="newEndYear">New End Year</label>
                            <input id="newEndYear" name="newEndYear" value="${fn:escapeXml(param.newEndYear)}">
                            <input type="submit" value = "Update">
                            <input type="hidden" name = "update_end_year" value = "true">
                            <input type="hidden" name = "title_id" value = ${movie.getTitle_id()}>
                        </p>
                    </form>
                </td>
                <td>

                    <input type="button" onclick="deleteConfirm('${movie.getTitle_id()}')" value = "Delete">
                    <form action="movies" method="post">
                        <p id="postDelete" style="display: none;">
                            <input type="hidden" name = "delete" value = "true">
                            <input type="hidden" name = 'title_id' value = ${movie.getTitle_id()}>
                            <input type="submit" id="TrueDelete" value = "TrueDelete">
                        </p>
                    </form>
                </td>
<%--                <td><a href="movies?delete=true&title_id=<c:out value="${movie.getTitle_id()}"/>">Delete</a></td>--%>
<%--                <td><a href="userupdate?username=<c:out value="${blogUser.getUserName()}"/>">Update</a></td>--%>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
