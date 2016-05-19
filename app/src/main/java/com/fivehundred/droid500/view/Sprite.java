package com.fivehundred.droid500.view;

import android.graphics.PointF;
import android.graphics.RectF;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.utils.ViewConstants;
import com.fivehundred.droid500.view.utils.ViewUtils;

public class Sprite {

    private RectF base;
    private RectF scaledBase;
    protected PointF translation;
    protected float angle;
    protected float scale;
    private float uvs[];
    private int index;
    private ShadowSprite shadow;
    private float scalar;
    private boolean triggerUvUpdate;

    public Sprite() {
        init();
    }
    
    public Sprite(int index, float ssu){   
        this.index = index;
        scalar = ssu;
        init(ssu);
    }

    public Sprite(float x, float y) {
        init(x,y);
    }
    
    private void init(){
        float cardWidth = ViewConstants.BASE_CARD_WIDTH;
        float cardHeight = ViewConstants.BASE_CARD_HEIGHT;
        // Left, Top, Right, Bottom relative to 0,0
        base = new RectF(-1 * cardWidth / 2, 
                         cardHeight / 2, 
                         cardWidth / 2, 
                         -1 * cardHeight / 2); 
        scaledBase = ViewUtils.copy(base);
        scale = 1f;
        angle = 0;
        translation = new PointF(ViewConstants.BASE_SCALE_WIDTH_PORTRAIT, ViewConstants.BASE_SCALE_HEIGHT_PORTRAIT);
    }
    
    private void init(float x, float y){
        init();
        translation = new PointF(x, y);
    }
    
    private void init(float ssu){
        init();
        setBaseScale(ssu);
        generateUvCoords(ViewConstants.CARD_BACK_INDEX);
        generateShadow(ssu);
        moveOffScreen(ViewConstants.RIGHT, ViewConstants.CENTER, ssu);
    }

    public void setBaseScale(float ssu) {
        scaledBase.left = base.left * ssu;
        scaledBase.top = base.top * ssu;
        scaledBase.right = base.right * ssu;
        scaledBase.bottom = base.bottom * ssu;
        translation.x *= ssu;
        translation.y *= ssu;
    }

    public void translate(float dX, float dY) {
        translation.x += dX;
        translation.y += dY;
        shadow.translate(dX, dY);
    }

    public void scale(float dS) {
        scale += dS;
        shadow.scale(dS);
    }

    public void rotate(float dA) {
        angle += dA;
        shadow.rotate(dA);
    }

    public float[] getVertices() {

        // Order of vertices manipulation: scale -> rotate -> translate
        // SCALE
        float x1 = scaledBase.left * scale;
        float x2 = scaledBase.right * scale;
        float y1 = scaledBase.bottom * scale;
        float y2 = scaledBase.top * scale;

		// ROTATE
        // Detach from Rect for rotation
        PointF one = new PointF(x1, y2);
        PointF two = new PointF(x1, y1);
        PointF three = new PointF(x2, y1);
        PointF four = new PointF(x2, y2); 
        
        float s = (float) Math.sin(Math.toRadians(angle));
        float c = (float) Math.cos(Math.toRadians(angle));
        one.x = x1 * c - y2 * s;
        one.y = x1 * s + y2 * c;
        two.x = x1 * c - y1 * s;
        two.y = x1 * s + y1 * c;
        three.x = x2 * c - y1 * s;
        three.y = x2 * s + y1 * c;
        four.x = x2 * c - y2 * s;
        four.y = x2 * s + y2 * c;

        // TRANSLATE
        one.x += translation.x;
        one.y += translation.y;
        two.x += translation.x;
        two.y += translation.y;
        three.x += translation.x;
        three.y += translation.y;
        four.x += translation.x;
        four.y += translation.y;

        return new float[]{
            one.x, one.y, 0.0f,
            two.x, two.y, 0.0f,
            three.x, three.y, 0.0f,
            four.x, four.y, 0.0f
        };
    }

    public void generateUvCoords(int imageIndex) {
        uvs = new float[8];
        //index = imageIndex;
        int size = Atlas.MAIN_ATLAS.getSize();
        int columns = Atlas.MAIN_ATLAS.getColumnCount();
        int rows = (int) Math.ceil(size / columns);
        float xOffset = 1.0f / columns;
        float yOffset = 1.0f / rows;
        float xMargin = 1.0f / 85.3f; // This value is dependent on the image, so consider passing in or creating subclass to handle it
        float x1 = xOffset * (imageIndex % columns) + xMargin;
        float x2 = x1 + xOffset - xMargin * 2;
        //float y1 = yOffset * (imageIndex % rows);
        float y1 = yOffset * (float)Math.floor(imageIndex/rows);
        float y2 = y1 + yOffset;

        // Top Left
        uvs[0] = x1;
        uvs[1] = y1;
        // Bottom Left
        uvs[2] = x1;
        uvs[3] = y2;
        // Bottom Right
        uvs[4] = x2;
        uvs[5] = y2;
        // Top Right
        uvs[6] = x2;
        uvs[7] = y1;
    }
    
    private void generateShadow(float ssu){
        shadow = new ShadowSprite();
        shadow.generateUvCoords(ViewConstants.CARD_SHADOW_INDEX);
        shadow.setBaseScale(ssu);
        shadow.setScale((float)(scale + scale * ViewConstants.SPRITE_SHADOW_SCALER));
        shadow.setTranslation(translateShadow(ssu));
    }
    
    private PointF translateShadow(float ssu){
        PointF shadowCoords = new PointF();
        shadowCoords.x = translation.x + 0.08f * ssu;
        shadowCoords.y = translation.y - 0.08f * ssu;
        return shadowCoords;
    }
    
    public void moveOffScreen(int xDirection, int yDirection, float ssu){
        float xOffset = xDirection * ViewConstants.BASE_SCALE_WIDTH_PORTRAIT * ssu;
        float yOffset = yDirection * ViewConstants.BASE_SCALE_HEIGHT_PORTRAIT * ssu;
        setTranslation(xOffset, yOffset);
        shadow.setTranslation(translateShadow(ssu));
    }
    
    public void center(float ssu){
        translation = new PointF(ViewConstants.BASE_SCALE_WIDTH_PORTRAIT * ssu / 2, ViewConstants.BASE_SCALE_HEIGHT_PORTRAIT * ssu / 2);
    }
    
    public void triggerUvUpdate(){
        triggerUvUpdate = true;
    }

    public PointF getTranslation() {
        return translation;
    }

    public void setTranslation(PointF translation) {
        this.translation = translation;
        shadow.setTranslation(translateShadow(scalar));
    }
    
    public void setTranslation(float x, float y){
        translation.x = x;
        translation.y = y;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        shadow.setAngle(angle);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        shadow.setScale(scale);
        // Simulate sprite in air by distancing the shadow
        shadow.setTranslation(translateShadow(scalar * scale));
    }

    public float[] getUvs() {
        return uvs;
    }

    public void setUvs(float uvs[]) {
        this.uvs = uvs;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ShadowSprite getShadow() {
        return shadow;
    }

    public void setShadow(ShadowSprite shadow) {
        this.shadow = shadow;
    }

    public float getScalar() {
        return scalar;
    }

    public void setScalar(float scalar) {
        this.scalar = scalar;
    }

    public boolean isTriggerUvUpdate() {
        return triggerUvUpdate;
    }

    public void setTriggerUvUpdate(boolean triggerUvUpdate) {
        this.triggerUvUpdate = triggerUvUpdate;
    }

}
