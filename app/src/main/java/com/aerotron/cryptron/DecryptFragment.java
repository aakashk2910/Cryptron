package com.aerotron.cryptron;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;

/**
 * Created by Aakash Kamble on 11-04-2016.
 */
public class DecryptFragment extends Fragment {
    View myView;

    private static final int SELECT_PICTURE = 0;
    private static final int FILE_SELECT_CODE = 0;
    private String selectedImagePath;

    ImageButton selectFile;
    ImageButton decrypt;
    EditText passKey;
    TextView path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(com.aerotron.cryptron.R.layout.decrypt_layout, container, false);
        selectFile = (ImageButton) myView.findViewById(com.aerotron.cryptron.R.id.btn_select_image);
        passKey = (EditText) myView.findViewById(com.aerotron.cryptron.R.id.passkey);
        decrypt = (ImageButton) myView.findViewById(com.aerotron.cryptron.R.id.btn_decrypt);
        path = (TextView) myView.findViewById(com.aerotron.cryptron.R.id.text_view);

        getActivity().setTitle("Decryption");

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Cryptron/Encrypted/");
                intent.setDataAndType(uri, "*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryption();
            }
        });

        return myView;
    }

    public  void decryption(){


        if (!validate()) {
            failed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                com.aerotron.cryptron.R.style.Base_ThemeOverlay_AppCompat_Light);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Decrypting Image...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Crypto c = new Crypto();
                        c.decrypt(selectedImagePath, passKey.getText().toString());
                        progressDialog.dismiss();
                    }
                }, 3000);
        Toast.makeText(getActivity(), "Decryption Completed", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (AdBuddiz.isReadyToShowAd(getActivity())) { // this = current Activity
                            AdBuddiz.showAd(getActivity()); // showAd will always display an ad
                        } else {
                            // use another ad network
                        }
                    }
                }, 3000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                path.setText("Path: " + selectedImagePath);

            }
        }


    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }


    public boolean validate() {
        boolean valid = true;

        String pass = passKey.getText().toString();
        String p = path.getText().toString();

        if (pass.isEmpty() || pass.length() != 8) {
            passKey.setError("8 digit passkey required ");
            valid = false;
        } else {
            passKey.setError(null);
        }

        if (p.isEmpty() || path.equals("null")){
           path.setError("Image not selected");
            valid = false;
        } else  {
            path.setError(null);
        }

        return valid;
    }

    public void failed() {
        Toast.makeText(getActivity(), "Decryption Failed", Toast.LENGTH_LONG).show();
        decrypt.setEnabled(true);
        return;
    }

}
