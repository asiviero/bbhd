package com.michaelchiavegato.baseballhack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by michaelchiavegato on 2016-03-19.
 */
public class ScoreboardFragment extends Fragment {

    private JSONObject data;
    private JSONArray Scoreboard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        try {
            data = new JSONObject(bundle.getString("data"));
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
        View v = inflater.inflate(R.layout.scoreboard_layout, container, false);

        TextView team1q1 = (TextView) v.findViewById(R.id.score1q1);
        TextView team1q2 = (TextView) v.findViewById(R.id.score1q2);
        TextView team1q3 = (TextView) v.findViewById(R.id.score1q3);
        TextView team1q4 = (TextView) v.findViewById(R.id.score1q4);
        TextView team1q5 = (TextView) v.findViewById(R.id.score1q5);
        TextView team1q6 = (TextView) v.findViewById(R.id.score1q6);
        TextView team1q7 = (TextView) v.findViewById(R.id.score1q7);
        TextView team1q8 = (TextView) v.findViewById(R.id.score1q8);
        TextView team1q9 = (TextView) v.findViewById(R.id.score1q9);

        TextView team2q1 = (TextView) v.findViewById(R.id.score1q1);
        TextView team2q2 = (TextView) v.findViewById(R.id.score2q2);
        TextView team2q3 = (TextView) v.findViewById(R.id.score2q3);
        TextView team2q4 = (TextView) v.findViewById(R.id.score2q4);
        TextView team2q5 = (TextView) v.findViewById(R.id.score2q5);
        TextView team2q6 = (TextView) v.findViewById(R.id.score2q6);
        TextView team2q7 = (TextView) v.findViewById(R.id.score2q7);
        TextView team2q8 = (TextView) v.findViewById(R.id.score2q8);
        TextView team2q9 = (TextView) v.findViewById(R.id.score2q9);

        TextView team1tot = (TextView) v.findViewById(R.id.score1tot);
        TextView team2tot = (TextView) v.findViewById(R.id.score2tot);


        //TextView tv = (TextView) v.findViewById(R.id.questionTitle);

            //tv.setText(data.getString("title"));
            //String s = data.getString("choices");

            String str_1 = "1,4,5";
            String str_2 = "2,3,8";

            // String[] choiceArray = s.split(", ");

            if(str_1.indexOf("0") != -1) {
                team1q1.setText("1");
            }
            if(str_1.indexOf("1") != -1) {
                team1q2.setText("1");
            }
            if(str_1.indexOf("2") != -1) {
                team1q3.setText("1");
            }
            if(str_1.indexOf("3") != -1) {
                team1q4.setText("1");
            }
            if(str_1.indexOf("4") != -1) {
                team1q5.setText("1");
            }
            if(str_1.indexOf("5") != -1) {
                team1q6.setText("1");
            }

            if(str_1.indexOf("6") != -1) {
                team1q7.setText("1");
            }
            if(str_1.indexOf("7") != -1) {
                team1q8.setText("1");
            }
            if(str_1.indexOf("8") != -1) {
                team1q9.setText("1");
            }

            if(str_2.indexOf("0") != -1) {
                team2q1.setText("1");
            }
            if(str_2.indexOf("1") != -1) {
                team2q2.setText("1");
            }
            if(str_2.indexOf("2") != -1) {
                team2q3.setText("1");
            }
            if(str_2.indexOf("3") != -1) {
                team2q4.setText("1");
            }
            if(str_2.indexOf("4") != -1) {
                team2q5.setText("1");
            }
            if(str_2.indexOf("5") != -1) {
                team2q6.setText("1");
            }

            if(str_2.indexOf("6") != -1) {
                team2q7.setText("1");
            }
            if(str_2.indexOf("7") != -1) {
                team2q8.setText("1");
            }
            if(str_2.indexOf("8") != -1) {
                team2q9.setText("1");
            }




        return v;
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result;
            try {
                String url = "http://192.168.1.79:5000/actual_scoreboard/1/";
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                    JSONObject o = new JSONObject(result);

                    Scoreboard = o.getJSONArray("questions");
                    for (int i = 0; i < Scoreboard.length(); i++) {
                        Log.d("Questions:", Scoreboard.getJSONObject(i).getString("title"));
                    }
                    Log.i("Result: ", result);
                }
                else
                    result = "Did not work!";

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static ViewPageFragment newInstance(JSONObject param1) {
        ViewPageFragment fragment = new ViewPageFragment();
        Bundle b = new Bundle();
        b.putString("data", param1.toString());

        fragment.setArguments(b);

        return fragment;
    }

    public ScoreboardFragment() {
        // Required empty public constructor
    }

}
