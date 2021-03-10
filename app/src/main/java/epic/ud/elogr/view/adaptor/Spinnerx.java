package epic.ud.elogr.view.adaptor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import epic.ud.elogr.util.Store;

/**
 * Created by Udith Perera on 4/15/2020.
 */
public class Spinnerx  extends ArrayAdapter<String> {
    // Initialise custom font, for example:
    Typeface font = Store.INSTANCE.getFace();


    private Spinnerx(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }
}
