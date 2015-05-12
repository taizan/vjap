package jp.taizan.android.vjap;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class ReversedSeekBar extends SeekBar {
	public ReversedSeekBar(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	protected void onDraw(Canvas c){
		float px = this.getWidth() / 2.0f;
		float py = this.getHeight() / 2.0f;
		c.scale(-1, 1, px, py);
		super.onDraw(c);
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event){
		event.setLocation(this.getWidth() - event.getX(), event.getY());
		return super.onTouchEvent(event);
	}
}