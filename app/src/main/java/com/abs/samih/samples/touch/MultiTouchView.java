package com.abs.samih.samples.touch;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import com.abs.samih.samples.R;

public class MultiTouchView extends View implements View.OnTouchListener {

	private Drawable shape;

	HashMap<Integer, PointF> touchPoints = new HashMap<Integer, PointF>();

	protected Drawable getShape() {
		if (shape == null) {
			shape = getContext().getResources().getDrawable(
					R.mipmap.ic_launcher);
		}
		return shape;
	}

	public MultiTouchView(Context context) {
		super(context);
		setOnTouchListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getActionMasked();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			captureDown(event);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			captureUp(event);
			break;
		case MotionEvent.ACTION_MOVE:
			capturePointerMoves(event);
			break;
		}
		invalidate();
		return true;
	}

	private void captureDown(MotionEvent event) {
		int index = event.getActionIndex();
		int id = event.getPointerId(index);
		touchPoints.put(id, new PointF(event.getX(index), event.getY(index)));
	}

	private void captureUp(MotionEvent event) {
		int index = event.getActionIndex();
		int id = event.getPointerId(index);
		touchPoints.remove(id);
	}

	private void capturePointerMoves(MotionEvent event) {
		int length = event.getPointerCount();
		int id;
		for (int i = 0; i < length; i++) {
			id = event.getPointerId(i);
			try {
				touchPoints.get(id).set(event.getX(i), event.getY(i));
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (touchPoints.size() > 0) {
			int radius = 80; // Size of finger indicator
			
			Iterator<Integer> iterator = touchPoints.keySet().iterator();
			while (iterator.hasNext()) {
				int key = iterator.next();
				
				PointF point = touchPoints.get(key);
				int x = (int) point.x;
				int y = (int) point.y;
				
				Rect bounds = new Rect();
				bounds.set(x - radius, y - radius, x + radius, y + radius);
				
				getShape().setBounds(bounds);
				getShape().draw(canvas);

				Paint paint = new Paint();
				paint.setColor(Color.WHITE);
				paint.setTextSize(48);
				canvas.drawText(String.valueOf(key), x - 12, y + 12, paint);
			}
			
		}
		

		super.onDraw(canvas);
	}

}