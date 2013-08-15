package leo.android.parseJason;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
 
public class ParseJSONActivity extends Activity {
    private String JSONString;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        try {
            JSONEncode();
            JSONDecode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 
    private void JSONEncode() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        String[] name = { "Android", "Ben", "Chris", "David", "Eric", "Frankie", "Gary", "Henry", "Ivan", "Joe" };
        int[] id = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] score = { 4.8, 3.2, 2.1, 4.3, 2.0, 0.9, 1.4, 2.8, 3.4, 0.1 };

 
        for (int i = 0; i < name.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name[i]);
            jsonObject.put("id", id[i]);
            jsonObject.put("score", score[i]);
            jsonArray.put(jsonObject);
        }
 
        Log.i("JSON String", jsonArray.toString());
        JSONString = jsonArray.toString();
    }
 
    private void JSONDecode() throws JSONException {
        JSONArray jsonArray = new JSONArray(JSONString);
        Log.i("Number of Entries", Integer.toString(jsonArray.length()));
 
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator it = jsonObject.keys();
            
            while(it.hasNext()){
            	String key = it.next().toString();
            	Log.i("IT:Key", key);
            	Log.i("IT:Key", jsonObject.getString(key) );
            }
            
            
            /*
            String name = jsonObject.getString("name");
            int id = jsonObject.getInt("id");
            double score = jsonObject.getDouble("score");
            Log.i("Entry", "name: " + name + ", id: " + id + ", score: " + score);
            */
        }
    }
 
}
