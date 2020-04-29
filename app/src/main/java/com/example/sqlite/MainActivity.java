package com.example.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    String strID, strNAME, strADDRESS, strPHONE, strAVATAR;

    ListView listStudent;
    ArrayList<Student> students;
    StudentAdapter studentAdapter;

    int position;
    Database database;

    boolean visible = false;

    int getPosition;
    int getPosition2;

    FloatingActionButton fabHelp, fabAdd;

    public static final int REQUEST_CODE = 113;
    public static final int RESULT_CODE = 114;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listStudent = (ListView) findViewById(R.id.list_student);
        students = new ArrayList<Student>();

        studentAdapter = new StudentAdapter(this, R.layout.student_item, students);
        listStudent.setAdapter(studentAdapter);

        createDatabase();

        studentAdapter.notifyDataSetChanged();


        listStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                getPosition = position;
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm Deletion");
                builder.setCancelable(false);
                builder.setMessage("Are you sure delete this student?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete();
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPosition2 = position;
                sendToMain2();
            }
        });

        fabButton();
    }

    public void createDatabase() {
        database = new Database(this, "student.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS STUDENT(ID VARCHAR(10) PRIMARY KEY, NAME VARCHAR(20), ADDRESS VARCHAR(100), PHONE VARCHAR(10), AVATAR VARCHAR(200))");

        Cursor cursor = database.GetData("SELECT * FROM STUDENT");
        students.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String phone = cursor.getString(3);
            String avatar = cursor.getString(4);
            students.add(new Student(id, name, address, phone, avatar));
        }
    }

    public void fabButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabHelp = (FloatingActionButton) findViewById(R.id.fab_help);

        fabHelp.hide();
        fabAdd.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visible();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHelp();
            }
        });

    }

    public void goToHelp() {
        Intent intent2 = new Intent(this, HelpActivity.class);
        startActivity(intent2);
    }

    public void visible() {
        if (visible == false) {
            fabHelp.show();
            fabAdd.show();
            visible = true;
        } else {
            fabAdd.hide();
            fabHelp.hide();
            visible = false;
        }
    }

    public void delete() {
        String idSt = students.get(getPosition).getId();
        database.QueryData("DELETE FROM STUDENT WHERE ID = '" + idSt + "' ");
        students.remove(students.get(getPosition));
        studentAdapter.notifyDataSetChanged();
    }

    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_them, null);

        final EditText editID = (EditText) alertLayout.findViewById(R.id.student_id_add);
        final EditText editNAME = (EditText) alertLayout.findViewById(R.id.student_name_add);
        final EditText editADDRESS = (EditText) alertLayout.findViewById(R.id.student_address_add);
        final EditText editPHONE = (EditText) alertLayout.findViewById(R.id.student_phone_add);
        final EditText editAVARTAR = (EditText) alertLayout.findViewById(R.id.student_avatar_add);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Canceled", LENGTH_LONG).show();
            }
        });
        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editID.getText().toString().trim().equalsIgnoreCase("") && editNAME.getText().toString().trim().equalsIgnoreCase("") && editADDRESS.getText().toString().trim().equalsIgnoreCase("") && editPHONE.getText().toString().trim().equalsIgnoreCase("") && editAVARTAR.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Not Completed!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                if (editID.getText().toString().trim().equalsIgnoreCase("") || editNAME.getText().toString().trim().equalsIgnoreCase("") || editADDRESS.getText().toString().trim().equalsIgnoreCase("") || editPHONE.getText().toString().trim().equalsIgnoreCase("") || editAVARTAR.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Not Completed!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    strID = editID.getText().toString().trim();
                    strNAME = editNAME.getText().toString().trim();
                    strADDRESS = editADDRESS.getText().toString().trim();
                    strPHONE = editPHONE.getText().toString().trim();
                    strAVATAR = editString̣̣̣(editAVARTAR.getText().toString().trim());

                    database.QueryData("INSERT INTO STUDENT VALUES('" + strID + "', '" + strNAME + "', '" + strADDRESS + "','" + strPHONE + "', '" + strAVATAR + "')");

                    Cursor cursor2 = database.GetData("SELECT * FROM STUDENT");
                    students.clear();
                    while (cursor2.moveToNext()) {
                        String id = cursor2.getString(0);
                        String name = cursor2.getString(1);
                        String address = cursor2.getString(2);
                        String phone = cursor2.getString(3);
                        String avatar = cursor2.getString(4);
                        students.add(new Student(id, name, address, phone, avatar));
                    }
                    studentAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Completed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void sendToMain2() {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        Student st = students.get(getPosition2);
        Bundle bundle = new Bundle();
        bundle.putString("id", st.getId());
        bundle.putString("name", st.getName());
        bundle.putString("address", st.getAddress());
        bundle.putString("phone", st.getPhone());
        bundle.putString("avatar", "https://" + st.getAvatar());
        intent.putExtra("data", bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CODE) {
                Bundle bundle3 = data.getBundleExtra("STUDENTS");
                String id = bundle3.getString("ID");
                String name = bundle3.getString("NAME");
                String address = bundle3.getString("ADDRESS");
                String phone = bundle3.getString("PHONE");
                String avatar = editString̣̣̣(bundle3.getString("AVATAR"));

                Student st = students.get(getPosition2);
                st.setName(name);
                st.setAddress(address);
                st.setPhone(phone);
                st.setAvatar(avatar);
                database.QueryData("UPDATE STUDENT SET NAME = '" + name + "' WHERE ID = '" + st.getId() + "'");
                database.QueryData("UPDATE STUDENT SET ADDRESS = '" + address + "' WHERE ID = '" + st.getId() + "'");
                database.QueryData("UPDATE STUDENT SET PHONE = '" + address + "' WHERE ID = '" + st.getId() + "'");
                database.QueryData("UPDATE STUDENT SET AVATAR = '" + avatar + "' WHERE ID = '" + st.getId() + "'");
                studentAdapter.notifyDataSetChanged();
            }
        }
    }

    public void receivedFromMain2() {
    }

    public String editString̣̣̣(String string) {
        String result = "";
        String link = "https://";
        String link2 = "http://";
        if (string.startsWith(link)) {
            result = string.substring(link.length());
        }
        if (string.startsWith(link2)) {
            result = string.substring(link2.length());
        }
        return result;
    }

}
