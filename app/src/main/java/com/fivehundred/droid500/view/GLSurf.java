package com.fivehundred.droid500.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import com.fivehundred.droid500.game.MainGame;

public class GLSurf extends GLSurfaceView {

    private final GLRenderer renderer;

    public GLSurf(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        renderer = new GLRenderer(context);
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        renderer.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        renderer.onResume();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        if(e != null){
            if(e.getAction() == MotionEvent.ACTION_DOWN){
                if(renderer != null){
                    queueEvent(new Runnable() {
                        @Override
                        public void run() {
                            renderer.processTouchEvent(e);
                        }
                    });
                    requestRender();
                    return true;
                }
            }
        }
        return false;
    }
    
    public void dealCards(MainGame game){
        renderer.dealCards(game);
    }

    public GLRenderer getRenderer() {
        return renderer;
    }
}
