package movierank.servlet;

import movierank.dal.MoviesDao;
import movierank.model.Movies;
import movierank.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/title")
public class TitleServlet extends HttpServlet {

    private MoviesDao moviesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
        System.out.println(Constants.SQL_CONNECT_SUCCESS);
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
        req.getRequestDispatcher("/MoviesByTitle.jsp").forward(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        String title = req.getParameter(Constants.TITLE);
        if (title != null && title.length() > 0) {
            doGetByTitle(req, resp);
        }
    }

}