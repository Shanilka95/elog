package epic.ud.elogr.view.adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.CatchDataFishListModel;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.CatchData.UpdateCatchDataActivity;

/**
 * Created by Udith Perera on 4/18/2020.
 */


public class CatchDataListAdaptor extends RecyclerView.Adapter<CatchDataListAdaptor.ViewHolder> {

    private List<CatchDataFishListModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private LocationTrack locationTrack;
    private Context context;

    // data is passed into the constructor
    public CatchDataListAdaptor(Context context, List<CatchDataFishListModel> data) {
//        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.gear_list_line, parent, false);
//        return new ViewHolder(view);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catch_data_list_line, parent, false);
        ViewHolder myviewHolder = new ViewHolder(view);

        return myviewHolder;

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CatchDataFishListModel line = mData.get(position);


        try {


            List<TripGear> tg = Store.INSTANCE.getAppDatabase().tripGearDao().loadAllByIds(line.getGearId());
            String gearName = Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("" + tg.get(0).getGearType());
            List<Fish> fl = Store.INSTANCE.getAppDatabase().fishDao().getAllById(Integer.parseInt(line.getFishType()));
            String fishTypeLable = "" + fl.get(0).getEn_name();


            //load word list and lang
            List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
            String lang = Store.INSTANCE.getLang();
            Typeface tf = Store.INSTANCE.getFace();
            if (word.size() < 67) {

            } else {


            }

            if (lang.equals("si")) {
                fishTypeLable = "" + fl.get(0).getSi_name();
                gearName = Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("" + tg.get(0).getGearType());
                holder.updateCatchDataBtn.setText("" + word.get(9).getSi_name());
            }
            if (lang.equals("en")) {
                fishTypeLable = "" + fl.get(0).getEn_name();
                gearName = Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("" + tg.get(0).getGearType());
                holder.updateCatchDataBtn.setText("" + word.get(9).getEn_name());
            }
            if (lang.equals("ta")) {
                fishTypeLable = "" + fl.get(0).getTm_name();
                gearName = Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("" + tg.get(0).getGearType());
                holder.updateCatchDataBtn.setText("" + word.get(9).getTm_name());
            }

            holder.lineLable.setText("" + line.getCatchid());
            holder.fishTypeLable.setText("" + fishTypeLable);
            holder.fishTypeLable.setTypeface(tf);
            holder.catchTypeLabel.setText("" + line.getCatchType());
            holder.gearLable.setText("" + gearName);
            holder.gearLable.setTypeface(tf);
            holder.updateCatchDataBtn.setTypeface(tf);
            holder.updateCatchDataBtn.setText("n,uq");


            CatchDataFishGridAdaptor fishGridAdaptor = new CatchDataFishGridAdaptor(context, line.getFishList());
            holder.fishGrid.setAdapter(fishGridAdaptor);
            ViewGroup.LayoutParams layoutParams = holder.fishGrid.getLayoutParams();
            int cnt = line.getFishList().size();
            layoutParams.height = cnt > 2 ? 400 * (cnt / 2) : 300;
            holder.fishGrid.setLayoutParams(layoutParams);

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "CatchDataListAdaptor"));
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        TextView lineLable, fishTypeLable, catchTypeLabel, gearLable;
        GridView fishGrid;
        Button updateCatchDataBtn;


        ViewHolder(final View itemView) {
            super(itemView);

            lineLable = itemView.findViewById(R.id.lineLable);
            fishTypeLable = itemView.findViewById(R.id.fishTypeLable);
            catchTypeLabel = itemView.findViewById(R.id.catchTypeLabel);
            gearLable = itemView.findViewById(R.id.gearLable);
            fishGrid = itemView.findViewById(R.id.fishGrid);
            updateCatchDataBtn = itemView.findViewById(R.id.updateCatchDataBtn);


            updateCatchDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateCatchDataActivity.class);
                    intent.putExtra("lineId", "" + lineLable.getText().toString());
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
    CatchDataFishListModel getItem(int id) {
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


    public int dpToPix(float dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

}