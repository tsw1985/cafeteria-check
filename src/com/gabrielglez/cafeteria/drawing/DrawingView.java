package com.gabrielglez.cafeteria.drawing;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
	
	private Paint mPaint;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 1;
	

	public DrawingView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(1);

		mPath = new Path();
		mBitmapPaint = new Paint();
		mBitmapPaint.setColor(Color.RED);
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		w = 800;
		h = 200;
		
		oldw = 800;
		oldh = 200;
		
		super.onSizeChanged(w, h, oldw, oldh);
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
			
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		canvas.drawPath(mPath, mPaint);
	}



	private void touch_start(float x, float y) {
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		mCanvas.drawPath(mPath, mPaint);
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}
	
	 public Bitmap getBitmap()
	 {
	    this.setDrawingCacheEnabled(true);  
	    this.buildDrawingCache();
	    Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());   
	    this.setDrawingCacheEnabled(false);
	    return bmp;
	 }
	 
	 
	 public boolean saveBitMap(Bitmap bitmap , String fileName){
		 
		 try {

		   File file = new File("/" + fileName + ".png");
		   file.createNewFile();
		   FileOutputStream out = new FileOutputStream(file);
		   bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		   
		   Log.v("draw", "Imagen creada");
		   return true;
		   
		  } catch (Exception e) {
			  Log.v("draw", "Error al crear imagen " + e.toString() );
			  return false;
		  }

	 }
}