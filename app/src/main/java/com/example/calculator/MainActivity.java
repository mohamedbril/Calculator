package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables renamed to be completely different
    private EditText champSuperficie;
    private EditText champChambres;
    private CheckBox casePiscine;
    private TextView affichageTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking views to the new variable names
        champSuperficie = findViewById(R.id.input_surface);
        champChambres = findViewById(R.id.input_pieces);
        casePiscine = findViewById(R.id.checkbox_piscine);
        affichageTotal = findViewById(R.id.result);
        
        Button btnCompute = findViewById(R.id.button_calcul);

        // Using an anonymous inner class instead of a lambda (v -> ...)
        btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executerEvaluation();
            }
        });
    }

    // Renamed the main execution method
    private void executerEvaluation() {
        try {
            // Extracting strings first before parsing
            String strArea = champSuperficie.getText().toString();
            String strRooms = champChambres.getText().toString();

            double areaValue = Double.parseDouble(strArea);
            int roomAmount = Integer.parseInt(strRooms);
            boolean isPoolPresent = casePiscine.isChecked();

            // Calling a separate function to handle the mathematical logic
            double finalAmount = computeTaxFormula(areaValue, roomAmount, isPoolPresent);

            // Using String.format for cleaner text construction instead of concatenation (+)
            String outputMessage = String.format("Impôt total : %.1f DH", finalAmount);
            affichageTotal.setText(outputMessage);

        } catch (NumberFormatException e) {
            // Safety measure: Catches the error if the user clicks calculate with empty fields
            Toast.makeText(this, "Veuillez remplir tous les champs avec des nombres.", Toast.LENGTH_SHORT).show();
        }
    }

    // New helper method created specifically to isolate the calculation logic
    private double computeTaxFormula(double area, int rooms, boolean hasPool) {
        double baseRate = area * 2.0;
        double roomSurcharge = rooms * 50.0;
        double poolFee = hasPool ? 100.0 : 0.0;
        
        return baseRate + roomSurcharge + poolFee;
    }
}
