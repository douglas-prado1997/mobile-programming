package com.prado.painter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.prado.painter.model.Service;
import com.prado.painter.persistence.ServicesDatabase;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {



    //region init

    private ListView listViewServices;
    private List<Service> services;
    private ServicesAdapter serviceAdapter;
    private ActionMode actionMode;
    private int positionseleced = -1;
    private View viewSelecionada;


    // dados para o tema
    private static final String File = "com.prado.painter.sharedpreferences.preferences";
    private static final String themechoice = "themechoice";
    private int themeApp = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.list));
        listViewServices = findViewById(R.id.list_services);
        verificationTheme();
        listViewServices.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {

                        positionseleced = position;

                        Service services1 = (Service) listViewServices.getItemAtPosition(position);

                        Toast.makeText(getApplicationContext(),
                                getString(R.string.name_client ) + (" ")+ services1.getNameClient() + (" Foi selecionado"),
                                Toast.LENGTH_SHORT).show();
                    }
                }

        );
        listViewServices.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewServices.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                        if(actionMode !=null){
                            return false;
                        }
                        positionseleced = position;

                        view.setBackgroundColor(Color.LTGRAY);
                        viewSelecionada = view;
                        listViewServices.setEnabled(false);
                        actionMode = startActionMode(ActionModeCallback);
                        return true;
                    }
                }

                );
        preparedata();
    }


    //endregion

    //region menu

    public ActionMode.Callback ActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.selected_item,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.menuItemEdit:
                    alter();
                    mode.finish();
                    return true;

                case R.id.menuItemDelete:
                    mode.finish();
                    deleteService();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelecionada = null;
            listViewServices.setEnabled(true);

        }
    };


    public void AddMenu (MenuItem item){
        Intent intent = new Intent(this, RegisterService.class);
        RegisterService.newService(this);

    }

    public void aboutMenu (MenuItem item){
        Intent intent = new Intent(this, AboutApp.class);

        startActivity(intent);
    }


    public void alter (){
        Service service = services.get(positionseleced);

        RegisterService.alterService(this,service);
    }

    public void deleteService(){
        Service service = services.get(positionseleced);
        ServicesDatabase db = ServicesDatabase.getDatabase(this);
        db.ServicesDAO().delete(service);
        services.remove(positionseleced);
        serviceAdapter.notifyDataSetChanged();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //endregion

    //region common
    private   void preparedata(){

        ServicesDatabase db = ServicesDatabase.getDatabase(this);

        services = db.ServicesDAO().queryAll();

        serviceAdapter = new ServicesAdapter(this,services);

        listViewServices.setAdapter(serviceAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if((requestCode == RegisterService.ALTER || requestCode == RegisterService.NEW) && resultCode == Activity.RESULT_OK){
            preparedata();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void savePreferenceTheme(int novoTema){
        SharedPreferences shared = getSharedPreferences(File, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(themechoice, novoTema);
        editor.commit();
        themeApp = novoTema;

        AppCompatDelegate.setDefaultNightMode(themeApp);
    }

    public void  verificationTheme (){
        SharedPreferences shared = getSharedPreferences(File, Context.MODE_PRIVATE);
        themeApp = shared.getInt(themechoice, themeApp);

        AppCompatDelegate.setDefaultNightMode(themeApp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemDark:
                savePreferenceTheme(AppCompatDelegate.MODE_NIGHT_YES);
                return true;

            case R.id.menuItemClaro:
                savePreferenceTheme(AppCompatDelegate.MODE_NIGHT_NO);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;
        switch (themeApp){

            case AppCompatDelegate.MODE_NIGHT_YES:
                item = menu.findItem(R.id.menuItemDark);
                break;

            case AppCompatDelegate.MODE_NIGHT_NO:
                item = menu.findItem(R.id.menuItemClaro);
                break;

            default:
                return false;
        }
        item.setChecked(true);
        return true;
    }

    //endregion

    //region Button
    public void About(View view){
        Intent intent = new Intent(this, AboutApp.class);
        startActivity(intent);
    }

    public void Add(View view){
        Intent intent = new Intent(this, RegisterService.class);

        startActivityForResult(intent, 1);


    }

    //endregion






}