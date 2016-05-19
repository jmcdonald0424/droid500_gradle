package com.fivehundred.droid500.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import com.fivehundred.droid500.R;
import com.fivehundred.droid500.utils.Logger;

public class BidButtonsAdapter extends BaseAdapter{
    private final Context context;
    private final String[] bidValues;
    
    public BidButtonsAdapter(Context context, String[] bidValues){
        this.context = context;
        this.bidValues = bidValues;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View bidView;
        
        if(convertView == null){
            bidView = inflater.inflate(R.layout.bid_buttons, null);
            Button bidValueBtn = (Button)bidView.findViewById(R.id.button_bidvalue);
            bidValueBtn.setText(bidValues[position]);
            ImageButton bidSuitBtn = (ImageButton)bidView.findViewById(R.id.button_bidsuit);
            switch(position){
                case 0:
                    bidSuitBtn.setImageResource(R.drawable.spade);
                    break;
                case 1:
                    bidSuitBtn.setImageResource(R.drawable.club);
                    break;
                case 2:
                    bidSuitBtn.setImageResource(R.drawable.diamond);
                    break;
                case 3:
                    bidSuitBtn.setImageResource(R.drawable.heart);
                    break;
                case 4:
                    bidSuitBtn.setImageResource(R.drawable.heart);
                    break;
                default:
                    Logger.logError("getView failed -- no image found for position " + position);
            }
        }else{
            bidView = (View)convertView;
        }    
        return bidView;
    }
    
    @Override
    public int getCount(){
        return bidValues.length;
    }
    
    @Override
    public Object getItem(int position){
        return null;
    }
    
    @Override
    public long getItemId(int position){
        return 0;
    }
}