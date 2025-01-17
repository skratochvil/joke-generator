

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class JokeGenerator
 */
@WebServlet("/JokeGenerator")
public class JokeGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public JokeGenerator() throws IOException {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 URL url = new URL("https://icanhazdadjoke.com/");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("User-Agent", "My Small Uni Project (https://github.com/skratochvil/joke-generator)");
	        
	        int status = connection.getResponseCode();
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader
	        		(connection.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	        content.append(inputLine);
	        }
	        in.close();
	        Gson g = new Gson();
	       
	        JokeContainer jokeContainer = g.fromJson(content.toString(), JokeContainer.class);
	        
	        System.out.println(jokeContainer.joke);
	        
	        ServletContext application=getServletContext(); 
	        application.setAttribute("joke", jokeContainer.joke);
	
	    response.getWriter().append("<H1>Welcome to the Joke Generator!</H1>");
	    response.getWriter().append("<H3>Powered by https://icanhazdadjoke.com/</H3>");
	        
		response.getWriter().append(jokeContainer.joke);
		response.getWriter().append("<p></p>");
		
		response.getWriter().append("<form method=\"post\" action=\"JokeGenerator\">");
		response.getWriter().append("<input type=\"submit\" value=\"Get a new joke!\" /></form>");
		
		response.getWriter().append("<form method=\"post\" action=\"SaveGoodJoke\">");
		response.getWriter().append("<input type=\"submit\" value=\"Save this Joke!\" /></form>");
		
		response.getWriter().append("<form method=\"post\" action=\"ViewJokes\">");
		response.getWriter().append("<input type=\"submit\" value=\"View Saved Jokes\" /></form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	class JokeContainer {
		String id;
		String joke;
		String status;
	}

}
