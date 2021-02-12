package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import config.TemplateEngineUtil;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebContext context = new WebContext(request, response, request.getServletContext());
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		
		engine.process("index.html", context, response.getWriter());
	}
}
