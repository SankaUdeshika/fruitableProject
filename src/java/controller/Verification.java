/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import dto.User_Dto;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Verification", urlPatterns = {"/Verification"})
public class Verification extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_Dto response_Dto = new Response_Dto();

        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        String VerificaitonCode = jsonObject.get("verificaiton").getAsString();

        if (request.getSession().getAttribute("email") != null) {

            Session session = HibernateUtil.getSessionFactory().openSession();

            String email = (String) request.getSession().getAttribute("email");

            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", email));
            criteria1.add(Restrictions.eq("verificaiton", VerificaitonCode));

            if (!criteria1.list().isEmpty()) {
                System.out.println("Verificaiton Process");
                User user = (User) criteria1.list().get(0);
                user.setVerificaiton("verified");
                session.update(user);

                session.beginTransaction().commit();

                User_Dto user_Dto = new User_Dto();
                user_Dto.setEmail(user.getEmail());
                user_Dto.setFname(user.getFname());
                user_Dto.setLname(user.getLname());
                user_Dto.setMobile(user.getMobile());
                user_Dto.setPassword(null);
                
                request.getSession().removeAttribute("email");
                request.getSession().setAttribute("user", user_Dto);
                
                
  

                response_Dto.setSuccess(true);
                response_Dto.setContent("Success");
            } else {
                response_Dto.setSuccess(false);
                response_Dto.setContent("Invalid Verificaitno Code");
            }
            session.close();
        }

        response.getWriter().write(gson.toJson(response_Dto));
        System.out.println(gson.toJson(response_Dto));
    }

}
