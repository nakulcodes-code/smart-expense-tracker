package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.model.Expense;
import com.expensetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/expense")
public class ExpenseServlet extends HttpServlet{
    private ExpenseDAO expenseDAO= new ExpenseDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        User user = (session!=null)?(User) session.getAttribute("currentUser"):null;
        
        if(user==null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        if("add".equals(action)){
            String title = request.getParameter("title");
            double amount = Double.parseDouble(request.getParameter("amount"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Date ecpenseDate = Date.valueOf(request.getParameter("expenseDate"));
            String notes = request.getParameter("notes");
            
            Expense expense = new Expense();
            expense.setUserId(user.getUserId());
            expense.setCategoryId(categoryId);
            expense.setTitle(title);
            expense.setAmount(amount);
            expense.setExpenseDate(ecpenseDate);
            expense.setNotes(notes);
            
            expenseDAO.addExpense(expense);
        }
        
        response.sendRedirect("dashboard.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        User user = (session!=null)?(User) session.getAttribute("currentUser"):null;
        
        if(user == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        if("delete".equals(action)){
            int expenseId = Integer.parseInt(request.getParameter("id"));
            expenseDAO.deleteExpense(expenseId, user.getUserId());
        }
        
        response.sendRedirect("dashboard.jsp");
    }
}
