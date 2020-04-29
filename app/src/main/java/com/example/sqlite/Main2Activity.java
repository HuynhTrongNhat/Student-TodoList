package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class Main2Activity extends AppCompatActivity {

    String strNAME, strADDRESS, strPHONE, strAVATAR;

    EditText editNAME;
    EditText editADDRESS;
    EditText editPHONE;
    EditText editAVATAR;

    String link;
    TextView id, name, address, phone;
    ImageView avatar;
    FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        id = (TextView) findViewById(R.id.student_id_detail);
        name = (TextView) findViewById(R.id.student_name_detail);
        address = (TextView) findViewById(R.id.student_address_detail);
        phone = (TextView) findViewById(R.id.student_phone_detail);
        avatar = (ImageView) findViewById(R.id.avatar_detail);

        fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("data");
        id.setText("Id: " + bundle.getString("id"));
        name.setText("Name: " + bundle.getString("name"));
        address.setText("Address: " + bundle.getString("address"));
        phone.setText("Phoone number: " + bundle.getString("phone"));
        link = bundle.getString("avatar");
        Picasso.get()
                .load(bundle.getString("avatar").trim())
                .error(R.mipmap.ic_launcher)
                .into(avatar, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAvatar();
            }
        });
    }

    public void goToAvatar() {
        Intent intent2 = new Intent(this, AvatarActivity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("avatar_avatar", link);
        intent2.putExtra("DATA", bundle2);
        startActivity(intent2);
    }

    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_edit, null);

        editNAME = (EditText) alertLayout.findViewById(R.id.student_name_edit);
        editADDRESS = (EditText) alertLayout.findViewById(R.id.student_address_edit);
        editPHONE = (EditText) alertLayout.findViewById(R.id.student_phone_edit);
        editAVATAR = (EditText) alertLayout.findViewById(R.id.student_avatar_edit);

        Bundle bundle = getIntent().getBundleExtra("data");
        editNAME.setText(bundle.getString("name"));
        editADDRESS.setText(bundle.getString("address"));
        editPHONE.setText(bundle.getString("phone"));
        editAVATAR.setText(bundle.getString("avatar"));

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Main2Activity.this, "Canceled", Toast.LENGTH_LONG).show();
            }
        });
        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editNAME.getText().toString().trim().equalsIgnoreCase("") && editADDRESS.getText().toString().trim().equalsIgnoreCase("") && editPHONE.getText().toString().trim().equalsIgnoreCase("") && editAVATAR.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Main2Activity.this, "Not Completed!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                if (editNAME.getText().toString().trim().equalsIgnoreCase("") || editADDRESS.getText().toString().trim().equalsIgnoreCase("") || editPHONE.getText().toString().trim().equalsIgnoreCase("") || editAVATAR.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Main2Activity.this, "Not Completed!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    strNAME = editNAME.getText().toString().trim();
                    strADDRESS = editADDRESS.getText().toString().trim();
                    strPHONE = editPHONE.getText().toString().trim();
                    strAVATAR = "https://" + editString̣̣̣(editAVATAR.getText().toString().trim());

                    name.setText("NAME: " + strNAME);
                    address.setText("ADDRESS: " + strADDRESS);
                    phone.setText("PHONE NUMBER: " + strPHONE);

                    Picasso.get()
                            .load(strAVATAR)
                            .error(R.mipmap.ic_launcher)
                            .into(avatar, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                }
                            });

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("ID", id.getText().toString());
                    bundle3.putString("NAME", strNAME);
                    bundle3.putString("ADDRESS", strADDRESS);
                    bundle3.putString("PHONE", strPHONE);
                    bundle3.putString("AVATAR", strAVATAR);
                    getIntent().putExtra("STUDENTS", bundle3);
                    setResult(MainActivity.RESULT_CODE, getIntent());
                    finish();
                    Toast.makeText(Main2Activity.this, "Completed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public String editString̣̣̣(String string) {
        String result = "";
        String link = "https://";
        String link2 = "http://";
        if(string.startsWith(link)) {
            result = string.substring(link.length());
        }
        if(string.startsWith(link2)) {
            result = string.substring(link2.length());
        }
        return result;
    }

}
