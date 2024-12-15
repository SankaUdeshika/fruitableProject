/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import dto.User_Dto;
import entity.Cart;
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "LoadCartDetails", urlPatterns = {"/LoadCartDetails"})
public class LoadCartDetails extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Response_Dto response_Dto = new Response_Dto();
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();

//        response_Dto.setContent("Something wrong, Please try again later");
//        response_Dto.setSuccess(false);
        User_Dto user_Dto = (User_Dto) request.getSession().getAttribute("user");
        
        try {
            if (user_Dto == null) {
                response_Dto.setContent("login");
                response_Dto.setSuccess(false);
                
            } else {
                
                Session session = HibernateUtil.getSessionFactory().openSession();
                
                User user = (User) session.get(User.class, user_Dto.getEmail());
                
                Criteria criteria = session.createCriteria(Cart.class);
                criteria.add(Restrictions.eq("user", user));
                
                if (criteria.list().isEmpty()) {
                    System.out.println("empty");
                } else {
                    List<Cart> cartList = criteria.list();
                    jsonObject.add("cartList", gson.toJsonTree(cartList));
                    response_Dto.setContent("success");
                    response_Dto.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response_Dto.setContent("login");
            response_Dto.setSuccess(false);
        }
        
        jsonObject.addProperty("response_dto", gson.toJson(response_Dto));

//        set response
        System.out.println(gson.toJson(jsonObject));
        response.setContentType("applicaiton/json");
        response.getWriter().write(gson.toJson(jsonObject));
        
    }
}
