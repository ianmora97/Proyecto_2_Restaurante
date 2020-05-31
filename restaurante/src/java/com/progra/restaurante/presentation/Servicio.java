///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.progra.restaurante.presentation;
//
//import com.google.gson.Gson;
//import com.progra.restaurante.logic.Model;
//import com.progra.restaurante.logic.Persona;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author Escinf
// */
//@WebServlet(name = "Servicio", urlPatterns = {"/api/personas/add","/api/personas/get",
//                                        "/api/personas/delete","/api/personas/search"})
//public class Servicio extends HttpServlet {
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        switch(request.getServletPath()){
//            case "/api/personas/add":
//                this.doPersonaAdd(request,response);
//                break;
//            case "/api/personas/get":
//                this.doPersonaGet(request,response);
//                break;  
//            case "/api/personas/delete":
//                this.doPersonaDelete(request,response);
//                break;                  
//            case "/api/personas/search":
//                this.doPersonaSearch(request,response);
//                break;                
//        }
//    }
//
//    protected void doPersonaSearch(HttpServletRequest request, 
//        HttpServletResponse response) throws ServletException, IOException {
//      try{
//        BufferedReader reader = request.getReader();
//        Gson gson = new Gson();
//        Persona persona = gson.fromJson(reader, Persona.class);
//        PrintWriter out = response.getWriter();
//        List<Persona> personas = Model.instance().personaSearch(persona.getNombre());
//        response.setContentType("application/json; charset=UTF-8");
//        out.write(gson.toJson(personas));        
//        response.setStatus(200); // ok with content
//      }
//      catch(Exception e){	
//        response.setStatus(status(e)); 
//      }		
//    }
//    
//    protected void doPersonaAdd(HttpServletRequest request, 
//        HttpServletResponse response) throws ServletException, IOException {
//      try{
//        BufferedReader reader = request.getReader();
//        Gson gson = new Gson();
//        Persona persona = gson.fromJson(reader, Persona.class);
//        PrintWriter out = response.getWriter();
//        Model.instance().personaAdd(persona);
//        List<Persona>personas = Model.instance().personaListAll();
//        response.setContentType("application/json; charset=UTF-8");
//        out.write(gson.toJson(personas));        
//        response.setStatus(200); // ok with content
//      }
//      catch(Exception e){	
//        response.setStatus(status(e)); 
//      }		
//    }
//
//    
//    protected void doPersonaDelete(HttpServletRequest request, 
//        HttpServletResponse response) throws ServletException, IOException {
//      try{
//        BufferedReader reader = request.getReader();
//        Gson gson = new Gson();
//        Persona persona = gson.fromJson(reader, Persona.class);
//        PrintWriter out = response.getWriter();
//        Model.instance().personaDelete(persona.getCedula());
//        List<Persona>personas = Model.instance().personaListAll();
//        response.setContentType("application/json; charset=UTF-8");
//        out.write(gson.toJson(personas));        
//        response.setStatus(200); // ok with content
//      }
//      catch(Exception e){	
//        response.setStatus(status(e)); 
//      }		
//    }
//    
//    protected void doPersonaGet(HttpServletRequest request, 
//        HttpServletResponse response) throws ServletException, IOException {
//      try{
//        BufferedReader reader = request.getReader();
//        Gson gson = new Gson();
//        Persona persona = gson.fromJson(reader, Persona.class);
//        PrintWriter out = response.getWriter();
//        persona = Model.instance().personaEdit(persona.getCedula());
//        response.setContentType("application/json; charset=UTF-8");
//        out.write(gson.toJson(persona));        
//        response.setStatus(200); // ok with content
//      }
//      catch(Exception e){	
//        response.setStatus(status(e)); 
//      }		
//    }
//    
//    protected int status(Exception e){
//        if(e.getMessage().startsWith("404")) return 404;
//        if(e.getMessage().startsWith("406")) return 406;
//        return 400;
//    }
//        
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
