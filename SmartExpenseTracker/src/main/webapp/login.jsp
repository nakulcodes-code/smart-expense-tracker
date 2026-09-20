<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Smart Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; }
        .card { border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card p-4">
                <h3 class="text-center mb-4 text-primary fw-bold">Expense Tracker Login</h3>

                <% if ("registered".equals(request.getParameter("msg"))) { %>
                    <div class="alert alert-success text-center">Account created! Please log in.</div>
                <% } else if ("invalid".equals(request.getParameter("error"))) { %>
                    <div class="alert alert-danger text-center">Invalid email or password.</div>
                <% } else if ("loggedout".equals(request.getParameter("msg"))) { %>
                    <div class="alert alert-info text-center">Logged out successfully.</div>
                <% } %>

                <form action="login" method="post">
                    <div class="mb-3">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" placeholder="john@example.com" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Log In</button>
                </form>

                <div class="text-center mt-3">
                    <p>Don't have an account? <a href="register.jsp">Sign Up</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>