package epic.ud.elogr.view.adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.ShowTripGearModel;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.SetData.SetDataActivity;
import epic.ud.elogr.view.TripMenuActivity;

/**
 * Created by Udith Perera on 4/18/2020.
 */





public class SetDataRecycleAdaptor extends RecyclerView.Adapter<SetDataRecycleAdaptor.ViewHolder> {

    private List<ShowTripGearModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private LocationTrack locationTrack;

    // data is passed into the constructor
    public SetDataRecycleAdaptor(Context context, List<ShowTripGearModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gear_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShowTripGearModel line = mData.get(position);

        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);


        holder.gearNameLable.setTypeface(tf);

        holder.startDateLable.setTypeface(tf);
        holder.endDateLable.setTypeface(tf);
        holder.startGpsLable.setTypeface(tf);
        holder.endGpsLable.setTypeface(tf);

        holder.idLable.setText(""+line.getLineId());



        if (lang.equals("si")) {
            holder.gearNameLable.setText(""+line.getGearWordSi());
            holder.startDateLable.setText(""+word.get(38).getSi_name());
            holder.endDateLable.setText(""+word.get(42).getSi_name());
            holder.startGpsLable.setText(""+word.get(40).getSi_name());
            holder.endGpsLable.setText(""+word.get(41).getSi_name());
        }

        if (lang.equals("ta")) {
            holder.gearNameLable.setText(""+line.getGearWordTa());
            holder.startDateLable.setText(""+word.get(38).getTm_name());
            holder.endDateLable.setText(""+word.get(42).getTm_name());
            holder.startGpsLable.setText(""+word.get(40).getTm_name());
            holder.endGpsLable.setText(""+word.get(41).getTm_name());
        }

        if (lang.equals("en")) {
            holder.gearNameLable.setText(""+line.getGearWordEn());
            holder.startDateLable.setText(""+word.get(38).getEn_name());
            holder.endDateLable.setText(""+word.get(42).getEn_name());
            holder.startGpsLable.setText(""+word.get(40).getEn_name());
            holder.endGpsLable.setText(""+word.get(41).getEn_name());
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

        TextView gearNameLable,
        idLable,
        startDateLable,
        endDateLable,
        startGpsLable,
        endGpsLable;

        EditText startDateInput,
        endDateInput,
        startGpsInput,
        endGpsInput;

        ViewHolder(final View itemView) {
            super(itemView);
           /* myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);*/

                    gearNameLable = itemView.findViewById(R.id.gearNameLable);
                    idLable = itemView.findViewById(R.id.idLable);
                    startDateLable = itemView.findViewById(R.id.startDateLable);
                    endDateLable = itemView.findViewById(R.id.endDateLable);
                    startGpsLable = itemView.findViewById(R.id.startGpsLable);
                    endGpsLable = itemView.findViewById(R.id.endGpsLable);

                    startDateInput = itemView.findViewById(R.id.startDateInput);
                    endDateInput = itemView.findViewById(R.id.endDateInput);
                    startGpsInput = itemView.findViewById(R.id.startGpsInput);
                    endGpsInput = itemView.findViewById(R.id.endGpsInput);

                    startDateInput.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog datePickerDialog = new DatePickerDialog(itemView.getContext(),
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {

                                            String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                            startDateInput.setText(""+date);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();

                            return false;
                        }
                    });
                    endDateInput.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog datePickerDialog = new DatePickerDialog(itemView.getContext(),
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {

                                            String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                            endDateInput.setText(""+date);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();


                            return false;
                        }
                    });
                    startGpsInput.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            startGpsInput.setText(""+setGps());
                            return false;
                        }
                    });
                    endGpsInput.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            endGpsInput.setText(""+setGps());
                            return false;
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



    public String setGps() {

        locationTrack = new LocationTrack(mInflater.getContext());
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            return  longitude+","+latitude;


        } else {
            return  "";
        }
    }


    public void dateTimePicker(){

    }

}