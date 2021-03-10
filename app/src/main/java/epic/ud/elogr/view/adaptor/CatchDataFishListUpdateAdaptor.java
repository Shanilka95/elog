package epic.ud.elogr.view.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.CatchDataFishModel;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;

/**
 * Created by Udith Perera on 4/18/2020.
 */


public class CatchDataFishListUpdateAdaptor extends RecyclerView.Adapter<CatchDataFishListUpdateAdaptor.ViewHolder>{

    private List<CatchFish> mData;
    private List<CatchFish> mDataNoFilter;
    private List<CatchDataFishModel> catchDataFishModels = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private LocationTrack locationTrack;
    private Context context;

    // data is passed into the constructor
    public CatchDataFishListUpdateAdaptor(Context context, List<CatchFish> data) {
//        this.mInflater = LayoutInflater.from(context);
        Log.d("TAG_FISHLIST", "" + data.size());
        this.context = context;
        this.mData = data;
        this.mDataNoFilter = new ArrayList<>(mData);
        defineArray(data.size());

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.gear_list_line, parent, false);
//        return new ViewHolder(view);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catch_fish_list_line, parent, false);
        ViewHolder myviewHolder = new ViewHolder(view);

        return myviewHolder;

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CatchFish line = mData.get(position);

        try {

        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        holder.fishNameLable.setTypeface(tf);
        holder.viewImageBtn.setTypeface(tf);

        holder.fishIdLable.setText("" + line.getId());

        final Fish fish = Store.INSTANCE.getAppDatabase().fishDao().getFishById(Integer.parseInt(line.getFishid()));


        if (lang.equals("si")) {
            holder.fishNameLable.setText(" - " + fish.getSi_name());
            holder.viewImageBtn.setText("n,uq");
        }

        if (lang.equals("ta")) {
            holder.fishNameLable.setText(" - " + fish.getTm_name());
            holder.viewImageBtn.setText("" + word.get(10).getTm_name());
        }

        if (lang.equals("en")) {
            holder.fishNameLable.setText(" - " + fish.getEn_name());
            holder.viewImageBtn.setText("" + word.get(10).getEn_name());
        }


//        holder.fishImageInput.setImageBitmap(getBitmapFromAssets("" + line.getFishid()));
        holder.viewImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage("" + fish.getId());
            }
        });
        holder.fishQtyInput.setText("" + line.getFishQty());
        holder.fishWeightInput.setText("" + line.getFishWeight());

        catchDataFishModels.get(position).setFishId(""+line.getFishid());
        catchDataFishModels.get(position).setId(""+line.getId());
//        catchDataFishModels.get(position).setFishPerentId(""+line.getParent_id());
        catchDataFishModels.get(position).setFishQty("" +line.getFishQty());
        catchDataFishModels.get(position).setFishWeight("" +line.getFishWeight());
        holder.fishQtyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(catchDataFishModels.size() > position) {
                    catchDataFishModels.get(position).setFishQty("" + s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.fishWeightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(catchDataFishModels.size() > position) {
                    catchDataFishModels.get(position).setFishWeight("" + s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        holder.myTextView.setText(animal);

    } catch (Exception e) {
        LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "CatchDataFishListUpdateAdaptor"));
    }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fishNameLable, fishIdLable;
        EditText fishQtyInput, fishWeightInput;
        ImageView fishImageInput;
        Button viewImageBtn;


        ViewHolder(final View itemView) {
            super(itemView);
           /* myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);*/

            fishIdLable = itemView.findViewById(R.id.fishIdLable);
            fishNameLable = itemView.findViewById(R.id.fishNameLable);
            fishQtyInput = itemView.findViewById(R.id.fishQtyInput);
            fishWeightInput = itemView.findViewById(R.id.fishWeightInput);
            fishImageInput = itemView.findViewById(R.id.fishImageInput);
            viewImageBtn = itemView.findViewById(R.id.viewImageBtn);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    CatchFish getItem(int id) {
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


    private Bitmap getBitmapFromAssets(String fileName) {

        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("images/" + fileName + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    public void defineArray(int size) {
        if (catchDataFishModels != null) {
            catchDataFishModels.removeAll(catchDataFishModels);
        }
        for (int i = 0; i < size; i++) {
            CatchDataFishModel model = new CatchDataFishModel();
            catchDataFishModels.add(model);
        }

    }

    public List<CatchDataFishModel> getCatchDataFishModels() {
        return catchDataFishModels;
    }

    public void setCatchDataFishModels(List<CatchDataFishModel> catchDataFishModels) {
        this.catchDataFishModels = catchDataFishModels;
    }

    public void showImage(String fileName) {
        try {
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("images/" + fileName + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
//        imageView.setImageURI(is);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();

    } catch (Exception e) {
        LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "CatchDataFishListUpdateAdaptor"));
    }
    }
}