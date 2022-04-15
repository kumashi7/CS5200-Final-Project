package movierank.servlet;

import movierank.dal.MoviesDao;
import movierank.dal.RatingsDao;
import movierank.model.Movies;
import movierank.model.Ratings;
import movierank.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {

    private MoviesDao moviesDao;
    private RatingsDao ratingsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        moviesDao = MoviesDao.getInstance();
        ratingsDao = RatingsDao.getInstance();
        System.out.println(Constants.SQL_CONNECT_SUCCESS);
    }

    private void doPostOnCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title_id = req.getParameter(Constants.TITLE_ID);
        String average_rating = req.getParameter(Constants.AVERAGE_RATING);
        String num_votes = req.getParameter(Constants.NUM_VOTES);
        try {
            Movies movie = moviesDao.getMovieByTitleId(title_id);
            if(movie!=null) {
                ratingsDao.create(new Ratings(title_id, Double.valueOf(average_rating), Integer.valueOf(num_votes), movie));
            }else{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
//        req.setAttribute(Constants.CREATE + Constants._REVIEW, Constants.CREATE +  Constants._REVIEW + Constants.SUCC + title_id);
        System.out.println(Constants.CREATE_SUCCESS);

        String prevRating = req.getParameter(Constants.PREVIEW_RATING);
        System.out.println("Preview Rating: " + prevRating);
        if(prevRating!=null)
            resp.sendRedirect(req.getContextPath() + "/index.jsp?avgRating="+prevRating);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isCreated = req.getParameter(Constants.CREATE);
        if(isCreated != null && isCreated.equals("true")){
            doPostOnCreate(req, resp);
        }

    }

}
