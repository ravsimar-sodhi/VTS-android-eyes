package ssadteam5.vtsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DeviceList extends Fragment
{

    private String token;
    private DeviceFetchTask mFetchTask;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        token = getArguments().getString("token");
//        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFeWVkZW50aWZ5Iiwib3JnYW5pc2F0aW9uSWQiOiI1OTY0YTIxMWMxZjA4MTQ5MGU1MWVlNTQiLCJjcmVhdGVkIjoxNTA1ODE3Njg5NDU4LCJyb2xlcyI6WyJTVVBFUl9BRE1JTiIsIlNVUEVSX0FETUlOIl0sIm9yZ2FuaXNhdGlvbiI6ImV5ZWRlbnRpZnkiLCJpZCI6IjU5NjRhMjExYzFmMDgxNDkwZTUxZWU1OCIsImlhdCI6MTUwNTgxNzY4OX0.uyYSCcBRfjBjJwMPyXYVV6t0aLpieYeWIZOiB8DsNZVDq_IgB5Gv-cyUUEkKsoe7l2K6bZtepx4VqJ2fNBh8Mw";
        mFetchTask = new DeviceFetchTask(token);
        mFetchTask.execute((Void) null);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Fragment 1");
    }
    public class DeviceFetchTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String mToken;

        DeviceFetchTask(String token)
        {
            mToken = token;
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {

            HttpURLConnection conn;
            try {

                String response = "";
                URL url = new URL("http://eyedentifyapps.com:8080/api/auth/device/all/");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept","*/*");
                conn.setRequestProperty("Authorization","Bearer " + mToken);
                InputStream in = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1)
                {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    response += current;
                }
//                Log.d("resp",response);
            }

            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
        }

        @Override
        protected void onCancelled()
        {
        }
    }
}

