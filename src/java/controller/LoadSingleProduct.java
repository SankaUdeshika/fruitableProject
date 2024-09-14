package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "LoadSingleProduct", urlPatterns = {"/LoadSingleProduct"})
public class LoadSingleProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String product_id = request.getParameter("id");
            
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Response_Dto response_Dto = new Response_Dto();

            if (Validation.isInteger(product_id)) {
                Product product = (Product) session.get(Product.class, Integer.parseInt(product_id)); // Single Product
                product.getUser().setPassword(null);
                product.getUser().setVerificaiton(null);
                product.getUser().setEmail(null);

                Criteria criteria = session.createCriteria(Product.class);
                criteria.add(Restrictions.eq("category", product.getCategory()));

                List<Product> ProductList = criteria.list();

                for (Product productItem : ProductList) {
                    productItem.getUser().setPassword(null);
                    productItem.getUser().setEmail(null);
                    productItem.getUser().setVerificaiton(null);
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.add("product", gson.toJsonTree(product));
                jsonObject.add("similarProducts", gson.toJsonTree(ProductList));

                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(jsonObject));
                System.out.println(gson.toJson(jsonObject));

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
