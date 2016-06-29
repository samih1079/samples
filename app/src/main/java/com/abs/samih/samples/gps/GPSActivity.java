package com.abs.samih.samples.gps;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

import com.abs.samih.samples.R;

public class GPSActivity extends Activity
{
	private TextView tvLat, tvLng, tvAddress;
	// 0 add access fine location permission
	// 1
	private LocationManager locationManager;
	// 4.1
	private MyLocLis locLis;

	private Geocoder geocoder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_gps);
//		tvLat = (TextView) findViewById(R.id.tvLat);
//		tvLng = (TextView) findViewById(R.id.tvlng);
//		tvAddress = (TextView) findViewById(R.id.tvAddress);
		// 2
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 4 (//3 write the location listener)
		locLis = new MyLocLis();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 20, locLis);
		geocoder=new Geocoder(getApplicationContext());

	}

	// 3
	private class MyLocLis implements LocationListener
	{

		@Override
		public void onLocationChanged(Location location)
		{
			tvLat.setText("Lat:" + location.getLatitude());
			tvLng.setText("Lng:" + location.getLongitude());

			try
			{
				List<Address> addresses = geocoder.getFromLocation(
						location.getLatitude(), location.getLongitude(), 2);
				tvAddress.setText("Addresses:"+addresses.size());
				for (Address address : addresses)
				{
					tvAddress.append(address.getAddressLine(0));
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onProviderDisabled(String provider)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			// TODO Auto-generated method stub

		}

	}

	// 5
	@Override
	protected void onDestroy()
	{
		locationManager.removeUpdates(locLis);
		super.onDestroy();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.g, menu);
//		return true;
//	}

}
