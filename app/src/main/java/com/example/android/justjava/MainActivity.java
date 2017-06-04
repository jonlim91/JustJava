/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.custom;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    private CompoundButton checkbox;
    int duration = Toast.LENGTH_SHORT;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Create summary of the order.
     *
     * @param hasWhippedCream denotes whether or not the user wants whipped cream topping
     * @param hasChocolate denotes whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    public void submitOrder(View view) {
        boolean hasWhippedCream, hasChocolate;
        String customerName;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        EditText name = (EditText) findViewById(R.id.customer_name_edittext);
        customerName = name.getText().toString();

        int total_price = calculatePrice(hasWhippedCream, hasChocolate);
        //displayMessage(createOrderSummary(total_price));

        String orderSummary = createOrderSummary(customerName, total_price, hasWhippedCream, hasChocolate);
        //Log.v("MainActivity", "Order summary: " + orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.justjava_order, customerName));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method is called when the plus button is clicked
     */
    public void increment(View view){
        if(quantity<100){
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else{
            Toast.makeText(this, getString(R.string.max_order_toast), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view){

        if(quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else{
            Toast.makeText(this, getString(R.string.min_order_toast), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given text on the screen.
     */
/*    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/

    /**
     * Calculates the price of the order.
     *
     * @return the total price
     */
     private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;

         //add $1 if customer adds whipped cream
        if(hasWhippedCream){price += 1;}
        //add $2 if customer adds chocolate
        if(hasChocolate) {price += 2; }
        return quantity * price;
     }

    /**
     * @return a message summarizing the order
     */
    private String createOrderSummary(String customerName, int priceOfOrder, boolean hasWhippedCream, boolean hasChocolate){
        String addWhippedCream = "" + hasWhippedCream, addChocolate = "" + hasChocolate, whippedCream, name;
        String priceMessage = getString(R.string.order_summary_name, customerName);
        /*whippedCream = getString(R.string.add_whipped_cream, addWhippedCream);
        name = getString(R.string.order_summary_name, customerName);
        Log.v("MainActivity", "Text is " + addWhippedCream + ", should be: " + whippedCream);
        Log.v("MainActivity", "Name: " + name);*/
        /**/priceMessage += "\n" + getString(R.string.add_whipped_cream, addWhippedCream);
        /**/priceMessage += "\n" + getString(R.string.add_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_quantity, quantity);
        priceMessage += "\n" + getString(R.string.total_price, priceOfOrder);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

}