package com.dou.unitpricedivider;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView listView;
    UPAdapter adapter;
    ArrayList<HashMap<String, Float>> items = new ArrayList<>();

    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = this;

        button = (Button)findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        listView = (ListView)findViewById(R.id.list_items);
        adapter = new UPAdapter(c, items);
        listView.setAdapter(adapter);
    }

    private class itemcal{
        float price =0;
        float count =0;
        float unit =0;

        @Deprecated
        itemcal(){}

        itemcal(float p,float c){
            price =p;
            count =c;
            unit = price/count;
        }
    }

    protected void showAddDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.add_dialog_layout, null);
        final EditText eprice = (EditText) textEntryView.findViewById(R.id.editText_add_p);
        final EditText ecount = (EditText) textEntryView.findViewById(R.id.editText_add_c);

        AlertDialog.Builder ad1 = new AlertDialog.Builder(c);

        ad1.setTitle("Add Goods:");
        ad1.setIcon(android.R.drawable.ic_input_add);
        ad1.setView(textEntryView);
        ad1.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

                try {
                    if (eprice.getText().toString().isEmpty()||ecount.getText().toString().isEmpty()){
                        return;
                    }

                    itemcal itm = new itemcal(
                            Float.parseFloat(eprice.getText().toString()),
                            Float.parseFloat(ecount.getText().toString())
                    );
                    HashMap<String, Float> map = new HashMap<>();
                    map.put("price", itm.price);
                    map.put("count", itm.count);
                    map.put("unit", itm.unit);
                    items.add(map);

                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(c, "......", Toast.LENGTH_SHORT).show();
                }


            }
        });
        ad1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        ad1.show();// 显示对话框

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showKB(eprice);
                    }
                });
            }
        };
        timer.schedule(timerTask, 200);

    }

    private void showKB(EditText editText) {
        InputMethodManager im = ((InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (editText != null && im != null){
            editText.requestFocus();
            im.showSoftInput(editText, 0);
        }
    }
}
