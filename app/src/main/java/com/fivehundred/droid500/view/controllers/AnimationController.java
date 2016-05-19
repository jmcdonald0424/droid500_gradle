package com.fivehundred.droid500.view.controllers;

import android.animation.ObjectAnimator;
import android.graphics.PointF;
import com.fivehundred.droid500.view.PointEvaluator;
import javax.inject.Inject;

public class AnimationController{

    @Inject
    public AnimationController(){

    }
    
    public ObjectAnimator translate(Object sprite, PointF end){        
        return ObjectAnimator.ofObject(sprite, "translation", new PointEvaluator(), end);
    }
    
    public ObjectAnimator rotate(Object sprite, float angle){        
        return ObjectAnimator.ofFloat(sprite, "angle", angle);
    }
    
    public ObjectAnimator rotate(Object sprite, float startAngle, float endAngle){        
        return ObjectAnimator.ofFloat(sprite, "angle", startAngle, endAngle);
    }
    
    public ObjectAnimator scale(Object sprite, float startScale, float endScale){
        return ObjectAnimator.ofFloat(sprite, "scale", startScale, endScale);
    }
}