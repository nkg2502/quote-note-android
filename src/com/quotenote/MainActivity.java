package com.quotenote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.*;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ListView lv;
	private QuoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lv = (ListView) findViewById(R.id.listView);
        ArrayList<Quote> l = new ArrayList<Quote>();

        adapter = new QuoteAdapter(this, l);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();;
			}
		});

       String url = "http://quote-note.appspot.com/mobile/displayQuote";

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	
		new HttpAsyncTask().execute(url);
    }
    
    public static String GET(String url) {
    	InputStream inputStream = null;
    	String result = "";
    	try {
    		HttpClient httpClient = new DefaultHttpClient();
    		HttpGet get = new HttpGet(url);
    		get.setHeader("Content-Type", "text/json");
    		
    		HttpResponse httpResponse = httpClient.execute(get);
		
    		inputStream = httpResponse.getEntity().getContent();
    		
    		if(null != inputStream) {
    			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    			
    			String line = "";
    			
    			while(null != (line = br.readLine()))
    				result += line;
    			
    		} else
    			result = "BAD";
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    	}

    	return result;
    }
    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
    	
    	ArrayList<Quote> list = new ArrayList<Quote>();

		@Override
		protected String doInBackground(String... params) {
			
			try {
				JSONObject obj = new JSONObject(GET(params[0]));
				JSONArray array = obj.getJSONArray("quote_list");
				
				for(int i = 0; i < array.length(); ++i) {
					JSONObject quoteObject = array.getJSONObject(i);
					list.add(new Quote(false,
							quoteObject.getString("book_name"),
							quoteObject.getString("content"),
							quoteObject.getString("link"),
							quoteObject.getString("created"),
							quoteObject.getString("modified"),
							quoteObject.getString("user")));
				}
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return GET(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			adapter.addAll(list);
		}
    	
    }

}

class QuoteAdapter extends ArrayAdapter<Quote>{
	
	private LayoutInflater inflater;
	
	public QuoteAdapter(Context context, List<Quote> quoteList) {
		super(context, 0, quoteList);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(null == convertView) {
			convertView = inflater.inflate(R.layout.quote, null);
		} else {

		}
		
		Quote quote = null;
		if(null != (quote = this.getItem(position))) {
			TextView origin = (TextView) convertView.findViewById(R.id.origin); 
			TextView content = (TextView) convertView.findViewById(R.id.content); 
			TextView createdDate = (TextView) convertView.findViewById(R.id.createdDate); 
			TextView userName = (TextView) convertView.findViewById(R.id.userName); 
			
			origin.setText(quote.getOrigin());
			content.setText(quote.getContent());
			createdDate.setText(quote.getCreatedDate());
			userName.setText(quote.getUser());
		}

		return convertView;
	}
	
}