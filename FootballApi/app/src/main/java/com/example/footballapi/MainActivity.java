package com.example.footballapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String arrayLeagues[] = new String[] { "Champions League","Premier League","Serie A","La Liga","Bundesliga","Ligue 1"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        final ListView listView = (ListView) findViewById(R.id.listView1);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button calendar = (Button) findViewById(R.id.calendar);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(c);
        textView.setText(date);
        final String[] todayDate = {"dateFrom=" + date + "&dateTo=" + date};
        int flag = 0;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int date) {

                String stringDate = ""+date;
                String stringMonth = ""+(month+1);
                if(date<10) {
                    stringDate = "0" + date;
                }
                if(month<10){
                    stringMonth = "0" + (month+1);
                }
                String dateSelected = year + "-" + stringMonth + "-" + stringDate;
                textView.setText(dateSelected);
                todayDate[0] = "dateFrom=" + dateSelected + "&dateTo=" + dateSelected;
                String spinnerText = spinner.getSelectedItem().toString();
                if(spinnerText=="Champions League"){
                    leagues("https://api.football-data.org/v2/matches?competitions=CL&" + todayDate[0], listView, "CL");
                }
                else if(spinnerText=="Premier League") {
                    leagues("https://api.football-data.org/v2/matches?competitions=PL&" + todayDate[0], listView, "PL");
                }
                else if(spinnerText=="Serie A"){
                    leagues("https://api.football-data.org/v2/matches?competitions=SA&" + todayDate[0], listView, "SA");
                }
                else if(spinnerText=="La Liga"){
                    leagues("https://api.football-data.org/v2/matches?competitions=PD&" + todayDate[0], listView, "PD");
                }
                else if(spinnerText=="Bundesliga"){
                    leagues("https://api.football-data.org/v2/matches?competitions=BL1&" + todayDate[0], listView, "BL1");
                }
                else if(spinnerText=="Ligue 1"){
                    leagues("https://api.football-data.org/v2/matches?competitions=FL1&" + todayDate[0], listView, "FL1");
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i==0) {
                            leagues("https://api.football-data.org/v2/matches?competitions=CL&" + todayDate[0],listView,"CL");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                        else if(i == 1) {
                            leagues("https://api.football-data.org/v2/matches?competitions=PL&"+ todayDate[0],listView,"PL");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                        else if(i == 2) {
                            leagues("https://api.football-data.org/v2/matches?competitions=SA&" + todayDate[0],listView,"SA");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                        else if(i == 3) {
                            leagues("https://api.football-data.org/v2/matches?competitions=PD&" + todayDate[0],listView,"PD");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                        else if(i == 4) {
                            leagues("https://api.football-data.org/v2/matches?competitions=BL1&" + todayDate[0],listView,"BL1");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                        else if(i == 5) {
                            leagues("https://api.football-data.org/v2/matches?competitions=FL1&" + todayDate[0],listView,"FL1");
                            listView.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {


                    }
                });


                listView.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.INVISIBLE);

            }
        });






        queue = Volley.newRequestQueue(this);
        //leagues("https://api.football-data.org/v2/competitions/CL/matches",listView,"CL"); //Champions League

        //https://api.football-data.org/v2/matches Today's Matches
        //https://api.football-data.org/v2/matches?competitions=CL&dateFrom=2020-08-16&dateTo=2020-08-22 Day to day


          /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  if (i == 0) {
                      leagues("https://api.football-data.org/v2/matches?competitions=CL&dateFrom=2020-08-23&dateTo=2020-08-23", listView, "CL");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  } else if (i == 1) {
                      leagues("https://api.football-data.org/v2/matches?competitions=PL&" + todayDate[0], listView, "PL");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  } else if (i == 2) {
                      leagues("https://api.football-data.org/v2/competitions/SA/matches", listView, "SA");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  } else if (i == 3) {
                      leagues("https://api.football-data.org/v2/competitions/PD/matches", listView, "PD");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  } else if (i == 4) {
                      leagues("https://api.football-data.org/v2/competitions/BL1/matches", listView, "BL1");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  } else if (i == 5) {
                      leagues("https://api.football-data.org/v2/competitions/FL1/matches", listView, "FL1");
                      listView.setVisibility(View.VISIBLE);
                      calendarView.setVisibility(View.INVISIBLE);
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {


              }
          });
      */

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayLeagues);
        spinner.setAdapter(adapterSpinner);

        calendarView.setVisibility(View.INVISIBLE);
        calendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    calendarView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);


            }
        });
       /* calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int date) {

                String stringDate = ""+date;
                String stringMonth = ""+(month+1);
                if(date<10) {
                    stringDate = "0" + date;
                }
                if(month<10){
                    stringMonth = "0" + (month+1);
                }

                    String dateSelected = year + "-" + stringMonth + "-" + stringDate;
                    textView.setText(dateSelected);
                    String urlDate= "dateFrom=" + dateSelected + "&dateTo=" + dateSelected;
                    String url = "https://api.football-data.org/v2/matches?competitions=PL&"+urlDate;
                    calendarView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    leagues(url,listView,"CL");


            }
        });*/

    }
private void leagues(String url, final ListView listView, final String leagueName) {
        queue = Volley.newRequestQueue(this);
        //final ListView listView = (ListView) findViewById(R.id.listView1);

        //final TextView textView = (TextView) findViewById(R.id.textView);
        //final TextView textView2 = (TextView) findViewById(R.id.textView2);



        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //textView.setText("Response: " + response);
                        JSONArray arr = null;

                        try {
                            arr = response.getJSONArray("matches");

                            String[] homeTeams = new String[arr.length()];
                            String[] awayTeams = new String[arr.length()];
                            String[] scoreHomeTeam = new String[arr.length()];
                            String[] scoreAwayTeam = new String[arr.length()];
                            String[] matches = new String[arr.length()];
                            String[] result = new String[arr.length()];


                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject item = arr.getJSONObject(i);
                                JSONObject homeTeamJson = item.getJSONObject("homeTeam");
                                JSONObject awayTeamJson = item.getJSONObject("awayTeam");
                                JSONObject scoreJson = item.getJSONObject("score");
                                JSONObject scoreFullTimeJson = scoreJson.getJSONObject("fullTime");


                                result[i] = arr.getJSONObject(i).getString("status");
                                homeTeams[i] = homeTeamJson.getString("name");
                                awayTeams[i] = awayTeamJson.getString("name");
                                scoreHomeTeam[i] = scoreFullTimeJson.getString("homeTeam");
                                scoreAwayTeam[i] = scoreFullTimeJson.getString("awayTeam");

                                if (scoreHomeTeam[i] == "null") {
                                    matches[i] = homeTeams[i] + " " + "" + "-" + "" + " " + awayTeams[i] + " | " + result[i];
                                } else {
                                    matches[i] = homeTeams[i] + " " + scoreHomeTeam[i] + "-" + scoreAwayTeam[i] + " " + awayTeams[i] + " | " + result[i];
                                }
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, matches){


                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    View view = super.getView(position, convertView, parent);
                                    TextView text = (TextView) view.findViewById(android.R.id.text1);

                                    //if (leagueName == "CL" || leagueName == "PL")
                                        text.setTextColor(Color.WHITE);
                                    return view;
                                }
                            };

                            listView.setAdapter(adapter);

                            //textView.setText(homeTeams[1]);
                            //textView2.setText(awayTeams[i]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-Auth-Token", "5756451bf1374656a97b33783aedec74");
                return params;
            }
        };

        queue.add(jsonObjectRequest);

    }



}
