package com.woofwoof88.hypermiler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class About extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about);
	    
	     ((ImageView)findViewById(R.id.imageView1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(Intent.ACTION_VIEW);
				myIntent.setData(Uri.parse("http://www.hypermiler.co.uk"));
				startActivity(myIntent);
			}
		});
	}

	
}
