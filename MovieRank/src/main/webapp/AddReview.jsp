<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/addreview_style.css"%></style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> MovieRank </title>
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
	"I wanna add my review to the database."
	</div>
	<form action="reviews" method="post">
	    <div>
	        <input type="button" id="createReviewBtn" value="Add Review" onclick="createReview()">
	        <table border="1" id = "createReviewTable" style="display: none">
	            <tr>
	                <th>TitleId</th>
	                <th>Average Rating</th>
	                <th>Number Of Votes</th>
	            </tr>
	            <tr>
	                <td>
	                    <input id="title_id_for_review" name="title_id" value="${fn:escapeXml(param.title_id)}">
	                </td>
	                <td>
	                    <input id="avgRatingForReview" name="average_rating" value="${fn:escapeXml(param.average_rating)}">
	                </td>
	                <td>
	                    <input id="numOfVotes" name="num_votes" value="${fn:escapeXml(param.num_votes)}">
	                </td>
	                <td>
	                    <input type="submit" value = "Create!">
	                    <input type="hidden" name = "create" value="true">
	                    <input type="hidden" id = "prevRating" name = "prevRating" value="${fn:escapeXml(param.avgRating)}">
	                </td>
	            </tr>
	        </table>
	    </div>
	</form>
</body>
</html>
