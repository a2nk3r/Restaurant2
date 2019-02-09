package com.example.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox[] = new CheckBox[4];
    int arr[] = new int[4];
    Button goBtn;
    static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkbox[0] = findViewById(R.id.checkBox1);
        checkbox[1] = findViewById(R.id.checkBox2);
        checkbox[2] = findViewById(R.id.checkBox3);
        checkbox[3] = findViewById(R.id.checkBox4);

        goBtn = findViewById(R.id.goBtn);
        goBtn.setEnabled(false);

        for(int i = 0;i < 4;i++) {
            checkbox[i].setOnCheckedChangeListener(this);
        }

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i < 4;i++){
                    if(checkbox[i].isChecked())
                        arr[i] = 1;
                }
                Intent dish = new Intent(getApplicationContext(), DishActivity.class);
                dish.putExtra("checkboxes", arr);
                startActivity(dish);
                arr = new int[4];
            }
        });

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        count += (isChecked) ? 1 : -1;
        if(count != 0)
            goBtn.setEnabled(true);
        else goBtn.setEnabled(false);

    }
}
