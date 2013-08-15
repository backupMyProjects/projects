/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import LeoLib.tools.*;
import dao.CommonDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import util.Toolet;

/**
 *
 * @author leo
 */
//@WebServlet(name = "getData", urlPatterns = {"/getData"})
public class getData extends HttpServlet {
    
    debug de;
    Toolet tools;
    CommonDAO cDAO;
    String prefix;
    String table;
    private void initParms(){
        de = new debug(true);
        tools = new Toolet();
        cDAO = new CommonDAO();
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        initParms();
        ////////////////////////////////////////////////////////////////////////
        //========================================
        // Get parameters
        //----------------------------------------
        String oper = tools.parmGetter(request, "oper");// select

        //tools.showParm(request);
        /*
        Properties pr = System.getProperties();
        TreeSet propKeys = new TreeSet(pr.keySet());
        for (Iterator it = propKeys.iterator(); it.hasNext(); ) {
            String key = (String)it.next();
            System.out.println("" + key + "=" + pr.get(key) + "\n");
        }
        */

        //========================================
        // Action
        //----------------------------------------
        List result = new ArrayList();// without this, occur the null point exception at xml page
        
        
            if ( "list".equals(oper) || "".equals(oper) ) {
                List temp =  getAvaiableDataList(request);
                result = temp == null ? new ArrayList() : temp;
            }else if( "one".equals(oper) ){
                //List temp =  getAvaiableDataOne(request);
                //result = temp == null ? new ArrayList() : temp;
            }
        
        de.println("result "+result);

        //========================================
        // Set parameters
        //----------------------------------------
        request.setAttribute("resultList", result);

        ////////////////////////////////////////////////////////////////////////
        ServletContext context = getServletConfig().getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/getData.jsp");
        dispatcher.include(request, response);
    }
    
    private List getAvaiableDataList(HttpServletRequest request){
        String target = tools.parmGetter(request, "target");// selected table
        List result = null;
        if ( !"".equals(target) ){
            result = cDAO.getInstanceListViaSQL("SELECT * from " + target);
        }
        //--------------------------------------------
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
