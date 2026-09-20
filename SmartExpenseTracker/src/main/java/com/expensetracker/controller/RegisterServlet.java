package com.expensetracker.controller;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
    private UserDAO userDAO= new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String fullName= request.getParameter("fullName");
        String email= request.getParameter("email");
        String password= request.getParameter("password");
        
        User user=new User(0, fullName, email, password);
        if(userDAO.registerUser(user)){
            response.sendRedirect("login.jsp?msg=registered");
        }else{
            response.sendRedirect("register.jsp?error=failed");
        }
    }
}