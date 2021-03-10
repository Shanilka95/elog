package epic.ud.elogr.view.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.ShowTripGearModel;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.CatchData.EnterCatchDataActivity;
import epic.ud.elogr.view.SetData.SetDataActivity;

/**
 * Created by Udith Perera on 4/18/2020.
 */





public class SetDataListAdaptor extends RecyclerView.Adapter<SetDataListAdaptor.ViewHolder> {

    private List<ShowTripGearModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private LocationTrack locationTrack;
    private  Context context;

    // data is passed into the constructor
    public SetDataListAdaptor(Context context, List<ShowTripGearModel> data) {
//        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.gear_list_line, parent, false);
//        return new ViewHolder(view);

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gear_list_line,parent,false);
        ViewHolder myviewHolder=new ViewHolder(view);

        return myviewHolder;

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShowTripGearModel line = mData.get(position);

        try {
        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);


        holder.gearSelectedNameLable.setTypeface(tf);

        holder.idSelectedLable.setText(""+line.getLineId());
        holder.gearSelectedNameLableSetNo.setText("SET "+line.getNumber());



        if (lang.equals("si")) {
            holder.gearSelectedNameLable.setText(""+line.getGearWordSi());
        }

        if (lang.equals("ta")) {
            holder.gearSelectedNameLable.setText(""+line.getGearWordTa());
        }

        if (lang.equals("en")) {
            holder.gearSelectedNameLable.setText(""+line.getGearWordEn());
        }


    } catch (Exception e) {
        LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "setDatahListAdaptor"));
    }

//        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        TextView gearSelectedNameLable,
                idSelectedLable,gearSelectedNameLableSetNo;



        ViewHolder(final View itemView) {
            super(itemView);
           /* myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);*/

            gearSelectedNameLable = itemView.findViewById(R.id.gearSelectedNameLable);
            idSelectedLable = itemView.findViewById(R.id.idSelectedLable);
            gearSelectedNameLableSetNo = itemView.findViewById(R.id.gearSelectedNameLableSetNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idx = idSelectedLable.getText().toString();
                    Intent intent = new Intent(context, EnterCatchDataActivity.class);
                    intent.putExtra("lineId", ""+idx);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ShowTripGearModel getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }





    public void dateTimePicker(){

    }

}