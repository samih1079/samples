package com.abs.samih.samples.touch;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MultiTouchView extends View implements OnTouchListener
{
	private Drawable shape;
	private HashMap<Integer, PointF> touchPoints;

	public MultiTouchView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setOnTouchListener(this);
		touchPoints = new HashMap<Integer, PointF>();
		shape = context.getResources().getDrawable(R.drawable.ic_launcher);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event)
	{
		int action = event.getActionMasked();
		switch (action)
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			addTouchDown(event);			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			removeTouchDown(event);			
			break;
		case MotionEvent.ACTION_MOVE:
			updateTouchDownMoves(event);
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}



	private void addTouchDown(MotionEvent event)
	{
		int index=event.getActionIndex();
		int id=event.getPointerId(index);
		PointF p=new PointF(event.getX(index),event.getY(index));
		touchPoints.put(id, p);	
	}
	private void removeTouchDown(MotionEvent event)
	{
		int index=event.getActionIndex();
		int id=event.getPointerId(index);
		touchPoints.remove(id);
		
	}
	private void updateTouchDownMoves(MotionEvent event)
	{
		int len=event.getPointerCount();
		int id;
		for (int i = 0; i < len; i++)
		{
			id=event.getPointerId(i);
			try
			{
			touchPoints.get(id).set(event.getX(i),event.getY(i));
			}catch (IndexOutOfBoundsException exception)
			{
				exception.printStackTrace();
			}
			
		}			
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		if(!touchPoints.isEmpty())
		{
			Iterator<Integer> iterator=touchPoints.keySet().iterator();
			while (iterator.hasNext())
			{
				int key = iterator.next();
				int x=(int) touchPoints.get(key).x;
				int y=(int)touchPoints.get(key).y;
				
				shape.setBounds(x-80, y-80, x+80, y+80);
				shape.draw(canvas);
				
				Paint paint=new Paint();
				paint.setColor(Color.WHITE);
				paint.setTextSize(44);
				canvas.drawText(key+"", x-10, y+10, paint);
			}
		}
		super.onDraw(canvas);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
