package com.fivehundred.droid500.view.utils;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import com.fivehundred.droid500.R;
import com.fivehundred.droid500.utils.Logger;

public class ViewUtils {

    public static RectF copy(RectF rect) {
        RectF newRect = new RectF();
        newRect.left = rect.left;
        newRect.top = rect.top;
        newRect.right = rect.right;
        newRect.bottom = rect.bottom;
        return newRect;
    }
    
    public static PointF getDropPoint(int playerIndex){
        // Divide table by 3 to determine quadrant size
        float divider = ViewConstants.BASE_SCALE_MIN / 3;
        float center = divider / 2;
        int xIndex = playerIndex > 0 ? (playerIndex > 3 ? 1 : playerIndex - 1) : playerIndex + 1;
        int yIndex = playerIndex < 3 ? playerIndex : (playerIndex > 3 ? 1 : playerIndex % 2);
        float xCoord = xIndex * divider + center;
        float yCoord = yIndex * divider + center;
        return new PointF(xCoord, yCoord);
    }
    
    public static PointF getDealerLocation(int dealerIndex){
        float divider = ViewConstants.BASE_SCALE_MIN / 3;        
        float center = divider + (divider / 2);
        switch(dealerIndex){
            case 0:
                return new PointF(center, center - (divider * 2));
            case 1:
                return new PointF(center - (divider * 2), center);
            case 2:
                return new PointF(center, center + (divider * 2));
            case 3:
                return new PointF(center + (divider * 2), center);
            default:                
                return new PointF(center, center - (divider * 2));
        }
    }
    
    public static PointF getPlaceCoordinates(int playerIndex, int cardIndex, float ssu){
        float padding = (ViewConstants.BASE_SCALE_MIN * ssu / 2) - (ViewConstants.BASE_CARD_WIDTH * 10 / 2 / 2 * ssu);
        float coordOffset = padding + cardIndex * ViewConstants.BASE_CARD_WIDTH / 2 * ssu;
        switch(playerIndex){
            case 0:
                return new PointF(ViewConstants.BASE_CARD_WIDTH / 2 * ssu + coordOffset, 
                                  ViewConstants.BASE_CARD_HEIGHT / 2 * ssu);
            case 1:
                return new PointF(ViewConstants.BASE_CARD_HEIGHT / 2 * ssu,
                                  ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_WIDTH / 2 * ssu - coordOffset);
            case 2:
                return new PointF(ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_WIDTH / 2 * ssu - coordOffset,
                                  ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_HEIGHT / 2 * ssu);
            case 3:
                return new PointF(ViewConstants.BASE_SCALE_MIN * ssu - ViewConstants.BASE_CARD_HEIGHT / 2 * ssu,
                                  ViewConstants.BASE_CARD_WIDTH / 2 * ssu + coordOffset);
            case 4:
                return new PointF(ViewConstants.BASE_SCALE_MIN / 2 * ssu,
                                  ViewConstants.BASE_SCALE_MIN / 2 * ssu);
            default:
                Logger.logError("getCoordinates failed -- No coordinates found for player: " + playerIndex);
                return null;
        }
    }
    
    public static int getLayout(int layout){
        switch(layout){
            case ViewListenerConstants.LOAD_GAME_LAYOUT:
                return R.id.gamelayout;
            case ViewListenerConstants.LOAD_BID_LAYOUT:
                return R.id.bidlayout;
            default:
                Logger.logError("getLayout failed -- No layout found for " + layout);
                return -1;
        }
    }
    
    public static int getView(int layout){
        switch(layout){
            case ViewListenerConstants.LOAD_GAME_LAYOUT:
                return R.layout.main;
            case ViewListenerConstants.LOAD_BID_LAYOUT:
                return R.layout.bid_portrait;
            default:
                Logger.logError("getView failed -- No view layout found for " + layout);
                return -1;
        }
    }
}
