import java.io.*;
import java.util.*;
   public class DeserializeDemo
   {
      public static void main(String [] args)
      {
         Map<String, Object> map = null;
         try
         {
            FileInputStream fileIn = new FileInputStream("employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (Map) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ioe){
        	System.out.println("IO Error");
        	ioe.printStackTrace();
            return;
        }catch(ClassNotFoundException cnfe){
            System.out.println("Map<String, Object> class not found");
            cnfe.printStackTrace();
            return;
        }
        System.out.println("Deserialized Map<String, Object>...");
        
        for( String key: map.keySet() ){
            System.out.println(key.toString() + ": " + map.get( key ));
        }
        

    }
}