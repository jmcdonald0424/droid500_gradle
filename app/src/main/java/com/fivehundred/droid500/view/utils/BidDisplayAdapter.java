package com.fivehundred.droid500.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fivehundred.droid500.R;

public class BidDisplayAdapter extends BaseAdapter{
    private Context context;
    private final String[] playerNames;
    private String[] bids = new String[4];
    
    public BidDisplayAdapter(Context context, String[] playerNames){
        this.context = context;
        this.playerNames = playerNames;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View bidView;
        
        if(convertView == null){
            bidView = inflater.inflate(R.layout.bid_display, null);
            TextView playerLabel = (TextView)bidView.findViewById(R.id.bidplayer);
            playerLabel.setText(playerNames[position]);
        }else{
            bidView = (View)convertView;
        }  
        if(bids[position] != null && !bids[position].trim().isEmpty()){
            TextView bidValue = (TextView)bidView.findViewById(R.id.bidvalue);
            bidValue.setText(bids[position]);            
        }
        return bidView;
    }
    
    @Override
    public int getCount(){
        return playerNames.length;
    }
    
    @Override
    public Object getItem(int position){
        return null;
    }
    
    @Override
    public long getItemId(int position){
        return 0;
    }
    
    public void setBid(int position, String bid){
        bids[position] = bid;
    }
}