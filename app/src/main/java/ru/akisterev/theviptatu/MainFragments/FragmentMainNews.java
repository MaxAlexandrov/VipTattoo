package ru.akisterev.theviptatu.MainFragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.akisterev.theviptatu.PostDetailActivity;
import ru.akisterev.theviptatu.R;
import ru.aleksandrov.news.Post;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMainNews.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMainNews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMainNews extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAB = "myLog";
    private ListAdapter adapter;
    private ListView listView;
    private ArrayList<Post> list;
    private ArrayList<String> textList,titleList;
    private ProgressDialog progress;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String data[] = new String[] { "one", "two", "three", "four" };

    private OnFragmentInteractionListener mListener;

    public FragmentMainNews() {
        // Required empty public constructor
        list = new ArrayList<Post>();
        textList = new ArrayList<String>();
        titleList = new ArrayList<String>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMainNews.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMainNews newInstance(String param1, String param2) {
        FragmentMainNews fragment = new FragmentMainNews();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_news, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new ParseTask().execute();
        for (int i = 0; i <list.size() ; i++) {
            String[] title  = list.get(i).getText().split("<br>");
            textList.add(list.get(i).getText());
            titleList.add(title[0]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.post_list_item,R.id.texView, textList);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                TextView titleView = (TextView) view.findViewById(R.id.text_title);
                TextView texView = (TextView) view.findViewById(R.id.texView);
                intent.putExtra("tittle",titleView.getText().toString());
                intent.putExtra("text",texView.getText().toString());
                startActivity(intent);
            }
        });
        setListAdapter(adapter);
//        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,textList);
//        progress = new ProgressDialog(getActivity());
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setMessage("Загружаю список услуг. Подождите...");
//        progress.setCanceledOnTouchOutside(false);
//        progress.show();
//        new ParseTask().execute();
//        for (Post str: list) {
//            textList.add(str.getText());
//        }
//        Log.d(LOG_TAB,"reposts = "+textList);
//        getListView().setAdapter(adapter);
       // listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ParseTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        public ParseTask() {

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(getResources().getString(R.string.link_vk_wall));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            JSONObject dataJsonObj = null;
            String cityName = "";
            try {
                Post oneNews;
                dataJsonObj = new JSONObject(strJson);
                JSONArray getResponse = dataJsonObj.getJSONArray("response");
                for (int i = 1; i <getResponse.length(); i++){
                   oneNews = new Post();
                   JSONObject post = getResponse.getJSONObject(i);
                   String id_text = post.getString("id");
                    Log.d(LOG_TAB,"id = "+id_text);
                    oneNews.setId_news(id_text);
                   String data_date = post.getString("date");
                    Log.d(LOG_TAB, "date = " + data_date);
                    oneNews.setData_date(data_date);
                   String text = post.getString("text");
                    Log.d(LOG_TAB,"text = "+text);
                    oneNews.setText(text);
                   JSONObject getComments = post.getJSONObject("comments");
                   String comments = getComments.getString("count");
                    Log.d(LOG_TAB,"comments = "+comments);
                    oneNews.setComments(comments);
                   JSONObject getLikes = post.getJSONObject("likes");
                   String likes = getLikes.getString("count");
                    Log.d(LOG_TAB,"likes = "+likes);
                    oneNews.setLikes(likes);
                   JSONObject getComents = post.getJSONObject("reposts");
                    String reposts = getComents.getString("count");
                    Log.d(LOG_TAB, "reposts = " + reposts);
                    oneNews.setRepost(reposts);
                    list.add(oneNews);
                }

                //JSONObject parametr = aboutList.getJSONObject(0);
//                String dateTameValue = parametr.getString("dt_txt");
//                JSONObject clouds = parametr.getJSONObject("clouds");
//                JSONObject degree = parametr.getJSONObject("main");
//                String cloudsValue = clouds.getString("all");
//                String degreeValue = degree.getString("temp");
//                String humidityValue = degree.getString("humidity");
//
//                TextView text_information_country = (TextView) getActivity().findViewById(R.id.text_information_country);
//                TextView text_information_city = (TextView) getActivity().findViewById(R.id.text_information_city);
//                TextView text_information_data = (TextView) getActivity().findViewById(R.id.text_information_data);
//                TextView text_information_time = (TextView) getActivity().findViewById(R.id.text_information_time);
//                TextView text_information_clouds = (TextView) getActivity().findViewById(R.id.text_information_clouds);
//                TextView text_information_degree = (TextView) getActivity().findViewById(R.id.text_information_degree);
//                TextView text_information_wet = (TextView) getActivity().findViewById(R.id.text_information_wet);
//                SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
//                SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//                SimpleDateFormat newTimeFormat = new SimpleDateFormat("EEEE kk:mm:ss", Locale.getDefault());
//                Date dateDate = null, dateTime = null;
//                try {
//                    dateDate = oldDateFormat.parse(dateTameValue.toString());
//                    dateTime = oldDateFormat.parse(dateTameValue.toString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                String changeFormatDateValue = newDateFormat.format(dateDate);
//                String changeFormatTimeValue = newTimeFormat.format(dateTime);
//
//                text_information_country.setText(getResources().getText(R.string.data_country).toString() + ":" + country);
//                text_information_city.setText(getResources().getText(R.string.data_city).toString() + ":" + name);
//                text_information_data.setText(getResources().getText(R.string.data_date).toString() + ":" + changeFormatDateValue);
//                text_information_time.setText(getResources().getText(R.string.data_time).toString() + ":" + changeFormatTimeValue);
//                text_information_clouds.setText(getResources().getText(R.string.data_cloud).toString() + ":" + cloudsValue);
//                text_information_degree.setText(getResources().getText(R.string.data_temperature).toString() + ":" + degreeValue + "K");
//                text_information_wet.setText(getResources().getText(R.string.data_wet).toString() + ":" + humidityValue);
//                try {
//
//                    DataBase mDatabaseHelper = new DataBase(getActivity(), "cities.db", null, 1);
//                    mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put(mDatabaseHelper.NAME_CITI, name);
//                    values.put(mDatabaseHelper.NAME_COUNTRY, country);
//                    values.put(mDatabaseHelper.DATA_DATE, changeFormatDateValue);
//                    values.put(mDatabaseHelper.DATA_TIME, changeFormatTimeValue);
//                    values.put(mDatabaseHelper.CLOUDS_VALUE, cloudsValue);
//                    values.put(mDatabaseHelper.DEGREE_VALUE, degreeValue + "K");
//                    values.put(mDatabaseHelper.HUMIDITY_VALUE, humidityValue);
//                    mSqLiteDatabase.insert("cities", null, values);
//
//                    Toast.makeText(getActivity(), "Recorded successfully", Toast.LENGTH_LONG).show();
//                } catch (Throwable t) {
//                    Toast.makeText(getActivity(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
//                }
              //  progress.hide();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
