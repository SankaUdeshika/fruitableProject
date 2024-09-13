package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import model.Mail;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "Signup", urlPatterns = {"/Signup"})
public class Signup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Response_Dto response_Dto = new Response_Dto();

        User_Dto user_Dto = gson.fromJson(request.getReader(), User_Dto.class);

//        validation
        if (user_Dto.getFname().isEmpty()) {
            System.out.println("Please Enter Your First Name");
        } else if (user_Dto.getLname().isEmpty()) {
            System.out.println("Please Enter Your Last Name");
        } else if (user_Dto.getEmail().isEmpty()) {
            System.out.println("Please Enter Your Email");
        } else if (!Validation.isEmailValid(user_Dto.getEmail())) {
            System.out.println("Please Enter Valid Email Address");
        } else if (user_Dto.getMobile().isEmpty()) {
            System.out.println("Please Enter Mobile number");
        } else if (!Validation.IsMobileValid(user_Dto.getMobile())) {
            System.out.println("Please Enter valid mobile number");
        } else if (user_Dto.getPassword().isEmpty()) {
            System.out.println("Please Enter Valid Password");
        } else if (!Validation.ispasswordValid(user_Dto.getPassword())) {
            System.out.println("Please Enter Valid Password");
        } else {

//            Insert
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_Dto.getEmail()));

            if (!criteria1.list().isEmpty()) {

                System.out.println("Already have a user");
                response_Dto.setSuccess(false);
                response_Dto.setContent("Already Have A user");

            } else {
                System.out.println("New User Registerd");

//                verification ID
                int code = (int) (Math.random() * 10000);

                User user = new User();
                user.setFname(user_Dto.getFname());
                user.setLname(user_Dto.getLname());
                user.setEmail(user_Dto.getEmail());
                user.setMobile(user_Dto.getMobile());
                user.setPassword(user_Dto.getPassword());
                user.setAddress(user_Dto.getAddress());
                user.setVerificaiton(String.valueOf(code));

                session.save(user);
                session.beginTransaction().commit();

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        Mail.sendMail(user_Dto.getEmail(), "Verifiaction Code", "<h1>Your Verificaiton ID is'" + code + "'</h1>");
                        System.out.println("Send EMail");
                    }

                };
                t.start();

                response_Dto.setSuccess(true);
                response_Dto.setContent("Registerd Success. Please Verify Your Accout");
                System.out.println("Insert Success");
            }
            session.close();
        }
        response.setContentType("applicaiton/json");
        response.getWriter().write(gson.toJson(response_Dto));
        System.out.println(gson.toJson(user_Dto));
    }

}
