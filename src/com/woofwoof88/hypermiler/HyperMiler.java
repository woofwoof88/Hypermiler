package com.woofwoof88.hypermiler;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HyperMiler extends Activity {
    private static final String PUBLISHER_ID= "a14d88c82f8d1af";
    @SuppressWarnings("unused")
	private static final double LITRE_GALLON =  0.219969157;
    @SuppressWarnings("unused")
	private static final double GALLONS_LITRES = 4.54609188;
    @SuppressWarnings("unused")
	private static final double MILE_KILOMETERS = 0.621371192;
    private static final double US_CONVERTER = 1.200949857;

	/** Called when the activity is first created. */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.mnAbout:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
			
		default:
			break;
		}
		
		return false;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		AdView adView = new AdView(this, AdSize.BANNER, PUBLISHER_ID);
		((LinearLayout)findViewById(R.id.Admob)).addView(adView);
		adView.loadAd(new AdRequest());
		
		
		OnKeyListener okl = new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // If there is text in the query box, handle enter, and action keys
	            // The search key is handled by the dialog's onKeyDown(). 
                if (keyCode == KeyEvent.KEYCODE_ENTER 
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                	doCalc();
                    return true;
                }
	            return false;
			}
		};
		((EditText)findViewById(R.id.LitresFuelUsed)).setOnKeyListener(okl);
		((EditText)findViewById(R.id.CostPerLitre)).setOnKeyListener(okl);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((EditText)findViewById(R.id.DistanceCovered)).setText("");
				((EditText)findViewById(R.id.DistanceCovered)).requestFocus();
				
				((EditText)findViewById(R.id.LitresFuelUsed)).setText("");
				((EditText)findViewById(R.id.CostPerLitre)).setText("");
				
				((TextView)findViewById(R.id.MPG)).setText(".................");
				((TextView)findViewById(R.id.KMPerLitre)).setText(".................");
				((TextView)findViewById(R.id.PencePerMile)).setText(".................");
				((TextView)findViewById(R.id.US_MPG)).setText(".................");
				
				
			}
		});
		
	}
	
	private void doCalc()
	{
		
		EditText et = (EditText)findViewById(R.id.CostPerLitre);
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);		
		
    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		String sDistanceCovered =  ((EditText)findViewById(R.id.DistanceCovered)).getText().toString();
		String sLitresFuelUsed=  ((EditText)findViewById(R.id.LitresFuelUsed)).getText().toString();
		String sCostPerLitre =  ((EditText)findViewById(R.id.CostPerLitre)).getText().toString();
		
		try {
			double DistCovered = Float.valueOf(sDistanceCovered).floatValue();
			double LitresUsed= Float.valueOf(sLitresFuelUsed).floatValue();
			
			double mpg = ((454.6 * DistCovered) / LitresUsed) / 100;
			double KMperLitre = ((DistCovered*1.609/LitresUsed)*100)/100;
			double us_mpg = ( mpg / US_CONVERTER);

			
			
			TextView widget = (TextView)findViewById(R.id.MPG);
			widget.setTextColor(Color.BLACK);
			widget.setText(strPre(mpg));
			
			widget = (TextView)findViewById(R.id.KMPerLitre);
			widget.setTextColor(Color.BLACK);
			widget.setText(strPre(KMperLitre));
			
			if (isNumeric(sCostPerLitre) )  {
				double CostPerLitre = Float.valueOf(sCostPerLitre).floatValue();
				double ppm = ((CostPerLitre * LitresUsed) / DistCovered);
				widget = (TextView)findViewById(R.id.PencePerMile);
				widget.setTextColor(Color.BLACK);
				widget.setText(strPre(ppm));
			}
			
			widget = (TextView)findViewById(R.id.US_MPG);
			widget.setTextColor(Color.BLACK);
			widget.setText(strPre(us_mpg));
			
		} catch (Exception e ) {
			//Log.d("HYPERM", e.getMessage());
		}
	}
	
	public String strPre(double inValue) {
		return Double.toString(((int)(inValue * 100))/100.0);
	}
	
	public static boolean isNumeric(String str )
	{
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}