package com.gatchi.notebooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * A special view for containing sketches.
 */
public class DrawingView extends View {

    /// initial color
    private static final int DEFAULT_PAINT_COLOR = Color.BLACK;
    /// drawing path
    private Path drawPath;
    /// drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    /// canvas
    private Canvas drawCanvas;
    /// canvas bitmap
    private Bitmap canvasBitmap;
    /// brush size
    private float brushSize;
    /// erase mode
    private boolean erase;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

	/** Settings */
    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();

        setBrushSize(10);

        drawPaint.setColor(DEFAULT_PAINT_COLOR);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (canvasBitmap == null) {
            canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            drawCanvas = new Canvas(canvasBitmap);
        }
        else {
            canvasBitmap = Bitmap.createBitmap(canvasBitmap);
            canvasBitmap = canvasBitmap.copy(Bitmap.Config.ARGB_8888, true);
            drawCanvas = new Canvas(canvasBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;

        }
        invalidate();
        return true;
    }

	
    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();  // forces the view to redraw
    }

	/**
	 * Set paint color.
	 * Set paint color using Android Color system.
	 * @todo Explain in docs how Android Color system works.
	 */
    public void setPaintColor(int paintColor) {
        drawPaint.setColor(paintColor);
    }

	/**
	 * Set brush size.
	 * @todo Docs: uses a float - but what do those values map to?
	 */
    public void setBrushSize(float size) {
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                size, getResources().getDisplayMetrics());
        drawPaint.setStrokeWidth(brushSize);

    }

	/**
	 * Changes paintbrush to eraser.
	 * @todo Make erase immediate (currently doesnt erase until finger is lifted)
	 */
    public void setErase(boolean isErase){
        erase = isErase;

        if(erase) {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else {
            drawPaint.setXfermode(null);
        }
    }

	/**
	 * Retrieves the canvas bitmap.
	 * Used by NoteActivity::saveOrUpdateNote.
	 */
    public Bitmap getCanvasBitmap() {
        return canvasBitmap;
    }

	/**
	 * Replaces canvas bitmap with already created one.
	 * Used by NoteActivity::loadNote.
	 */
    public void setBitmap(Bitmap bmp) {
        canvasBitmap = bmp;
    }
}
