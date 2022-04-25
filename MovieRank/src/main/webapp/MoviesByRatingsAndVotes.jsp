<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/ratingvotes_style.css"%></style>
<script type="text/javascript">
function showResult(){
    document.getElementById("result").style.display = "block";
}
</script>
<html>
<head>
	<title>Movies by Average Ratings and Number of Votes</title>
</head>
<body>
	<nav>
	<a href="/index.jsp">Back to home</a>
	</nav>
	<div class="intro">
	"I wanna watch a good movie that is also popular."
	</div>
	<form action=RatingsVotes method="get">
<!-- 		<h1>Search for a Movie by Average Ratings and Number of Votes</h1> -->
		<div class="input1">
		<p>
			<label for="average_rating">Rating</label>
			<input type="text" id="average_rating" name="average_rating" value="${fn:escapeXml(param.average_rating)}">
		</p>
		</div>
		<div class="input2">
		<p>
			<label for="num_votes">Number of Votes</label>
			<input type="text" id="num_votes" name="num_votes" value="${fn:escapeXml(param.num_votes)}">
		</p>
		</div>
		<p>
			<input type="submit" value="SUBMIT" onClick="showResult()">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<div id="result" style="display: block">
		<h2>Movies above ${messages.average_rating} and have more than ${messages.num_votes}</h2>
			<table border="1">
	            <tr>
	                <th>TitleId</th>
	                <th>Movie Name</th>
	                <th>Average Rating</th>
	                <th>Number Of Votes</th>
	            </tr>
	            <c:forEach items="${ratings}" var="ratings" >
	                <tr>
	                    <td><c:out value="${ratings.getTitleId()}" /></td>
	                    <td><c:out value="${ratings.getMovie().getPrimary_title()}" /></td>
	                    <td><c:out value="${ratings.getAverageRating()}" /></td>
	                    <td><c:out value="${ratings.getNumVotes()}" /></td>
	                </tr>
	            </c:forEach>
	       </table>
    </div>
</body>
</html>