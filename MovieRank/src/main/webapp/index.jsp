<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="/WEB-INF/css/index_style.css"%></style>
<html>
<head>
    <title>Movie Rank</title>
</head>
<body>
	<h1 class="title">Movie Rank</h1>
	<p class="intro">Search or modify movies database by.....</p>
	<nav>
	<a href="./AddMovie.jsp">Add Movies</a> |
	<a href="./MoviesByRYG.jsp">Search movies by Ratings/Year/Genre</a> |
	<a href="./MoviesByTitle.jsp">Search movies by Title</a> |
	<a href="./MoviesByName.jsp">Search movies by Person's name</a> |
	<a href="./MoviesByRatingsAndVotes.jsp">Search movies by Ratings and Votes</a> 
	</nav>
</body>
</html>
