/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author leo
 */
public class JsonParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String stringInput = readFileAsString("/Users/leo/test2.json");
        stringInput = stringInput.replaceAll("\\:\\[\\]", "\\:\\\"NULL\\\"");
        jsonTestFile(stringInput);
        
        
    }

    public static void jsonTestFile(String stringInput) {
        ArrayList<Map<String, Object>> resultAL = new ArrayList<Map<String, Object>>();
        ArrayList<ArrayList<Map<String, Object>>> subAL = new ArrayList<ArrayList<Map<String, Object>>>();
        for( int i = 0 ;stringInput.contains(":[{") ; i++ ){
            int start = stringInput.indexOf(":[{");
            int end = stringInput.indexOf("}]");
            //ArrayList<Map<String, Object>> resultALTemp = jsonTestString( stringInput.substring( start+1, end+2 ) );
            subAL.add(jsonTestString( stringInput.substring( start+1, end+2 ), null ));
            
            stringInput = stringInput.substring(0, start+1) + "\"ARRAYLIST"+i+"\"" + stringInput.substring(end+2, stringInput.length());
            //System.out.println(stringInput);
        }
        resultAL = jsonTestString(stringInput, subAL);
        printALHM(resultAL);


    }

    //Write to Recoursive
    public static ArrayList<Map<String, Object>> jsonTestString(String stringInput, ArrayList<ArrayList<Map<String, Object>>> subAL) {
        //stringInput = stringInput.replaceAll("\\\n", "");
        //System.out.println(stringInput);
        ArrayList<Map<String, Object>> resultAL = new ArrayList<Map<String, Object>>();
        try {
                ObjectMapper mapper = new ObjectMapper();
                if ( stringInput.contains("},{")){
                    stringInput = stringInput.substring( stringInput.indexOf("[")+1, stringInput.lastIndexOf("}]")+1 );
                    //System.out.println(stringInput);
                    // Add new split tag : "_,_"
                    stringInput = stringInput.replaceAll("\\}\\,\\{", "\\}\\_\\,\\_\\{");
                }
                
                String[] tempArray = stringInput.split("\\_\\,\\_");
                for (int i = 0; i < tempArray.length; i++) {
                    //System.out.println(tempArray[i]);
                    Map<String, Object> resultMap = mapper.readValue(tempArray[i], new TypeReference<Map<String, Object>>() {});
                    
                    if ( subAL != null ){
                        int subALIndex = 0;
                        Iterator it = resultMap.keySet().iterator();
                        while( it.hasNext() ){
                            String key = it.next().toString();
                            String value = resultMap.get(key).toString();
                            //System.out.println(key + " : "+ value);
                            if ( value.indexOf("ARRAYLIST") != -1 ){
                                //System.out.println("Here");
                                resultMap.put(key, subAL.get(subALIndex));
                                subALIndex++;
                            }
                        }
                    }
                    
                    
                    resultAL.add(resultMap);
                }
                
                //printALHM(resultAL);
                return resultAL;

            } catch (Exception e) {
                e.printStackTrace();
            }
        
        
        return resultAL;
        
    }

    public static String printALHM(ArrayList<Map<String, Object>> resultAL) {
        String result = null;
        // Check Value
        for (Iterator ita = resultAL.iterator(); ita.hasNext();) {
            Map<String, Object> resultMap = (Map<String, Object>) ita.next();
            for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
                String key = it.next().toString();
                String value = resultMap.get(key).toString();
                Object obj = resultMap.get(key);
                //System.out.println(obj.getClass().getName());
                if ( obj.getClass().getName() == "java.util.ArrayList" ){
                    System.out.println(key + " ::: ");
                    printALHM((ArrayList<Map<String, Object>>) obj);
                }else{
                    System.out.println(key + " : " + value);
                }
                
                result = key + " : " + value + "\n";
            }
            System.out.println();
        }
        return result;
    }

    public static void jsonOriginal(String jsonPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //String stringInput = readFileAsString("/Users/leo/test.json");
            String stringInput = readFileAsString(jsonPath);
            //System.out.println(stringInput);
            stringInput = stringInput.replaceAll("\\[", "");
            stringInput = stringInput.replaceAll("\\]", "");
            // Add new split tag : "_,_"
            stringInput = stringInput.replaceAll("\\}\\,\\{", "\\}\\_\\,\\_\\{");

            String[] tempArray = stringInput.split("\\_\\,\\_");

            ArrayList<Map<String, Object>> resultAL = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < tempArray.length; i++) {
                //System.out.println(tempArray[i]);
                Map<String, Object> resultMap = mapper.readValue(tempArray[i], new TypeReference<Map<String, Object>>() {
                });
                resultAL.add(resultMap);
            }


            // Check Value
            for (Iterator ita = resultAL.iterator(); ita.hasNext();) {
                Map<String, Object> resultMap = (Map<String, Object>) ita.next();
                for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
                    String key = it.next().toString();
                    String value = resultMap.get(key).toString();
                    System.out.println(key + " : " + value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFileAsString(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();

        } catch (IOException ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
}
