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
import entities.Product;
import service.ProductService;

@WebServlet(urlPatterns = { "/products", "/delete", "/edit" })

public class ProductServlet extends HttpServlet {

	private ProductService productService;

	public ProductServlet() {
		this.productService = new ProductService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("productid: " + request.getParameter("productid"));
		System.out.println("ServletPath:" + request.getServletPath());

		if (request.getServletPath().equals("/delete")) {
			deleteUser(request, response);
		}

		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());
		context.setVariable("products", productService.listProducts());
		engine.process("products.html", context, response.getWriter());
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("productid"));
		try {
			this.productService.deleteUser(id);
		} catch (Exception e) {
			System.err.println("Error at upload: " + e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String price = request.getParameter("price");

		Product product = new Product();
		product.setName(name);
		product.setType(type);
		product.setPrice(price);
		
		productService.addNewProducts(product);
		response.sendRedirect("/kisKosar/products");
		

	}
}
