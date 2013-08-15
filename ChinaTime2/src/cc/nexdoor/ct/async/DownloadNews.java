package cc.nexdoor.ct.async;

import java.net.URL;

import android.os.AsyncTask;

public class DownloadNews extends AsyncTask<URL, Integer, Long> {

	@Override
	protected Long doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		 int count = urls.length;
         long totalSize = 0;
         for (int i = 0; i < count; i++) {
         }
         return totalSize;
	}

}
