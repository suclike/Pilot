package com.kodroid.pilot.lib.android.frameBacking;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.kodroid.pilot.lib.stack.PilotFrame;

/**
 * Convenience FrameLayout base for BackedByFrame backed views. Custom views do not have to use this as
 * long as they conform to the {@link PilotFrameBackedUI} interface.
 *
 * @param <P> The BackedByFrame (controller / whatever) you want to use for this view. Support for thin
 *           views but easily usable with fragments.
 */
public abstract class PilotFrameLayout<P extends PilotFrame> extends FrameLayout implements PilotFrameBackedUI<P>,PilotFrame.Observer {
    //==================================================================//
    // Fields
    //==================================================================//

    private P mBackingPilotFrame;

    //==================================================================//
    // Constructor
    //==================================================================//

    public PilotFrameLayout(Context context)
    {
        super(context);
    }

    public PilotFrameLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PilotFrameLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //==================================================================//
    // BackedByFrame IOC
    //==================================================================//

    /**
     * Subclasses can override this but will need to call super(). If any child views use child Presenters
     * these can be grabbed from this (parent) PilotFrame on this method call.
     *
     * @param backingPilotFrame
     * @return
     */
    public View setBackingPilotFrame(P backingPilotFrame)
    {
        mBackingPilotFrame = backingPilotFrame;
        return this;
    }

    protected P getBackingPilotFrame()
    {
        return mBackingPilotFrame;
    }

    @Override
    public void backingFrameSet(P backingPilotFrame) {
        //do nothing - subclasses can override
    }

    //==================================================================//
    // View lifecycle
    //==================================================================//

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getBackingPilotFrame().addObserver(this, true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getBackingPilotFrame().removeObserver(this);
    }

    //==================================================================//
    // Observer updated()
    //==================================================================//

    @Override
    public void updated(){};
}
