package com.prado.painter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.prado.painter.model.Service;
import com.prado.painter.persistence.ServicesDatabase;

import java.util.ArrayList;



public class RegisterService extends AppCompatActivity {

    private EditText editTextNameClient,has_value;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private Spinner spinnerType;

    public static final String ID            = "ID";
    public static final String NAME          = "NAME";
    public static final String VALUE         = "VALUE";
    public static final String IS_BUDGET     = "IS_BUDGET";
    public static final String HAS_DISCOUNT  = "HAS_DISCOUNT";
    public static final String  TYPE          = "1";
    public static final int  TYPEINT          = 0;



    public static final String MODO = "MODO";
    public static final int NEW = 1;
    public static final int ALTER = 2;
    private Service service;



    private int modo;

    public static void newService (AppCompatActivity activity){
        Intent intent = new Intent(activity, RegisterService.class);
        intent.putExtra(MODO, NEW);
        activity.startActivityForResult(intent,NEW);
    }

    public static void alterService (AppCompatActivity activity, Service service){
        Intent intent = new Intent(activity, RegisterService.class);
        intent.putExtra(MODO, ALTER);


        intent.putExtra(ID, service.getId());
        intent.putExtra(NAME, service.getNameClient());
        intent.putExtra(VALUE, service.getValue());
        intent.putExtra(IS_BUDGET, service.Is_Budget());
        intent.putExtra(HAS_DISCOUNT, service.getValue());
        intent.putExtra(TYPE, service.getType());

        activity.startActivityForResult(intent, ALTER);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(getString(R.string.register));
        editTextNameClient = findViewById(R.id.name_client);
        radioGroup = findViewById(R.id.radioGroupbudget);
        checkBox = findViewById(R.id.discount);
        spinnerType = findViewById(R.id.spinnertype);
        has_value = findViewById(R.id.editTextNumber);
        put_data();



        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null){
            modo = bundle.getInt(MODO);
            if(modo == NEW){
                setTitle(getString(R.string.register));
            }else {
                RadioButton button;
                String discount = bundle.getString(HAS_DISCOUNT);
                String type = bundle.getString(TYPE);
                String budget = bundle.getString(IS_BUDGET);
                switch(type){
                    case "house":
                        spinnerType.setSelection(0);
                        break;
                    case "building":
                        spinnerType.setSelection(1);
                        break;
                    case "business":
                        spinnerType.setSelection(2);
                        break;

                }
                editTextNameClient.setText(bundle.getString(NAME));
                has_value.setText(String.valueOf(bundle.getFloat(VALUE)));
                checkBox.setChecked(true);
                if (budget.equals(getString(R.string.is_budget))) {
                    button = findViewById(R.id.yes);
                    button.setChecked(true);
                }
                else{
                    button = findViewById(R.id.no);
                    button.setChecked(true);
                }

                setTitle(getString(R.string.edit));
            }
        }
    }

    private void put_data(){
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.house));
        list.add(getString(R.string.building));
        list.add(getString(R.string.business));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        list);

        spinnerType.setAdapter(adapter);
    }

    public void clearfields(View view) {
        editTextNameClient.setText(null);
        radioGroup.clearCheck();
        checkBox.setChecked(false);
        has_value.setText(null);
        Toast.makeText(this,
                R.string.discarded_data,
                Toast.LENGTH_LONG).show();
        editTextNameClient.requestFocus();
    }


    public boolean isvalid() {
        boolean nameisvalid       = editTextNameClient.length() > 0 ? true :false ;
        boolean radioGroupisvalid = radioGroup != null ? true :false;
        boolean valuenull = has_value.getText().length() > 0 ? true :false;
        return  valuenull == false || nameisvalid == false  || radioGroupisvalid == false ? false : true;

    }

    public void saveMenu (){
            if(!isvalid()){
                Toast.makeText(this,
                        R.string.no_valid_data,
                        Toast.LENGTH_LONG).show();
            }
            else  {
                int is_budgets = 0;
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.yes:
                        is_budgets = 1;
                        break;

                    case R.id.no:
                        is_budgets = 0;
                        break;
                }

                String name = editTextNameClient.getText().toString();
                int type = spinnerType.getSelectedItemPosition();
                String  is_budget = is_budgets == 1 ? getString(R.string.is_budget) : getString(R.string.not_is_budget);
                String discount = checkBox.isChecked() ? getString(R.string.has_discount) : getString(R.string.not_has_discount);
                String value = has_value.getText().toString();
                Float valueservice = Float.parseFloat(value);

                service = new Service(name,valueservice,is_budget,discount,"0",type);

                ServicesDatabase db = ServicesDatabase.getDatabase(this);

                if (modo == NEW){
                    db.ServicesDAO().insert(service);
                }

                setResult(Activity.RESULT_OK);

                finish();
            }
    }

    public void cleanFildesMenu (){
        editTextNameClient.setText(null);
        radioGroup.clearCheck();
        checkBox.setChecked(false);
        has_value.setText(null);
        Toast.makeText(this,
                R.string.discarded_data,
                Toast.LENGTH_LONG).show();
        editTextNameClient.requestFocus();
    }


    public void viewdata(View view) {
        if(!isvalid()){
            Toast.makeText(this,
                    R.string.no_valid_data,
                    Toast.LENGTH_LONG).show();
        }
        else  {
            int is_budgets = 0;
            switch(radioGroup.getCheckedRadioButtonId()) {
                case R.id.yes:
                    is_budgets = 1;
                    break;

                case R.id.no:
                    is_budgets = 0;
                    break;
            }

            String name = editTextNameClient.getText().toString();
            String type = String.valueOf(spinnerType.getSelectedItemPosition());
            String  is_budget = is_budgets == 1 ? getString(R.string.is_budget) : getString(R.string.not_is_budget);
            String discount = checkBox.isChecked() ? getString(R.string.has_discount) : getString(R.string.not_has_discount);
            String value = has_value.getText().toString();


            Intent intent = new Intent();
            intent.putExtra(NAME, name);
            intent.putExtra(VALUE, value);
            intent.putExtra(IS_BUDGET, is_budget);
            intent.putExtra(HAS_DISCOUNT, discount);
            intent.putExtra(TYPE, type);



            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemSave:
                saveMenu();
                return true;

            case R.id.menuItemClean:
                cleanFildesMenu();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register_option, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}