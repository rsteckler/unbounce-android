package com.ryansteckler.nlpunbounce;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by rsteckler on 9/9/14.
 */
public class ExpandingLayout extends FrameLayout {

    private int mStartTop;
    private int mFinalTop;
    private int mStartBottom;
    private int mFinalBottom;

    public ExpandingLayout(Context context) {
        super(context);
    }

    public ExpandingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAnimationBounds(int startTop, int finalTop, int startBottom, int finalBottom)
    {
        mStartBottom =  startBottom;
        mStartTop = startTop;
        mFinalBottom = finalBottom;
        mFinalTop = finalTop;
    }

    public float getYFraction() {
        if (getHeight() == 0)
            return 0;
        return getY() / getHeight();
    }

    public void setYFraction(float yFraction) {

        if (yFraction <= 0) {
            setY(mStartTop);
            setBottom(mStartBottom - mStartTop);
            setAlpha((float) ((-0.2 - yFraction) * -5));
        }
        else if (yFraction > 0)
        {
            setAlpha(1);

            int newY = (int)(mStartTop - ((mStartTop - mFinalTop) * yFraction));
            setY(newY);
            int newBottom = (int)(mStartBottom + ((mFinalBottom - mStartBottom) * yFraction));
            this.setBottom(newBottom - newY);
        }

        if (yFraction > -0.2 && getVisibility() == INVISIBLE)
        {
            setVisibility(View.VISIBLE);
        }
        else if (yFraction <= -0.2)
            setVisibility(View.INVISIBLE);
    }
}