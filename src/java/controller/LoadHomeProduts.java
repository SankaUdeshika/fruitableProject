/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import entity.Category;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "LoadHomeProduts", urlPatterns = {"/LoadHomeProduts"})
public class LoadHomeProduts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        Response_Dto response_Dto = new Response_Dto();
        response_Dto.setContent("Something worong Please try again later");
        response_Dto.setSuccess(false);
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria productCriteria = session.createCriteria(Product.class);
        if (type.equals("All")) {
            productCriteria = session.createCriteria(Product.class);
        } else if (type.equals("veg")) {
            Category category = (Category) session.get(Category.class, 1);
            productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("category", category));
        } else if (type.equals("fruit")) {
            Category category = (Category) session.get(Category.class, 2);
            productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("category", category));
        } else if (type.equals("bread")) {
            Category category = (Category) session.get(Category.class, 3);
            productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("category", category));
        } else if (type.equals("meat")) {
            Category category = (Category) session.get(Category.class, 4);
            productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("category", category));
        }

        if (productCriteria.list().isEmpty()) {
            System.out.println("Empty");
            response_Dto.setContent("Error");
            response_Dto.setSuccess(false);
            jsonObject.addProperty("response_Dto", gson.toJson(response_Dto));
        } else {
            List<Product> productList = productCriteria.list();

            response_Dto.setContent("Success");
            response_Dto.setSuccess(true);

            jsonObject.addProperty("response_Dto", gson.toJson(response_Dto));
            jsonObject.add("productList", gson.toJsonTree(productList));

        }
        System.out.println(gson.toJson(jsonObject.get("productList")));
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

    }

}
