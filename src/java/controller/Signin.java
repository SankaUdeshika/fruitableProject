package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_Dto;
import dto.User_Dto;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
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

@WebServlet(name = "Signin", urlPatterns = {"/Signin"})
public class Signin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_Dto response_Dto = new Response_Dto();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        User_Dto user_Dto = gson.fromJson(request.getReader(), User_Dto.class);

        if (user_Dto.getEmail().isEmpty()) {
            response_Dto.setContent("Please Enter Your Email First");
        } else if (!Validation.isEmailValid(user_Dto.getEmail())) {
            response_Dto.setContent("Please Enter Valid  Email Address");
        } else if (user_Dto.getPassword().isEmpty()) {
            response_Dto.setContent("Please Enter Your password");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", user_Dto.getEmail()));
            criteria.add(Restrictions.eq("password", user_Dto.getPassword()));

            if (!criteria.list().isEmpty()) {
                User user = (User) criteria.list().get(0);

                if (!user.getVerificaiton().equals("verified")) {
//                    not Verified Person
                    request.getSession().setAttribute("email", user_Dto.getEmail());
                    response_Dto.setContent("unverified");
                } else {
//                    Verified Person
                    user.setEmail(user_Dto.getEmail());
                    user.setFname(user_Dto.getFname());
                    user.setLname(user_Dto.getLname());
                    user.setMobile(user_Dto.getMobile());
                    user.setPassword(null);
                    user.setAddress(user_Dto.getAddress());

                    request.getSession().setAttribute("user", user_Dto);

                    response_Dto.setContent("Login Success");
                    response_Dto.setSuccess(true);
                }

            } else {
                response_Dto.setContent("Invalid User Login Details, Please Try again later");

            }
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_Dto));
        System.out.println(gson.toJson(response_Dto));

    }

}
