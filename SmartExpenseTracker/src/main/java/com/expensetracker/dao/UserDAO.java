package com.expensetracker.dao;

import com.expensetracker.config.DBConnection;
import com.expensetracker.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO{
    public boolean registerUser(User user){
        String sql="INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps.executeUpdate()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public User loginUser(String email, String password){
        String sql="SELECT * FROM users WHERE email = ? AND password = ?";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}