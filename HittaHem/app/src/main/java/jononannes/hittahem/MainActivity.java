package jononannes.hittahem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

class HttpURLConnectionTest {
    String accessToken = null;

    public static void main() throws Exception {

        HttpURLConnectionTest http = new HttpURLConnectionTest();

        http.sendPost();

        http.sendGet();
    }

    private void sendGet() throws Exception {
        String url = "https://api.vasttrafik.se/bin/rest.exe/v2/trip?" + "originCoordLat=57.6791323" + "&originCoordLong=11.8771718" + "&originCoordName=none" + "&destCoordLat=57.6888144" + "&destCoordLong=11.9781232" + "&destCoordName=none" + "&format=json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        System.out.println(responseCode + ": " + responseMessage);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        JSONObject object = new JSONObject(response.toString());
        //System.out.println(object);
    }

    private void sendPost() throws Exception {
        //String key = "RdPqSkfT12Qe9n4MsgsgLkxfXSEa";
        //String secret = "3LKEtJTy9bP5yjMv_maDWhjMNaIa";
        //String toConvert = key + ":" + secret;
        //System.out.println("About to base64 encode key:secret");
        String authorization = "UmRQcVNrZlQxMlFlOW40TXNnc2dMa3hmWFNFYTozTEtFdEpUeTliUDV5ak12X21hRFdoak1OYUlh";
        /*
        try {
            accessToken = Base64.encodeToString(toConvert.getBytes("UTF-8"), android.util.Base64.NO_WRAP);
            System.out.println(accessToken);
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */

        String url = "https://api.vasttrafik.se/token";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Basic " + authorization);


        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("grant_type=client_credentials");
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        System.out.println(responseCode + ": " + responseMessage);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseString = response.toString();

        System.out.println(responseString);

        String[] splitResponse = responseString.split("\"");
        String accessToken = splitResponse[splitResponse.length - 2];
        System.out.println(accessToken);


        /* System.out.println(1);
        JSONObject jsonObj = new JSONObject(responseString);
        System.out.println(2);
        accessToken = jsonObj.getString("access_token");
        System.out.println(3);
        System.out.println(accessToken);
        */
    }
}



public class MainActivity extends AppCompatActivity {

    String post() {
        System.out.println("POST");
        String accessToken = null;
        String authorization = "UmRQcVNrZlQxMlFlOW40TXNnc2dMa3hmWFNFYTozTEtFdEpUeTliUDV5ak12X21hRFdoak1OYUlh";
        String url = "https://api.vasttrafik.se/token";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization", "Basic " + authorization);
            con.setDoOutput(true);
            OutputStream wr = con.getOutputStream();
            wr.write("grant_type=client_credentials".getBytes());
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseString = response.toString();

            accessToken = responseString;

            System.out.println(con.getResponseCode() + ": " + con.getResponseMessage());

            String[] splitResponse = responseString.split("\"");
            accessToken = splitResponse[splitResponse.length - 2];
        } catch(IOException e) {
            e.printStackTrace();
        } /*catch(MalformedURLException e) {
            e.printStackTrace();
        }*/
        System.out.println("Accesstoken: " + accessToken);
        return accessToken;
    }

    JSONObject get(String accessToken) {
        System.out.println("GET");
        String route = null;
        String url = "https://api.vasttrafik.se/bin/rest.exe/v2/trip?" + "originCoordLat=57.6791323" + "&originCoordLong=11.8771718" + "&originCoordName=none" + "&destCoordLat=57.6888144" + "&destCoordLong=11.9781232" + "&destCoordName=none" + "&format=json";
        try {
            URL obj = new URL(url);

            try {
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);

                // int responseCode = con.getResponseCode();
                // String responseMessage = con.getResponseMessage();
                System.out.println(con.getResponseCode() + ": " + con.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                route = response.toString();

                // System.out.println(response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    return jsonObject;
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(route);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = (Button)findViewById(R.id.btn);
        final TextView txt = (TextView)findViewById(R.id.txt);
        txt.setSingleLine(false);

        btn.setOnClickListener(new View.OnClickListener() {
            JSONObject route = null;
            String toPutInView = "";

            @Override
            public void onClick(View v) {
                toPutInView = "";
                System.out.println(route);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        route = get(post());
                        try {
                            JSONArray jsonArray = route.getJSONObject("TripList").getJSONArray("Trip").getJSONObject(0).getJSONArray("Leg");
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                if (!(jsonArray.getJSONObject(i).getString("name").equals("Gå"))) {
                                    toPutInView += jsonArray.getJSONObject(i).getString("name") + " från \n";
                                    toPutInView += jsonArray.getJSONObject(i).getJSONObject("Origin").getString("name") + "\n";
                                    toPutInView += "till " + jsonArray.getJSONObject(i).getJSONObject("Destination").getString("name") + "\n\n";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                txt.setText(toPutInView);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
