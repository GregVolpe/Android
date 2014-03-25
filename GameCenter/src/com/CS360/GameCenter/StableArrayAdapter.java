package com.CS360.GameCenter;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;
/**
 * Class to use as a stable array adapter for list items
 * @author Greg Volpe
 *
 */
public class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    /**
     * Constructor for a Stable Array Object
     * @param context the Context of the current View
     * @param textViewResourceId ID of the intended text resource
     * @param objects  List of String objects to put in the List
     */
    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects) {
      super(context, textViewResourceId, objects);
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }


    @Override
    /**
     * Returns the items ID at the position
     * @param position Position of the item
     */
    public long getItemId(int position) {
      String item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

  }

