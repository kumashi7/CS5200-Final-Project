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

@WebServlet("/personName")
public class PersonNameServlet extends HttpServlet {

    private MoviesDao moviesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
        System.out.println(Constants.SQL_CONNECT_SUCCESS);
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
        req.getRequestDispatcher("/MoviesByName.jsp").forward(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        String person = req.getParameter(Constants.PERSON);
        if (person != null && person.length() > 0) {
            doGetByPerson(req, resp);
        }
    }

}