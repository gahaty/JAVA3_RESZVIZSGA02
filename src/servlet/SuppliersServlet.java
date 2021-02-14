package servlet;

import java.io.IOException;
import java.util.Objects;

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
import service.SuppliersService;

@WebServlet(urlPatterns = { "/suppliers", "/delete_supplier", "/edit_supplier" })
public class SuppliersServlet extends HttpServlet {

	private final String EMPTY_FIELDS = "empty_field";

	private SuppliersService suppliersService;

	public SuppliersServlet() {
		suppliersService = new SuppliersService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Suppliers editableSupplier = null;

		if (request.getServletPath().equals("/delete_supplier")) {
			int id = Integer.parseInt(request.getParameter("supplierid"));
			suppliersService.deleteSupplier(id);
		} else if (request.getServletPath().equals("/edit_supplier")) {
			int id = Integer.parseInt(request.getParameter("supplierid"));
			editableSupplier = suppliersService.findSupplierById(id);
		}

		if (editableSupplier == null) {
			editableSupplier = new Suppliers();
		}

		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());

		context.setVariable(EMPTY_FIELDS, request.getParameter(EMPTY_FIELDS));
		context.setVariable("suppliers", suppliersService.listAllSuppliers());
		context.setVariable("suppliers_edit", editableSupplier);
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
		String parameter = request.getParameter("id");
		if (StringUtils.isNotBlank(parameter)) {
			suppliers.setId(Integer.parseInt(parameter));
		}
		suppliers.setName(name);
		suppliers.setContact(contact);
		suppliers.setEmail(email);
		suppliers.setPhone(Integer.parseInt(phone));

		if (Objects.isNull(suppliers.getId())) {
			suppliersService.addNewSuppliers(suppliers);

		} else {
			suppliersService.updateSupplier(suppliers);
		}

		response.sendRedirect("/kisKosar/suppliers");
	}

}
