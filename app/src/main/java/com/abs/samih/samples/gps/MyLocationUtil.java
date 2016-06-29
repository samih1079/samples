package com.abs.samih.samples.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.util.Log;

/*

manifest:
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

 */
public class MyLocationUtil extends Service implements LocationListener
{
	private Context context;
	private boolean isGPSEnabled = false, isNetworkEnabled = false;
	private boolean canGetLoaction = false;
	private Location location;
	private double lat, lng;

	private int minDistance = 10;// distance [m];
	private int minTime = 5 * 1000;// time [ms]

	private LocationManager locationMngr;

	public MyLocationUtil()
	{
		super();
	}

	public MyLocationUtil(Context context)
	{
		this.context = context;
		getLoaction();
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		this.context = getApplicationContext();
		getLoaction();

	}

	@Override
	public void onDestroy()
	{
		if (locationMngr != null)
			locationMngr.removeUpdates(this);
		super.onDestroy();
	}

	public  Location getLoaction()
	{
		locationMngr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationMngr
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationMngr
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isGPSEnabled && !isNetworkEnabled)
		{
			canGetLoaction = false;
		} else
		{
			canGetLoaction = true;
			if (isNetworkEnabled)
			{
				locationMngr.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, minTime, minDistance,
						this);
				Log.d("LOC", "Network location requested");
				location = locationMngr
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if(location!=null)
				{
					lat=location.getLatitude();
					lng=location.getLongitude();
				}
			}
			if (isGPSEnabled)
			{
				locationMngr.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, minTime, minDistance,
						this);
				Log.d("LOC", "GPS location requested");
				location = locationMngr
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if(location!=null)
				{
					lat=location.getLatitude();
					lng=location.getLongitude();
				}
			}
			
		}
		return location;

	}	

	@Override
	public void onLocationChanged(Location location)
	{
		this.location=location;

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

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
