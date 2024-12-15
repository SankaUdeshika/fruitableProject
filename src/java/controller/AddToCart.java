/*

* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license

* Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template

 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Cart_DTO;

import dto.Response_Dto;
import dto.Response_Dto;

import dto.User_Dto;
import dto.User_Dto;

import entity.Cart;

import entity.Product;

import entity.User;

import java.io.IOException;

import java.io.PrintWriter;

import java.util.ArrayList;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import model.HibernateUtil;

import model.Validation;

import org.hibernate.Criteria;

import org.hibernate.Session;

import org.hibernate.Transaction;

import org.hibernate.criterion.Restrictions;

@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})

public class AddToCart extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_Dto response_DTO = new Response_Dto();
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();

        try {
            User_Dto user_Dto = (User_Dto) request.getSession().getAttribute("user");
            System.out.println(user_Dto.getEmail());

            String id = request.getParameter("id");
            String qty = request.getParameter("qty");

            Session session = HibernateUtil.getSessionFactory().openSession();

            if (!Validation.isInteger(qty)) {
                response_DTO.setContent("notvalidqty");
                response_DTO.setSuccess(false);
            }
            int productQty = Integer.parseInt(qty);
            int productId = Integer.parseInt(id);

//          getproduct Detailss
            Product product = (Product) session.get(Product.class, productId);

            //user check
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_Dto.getEmail()));
            User user = (User) criteria1.uniqueResult();

            //db check
            Criteria criteria2 = session.createCriteria(Cart.class);
            criteria2.add(Restrictions.eq("user", user));
            criteria2.add(Restrictions.eq("product_product_id", product));
            if (criteria2.list().isEmpty()) {
                System.out.println("no Items");
                //no items on cart
                if (productQty <= product.getQty()) {

                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setProduct_product_id(product);
                    cart.setQty(productQty);
                    session.save(cart);
                    session.beginTransaction().commit();
                    System.out.println("saved");
                    response_DTO.setContent("addedtocart");
                    response_DTO.setSuccess(true);
                } else {
                    //stock case qty
                    response_DTO.setContent("lacsofqty");
                    response_DTO.setSuccess(false);
                }
            } else {
                //items on cart
                Cart cartItem = (Cart) criteria2.uniqueResult();

                session.delete(cartItem);
                session.beginTransaction().commit();
                System.out.println("Deleted");
                response_DTO.setContent("DeletefromCart");
                response_DTO.setSuccess(true);
//                    
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            response_DTO.setContent("login");
            response_DTO.setSuccess(false);
            System.out.println("Error");
        }
        jsonObject.addProperty("response_dto", gson.toJson(response_DTO));

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

////        try {
////
////            Session session = HibernateUtil.getSessionFactory().openSession();
////
////            Transaction transaction = session.beginTransaction();
////
////            String id = request.getParameter("id");
////
////            String qty = request.getParameter("qty");
////
////            if (!Validation.isInteger(id)) {
////
////                //product not found
////            } else if (!Validation.isInteger(qty)) {
////
////                //qty check            
////            } else {
////
////                int productId = Integer.parseInt(id);
////
////                int productQty = Integer.parseInt(qty);
////
////                if (productId <= 0) {
////
////                    //case
////                } else {
////
////                    Product product = (Product) session.get(Product.class, productId);
////
////                    if (product != null) {
////
////                        if (request.getSession().getAttribute("user") != null) {
////
////                            //user cart
////                            User_Dto user_DTO = (User_Dto) request.getSession().getAttribute("user");
////
////                            //user check
////                            Criteria criteria1 = session.createCriteria(User.class);
////
////                            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
////
////                            User user = (User) criteria1.uniqueResult();
////
////                            //db check
////                            Criteria criteria2 = session.createCriteria(Cart.class);
////
////                            criteria2.add(Restrictions.eq("user", user));
////
////                            criteria2.add(Restrictions.eq("product", product));
////
////                            if (criteria2.list().isEmpty()) {
////
////                                //no items on cart
////                                if (productQty <= product.getQty()) {
////
////                                    Cart cart = new Cart();
////
////                                    cart.setUser(user);
////
////                                    cart.setProduct_product_id(product);
////
////                                    cart.setQty(productQty);
////
////                                    session.save(cart);
////
////                                    transaction.commit();
////
////                                } else {
////
////                                    //stock case qty
////                                }
////
////                            } else {
////
////                                //items on cart
////                                Cart cartItem = (Cart) criteria2.uniqueResult();
////
////                                if ((cartItem.getQty() + productQty) <= product.getQty()) {
////
////                                    cartItem.setQty(cartItem.getQty() + productQty);
////
////                                    session.update(cartItem);
////
////                                    transaction.commit();
////
////                                }
////
////                            }
////
////                        } else {
////
////                            //session cart
////                            HttpSession httpSession = request.getSession();
////
////                            if (httpSession.getAttribute("sessionCart") != null) {
////
////                                //session cart found
////                                ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");
////
////                                Cart_DTO foundCart_DTO = null;
////
////                                for (Cart_DTO cart_DTO : sessionCart) {
////
////                                    if (cart_DTO.getProduct().getProduct_id()== product.getProduct_id()) {
////
////                                        foundCart_DTO = cart_DTO;
////
////                                        break;
////
////                                    }
////
////                                }
////
////                                if (foundCart_DTO != null) {
////
////                                    //product found
////                                    if (foundCart_DTO.getQty() + productQty <= product.getQty()) {
////
////                                        //update qty
////                                        foundCart_DTO.setQty(foundCart_DTO.getQty() + productQty);
////
////                                        response_DTO.setSuccess(true);
////
////                                        response_DTO.setContent("Product updated successfully");
////
////                                    } else {
////
////                                        //qty limit excced, qty not available
////                                        response_DTO.setContent("Quantity not available, limit excced");
////
////                                    }
////
////                                } else {
////
////                                    //product not found
////                                    if (productQty <= product.getQty()) {
////
////                                        //add to session cart
////                                        Cart_DTO cart_DTO = new Cart_DTO();
////
////                                        cart_DTO.setProduct(product);
////
////                                        cart_DTO.setQty(productQty);
////
////                                        sessionCart.add(cart_DTO);
////
////                                        response_DTO.setSuccess(true);
////
////                                        response_DTO.setContent("Product added to the Cart");
////
////                                    } else {
////
////                                        //quantity not available
////                                        response_DTO.setContent("Quantity not available");
////
////                                    }
////
////                                }
////
////                            } else {
////
////                                //session cart not found
////                                if (productQty <= product.getQty()) {
////
////                                    //add to session cart
////                                    ArrayList<Cart_DTO> sessionCart = new ArrayList<>();
////
////                                    Cart_DTO cart_DTO = new Cart_DTO();
////
////                                    cart_DTO.setProduct(product);
////
////                                    cart_DTO.setQty(productQty);
////
////                                    sessionCart.add(cart_DTO);
////
////                                    httpSession.setAttribute("sessionCart", sessionCart);
////
////                                    response_DTO.setSuccess(true);
////
////                                    response_DTO.setContent("Product added to the Cart");
////
////                                } else {
////
////                                    //quantity not available
////                                    response_DTO.setContent("Quantity not available");
////
////                                }
////
////                            }
////
////                        }
////
////                    } else {
////
////                        //no product
////                    }
////
////                }
////
////            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
    }

}
