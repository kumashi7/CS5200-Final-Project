<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<form action="movies" method="get">
    <h1>Search for Movies above Rating</h1>
    <p>
        <label for="avgRating">Rating</label>
        <input id="avgRating" name="avgRating" value="${fn:escapeXml(param.avgRating)}">
         Year：<input type="text" name="year" value="${fn:escapeXml(param.year)}"></input>
    	 Genre：<input type="text" name="type" value="${fn:escapeXml(param.type)}"></input>
    </p>
    <p>
        <input type="submit">
        <br/><br/><br/>
        <span id="successMessage"><b>${Message.Success}</b></span>
    </p>
</form>
<form action="movies" method="post">
    <div>
        <input type="button" id="createMovieBtn" value="Create Movie" onclick="createMovie()">
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
<%--<style>--%>
<%--    .hide { position:absolute; top:-1px; left:-1px; width:1px; height:1px; }--%>
<%--</style>--%>

<%--<iframe name="hiddenFrame" class="hide"></iframe>--%>
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
    <h1>Matching Movies</h1>
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
