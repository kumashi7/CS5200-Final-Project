<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/title_style.css"%></style>
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
	"I wanna watch 007 today."
	</div>
	<form action="title" method="get">
<%--    <h2>Search for Movies by Title</h2>                                                           																										    --%>
	    <div class="input">	    
	    <p>
	        <label for="title">Title</label>
	        <input type="text" id="title" name="title" value="${fn:escapeXml(param.title)}"></input>
	    </p>
	    </div>
	    <p>
	        <input class="submit" type="submit">
	    </p>
	</form>
	
    <h2>Movies containing word ${param.title} in title:</h2>
    <table border="1">
        <tr>
            <th>TitleID</th>
            <th>PrimaryTitle</th>
            <th>StartYear</th>
            <th>EndYear</th>
            <th>Update End Year</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${movies}" var="movie" >
            <tr>
                <td><c:out value="${movie.getTitle_id()}" /></td>
                <td><c:out value="${movie.getPrimary_title()}" /></td>
                <td><c:out value="${movie.getStart_year()}" /></td>
                <td><c:out value="${movie.getEnd_year()}" /></td>
				<td>
                    <input class="update" type="button" id="updateBtn${movie.getTitle_id()}" value="Update" onclick="showForm('${movie.getTitle_id()}')">
                    <form action="movies" method="post">
                        <p id="postUpdateEndYear${movie.getTitle_id()}" style="display: none;">
                            <label for="newEndYear">New End Year</label>
                            <input id="newEndYear" name="newEndYear" value="${fn:escapeXml(param.newEndYear)}">
                            <input class="doUpdate" type="submit" value = "Update">
                            <input type="hidden" name = "update_end_year" value = "true">
                            <input type="hidden" name = "title_id" value = ${movie.getTitle_id()}>
                        </p>
                    </form>
                </td>
                <td>

                    <input class="delete" type="button" onclick="deleteConfirm('${movie.getTitle_id()}')" value = "Delete">
                    <form action="movies" method="post">
                        <p id="postDelete" style="display: none;">
                            <input type="hidden" name = "delete" value = "true">
                            <input type="hidden" name = 'title_id' value = ${movie.getTitle_id()}>
                            <input type="submit" id="TrueDelete" value = "TrueDelete">
                        </p>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    
</body>
</html>
