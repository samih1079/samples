package com.abs.samih.samples.touch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.abs.samih.samples.R;

public class TouchEvntActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		SingleTouchEventView singleTouchEventView = new SingleTouchEventView(
				getApplicationContext(), null);
		
		MultiTouchView multiTouchView=new MultiTouchView(getApplicationContext());
		setContentView(multiTouchView);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.touch_evnt, menu);
//		return true;
//	}

}
