package com.workflow.controller;

import com.google.gson.Gson;
import com.workflow.model.MachineTelemetry;
import com.workflow.service.DigitalTwinEngine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/digital-twin/telemetry")
public class DigitalTwinServlet extends HttpServlet {
    private final DigitalTwinEngine engine = new DigitalTwinEngine();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String machineId = req.getParameter("machineId");
        if (machineId == null || machineId.isBlank()) {
            machineId = "TWIN-CNC-01";
        }

        MachineTelemetry telemetry = engine.evaluateAsset(machineId);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(telemetry));
        out.flush();
    }
}