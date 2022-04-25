package movierank.servlet;

import movierank.dal.RatingsDao;
import movierank.model.Ratings;
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

@WebServlet("/RatingsVotes")
public class RatingsVotesServlet extends HttpServlet {
	
    private RatingsDao ratingsDao;
	
    @Override
    public void init() throws ServletException {
        ratingsDao = RatingsDao.getInstance();
    	System.out.println(Constants.SQL_CONNECT_SUCCESS);
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
    	// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
    	
        // Retrieve and validate ratings & votes
        String average_rating = req.getParameter(Constants.AVERAGE_RATING);
        String num_votes = req.getParameter(Constants.NUM_VOTES);
        
        if (average_rating == null) {
        	messages.put("average_rating", "Invalid average rating. Please enter a number between 0.0 and 10.0");
        } else {
        	messages.put("average_rating", "Average rating: " + average_rating);
        }
        
        if (num_votes == null) {
        	messages.put("num_votes", "Invalid number of votes. Please enter a number");
        } else {
        	messages.put("num_votes", "Number of votes: " + num_votes);
        }
        
        // Retrieve Ratings and Movies
        List<Ratings> ratings = new ArrayList<Ratings>();
        try {
        	ratings = ratingsDao.getRatingsByRatingsAndVotes(Double.valueOf(average_rating), Integer.valueOf(num_votes));
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.setAttribute("ratings", ratings);
        req.getRequestDispatcher("/MoviesByRatingsAndVotes.jsp").forward(req, resp);
    }
	
}