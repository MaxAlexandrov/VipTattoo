package ru.akisterev.theviptatu.MainFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.net.ssl.HttpsURLConnection;

import ru.akisterev.theviptatu.MainActivity;
import ru.akisterev.theviptatu.R;
import ru.akisterev.theviptatu.YclientConnect;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMainEnrollMaster.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMainEnrollMaster#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMainEnrollMaster extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View main;
    private HashMap<String, JSONObject> listMap;
    private Intent loadingIntent;
    private ProgressDialog dialog;

    private OnFragmentInteractionListener mListener;

    public FragmentMainEnrollMaster() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMainEnrollMaster.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMainEnrollMaster newInstance(String param1, String param2) {
        FragmentMainEnrollMaster fragment = new FragmentMainEnrollMaster();
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
        main = inflater.inflate(R.layout.fragment_main_enroll_master, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Загружаю список услуг. Подождите...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new FetchItemTask().execute();

        return main;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    private class FetchItemTask extends AsyncTask<Void, Void, Void> {

        JSONArray resBody;
        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("поток запущен");

            //формируем json
            JSONObject paramObj = new JSONObject();
            JSONObject masObj1 = new JSONObject();
            JSONObject masObj2 = new JSONObject();
            JSONArray mas = new JSONArray();
            JSONArray serrv1 = new JSONArray();
            JSONArray serrv2 = new JSONArray();
            try {
                masObj1.put("id", 1);
                serrv1.put(4);
                masObj1.put("services", serrv1);
                masObj1.put("staff_id", 3247);
                masObj1.put("datetime", "2016-04-01T12:00:00+04:00");

                masObj2.put("id", 2);
                serrv2.put(96);
                masObj2.put("services", serrv2);
                masObj2.put("staff_id", 3247);
                masObj2.put("datetime", "2016-04-01T12:00:00+04:00");


                mas.put(masObj1);
                mas.put(masObj2);

                paramObj.put("phone", "79000000000");
                paramObj.put("fullname", "ДИМА");
                paramObj.put("email", "d@yclients.com");
                paramObj.put("code", "38829");
                paramObj.put("comment", "тестовая запись!");
                paramObj.put("type", "mobile");
                paramObj.put("notify_by_sms", 6);
                paramObj.put("notify_by_email", 24);
                paramObj.put("api_id", "777");
                paramObj.put("appointments", mas);

                //System.out.println(paramObj.toString());
                //System.out.println(URLEncoder.encode(paramObj.toString(), "UTF-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*YclientConnect vk = new YclientConnect("https://api.vk.com/method/wall.get?owner_id=-1252064");
            JSONObject resultVk = vk.get();
            try {
                JSONObject resBody = new JSONObject((String) resultVk.get("body"));
                System.out.println(resBody.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            YclientConnect yCli = new YclientConnect("http://api.yclients.com/api/v1/service_categories");
            yCli.setNumCompany("31280")
                .setAuthorization("Bearer fk2ganr9amep27tu2z7y");

            /*JSONObject result = yCli.post(paramObj);
            try {
                JSONObject resBody = new JSONObject((String) result.get("body"));
                System.out.println(resBody.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            //yCli.setRequest(YclientConnect.REQUEST_SERVICE);

            HashMap getParam = new HashMap<String, String>();
            getParam.put("staff_id","0");
            getParam.put("datetime", "2016-09-29T13:00:00+04:00");
            JSONObject result = yCli.get(getParam);
            try {
                resBody = new JSONArray((String) result.get("body"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

           /* yCli.setRequest(YclientConnect.REQUEST_STAFF);
            getParam = new HashMap<String, String>();
            getParam.put("service_ids[]", "331");
            result = yCli.get(getParam);
            try {
                JSONArray resBody = new JSONArray((String) result.get("body"));
                System.out.println(resBody.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            yCli.setRequest(YclientConnect.REQUEST_DATES);
            getParam = new HashMap<String, String>();
            getParam.put("staff_id", "0");
            result = yCli.get(getParam);
            try {
                JSONObject resBody = new JSONObject((String) result.get("body"));
                System.out.println(resBody.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JSONArray category = resBody;

            /*try {
                category = resBody;
                System.out.println(category.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            ListView listView = (ListView) main.findViewById(R.id.listVService);
            Spinner spinnerCategory = (Spinner) main.findViewById(R.id.spinnerCategory);
            //String[] value = new String[]{"Android", "iPhone", "WindowsMobile", "5555"};
            ArrayList<String> list = new ArrayList<String>();
            listMap = new HashMap<String, JSONObject>();
            if (category != null){
                int len = category.length();
                for (int i=0; i<len; i++){
                    try {
                        list.add(category.getJSONObject(i).getString("title"));
                        listMap.put(category.getJSONObject(i).getString("title"), category.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            dialog.hide();
            try {
                ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
                //listView.setAdapter(files);
                spinnerCategory.setAdapter(files);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
