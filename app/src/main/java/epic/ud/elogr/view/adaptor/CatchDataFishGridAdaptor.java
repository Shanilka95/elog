package epic.ud.elogr.view.adaptor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;

/**
 * Created by Udith Perera on 4/18/2020.
 */





public class CatchDataFishGridAdaptor extends BaseAdapter {

    private List<CatchFish> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private LocationTrack locationTrack;
    private  Context context;

    // data is passed into the constructor
    public CatchDataFishGridAdaptor(Context context, List<CatchFish> data) {
//        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public CatchFish getItem(int id) {
        return mData.get(id);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gird = null;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try{

        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();


        if(convertView == null){
            gird = new View(context);
            gird = layoutInflater.inflate(R.layout.activity_catch_data_list_line_cell, null);
            TextView fishName, fishQty, fishWeight;

            Fish fish = Store.INSTANCE.getAppDatabase().fishDao().getFishById(Integer.parseInt(mData.get(position).getFishid()));
            String finName = fish.getEn_name();
            if (lang.equals("si")) {finName = fish.getSi_name();}
            if (lang.equals("en")) {finName = fish.getEn_name();}
            if (lang.equals("ta"))  {finName = fish.getTm_name();}
            fishName = gird.findViewById(R.id.fishname);
            fishQty = gird.findViewById(R.id.fishQty);
            fishWeight = gird.findViewById(R.id.fishWeight);
            fishName.setTypeface(tf);

            fishName.setText(""+finName);
            fishQty.setText("QTY : "+mData.get(position).getFishQty());
            fishWeight.setText("WEIGHT : "+mData.get(position).getFishWeight());

        }else gird = (View)convertView;

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "CatchDataFishGridAdaptor"));
        }

        return gird;
    }



}