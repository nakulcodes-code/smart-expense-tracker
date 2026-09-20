<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="com.expensetracker.model.User" %>
<%@ page import="com.expensetracker.model.Expense" %>
<%@ page import="com.expensetracker.model.Category" %>
<%@ page import="com.expensetracker.dao.ExpenseDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!-- Load JSTL Bundle (reads messages.properties or messages_hi.properties) -->
<fmt:setBundle basename="messages" />

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ExpenseDAO expenseDAO = new ExpenseDAO();
    List<Expense> expenses = expenseDAO.getExpensesByUserId(currentUser.getUserId());
    List<Category> categories = expenseDAO.getAllCategories();
    Map<String, Double> categoryTotals = expenseDAO.getCategoryWiseExpenses(currentUser.getUserId());

    double totalSpent = 0;
    for (Expense e : expenses) {
        totalSpent += e.getAmount();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="app.title" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { background-color: #f8f9fa; }
        .summary-card { border-left: 5px solid #0d6efd; }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#"><fmt:message key="app.title" /></a>
        <div class="d-flex align-items-center">
            
            <!-- Language Switcher Dropdown -->
            <div class="dropdown me-3">
                <button class="btn btn-outline-light btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    🌐 Language / भाषा
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="changeLanguage?lang=en">English</a></li>
                    <li><a class="dropdown-item" href="changeLanguage?lang=hi">हिंदी (Hindi)</a></li>
                </ul>
            </div>

            <span class="text-white me-3"><fmt:message key="nav.welcome" />, <%= currentUser.getFullName() %></span>
            <a href="logout" class="btn btn-outline-light btn-sm"><fmt:message key="nav.logout" /></a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <!-- Total Summary Card -->
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card p-3 shadow-sm summary-card">
                <h5><fmt:message key="card.total" /></h5>
                <h2 class="text-primary">₹<%= String.format("%.2f", totalSpent) %></h2>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Add Expense Form -->
        <div class="col-md-4 mb-4">
            <div class="card p-3 shadow-sm">
                <h5 class="mb-3"><fmt:message key="form.add_title" /></h5>
                <form action="expense" method="post">
                    <input type="hidden" name="action" value="add">
                    
                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="form.title" /></label>
                        <input type="text" name="title" class="form-control" required placeholder="Groceries / राशन">
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="form.amount" /></label>
                        <input type="number" step="0.01" name="amount" class="form-control" required placeholder="500.00">
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="form.category" /></label>
                        <select name="categoryId" class="form-select" required>
                            <% for (Category cat : categories) { %>
                                <option value="<%= cat.getCategoryId() %>"><%= cat.getCategoryName() %></option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="form.date" /></label>
                        <input type="date" name="expenseDate" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="form.notes" /></label>
                        <textarea name="notes" class="form-control" rows="2"></textarea>
                    </div>

                    <button type="submit" class="btn btn-success w-100"><fmt:message key="form.submit" /></button>
                </form>
            </div>
        </div>

        <!-- Graph Analysis & History Table -->
        <div class="col-md-8">
            <!-- Doughnut Chart Card -->
            <div class="card p-3 shadow-sm mb-4">
                <h5 class="mb-3"><fmt:message key="chart.title" /></h5>
                <div style="max-height: 300px; position: relative;">
                    <canvas id="expenseChart"></canvas>
                </div>
            </div>

            <!-- Expense History Table -->
            <div class="card p-3 shadow-sm">
                <h5 class="mb-3"><fmt:message key="table.title" /></h5>
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th><fmt:message key="form.date" /></th>
                            <th><fmt:message key="form.title" /></th>
                            <th><fmt:message key="form.category" /></th>
                            <th><fmt:message key="form.amount" /></th>
                            <th><fmt:message key="table.action" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (expenses.isEmpty()) { %>
                            <tr>
                                <td colspan="5" class="text-center text-muted">No records found.</td>
                            </tr>
                        <% } else { 
                            for (Expense e : expenses) { %>
                                <tr>
                                    <td><%= e.getExpenseDate() %></td>
                                    <td><%= e.getTitle() %></td>
                                    <td><span class="badge bg-secondary"><%= e.getCategoryName() %></span></td>
                                    <td class="fw-bold text-danger">₹<%= String.format("%.2f", e.getAmount()) %></td>
                                    <td>
                                        <a href="expense?action=delete&id=<%= e.getExpenseId() %>" 
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Delete record?');"><fmt:message key="table.delete" /></a>
                                    </td>
                                </tr>
                        <%  } 
                           } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap 5 JS Bundle (required for language dropdown to expand) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const categoryLabels = [
        <% for (String label : categoryTotals.keySet()) { %>
            "<%= label %>",
        <% } %>
    ];

    const categoryData = [
        <% for (Double value : categoryTotals.values()) { %>
            <%= value %>,
        <% } %>
    ];

    const ctx = document.getElementById('expenseChart').getContext('2d');
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: categoryLabels,
            datasets: [{
                label: 'Expenses (₹)',
                data: categoryData,
                backgroundColor: [
                    '#FF6384', '#36A2EB', '#FFCE56', 
                    '#4BC0C0', '#9966FF', '#FF9F40'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });
</script>

</body>
</html>