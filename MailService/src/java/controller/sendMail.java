/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Toolet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import module.Postman;

/**
 *
 * @author leo
 */
@WebServlet(name = "sendMail", urlPatterns = {"/sendMail"})
public class sendMail extends HttpServlet {
    //******************Init Variable : START*********************************//
    Toolet tools;
    //******************Init Variable : END***********************************//
    private void initParms(){
        //******************Init Variable : START*********************************//
        tools = new Toolet();
        //******************Init Variable : END***********************************//
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        initParms();
        ////////////////////////////////////////////////////////////////////////

        //========================================
        // Get parameters
        //----------------------------------------
        String function = tools.parmGetter(request, "function");// select

        tools.showParm(request);

        //========================================
        // Action
        //----------------------------------------
        List result = new ArrayList();// without this, occur the null point exception at xml page
        if ( "sendMail".equals(function) ) {
            result = sendMail(request);
        }
        

        //========================================
        // Set parameters
        //----------------------------------------
        request.setAttribute("resultList", result);

        ////////////////////////////////////////////////////////////////////////
        ServletContext context = getServletConfig().getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/getResult.jsp");
        dispatcher.include(request, response);
    }
    
    private List sendMail(HttpServletRequest request){
        //--------------------------------------------
        List result = new ArrayList();
        HashMap hmError = new HashMap();
        //--
        String userName = "anubis.mummy.pop@gmail.com";
        String passwd = "a127359058";
        
        Postman postman = new Postman(userName, passwd);        
        
        String subject = "test 1";
        String message = "This is just a tes tfor what I want";
        String from = "anubis.mummy.pop@gmail.com";
        String to = "anubis.mummy.pop@gmail.com, anubis.mummy@gmail.com";
        String attachedPic = "D:/USING/Documents/working/NetBeansProjects/JavaExampleCollections/src/javaexamplecollections/avatar.gif";
        boolean actionReturn = postman.sendMail(subject, message, attachedPic , from, to);
        HashMap<String, String> temp = new HashMap();
        temp.put("actionResult", ""+actionReturn);
        result.add(temp);
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
