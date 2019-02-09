package com.example.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;


public class DishActivity extends AppCompatActivity{

    private ArrayList<Dish> Dishes = new ArrayList<>();
    static int checkboxes[];
    static int dishesOfCuisines[] = new int[4];

    void addDishes(Cuisine C, int noOfDishes){
        dishesOfCuisines[C.ordinal()] = noOfDishes;
        for(int i = 0;i < noOfDishes;i++){
            this.Dishes.add(new Dish(C,C.toString().toLowerCase() + (i+1), (i+1)*50));
        }
    }
    private static int findTotalNoOfDishes(){
        int totalNoOfDishes = 0;
        for(int i = 0;i < 4;i++)
            totalNoOfDishes += dishesOfCuisines[i];
        return totalNoOfDishes;
    }
    void showSummary(int noOfDishesSelected, StringBuilder selectedDishes, int totalPrice){
        new AlertDialog.Builder(this)
                .setTitle("Summary")
                .setMessage(noOfDishesSelected + " Dishes selected:\n" + selectedDishes + "\nTotal Price: " + totalPrice)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);


        final TableLayout tableLayout = findViewById(R.id.table_parent);

        Intent i = getIntent();
        checkboxes = i.getIntArrayExtra("checkboxes");

        this.addDishes(Cuisine.CHINESE, 1);
        this.addDishes(Cuisine.ITALIAN, 2);
        this.addDishes(Cuisine.INDIAN, 3);
        this.addDishes(Cuisine.ARABIAN, 4);

        int noOfDishesToBeDisplayed = findTotalNoOfDishes();
        int sno = 1;
        for(int n = 0;n < noOfDishesToBeDisplayed;n++){

            if(checkboxes[Dishes.get(n).getCuisine().ordinal()] == 1){
                TableRow row = new TableRow(this);
                TextView serialNo = new TextView(this);
                RadioButton radio = new RadioButton(this);
                TextView price = new TextView(this);
                serialNo.setTypeface(null, Typeface.BOLD);
                serialNo.setText(getString(R.string.serialno, sno++));
                radio.setId(View.generateViewId());
                radio.setText(Dishes.get(n).getDish());
                price.setText(getString(R.string.price, Dishes.get(n).getPrice()));
                serialNo.setGravity(Gravity.CENTER);
                radio.setGravity(Gravity.CENTER);
                price.setGravity(Gravity.CENTER);
                row.addView(serialNo);
                row.addView(radio);
                row.addView(price);

                tableLayout.addView(row);
            }
        }
        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.showPriceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int childCount = tableLayout.getChildCount();
                StringBuilder selectedDishes = new StringBuilder();
                int totalPrice = 0;
                int noOfDishesSelected = 0;
                for(int i = 0;i < childCount;i++){
                    TableRow row = (TableRow)tableLayout.getChildAt(i+2);
                    if(row == null)
                        break;
                    RadioButton radio = (RadioButton)row.getChildAt(1);
                    if(radio.isChecked()) {
                        selectedDishes.append(radio.getText().toString()).append("\n");
                        TextView priceView = (TextView) row.getChildAt(2);
                        String priceString = priceView.getText().toString();
                        int price = Integer.parseInt(priceString.substring(priceString.indexOf('₹') + 2, priceString.indexOf('/')-1));
                        totalPrice += price;
                        noOfDishesSelected++;
                    }
                }
                showSummary(noOfDishesSelected, selectedDishes, totalPrice);
            }
        });
    }
}
enum Cuisine{
    CHINESE, ITALIAN, INDIAN, ARABIAN
}
class Dish{
    private Cuisine cuisine;
    private String dishName;
    private int price;
    Dish(Cuisine cuisine, String dish, int price){
        this.cuisine = cuisine;
        this.dishName = dish;
        this.price = price;
    }
    String getDish(){
        return this.dishName;
    }
    int getPrice(){
        return this.price;
    }
    Cuisine getCuisine(){
        return this.cuisine;
    }
}