package com.prado.painter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prado.painter.model.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ServicesAdapter extends BaseAdapter {

    private Context      context;
    private List<Service> services;
    private NumberFormat numberFormat;

    private static class ServiceHolder {
        public TextView textViewValuename;
        public TextView textViewValue;
        public TextView textViewValuetype;
        public TextView textViewdiscount;
    }

    public ServicesAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;



        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int i) {
        return services.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ServiceHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_services, viewGroup, false);

            holder = new ServiceHolder();

            holder.textViewValuename = view.findViewById(R.id.textViewName);
            holder.textViewValue     = view.findViewById(R.id.textViewValue_value);
            holder.textViewValuetype = view.findViewById(R.id.textViewValue_type);
            holder.textViewdiscount  = view.findViewById(R.id.textViewValue_discount);

            view.setTag(holder);

        } else {

            holder = (ServiceHolder) view.getTag();
        }
        String valorFormatado = numberFormat.format(services.get(i).getValue());

        holder.textViewValuename.setText(services.get(i).getNameClient());
        holder.textViewValue.setText(valorFormatado);
        holder.textViewdiscount.setText(services.get(i).Is_Budget());
        switch(services.get(i).getType()){
            case "building":
                holder.textViewValuetype.setText(R.string.building);
                break;
            case "business":
                holder.textViewValuetype.setText(R.string.business);
                break;
            case "house":
                holder.textViewValuetype.setText(R.string.house);
                break;
        }



        return view;
    }
}

