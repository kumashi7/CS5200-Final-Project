<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/addmovie_style.css"%></style>
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
	"I wanna add a movie that is missing is the database."
	</div>
	<form action="movies" method="post">
	    <div>
	        <input class="create" type="button" id="createMovieBtn" value="Create Movie" onclick="createMovie()">
	        <table border="1" id = "createMovieTable" style="display: none">
	            <tr>
	                <th>TitleId</th>
	                <th>Primary Title</th>
	                <th>Title Type</th>
	                <th>Original Title</th>
	                <th>IsAdult</th>
	                <th>Start Year</th>
	                <th>End Year</th>
	                <th>Run Time Minutes</th>
	            </tr>
	            <tr>
	                <td>
	                    <input id="title_id" name="title_id" value="${fn:escapeXml(param.title_id)}">
	                </td>
	                <td>
	                    <input id="primary_title" name="primary_title" value="${fn:escapeXml(param.primary_title)}">
	                </td>
	                <td>
	                    <input id="title_type" name="title_type" value="${fn:escapeXml(param.title_type)}">
	                </td>
	                <td>
	                    <input id="original_title" name="original_title" value="${fn:escapeXml(param.original_title)}">
	                </td>
	                <td>
	                    <input type="checkbox" id="is_Adult" name="is_Adult" value="${fn:escapeXml(param.is_Adult)}">
	                </td>
	                <td>
	                    <input id="start_year" name="start_year" value="${fn:escapeXml(param.start_year)}">
	                </td>
	                <td>
	                    <input id="end_year" name="end_year" value="${fn:escapeXml(param.end_year)}">
	                </td>
	                <td>
	                    <input id="runtime_minutes" name="runtime_minutes" value="${fn:escapeXml(param.runtime_minutes)}">
	                </td>
	                <td>
	                    <input type="submit" value = "Create!">
	                    <input type="hidden" name = "create" value="true">
	                </td>
	            </tr>
	        </table>
	        <span id="createSuccess"><b>${create}</b></span>
	    </div>
	</form>
    
</body>
</html>
