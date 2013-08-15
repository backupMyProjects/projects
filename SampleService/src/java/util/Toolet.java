/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leo
 */
public class Toolet {

    public String parmGetter(HttpServletRequest request, String target) {
        String re;
        re = request.getParameter(target) != null ? request.getParameter(target) : "";
        return re;
    }

    public String stripHTMLTag(String srcStr) {
        String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
        return srcStr.replaceAll(regex, "");
    }

    public void showParm(HttpServletRequest request){
        Enumeration enu = request.getParameterNames();

        while( enu.hasMoreElements() ){
            String parmKey = enu.nextElement().toString();
            System.out.println("parm key "+ parmKey );
            System.out.println("parm value "+request.getParameter(parmKey));
        }

    }
    
}
