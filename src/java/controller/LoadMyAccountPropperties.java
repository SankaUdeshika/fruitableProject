/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import dto.User_Dto;
import entity.Category;
import entity.Product;
import entity.User;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "LoadMyAccountPropperties", urlPatterns = {"/LoadMyAccountPropperties"})
public class LoadMyAccountPropperties extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_Dto response_Dto = new Response_Dto();
        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();

//      Get Category List
        Criteria criteria1 = session.createCriteria(Category.class);
        criteria1.addOrder(Order.asc("category_id"));
        List<Category> categoryList = criteria1.list();
        System.out.println(categoryList);

//      Get Product List
        User_Dto user_Dto = (User_Dto) request.getSession().getAttribute("user");
        String loggedUserEmail = user_Dto.getEmail();
        User user = (User) session.get(User.class, loggedUserEmail);

        Criteria productCriteria = session.createCriteria(Product.class);
        productCriteria.add(Restrictions.eq("user", user));
        List<Product> productList = productCriteria.list();
        System.out.println(gson.toJsonTree(productList));
        //System.out.println(gson.toJsonTree(categoryList));

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("categoryList", gson.toJsonTree(categoryList));
        jsonObject.add("productList", gson.toJsonTree(productList));
//        System.out.println(gson.toJsonTree(categoryList));

        response.setContentType("applocaiton/json");
        response.getWriter().write(gson.toJson(jsonObject));
        session.close();

    }

}
