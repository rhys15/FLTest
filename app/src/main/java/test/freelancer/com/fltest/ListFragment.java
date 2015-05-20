package test.freelancer.com.fltest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import test.freelancer.com.fltest.database.tables.TvProgramsTable;
import test.freelancer.com.fltest.handlers.TvProgramHandler;
import test.freelancer.com.fltest.interfaces.JSONListener;
import test.freelancer.com.fltest.model.DownloadDataTask;

/**
 * List that displays the TV Programmes
 */
public class ListFragment extends Fragment implements JSONListener, AbsListView.OnScrollListener{


    private static final String TAG = ListFragment.class.getSimpleName();
    private int maxCount = 0;
    private int currentItem = 0;
    private int start = 0;
    private View view;
    private ListView programListView;
    private ListAdapter listAdapter;
    private ArrayList<TvProgramHandler> programArray;
    private TvProgramsTable tvProgramsTable;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_programlist, container, false);
        programListView = (ListView) view.findViewById(R.id.program_list);
        // eurgh, damn android.os.NeworkOnMainThreadException - so pesky!
        // stackoverflow told me to do this:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //initialize tagble
        tvProgramsTable = new TvProgramsTable();
        //get all pre loadaed data
        programArray = tvProgramsTable.getAllTvPrograms();
        //insert data to adapter
        listAdapter = new ListAdapter(getActivity(), programArray);
        //fill listview
        programListView.setAdapter(listAdapter);
        programListView.setOnScrollListener(this);
        //download data
        downloadData();


        return view;
    }

    public void downloadData() {
        Log.i(TAG, "max count " + maxCount + "data size " + programArray.size());
        if (programArray.size() < maxCount || maxCount == 0) {
            Log.i(TAG, "download data ");
            DownloadDataTask downloadDataTask = new DownloadDataTask(getActivity(), "http://whatsbeef.net/wabz/guide.php?start=" + currentItem);
            downloadDataTask.setJsonListener(this);
            downloadDataTask.execute();
        }else{
            Log.i(TAG, "dont download data ");
        }
    }

    @Override
    public void onDataFetch(String jsonString) {
        Log.i(TAG, "download result " + jsonString);
        try {
            JSONObject jsonData = new JSONObject(jsonString);
            maxCount = jsonData.getInt("count");
            JSONArray result = jsonData.getJSONArray("results");
            for (int i = 0; i < result.length(); i++) {
                JSONObject resultItem = result.getJSONObject(i);
                String name = resultItem.getString("name");
                String startTime = resultItem.getString("start_time");
                String endTime = resultItem.getString("end_time");
                String channel = resultItem.getString("channel");
                String rating = resultItem.getString("rating");
                TvProgramHandler dataHandler = new TvProgramHandler();
                dataHandler.setName(name);
                dataHandler.setStartTime(startTime);
                dataHandler.setEndTime(endTime);
                dataHandler.setChannel(channel);
                dataHandler.setRating(rating);
                long dbResult = tvProgramsTable.insertProgram(dataHandler);
                Log.i(TAG, "result " + dbResult);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        programArray = tvProgramsTable.getAllTvPrograms();
        currentItem = programArray.size();
        listAdapter.setItems(programArray);
    }

    @Override
    public void onNetworkError(String errorMessage) {
        Log.i(TAG, "download error " + errorMessage);
    }

    @Override
    public void onUnknownError(String errorMessage) {
        Log.i(TAG, "download error " + errorMessage);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
        this.isScrollCompleted();
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
            downloadData();
        }
    }


    public class ListAdapter extends BaseAdapter {

        private Context context;
        JSONArray array;
        ArrayList<TvProgramHandler> data = new ArrayList<>();

        public ListAdapter(Context context, ArrayList<TvProgramHandler> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public TvProgramHandler getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                // inflate the layout
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.adapter_program, parent, false);
                // well set up the ViewHolder
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
                viewHolder.channel = (TextView) convertView.findViewById(R.id.channel_tv);
                viewHolder.startTime = (TextView) convertView.findViewById(R.id.start_time_tv);
                viewHolder.endTime = (TextView) convertView.findViewById(R.id.end_time_tv);
                viewHolder.rate = (TextView) convertView.findViewById(R.id.rate_tv);
                // store the holder with the view.
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // object item based on the position
            TvProgramHandler item = data.get(position);
            viewHolder.name.setText(item.getName());
            viewHolder.channel.setText(item.getChannel());
            viewHolder.startTime.setText(item.getStartTime());
            viewHolder.endTime.setText(item.getEndTime());
            viewHolder.rate.setText(item.getRating());
            return convertView;
        }


        public void setItems(ArrayList<TvProgramHandler> items) {
            this.data = items;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {
        TextView name, channel, startTime, endTime, rate;
    }



}
