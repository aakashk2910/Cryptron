package com.aerotron.cryptron;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Aakash Kamble on 16-03-2016.
 */
public class AboutUsFragment extends Fragment{

    View myView;
    TextView feedback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(com.aerotron.cryptron.R.layout.about_us_layout, container, false);

        getActivity().setTitle("About");


        feedback = (TextView) myView.findViewById(com.aerotron.cryptron.R.id.btn_feedback);
        feedback.setText(Html.fromHtml("<a href=\"mailto:aakashk2910@gmail.com\">aakashk2910@gmail.com</a>"));
        feedback.setMovementMethod(LinkMovementMethod.getInstance());

        return myView;
    }

}
