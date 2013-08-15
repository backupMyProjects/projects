import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SerializeDemo
{
   public static void main(String [] args)
   {
	   LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
	   //TreeMap<String, Object> map = new TreeMap<String, Object>();
	   //ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	   map.put("key1", "Hello");
	   map.put("key2", "I am");
	   map.put("key3", "Leo......");

	   Map tt = Collections.synchronizedMap(map);
      try
      {
         FileOutputStream fileOut =
         new FileOutputStream("employee.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(tt);
         //out.writeObject(map);
         out.close();
         fileOut.close();
         System.out.println("done");
      }catch(IOException i)
      {
          i.printStackTrace();
      }
   }
   

}