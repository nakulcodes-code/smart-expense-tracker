package com.expensetracker.controller;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("=== DEBUG: LoginServlet Triggered ===");
        System.out.println("Attempting login for email: " + email);

        User user = userDAO.loginUser(email, password);

        if (user != null) {
            System.out.println("=== DEBUG: User Found! Redirecting to dashboard. ===");
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            response.sendRedirect("dashboard.jsp");
        } else {
            System.out.println("=== DEBUG: Login Failed (User is NULL). ===");
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}