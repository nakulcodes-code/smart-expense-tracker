<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Smart Expense Tracker</title>
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
                <h3 class="text-center mb-4 text-primary fw-bold">Create Account</h3>

                <% if ("failed".equals(request.getParameter("error"))) { %>
                    <div class="alert alert-danger text-center">Registration failed. Email might exist!</div>
                <% } %>

                <form action="register" method="post">
                    <div class="mb-3">
                        <label class="form-label">Full Name</label>
                        <input type="text" name="fullName" class="form-control" placeholder="John Doe" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" placeholder="john@example.com" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Sign Up</button>
                </form>

                <div class="text-center mt-3">
                    <p>Already have an account? <a href="login.jsp">Log In</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>