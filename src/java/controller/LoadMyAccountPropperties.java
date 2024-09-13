/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import entity.Category;
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

        Criteria criteria1 = session.createCriteria(Category.class);
        criteria1.addOrder(Order.asc("category_id"));
        List<Category> categoryList = criteria1.list();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("categoryList", gson.toJsonTree(categoryList));
        
        System.out.println(gson.toJsonTree(categoryList));

        response.setContentType("applocaiton/json");
        response.getWriter().write(gson.toJson(jsonObject));
        session.close();

    }

}
