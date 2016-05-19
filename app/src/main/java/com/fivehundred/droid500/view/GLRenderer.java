package com.fivehundred.droid500.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.view.MotionEvent;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.game.Card;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.Player;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.animations.DealerAnimation;
import com.fivehundred.droid500.view.controllers.AnimationController;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.fivehundred.droid500.view.utils.ShaderUtils;
import com.fivehundred.droid500.view.utils.ViewConstants;
import com.fivehundred.droid500.view.utils.ViewListenerConstants;
import javax.inject.Inject;

public class GLRenderer implements Renderer {

    // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    // Buffer properties
    private short indices[];
    private FloatBuffer vertexBuffer;
    private FloatBuffer uvBuffer;
    private ShortBuffer drawListBuffer;

    // Our screen resolution
    private float screenWidth = 1280;
    private float screenHeight = 768;

    // Scale variables
    float ssu = 1.0f;
    float ssx = 1.0f;
    float ssy = 1.0f;
    float swp = ViewConstants.BASE_SCALE_WIDTH_PORTRAIT;
    float shp = ViewConstants.BASE_SCALE_HEIGHT_PORTRAIT;

    // Misc
    private Context context;
    private long lastTime;
    private List<Sprite> sprites = new ArrayList<>();
    
    @Inject AnimationController animation;

    public GLRenderer(Context c) {
        context = c;
        lastTime = System.currentTimeMillis() + 100;
        setupScaling();
    }

    public void onPause() {
        /* Do stuff to pause the renderer */
    }

    public void onResume() {
        /* Do stuff to resume the renderer */
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Get the current time
        long now = System.currentTimeMillis();

        // We should make sure we are valid and sane
        if (lastTime > now) {
            return;
        }

        // Get the amount of time the last frame took.
        long elapsed = now - lastTime;

        // Check listeners
        if(getGame() != null && !getGame().getListeners().isEmpty()){
            handleListeners(getGame().getListeners());
        }
        
        // Update our sprites
        updateSprites();

        // Render our view
        render(mtrxProjectionAndView);

        // Save the current time to see how long it took 
        lastTime = now;

    }
    
    public void loadIntoObjectGraph(){        
        MainApplication app = (MainApplication)(((MainActivity)context).getApplication());
        app.getMainComponent().inject(this);
    }

    public void createCardSprites(List<Card> cards) {
        for(Card card : cards){
            Sprite sprite = new Sprite(card.getUvIndex(), ssu);
            
            // This list should only hold sprites that need to be rendered (are currently on screen)
            //sprites.add(sprite);
            card.setSprite(sprite);            
        }
    }

    private void render(float[] m) {

        // clear Screen and Depth Buffer and set clear color to green
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.13f, 0.46f, 0.067f, 1.0f);

        // get handle to vertex shader's vPosition member and add vertices
        int mPositionHandle = GLES20.glGetAttribLocation(ShaderUtils.shaderImage, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Get handle to texture coordinates location and load the texture uvs
        int mTexCoordLoc = GLES20.glGetAttribLocation(ShaderUtils.shaderImage, "a_texCoord");
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);

        // Get handle to shape's transformation matrix and add our matrix
        int mtrxhandle = GLES20.glGetUniformLocation(ShaderUtils.shaderImage, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        // Get handle to textures locations
        int mSamplerLoc = GLES20.glGetUniformLocation(ShaderUtils.shaderImage, "s_texture");

        // Set the sampler texture unit to 0, where we have saved the texture.
        GLES20.glUniform1i(mSamplerLoc, 0);

        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // We need to know the current width and height.
        screenWidth = width;
        screenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int) screenWidth, (int) screenHeight);

        // Clear our matrices
        for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, screenWidth, 0.0f, screenHeight, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

        setupScaling();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        setupScaling();
        // Create the triangles
        setupTriangle();
        // Create the image information
        setupImage();

        // Set the clear color to black
        //GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

        // Blend
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        // Create the shaders for image
        int vertexShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, ShaderUtils.vs_Image);
        int fragmentShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderUtils.fs_Image);

        ShaderUtils.shaderImage = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(ShaderUtils.shaderImage, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(ShaderUtils.shaderImage, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(ShaderUtils.shaderImage);                  // creates OpenGL ES program executables

        // Set our shader program
        GLES20.glUseProgram(ShaderUtils.shaderImage);
    }

    private void setupTriangle() {
        setupVertices();
        setupIndices();
    }

    private void setupImage() {
        setupUvs();

        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        // Retrieve our image atlas from resources.
        int id = context.getResources().getIdentifier(Atlas.MAIN_ATLAS.getLocation(), null, context.getPackageName());

        // Temporary create a bitmap
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);

        // Bind texture to texture name
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        // We are done using the bitmap so we should recycle it.
        bmp.recycle();
    }

    private void setupVertices() {

        float vertices[] = new float[0];

        for (Sprite sprite : new ArrayList<>(sprites)) {
            vertices = combineArrays(vertices, sprite.getShadow().getVertices());
            vertices = combineArrays(vertices, sprite.getVertices());
        }

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    private void setupIndices() {

        // The order of vertex rendering for a quad
        indices = new short[sprites.size() * 6 * 2]; // Multiply by 2 to account for shadow sprites
        int last = 0;
        for (int i = 0; i < sprites.size()*2; i++) {
            // We need to set the new indices for the new quad
            indices[(i * 6) + 0] = (short) (last + 0);
            indices[(i * 6) + 1] = (short) (last + 1);
            indices[(i * 6) + 2] = (short) (last + 2);
            indices[(i * 6) + 3] = (short) (last + 0);
            indices[(i * 6) + 4] = (short) (last + 2);
            indices[(i * 6) + 5] = (short) (last + 3);

            // Our indices are connected to the vertices so we need to keep them
            // in the correct order.
            // normal quad = 0,1,2,0,2,3 so the next one will be 4,5,6,4,6,7
            last = last + 4;
        }

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }

    private void setupUvs() {
        float uvs[] = new float[0];

        for (Sprite sprite : new ArrayList<>(sprites)) {
            // Draw shadow UVs first so source object is drawn on top
            uvs = combineArrays(uvs, sprite.getShadow().getUvs());
            uvs = combineArrays(uvs, sprite.getUvs());
        }

        // The texture buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);
    }

    private void updateSprites() {
        setupVertices();
        boolean uvUpdateTriggered = false;
        for(Sprite sprite : sprites){
            if(sprite.isTriggerUvUpdate()){
                uvUpdateTriggered = true;
                break;
            }
        }
        if(uvUpdateTriggered){
            for(Sprite sprite : sprites){
                sprite.setTriggerUvUpdate(false);
            }
            setupUvs();
        }
    }
    
    public void processTouchEvent(MotionEvent event) {
        
    }
    
    public void dealCards(MainGame game){
        List<Sprite> cardSprites = new ArrayList<>();
        for(Card card : game.getDeck()){
            cardSprites.add(card.getSprite());
        }
        addSprites(cardSprites);
        DealerAnimation dealerAnimation = new DealerAnimation(game, ssu, context);
        injectIntoObjectGraph(dealerAnimation);
        dealerAnimation.dealCards();
    }
    
    /*public void sortCards(){
        for(Player player : getGame().getPlayers()){
            for(Card card : player.getCards()){
                PointF placeCoordinates = ViewUtils.getPlaceCoordinates(player.getPlayerIndex(), player.getCards().indexOf(card), ssu);
                card.getSprite().setTranslation(placeCoordinates);
            }
        }
        rebuildSprites();
    }*/
    
    public void buildCardSprites(List<Card> cards){
        sprites.clear();
        List<Sprite> builtSprites = new ArrayList<>();
        for(Card card : cards){
            builtSprites.add(card.getSprite());
        }
        addSprites(builtSprites);
    }
    
    private void rebuildSprites(){
        sprites.clear();
        List<Sprite> rebuiltSprites = new ArrayList<>();
        for(Player player : getGame().getPlayers()){
            for(Card card : player.getCards()){
                rebuiltSprites.add(card.getSprite());
            }
        }
        for(Card card : getGame().getKitty()){
            rebuiltSprites.add(card.getSprite());
        }
        addSprites(rebuiltSprites);
    }
    
    private float[] combineArrays(float[] a, float[] b) {
        float result[] = new float[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private void setupScaling() {
        // Screen resolution
        swp = (int) (context.getResources().getDisplayMetrics().widthPixels);
        shp = (int) (context.getResources().getDisplayMetrics().heightPixels);

        if (swp < shp) {
            // Portrait orientation
            ssx = swp / 320.0f;
            ssy = shp / 480.0f;
        } else {
            // Landscape orientation
            ssx = swp / 480.0f;
            ssy = shp / 320.0f;
        }

        if (ssx > ssy) {
            ssu = ssy;
        } else {
            ssu = ssx;
        }
    }
    
    private void addSprite(Sprite sprite){
        if(!sprites.contains(sprite)){
            sprites.add(sprite);
            setupTriangle();
            setupUvs();
        }
    }
    
    private void addSprites(List<Sprite> sprites){
        this.sprites.addAll(sprites);
        setupTriangle();
        setupUvs();
    }
    
    private void injectIntoObjectGraph(Object object){
        MainApplication app = (MainApplication)((MainActivity)context).getApplication();
        app.getMainComponent().inject(object);
    }
    
    private void handleListeners(List<Integer> listeners){
        for(int listener : listeners){
            switch(listener){
                case ViewListenerConstants.REBUILD_SPRITES:
                    rebuildSprites();
                    break;
                default:
                    Logger.logError("handleListeners failed -- No handler found for " + listener);
            }
        }
        listeners.clear();
    }
    
    private MainGame getGame(){
        return ((MainActivity)context).getGame();
    }
}
