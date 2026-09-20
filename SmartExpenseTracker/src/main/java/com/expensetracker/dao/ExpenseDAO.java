package com.expensetracker.dao;

import com.expensetracker.config.DBConnection;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO{
    public boolean addExpense(Expense expense){
    String sql="INSERT INTO expenses (user_id, category_id, title, amount, expense_date, notes) VALUES (?, ?, ?, ?, ?, ?)";
    try(Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement(sql)){
        ps.setInt(1, expense.getUserId());
        ps.setInt(2, expense.getCategoryId());
        ps.setString(3, expense.getTitle());
        ps.setDouble(4, expense.getAmount());
        ps.setDate(5, expense.getExpenseDate());
        ps.setString(6, expense.getNotes());
        return ps.executeUpdate()>0;
    }catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }
    
    public List<Expense> getExpensesByUserId(int userId){
        List<Expense> list=new ArrayList<>();
        String sql="SELECT e.*, c.category_name FROM expenses e JOIN categories c ON e.category_id = c.category_id WHERE e.user_id = ? ORDER BY e.expense_date DESC";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1, userId);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Expense e=new Expense();
                e.setExpenseId(rs.getInt("expense_id"));
                e.setUserId(rs.getInt("user_id"));
                e.setCategoryId(rs.getInt("category_id"));
                e.setCategoryName(rs.getString("category_name"));
                e.setTitle(rs.getString("title"));
                e.setAmount(rs.getDouble("amount"));
                e.setExpenseDate(rs.getDate("expense_date"));
                e.setNotes(rs.getString("notes"));
                list.add(e);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean deleteExpense(int expenseId, int userId){
        String sql="DELETE FROM expenses WHERE expense_id = ? AND user_id = ?";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1, expenseId);
            ps.setInt(2, userId);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Category>getAllCategories(){
        List<Category> list=new ArrayList<>();
        String sql="SELECT * FROM categories";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public Map<String, Double> getCategoryWiseExpenses(int userId) {
    Map<String, Double> categoryData = new HashMap<>();
    String sql = "SELECT c.category_name, SUM(e.amount) AS total " +
                 "FROM expenses e " +
                 "JOIN categories c ON e.category_id = c.category_id " +
                 "WHERE e.user_id = ? " +
                 "GROUP BY c.category_name";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            categoryData.put(rs.getString("category_name"), rs.getDouble("total"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return categoryData;
}
}