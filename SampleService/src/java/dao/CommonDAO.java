/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import LeoLib.tools.debug;
import java.util.*;
import LeoLib.tools.*;
import java.sql.SQLException;

/**
 *
 * @author leo
 */
public class CommonDAO {
    //******************Init Variable : START*********************************//
    debug de = new debug(true);
    /*
    String propertySetting = "application.properties";
    APProperty sysprop = new APProperty(propertySetting);
    String env_filter = sysprop.getPropertyValue("env.filter");
    Properties db_prop = sysprop.loadPropertyFile(sysprop.getPropertyValue("db.properties." + env_filter));
    DBKits dbk = new DBKits(db_prop);
    String DBconnect_target = env_filter;
    */
    APProperty app_prop = new APProperty();
    Properties start_prop = app_prop.loadStartProperty();
    Properties db_prop = app_prop.loadDBProperty();
    DBKits dbk = new DBKits(db_prop);
    String env_filter = app_prop.getPropertyValue("env.filter");
    String DBconnect_target = env_filter;
    //******************Init Variable : END***********************************//

    public List getInstanceListViaSQL(String selfSQL) {
        List re = null;

        dbk.setDBConnection(DBconnect_target);
        try{
            dbk.exeSelectSQL(selfSQL);
        }catch(Exception e){
            
        }
        
        de.println("CommonDAO:getInstanceListViaSQL "+dbk.getSQL());
        re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public List getInstanceList(String targetTable, List<String> selectorList, Map<String, Object> whereCondition, List<String> orderByList, Integer limit, Integer offset) {
        List re = null;

        dbk.setDBConnection(DBconnect_target);
        dbk.exeSelect(targetTable, selectorList, whereCondition, orderByList, null, null);
        de.println("CommonDAO:getInstanceList "+dbk.getSQL());
        re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public HashMap getInstance(String table, ArrayList selectList, HashMap whereCondition) {
        HashMap re = null;

        dbk.setDBConnection(DBconnect_target);

        dbk.exeSelect(table, selectList, whereCondition, null, 1, 0);
        de.println("CommonDAO:getInstance "+dbk.getSQL());
        List<HashMap> result = dbk.getList();
        dbk.setDBDisconnect();
        re = result.get(0);

        return re;
    }
    
    public int setInstanceViaSQL(String selfSQL) {
        int re = 0;

        dbk.setDBConnection(DBconnect_target);
        dbk.exeModifySQL(selfSQL);
        de.println("CommonDAO:setInstanceViaSQL "+dbk.getSQL());
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

    public int setInstance(String table, HashMap keyMap, HashMap whereHM) {
        int re = 0;

        dbk.setDBConnection(DBconnect_target);
        dbk.exeUpdateInsert(table, keyMap, whereHM);
        de.println("CommonDAO:setInstance "+dbk.getSQL());
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

    public int deleteInstance(String table, String id) {
        int re = 0;

        dbk.setDBConnection(DBconnect_target);
        HashMap whereHM = new HashMap();
            whereHM.put("id", id);
        dbk.exeDelete(table, whereHM);
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

}
