package movierank.servlet;

import movierank.dal.*;
import movierank.model.*;
import movierank.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/movies")
public class MoviesServlet extends HttpServlet {
    private MoviesDao moviesDao;
    private RatingsDao ratingsDao;
    private HttpServletRequest prevReq;
    private HttpServletResponse prevResp;
    private String prevRating;

    @Override
    public void init() throws ServletException {
        super.init();
        moviesDao = MoviesDao.getInstance();
        ratingsDao = RatingsDao.getInstance();
        System.out.println(Constants.SQL_CONNECT_SUCCESS);
    }

    private void doGetOnAverageRating(HttpServletRequest req, HttpServletResponse resp, String avgRating) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute(Constants.MESSAGE, messages);
        List<Movies> movies = new ArrayList<>();
        String year= req.getParameter("year");
        String type=req.getParameter("type");
        try {
        	if(avgRating.equals("")||avgRating==null) {
        		avgRating=null;
        	}
            List<Ratings> ratings = ratingsDao.getRatingsByAverageRating(avgRating,type,year);
            for(Ratings rating : ratings) {
            	
                Movies movie = moviesDao.getMovieByTitleId(rating.getTitleId(),year);
                if(movie!=null) movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messages.put(Constants.SUCCESS, "Displaying result for rating: " + avgRating + ", year: " + year + ", genre: " + type);
        req.setAttribute(Constants.MOVIE, movies);
        req.getRequestDispatcher(Constants.INDEX_PAGE).forward(req, resp);
    }
    
    private void doGetByTitle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute(Constants.MESSAGE, messages);
        List<Movies> movies = new ArrayList<>();
        String title = req.getParameter(Constants.TITLE);
        try {
        	movies = moviesDao.getMovieByTitleName(title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messages.put(Constants.SUCCESS, "Displaying result for name containing: " + title);
        req.setAttribute(Constants.MOVIE, movies);
        req.getRequestDispatcher(Constants.INDEX_PAGE).forward(req, resp);
    }
    
    private void doGetByPerson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute(Constants.MESSAGE, messages);
        List<Movies> movies = new ArrayList<>();
        String person = req.getParameter(Constants.PERSON);
        try {
        	movies = moviesDao.getMovieByPersonName(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messages.put(Constants.SUCCESS, "Displaying result for movies related to person: " + person);
        req.setAttribute(Constants.MOVIE, movies);
        req.getRequestDispatcher(Constants.INDEX_PAGE).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String avgRating = req.getParameter(Constants.AVG_RATING);
        String title = req.getParameter(Constants.TITLE);
        String person = req.getParameter(Constants.PERSON);
        if(avgRating != null && avgRating.length() > 0)  {
            prevRating = avgRating;
            prevReq = req;
            prevResp = resp;
            doGetOnAverageRating(req, resp, avgRating);
        } else if (title != null && title.length() > 0) {
            prevReq = req;
            prevResp = resp;
            doGetByTitle(req, resp);
        } else if (person != null && person.length() > 0) {
            prevReq = req;
            prevResp = resp;
            doGetByPerson(req, resp);
        }
    }

    private void doPostOnUpdateEndYear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newEndYear = req.getParameter(Constants.NEW_END_YEAR);
        String title_id = req.getParameter(Constants.TITLE_ID);
        Movies movie = null;
        try {
            movie = moviesDao.getMovieByTitleId(title_id,"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(null == newEndYear || null == title_id || null == movie) {
            System.out.println(Constants.ERROR_PARAMETER);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            moviesDao.updateMovieEndyear(movie, Integer.parseInt(newEndYear));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        doGet(prevReq, prevResp);
    }

    private void doPostOnDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title_id = req.getParameter(Constants.TITLE_ID);
        Movies movie = null;
        try {
            movie = moviesDao.getMovieByTitleId(title_id,"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(movie!=null) {
            try {
                moviesDao.delete(movie);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        }else resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        doGet(prevReq, prevResp);
    }

    private void doPostOnCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title_id = req.getParameter(Constants.TITLE_ID);
        String primary_title = req.getParameter(Constants.PRIMARY_TITLE);
        String title_type = req.getParameter(Constants.TITLE_TYPE);
        String original_title = req.getParameter(Constants.ORIGINAL_TITLE);
        Boolean is_adult = Boolean.valueOf(req.getParameter(Constants.IS_ADULT));
        String start_year = req.getParameter(Constants.START_YEAR);
        String end_year = req.getParameter(Constants.END_YEAR);
        String runtime_minutes = req.getParameter(Constants.RUNTIME_MINUTES);
        try {
            moviesDao.create(new Movies(title_id, primary_title, title_type, original_title, is_adult, Integer.valueOf(start_year), Integer.valueOf(end_year), Integer.valueOf(runtime_minutes)));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute(Constants.CREATE, Constants.CREATE + Constants.FAILED + title_id);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
        System.out.println(Constants.CREATE_SUCCESS);
        req.setAttribute(Constants.CREATE, Constants.CREATE + Constants.SUCC + title_id);
        doGet(prevReq, prevResp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isCreated = req.getParameter(Constants.CREATE);
        if(isCreated != null && isCreated.equals("true")){
            doPostOnCreate(req, resp);
        }

        String isDelete = req.getParameter(Constants.DELETE);
        if(isDelete != null && isDelete.equals("true")){
            doPostOnDelete(req, resp);
        }
        String updateEndYear = req.getParameter(Constants.UPDATE_END_YEAR);
        if(updateEndYear != null && updateEndYear.equals("true")) doPostOnUpdateEndYear(req, resp);
    }
}
