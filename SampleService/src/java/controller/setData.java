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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import util.Toolet;


/**
 *
 * @author leo
 */
//@WebServlet(name = "setData", urlPatterns = {"/setData"})
public class setData extends HttpServlet {
    
    debug de;
    Toolet tools;
    CommonDAO cDAO;
    private void initParms(){
        //******************Init Variable : START*********************************//
        de = new debug(true);
        tools = new Toolet();
        cDAO = new CommonDAO();
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
        response.setContentType("text/xml;charset=UTF-8");
        initParms();
        ////////////////////////////////////////////////////////////////////////
        
        //========================================
        // Get parameters
        //----------------------------------------
        String oper = tools.parmGetter(request, "oper");// select

        //========================================
        // Operation
        //----------------------------------------
        int result = 0;
        if (        "update".equals(oper) ) {
            result = updateItem(request);
            tools.showParm(request);
        } else if ( "insert".equals(oper) ) {
            result = insertItem(request);
        } else if ( "delete".equals(oper) ) {
            //result = deleteData(request);
        } else {
            de.println("Wrong Operation : "+oper);
        }
        
        de.println("result "+result);

        //========================================
        // Set parameters
        //----------------------------------------
        request.setAttribute("oper", oper);
        request.setAttribute("setResult", result);
        

        ////////////////////////////////////////////////////////////////////////
        ServletContext context = getServletConfig().getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/setData.jsp");
        dispatcher.include(request, response);
    }
    
    private int insertItem(HttpServletRequest request){
        //--------------------------------------------
        String target = tools.parmGetter(request, "target");
        
        String id         = tools.parmGetter(request, "id");
        String name       = tools.parmGetter(request, "name");
        //String status     = tools.parmGetter(request, "status");
        //String updateTime = tools.parmGetter(request, "updateTime");
        //String createDate = tools.parmGetter(request, "createDate");

        HashMap keyMap = new HashMap();
            if(!"".equals(id)){keyMap.put("id", id);}//update else : insert
            if(!"".equals(name))  {keyMap.put("name", name);}
            //if( "".equals(status)){keyMap.put("status", "normal");}
            //if(!"".equals(updateTime)){keyMap.put("updateTime", updateTime);}else{keyMap.put("updateTime",  ( (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) ) );}
            //if( "".equals(createDate)){keyMap.put("createDate",  ( (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) ) );}

        HashMap whereCondition = new HashMap();
            whereCondition.put("id", id);
        //--
        int result = cDAO.setInstance(target, keyMap, whereCondition);

        //--------------------------------------------
        return result;
    }

    
    private int updateItem(HttpServletRequest request){
       //--------------------------------------------
        String target     = tools.parmGetter(request, "target");
        
        String id         = tools.parmGetter(request, "id");
        String name       = tools.parmGetter(request, "name");
        //String status     = tools.parmGetter(request, "status");
        //String createDate = tools.parmGetter(request, "createDate");

        HashMap keyMap = new HashMap();
            if(!"".equals(id))        {keyMap.put("id", id);}
            if(!"".equals(name))      {keyMap.put("name", name);}
            //if( "".equals(status))    {keyMap.put("status", "normal");}
            //if(!"".equals(updateTime)){keyMap.put("updateTime", updateTime);}else{keyMap.put("updateTime",  ( (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) ) );}
            //if( "".equals(createDate)){keyMap.put("createDate",  ( (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) ) );}

        HashMap whereCondition = new HashMap();
            whereCondition.put("id", id);
        
        //--
        int result = cDAO.setInstance(target, keyMap, whereCondition);

        //--------------------------------------------
        return result;
    }
    
    private int deleteData(HttpServletRequest request){
        //--------------------------------------------
        String target = tools.parmGetter(request, "target");
        String id = tools.parmGetter(request, "id");
        String status = "disable";

        HashMap keyMap = new HashMap();
            keyMap.put("status", status);
        HashMap whereCondition = new HashMap();
                //whereCondition.put("dem_no", id);
        //-- where condition & key map combination
        whereCondition.put("id", id);
        //--
        int result = cDAO.setInstance(target, keyMap, whereCondition);

        //--------------------------------------------
        return result;
    }
    
    private void selector(String target){
        if (       "goods".equals(target) ) {
        }else if ( "group".equals(target) ) {
        }else if ( "trad".equals(target) ) {
        }else if ( "user".equals(target) ) {
        }
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
