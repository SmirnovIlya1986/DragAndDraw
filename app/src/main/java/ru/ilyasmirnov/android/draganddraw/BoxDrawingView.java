package ru.ilyasmirnov.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {

    private static String INSTANCE_STATE = "instance_state";

    // private static String SAVED_CURRENT_BOX = "current_box";
    private static String SAVED_BOXEN = "boxen";
    // private static String SAVED_BOX_PAINT = "box_paint";
    // private static String SAVED_BACKGROUND_PAINT = "background_paint";

    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    // Используется при создании, представления в коде
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    // Используется при заполнении представления по разметке XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Прямоугольники рисуются полупрозрачным красным цветом (ARGB)
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        // Фон закрашивается серовато-белым цветом
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                // Сброс текущего состояния
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
        }

        Log.i(TAG, action + " at x=" + current.x +
                ", y=" + current.y);

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Заполнение фона
        canvas.drawPaint(mBackgroundPaint);

        for(Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);

        }


    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Log.i(TAG, " BoxDrawingView.onSaveInstanceState()");
        Log.i(TAG, " ");

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());

        /*
        Box[] arrayOfBoxen = new Box[mBoxen.size()];

        for (int i = 0; i < mBoxen.size(); i++) {
            arrayOfBoxen[i] = mBoxen.get(i);
        }

        for (int i = 0; i < arrayOfBoxen.length; i++) {
            Log.i(TAG, "arrayOfBoxen[" + i + "] : originX = " + arrayOfBoxen[i].getOrigin().x);
            Log.i(TAG, "arrayOfBoxen[" + i + "] : originY = " + arrayOfBoxen[i].getOrigin().y);
            Log.i(TAG, "arrayOfBoxen[" + i + "] : currentX = " + arrayOfBoxen[i].getCurrent().x);
            Log.i(TAG, "arrayOfBoxen[" + i + "] : currentY = " + arrayOfBoxen[i].getCurrent().y);
            Log.i(TAG, " ");
        }

        bundle.putParcelableArray(SAVED_BOXEN, arrayOfBoxen);

        */

        bundle.putParcelableArrayList(SAVED_BOXEN, (ArrayList<Box>) mBoxen);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        Log.i(TAG, " BoxDrawingView.onRestoreInstanceState()");
        Log.i(TAG, " ");

        if (state instanceof Bundle) {

            Bundle bundle = (Bundle) state;

            /*
            Box[] arrayOfBoxen = (Box[]) bundle.getParcelableArray(SAVED_BOXEN);

            mBoxen = new ArrayList<>();

            for (int i = 0; i < arrayOfBoxen.length; i++) {
                Log.i(TAG, "arrayOfBoxen[" + i + "] : originX = " + arrayOfBoxen[i].getOrigin().x);
                Log.i(TAG, "arrayOfBoxen[" + i + "] : originY = " + arrayOfBoxen[i].getOrigin().y);
                Log.i(TAG, "arrayOfBoxen[" + i + "] : currentX = " + arrayOfBoxen[i].getCurrent().x);
                Log.i(TAG, "arrayOfBoxen[" + i + "] : currentY = " + arrayOfBoxen[i].getCurrent().y);
                Log.i(TAG, " ");

                mBoxen.add(arrayOfBoxen[i]);
                }
                */

                mBoxen = bundle.getParcelableArrayList(SAVED_BOXEN);

                super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));

                // return;
        }
    }
}
