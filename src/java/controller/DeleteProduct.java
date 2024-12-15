/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_Dto;
import entity.Product;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
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
@WebServlet(name = "DeleteProduct", urlPatterns = {"/DeleteProduct"})
public class DeleteProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String product_id = request.getParameter("pid");

        Response_Dto response_Dto = new Response_Dto();
        response_Dto.setContent("something wrong, Please Try again later");
        response_Dto.setSuccess(false);

        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = (Product) session.get(Product.class, Integer.parseInt(product_id));

        if (product != null) {
            try {
                session.delete(product);
                session.beginTransaction().commit();
                System.out.println("Delete Success");
                String applicationPath = request.getServletContext().getRealPath("");
                File folder = new File(applicationPath + File.separator + "product-images" + File.separator + product_id);
                File file1 = new File(folder, "image1.jpg");
                File file2 = new File(folder, "image2.jpg");
                File file3 = new File(folder, "image3.jpg");

                if (file1.exists()) {
                    if (file1.delete()) {
                        System.out.println("Delete file 1");
                    } else {
                        response.getWriter().println("Failed to delete file 1");
                    }
                }

                if (file2.exists()) {
                    if (file2.delete()) {
                        System.out.println("Delete file 2");
                    } else {
                        response.getWriter().println("Failed to delete file 2");
                    }
                }

                if (file3.exists()) {
                    if (file3.delete()) {
                        System.out.println("Delete file 3");
                    } else {
                        response.getWriter().println("Failed to delete file 3");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            response_Dto.setContent("Success");
            response_Dto.setSuccess(true);
        }

        System.out.println(response_Dto.getContent());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("response_dto", gson.toJson(response_Dto));

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

    }

}
