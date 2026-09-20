package com.expensetracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/changeLanguage")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();

        if (lang != null && !lang.isEmpty()) {
            Locale locale = new Locale(lang);
            // Sets JSTL fmt locale in current HTTP session
            Config.set(session, Config.FMT_LOCALE, locale);
            session.setAttribute("lang", lang);
        }

        // Return user to previous page or dashboard
        String referrer = request.getHeader("referer");
        if (referrer != null && !referrer.isEmpty()) {
            response.sendRedirect(referrer);
        } else {
            response.sendRedirect("dashboard.jsp");
        }
    }
}