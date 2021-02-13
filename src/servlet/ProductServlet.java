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
import entities.Product;
import repository.ProductsRepository;
import service.ProductService;
import service.SuppliersService;


@WebServlet(urlPatterns = { "/products", "/delete", "/edit" })

public class ProductServlet extends HttpServlet {

	private final String MISSING_FIELDS = "missing_field";

	private ProductService productService;
	private SuppliersService suppliersService;
	private ProductsRepository productRepository;

	public ProductServlet() {
		this.productService = new ProductService();
		this.suppliersService = new SuppliersService();
		this.productRepository = new ProductsRepository();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());

		System.out.println("productid: " + request.getParameter("productid"));
		System.out.println("ServletPath:" + request.getServletPath());

		Product editableProduct = null;

		if (request.getServletPath().equals("/delete")) {
			int id = Integer.parseInt(request.getParameter("productid"));
			this.productService.deleteSuppliersProduct(id);
			this.productService.deleteProduct(id);
		} else if (request.getServletPath().equals("/edit")) {
			int id = Integer.parseInt(request.getParameter("productid"));
			editableProduct = productService.findProductById(id);
		}

		if (editableProduct == null) {
			editableProduct = new Product();
		}

		context.setVariable(MISSING_FIELDS, request.getParameter(MISSING_FIELDS));
		context.setVariable("products", productService.listProducts());
		context.setVariable("suppliers", suppliersService.listAllSuppliers());
		context.setVariable("productEdit", editableProduct);
		engine.process("products.html", context, response.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String price = request.getParameter("price");
		String supplierPrice = request.getParameter("supplierPrice");

		if (StringUtils.isBlank(name) || StringUtils.isBlank(type) || StringUtils.isBlank(price)
				|| StringUtils.isBlank(supplierPrice)) {
			response.sendRedirect("/kisKosar/products?" + MISSING_FIELDS + "=true");
			return;
		}

		String supplier = request.getParameter("supplier");
		String[] split = supplier.split(" ");

		Integer supplierId = Integer.parseInt(split[0]);
		Integer productId = null;

		Product product = new Product();
		String parameter = request.getParameter("id");
		if (StringUtils.isNotBlank(parameter)) {
			product.setId(Integer.parseInt(parameter));
		}
		product.setName(name);
		product.setType(type);
		product.setPrice(price);
		product.setSupplierId(supplierId);
		product.setSupplierPrice(supplierPrice);

		if (Objects.isNull(product.getId())) {
			productService.addNewProducts(product);
			Product findProductId = productService.findProductIdByMax();
			productId = findProductId.getId();
			System.out.println("productID:" + productId);
			product.setId(productId);
			productRepository.addNewSuppliersProduct(product);
		} else {
			productService.updateProduct(product);
			productService.updateSuppliersProduct(product);
		}
		response.sendRedirect("/kisKosar/products");
	}
}
