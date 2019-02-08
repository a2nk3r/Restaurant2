package com.example.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class DishActivity extends AppCompatActivity{

    ArrayList<Dish> Dishes = new ArrayList<>();

    TextView msgView;
    Button closeBtn, showPriceBtn;
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
    void showSummary(String selectedDishes, int totalPrice){

        new AlertDialog.Builder(this)
                .setTitle("Summary!")
                .setMessage("Dishes selected:\n" + selectedDishes + "\nTotal Price: " + totalPrice)
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
        closeBtn = findViewById(R.id.closeBtn);
        showPriceBtn = findViewById(R.id.showPriceBtn);
        msgView = findViewById(R.id.msgView);

        Intent i = getIntent();
        checkboxes = i.getIntArrayExtra("checkboxes");

        this.addDishes(Cuisine.CHINESE, 1);
        this.addDishes(Cuisine.ITALIAN, 2);
        this.addDishes(Cuisine.INDIAN, 3);
        this.addDishes(Cuisine.ARABIAN, 4);

        int noOfDishesToBeDisplayed = findTotalNoOfDishes();
        for(int n = 0;n < noOfDishesToBeDisplayed;n++){

            if(checkboxes[Dishes.get(n).getCuisine().ordinal()] == 1){
                TableRow row = new TableRow(this);
                RadioButton radio = new RadioButton(this);
                TextView price = new TextView(this);
                radio.setId(View.generateViewId());
                radio.setText(Dishes.get(n).getDish());
                price.setText("\tprice ₹"+Dishes.get(n).getPrice()+"/-");
                row.addView(radio);
                row.addView(price);
                tableLayout.addView(row);
            }
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int childCount = tableLayout.getChildCount();
                String selectedDishes = "";
                int totalPrice = 0;
                for(int i = 0;i < childCount;i++){
                    TableRow row = (TableRow)tableLayout.getChildAt(i);
                    RadioButton radio = (RadioButton)row.getChildAt(0);
                    if(radio.isChecked()) {
                        selectedDishes += radio.getText().toString() + "\n";
                        TextView priceView = (TextView) row.getChildAt(1);
                        String priceString = priceView.getText().toString();
                        int price = Integer.parseInt(priceString.substring(priceString.indexOf('₹') + 1, priceString.indexOf('/')));
                        totalPrice += price;
                    }
                }
                showSummary(selectedDishes, totalPrice);
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
    public Dish(Cuisine cuisine, String dish, int price){
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