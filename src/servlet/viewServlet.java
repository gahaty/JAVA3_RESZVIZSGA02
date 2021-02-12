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
import entities.View;
import service.ViewService;


@WebServlet("/view")
public class viewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ViewService viewService;

    public viewServlet() {
       this.viewService = new ViewService();
    }
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());
		
		System.out.println("productid: " + request.getParameter("productid"));
		System.out.println("ServletPath:" + request.getServletPath());
		
		View viewList = viewService.viewList(Integer.parseInt(request.getParameter("productid")));
		System.out.println(viewList);

		context.setVariable("viewList", viewList);
		engine.process("view.html", context, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
