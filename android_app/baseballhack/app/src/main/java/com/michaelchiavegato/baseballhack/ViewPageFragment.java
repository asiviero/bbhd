package com.michaelchiavegato.baseballhack;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link ViewPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPageFragment extends Fragment {

    private JSONObject data;
    private boolean correct_answer;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        try {
            data = new JSONObject(bundle.getString("data"));
            index = bundle.getInt("index");
            Log.d("bundle: ", bundle.getString("data"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.question_layout, container, false);

        Button button1 = (Button) v.findViewById(R.id.button1);
        Button button2 = (Button) v.findViewById(R.id.button2);
        Button button3 = (Button) v.findViewById(R.id.button3);
        Button button4 = (Button) v.findViewById(R.id.button4);


        TextView tv = (TextView) v.findViewById(R.id.questionTitle);
        try {
            tv.setText(data.getString("title"));
            String s = data.getString("choices");
            String[] choiceArray = s.split(", ");
            button1.setText(choiceArray[0]);
            button1.setOnClickListener(new ListenerButton());
            button2.setText(choiceArray[1]);
            button2.setOnClickListener(new ListenerButton());
            button3.setText(choiceArray[2]);
            button3.setOnClickListener(new ListenerButton());
            button4.setText(choiceArray[3]);
            button4.setOnClickListener(new ListenerButton());
        }
        catch (JSONException e) {

        }






        /*new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTv.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTv.setText("done!");
            }
        }.start();*/

        return v;
    }

    public static ViewPageFragment newInstance(JSONObject param1, int index) {
        ViewPageFragment fragment = new ViewPageFragment();
        Bundle b = new Bundle();
        b.putString("data", param1.toString());
        b.putInt("index",index);
        fragment.setArguments(b);

        return fragment;
    }

    public ViewPageFragment() {
        // Required empty public constructor
    }

    class ListenerButton implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            try {
                Log.d("button",button.getText().toString());
                Log.d("correct", data.getString("correct"));
                correct_answer = button.getText().toString().equals(data.getString("correct"));
                if (correct_answer) {
                    button.setBackgroundColor(getResources().getColor(R.color.button_material_light));
                } else {
                    button.setBackgroundColor(getResources().getColor(R.color.button_material_dark));
                }
                new AnswerQuestion().execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private class AnswerQuestion extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                InputStream inputStream = null;
                String result;
                try {
                    String url = "http://192.168.1.79:5000/answer_question/1";
                    // create HttpClient
                    HttpClient httpclient = new DefaultHttpClient();

                    // make POST request to the given URL
                    HttpPost post = new HttpPost(url);
                    post.setHeader("Content-type", "application/json");

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("user_id", String.valueOf(MainActivity.ID)));
                    nameValuePairs.add(new BasicNameValuePair("question_correct", correct_answer ? "1" : "0"));
                    nameValuePairs.add(new BasicNameValuePair("question_index", String.valueOf(index)));

                    JSONObject obj = new JSONObject();
                    obj.put("user_id", String.valueOf(MainActivity.ID));
                    obj.put("question_correct", correct_answer ? "1" : "0");
                    obj.put("question_index", String.valueOf(index));

                    StringEntity se = new StringEntity(obj.toString());
                    post.setEntity(se);

                    HttpResponse httpResponse = httpclient.execute(post);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }
        }
    }




}
