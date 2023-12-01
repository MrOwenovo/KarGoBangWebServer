package com.example.controller.listener;

import com.example.entity.constant.ThreadDetails;
import com.example.service.util.IpTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashMap;

@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();
        HashMap<String, HttpSession> sessions = (HashMap<String, HttpSession>) application.getAttribute("sessions");
        if (sessions == null) {
            sessions = new HashMap<>();
            application.setAttribute("sessions", sessions);
        }

        // You need to replace this with the correct way to get the request object if possible
        // HttpServletRequest request = ...;

        // Store the IP address in the session
        String ipAddress = "IP_ADDRESS"; // Replace with actual IP address from the request
        session.setAttribute("ipAddress", ipAddress);
        sessions.put(ipAddress, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();
        HashMap<String, HttpSession> sessions = (HashMap<String, HttpSession>) application.getAttribute("sessions");
        String ipAddress = (String) session.getAttribute("ipAddress");

        if (ipAddress != null && sessions != null) {
            sessions.remove(ipAddress);
        }
    }
}
