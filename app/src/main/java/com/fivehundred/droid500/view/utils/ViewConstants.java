package com.fivehundred.droid500.view.utils;

public class ViewConstants{
    
    public static final int ACE_ROW = 5;
    public static final int JOKER_UV_INDEX = 44;
    public static final int CARD_SHADOW_INDEX = 46;
    public static final int CARD_BACK_INDEX = 45;
    
    public static final float BASE_CARD_WIDTH = 40.0f;
    public static final float BASE_CARD_HEIGHT = 60.0f;
    
    public static final float BASE_SCALE_WIDTH_PORTRAIT = 320.0f;
    public static final float BASE_SCALE_HEIGHT_PORTRAIT = 480.0f;
    public static final float BASE_SCALE_WIDTH_LANDSCAPE = 320.0f;
    public static final float BASE_SCALE_HEIGHT_LANDSCAPE = 480.0f;
    public static final float BASE_SCALE_MIN = 320.0f;

    public static final float KITTY_OFFSET_SCALAR = 2.5f;
    public static final float FOCUS_SCALE = 0.25f;
    public static final int FOCUS_MULTIPLIER = 1;
    public static final int UNFOCUS_MULTIPLIER = -1;
    
    public static final int LEFT = -2;
    public static final int TOP = 2;
    public static final int RIGHT = 2;
    public static final int BOTTOM = -2;
    public static final int CENTER = 1;
    
    public static final float SPRITE_SHADOW_SCALER = 0.02f;
    
    // Score Grid
    public static final String[] SCORE_GRID = new String[]{
                    " ",   "S",   "C",   "D",   "H",   "NT",
                    "6",  "40",  "60",  "80",  "100", "120",
                    "7",  "140", "160", "180", "200", "220",
                    "8",  "240", "260", "280", "300", "320",
                    "9",  "340", "360", "380", "400", "420",
                    "10", "440", "460", "480", "500", "520"};
    
    public static final String[] BID_VALUES = new String[]{"6", "7", "8", "9", "10"};
    public static final String[] PLAYER_VALUES = new String[]{"Player 1", "Player 2", "Player 3", "Player 4"};
    
    public static final String PASSED = "PASSED";
}