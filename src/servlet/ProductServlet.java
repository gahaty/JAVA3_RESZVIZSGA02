package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import config.TemplateEngineUtil;
import entities.Product;
import repository.ProductsRepository;
import repository.SmallBasketRepository;
import service.ProductService;
import service.SmallBasketService;

@WebServlet(urlPatterns = { "/products", "/delete", "/edit" })

public class ProductServlet extends HttpServlet {

	private ProductService productService;
	private SmallBasketService smallBasketService;
	private ProductsRepository productRepository;

	public ProductServlet() {
		this.productService = new ProductService();
		this.smallBasketService = new SmallBasketService();
		this.productRepository = new ProductsRepository();
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
		context.setVariable("suppliers", smallBasketService.listAllSuppliers());
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
		String supplierPrice = request.getParameter("supplierPrice");
		
		String supplier = request.getParameter("supplier");
		String[] split = supplier.split(" ");
		
		Integer supplierId = Integer.parseInt(split[0]);
		Integer productId = null;
		
		Product product = new Product();
		product.setName(name);
		product.setType(type);
		product.setPrice(price);
		product.setSupplierId(supplierId);
		product.setSupplierPrice(supplierPrice);
		
		productService.addNewProducts(product);
		List<Product> findProductId = productRepository.findProductId();
		productId = findProductId.get(0).getId();
		System.out.println("productID:" + productId);
		
		product.setId(productId);
		productRepository.addNewSuppliersProduct(product);
		
		response.sendRedirect("/kisKosar/products");
	}
}
