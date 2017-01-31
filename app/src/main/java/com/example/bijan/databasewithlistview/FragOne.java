package com.example.bijan.databasewithlistview;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragOne extends Fragment {

    EditText editText1,editText2;
    Button button;
    ListView listView;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    MyDatabase myDatabase;


    public FragOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabase = new MyDatabase(getActivity());
        myDatabase.open();
    }

    @Override
    public void onDestroy() {
        myDatabase.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag_one, container, false);

        editText1 = (EditText) v.findViewById(R.id.edittext1);
        editText2 = (EditText) v.findViewById(R.id.edittext2);
        button = (Button) v.findViewById(R.id.button);
        listView = (ListView) v.findViewById(R.id.listview);

        //DISPLAYING DATABASE TABLE INFO ON LISTVIEW

        //step 1 (CALLING QUARYSTUDENT)
        cursor = myDatabase.quaryStudent();

        //step 2 (ESTABLISHED LINK BETWEEN CURSOR AND CURSOR ADPTR)
        simpleCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.row,
                cursor,
                new String[]{"_id", "sname", "ssub"},
                new int[]{R.id.textview1, R.id.textview2, R.id.textview3});

        //step 3 (ESTABLISHED LINK BETWEEN CURSOR ADPTR AND LISTVIEW)
        listView.setAdapter(simpleCursorAdapter);

        //HANDELING LISTVIEW ITEMCLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //REQUEST CURSOR TO MOVE POISTION i
                cursor.moveToPosition(i);
                int sno = cursor.getInt(0);
                String name = cursor.getString(1);
                String sub = cursor.getString(2);

                Toast.makeText(getActivity(), "Student id : "+sno, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Name : "+name, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Subject : "+sub, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                String sub = editText2.getText().toString();
                myDatabase.insertStudent(name, sub);
                cursor.requery();
                Toast.makeText(getActivity(), "Inserted One Row", Toast.LENGTH_SHORT).show();

                editText1.setText("");
                editText2.setText("");
                editText1.requestFocus();


            }
        });

        return  v;
    }

}
