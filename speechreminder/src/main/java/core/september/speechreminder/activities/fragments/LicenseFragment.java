package core.september.speechreminder.activities.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.september.android.basement.WebViewFragment;
import core.september.speechreminder.R;

/**
 * Created by ilgrac on 05/05/14.
 */
public class LicenseFragment extends WebViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView view = (WebView) super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        InputStream stream = getResources().openRawResource(R.raw.license);
        InputStreamReader inputreader = new InputStreamReader(stream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line = "";
        StringBuilder text = new StringBuilder();
        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return view;
        }
        view.loadData(text.toString(), "text/html", null);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getActivity().getMenuInflater();
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }
}
