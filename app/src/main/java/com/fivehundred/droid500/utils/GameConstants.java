package com.fivehundred.droid500.utils;

public final class GameConstants{
    
    // Game Related Values :: These will most likely be dynamic user options at some point
    public static final int LOWEST_POWER = 4; // 4 is lowest card in play
    public static final int NUMBER_OF_PLAYERS = 4;
    
    // Constant Values :: These will remain constants
    
    public static final String SPADES = "S";
    public static final String CLUBS = "C";
    public static final String DIAMONDS = "D";
    public static final String HEARTS = "H";
    public static final String JOKER = "J";
    public static final String NO_TRUMP = "N";
    public static final String SUITS[] = {SPADES, CLUBS, DIAMONDS, HEARTS};
    
    public static final int SPADES_POWER = 0;
    public static final int CLUBS_POWER = 1;
    public static final int DIAMONDS_POWER = 2;
    public static final int HEARTS_POWER = 3;
    
    public static final String SPADES_LABEL = "Spades";
    public static final String CLUBS_LABEL = "Clubs";
    public static final String DIAMONDS_LABEL = "Diamonds";
    public static final String HEARTS_LABEL = "Hearts";
    public static final String JOKER_LABEL = "Joker";
    public static final String ACE_LABEL = "Ace";
    public static final String KING_LABEL = "King";
    public static final String QUEEN_LABEL = "Queen";
    public static final String JACK_LABEL = "Jack";
    public static final String TEN_LABEL = "Ten";
    public static final String NINE_LABEL = "Nine";
    public static final String EIGHT_LABEL = "Eight";
    public static final String SEVEN_LABEL = "Seven";
    public static final String SIX_LABEL = "Six";
    public static final String FIVE_LABEL = "Five";
    public static final String FOUR_LABEL = "Four";
    
    public static final int JACK_POWER = 11;
    public static final int QUEEN_POWER = 12;
    public static final int KING_POWER = 13;
    public static final int ACE_POWER = 14;
    public static final int COMPLIMENTARY_JACK_POWER = 25;
    public static final int TRUMP_JACK_POWER = 26;
    public static final int JOKER_POWER = 27; 
    
    public static final int TRUMP_COUNT = 13; // 4-10, Q, K, A, J of color, J of suit, Joker
    
    public static final int SPADES_BASE_VALUE = 40;
    public static final int CLUBS_BASE_VALUE = 60;
    public static final int DIAMONDS_BASE_VALUE = 80;
    public static final int HEARTS_BASE_VALUE = 100;
    public static final int NO_TRUMP_BASE_VALUE = 120;

    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int PLAYER_3 = 2;
    public static final int PLAYER_4 = 3;
    public static final int TABLE_CENTER_INDEX = 4;
    public static final int KITTY_INDEX_CLOSED = 4;
    public static final int KITTY_INDEX_OPEN = 5;

    public static final int BID_PHASE = 1;
    public static final int KITTY_PHASE = 2;
    public static final int PLAY_PHASE = 3;
    public static final int SCORING_PHASE = 4;
}