/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_Dto;
import dto.User_Dto;
import entity.Category;
import entity.Product;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@MultipartConfig
@WebServlet(name = "ProductAdding", urlPatterns = {"/ProductAdding"})
public class ProductAdding extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Gson gson = new Gson();

        String product_name = request.getParameter("product_name");
        String product_price = request.getParameter("product_price");
        String qty = request.getParameter("qty");
        String Category_category_id = request.getParameter("Category_category_id");
        String description = request.getParameter("description");

        Part image1 = request.getPart("image1");
        Part image2 = request.getPart("image2");
        Part image3 = request.getPart("image3");

        Response_Dto response_Dto = new Response_Dto();

        if (product_name.isEmpty()) {
            response_Dto.setContent("Please Enter Product Name");
        } else if (!Validation.isDouble(product_price)) {
            response_Dto.setContent("Please Enter Valid Price value ");
        } else if (!Validation.isInteger(qty)) {
            response_Dto.setContent("Please Enter Valid Quantity ");
        } else if (Category_category_id == "0") {
            response_Dto.setContent("Please Select  Valid Category ");
        } else if (description.isEmpty()) {
            response_Dto.setContent("Please Enter Descrtiption ");
        } else if (Integer.parseInt(product_price) <= 0) {
            response_Dto.setContent("Please Enter Valid Quanitity");
        } else if (Integer.parseInt(qty) <= 0) {
            response_Dto.setContent("Please Enter Valid Quanitity");
        } else if (image1.getSubmittedFileName() == null) {
            response_Dto.setContent("Please Select Valid First Image");
        } else if (image2.getSubmittedFileName() == null) {
            response_Dto.setContent("Please Select Valid Second Image");
        } else if (image3.getSubmittedFileName() == null) {
            response_Dto.setContent("Please Select Valid Third Image");
        } else {
            Category category = (Category) session.get(Category.class, Integer.parseInt(Category_category_id));

            if (category == null) {
                response_Dto.setContent("Please Select Valid Category");
            } else {

                Product product = new Product();
                product.setCategory(category);
                product.setDescription1(description);
                product.setProduct_name(product_name);
                product.setProduct_price(Double.parseDouble(product_price));
                product.setQty(Integer.parseInt(qty));

                User_Dto user_Dto = (User_Dto) request.getSession().getAttribute("user");
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_Dto.getEmail()));
                User user = (User) criteria1.uniqueResult();
                product.setUser(user);

                int pid = (int) session.save(product);
                session.beginTransaction().commit();

                String applicationPath = request.getServletContext().getRealPath("");
                String newApplicaitonPath = applicationPath.replace("build" + File.separator + "web", "productImages");

                File folder = new File(applicationPath + "//" + pid);
                if (!folder.exists()) {
                    folder.mkdir();
                }

                File file1 = new File(folder, +pid + "image1.png");// file object ekak handaawa.
                InputStream inputStream = image1.getInputStream(); // image eka input Stream ekak widihata gannawa
                Files.copy(inputStream, file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

                File file2 = new File(folder, pid + "image2.png");
                InputStream inputStrea2 = image2.getInputStream();
                Files.copy(inputStrea2, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

                File file3 = new File(folder, pid + "image3.png");
                InputStream inputStrea3 = image3.getInputStream();
                Files.copy(inputStream, file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

                response_Dto.setContent("New Product Added");
                response_Dto.setSuccess(true);
            }

        }

        session.close();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_Dto));

        System.out.println(gson.toJson(response_Dto));
    }
}
