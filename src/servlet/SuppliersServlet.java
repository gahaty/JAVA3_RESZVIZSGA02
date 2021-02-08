package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import config.TemplateEngineUtil;
import entities.Suppliers;
import service.SmallBasketService;

@WebServlet("/suppliers")
public class SuppliersServlet extends HttpServlet {

	private final String EMPTY_FIELDS = "empty_field";

	private SmallBasketService smallBasketService;

	public SuppliersServlet() {
		smallBasketService = new SmallBasketService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());

		context.setVariable(EMPTY_FIELDS, request.getParameter(EMPTY_FIELDS));
		context.setVariable("suppliers", smallBasketService.listAllSuppliers());
		engine.process("suppliers.html", context, response.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String contact = request.getParameter("contact");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		if (StringUtils.isAnyBlank(name, contact, email, phone)) {
			response.sendRedirect("/kisKosar/suppliers?" + EMPTY_FIELDS + "=true");
			return;
		}

		Suppliers suppliers = new Suppliers();
		suppliers.setName(name);
		suppliers.setContact(contact);
		suppliers.setEmail(email);
		suppliers.setPhone(phone);
		smallBasketService.addNewSuppliers(suppliers);
		System.out.println(suppliers.toString());
		response.sendRedirect("/kisKosar/suppliers");
	}
}
