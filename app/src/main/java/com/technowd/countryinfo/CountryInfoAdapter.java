package com.technowd.countryinfo;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.technowd.countryinfo.generals.Setting;
import com.technowd.countryinfo.model.CountryCode;
import java.util.ArrayList;
import java.util.List;

public class CountryInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements Filterable {
    // variables
    private List<CountryCode> countryCode;
    private List<CountryCode> countryCodeFull;
    private int rowLayout;
    private Context context;
    //
    private OnClickListener mListener;
    // Interface click listener
    public interface OnClickListener{
        void onItemClick(int position);
    }
    //

    // constructor
    public CountryInfoAdapter(List<CountryCode> countryCode, int rowLayout) {
        this.countryCode = countryCode;
        countryCodeFull = new ArrayList<>(countryCode);
        this.rowLayout = rowLayout;
    }

    public void setOnItemClickListener(OnClickListener listener){
        this.mListener = listener;
    }
    // Main holder => CountryInfoHolder
    public class CountryInfoHolder extends RecyclerView.ViewHolder{
        ImageView countryFlag;
        TextView countryName, countryCallCode;

        public CountryInfoHolder(View itemView, final OnClickListener listener) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.country_flag);
            countryName = itemView.findViewById(R.id.country_name);
            countryCallCode = itemView.findViewById(R.id.country_call_code);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    // CenterFlagHolder
    public class CenterFlagHolder extends RecyclerView.ViewHolder{
        ImageView countryFlag;
        TextView countryName, countryCallCode, countryCapital;
        public CenterFlagHolder(View itemView, final OnClickListener listener) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.country_flag);
            countryName = itemView.findViewById(R.id.country_name);
            countryCallCode = itemView.findViewById(R.id.country_call_code);
            countryCapital = itemView.findViewById(R.id.country_capital);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    // get layout base on user select
    @Override
    public int getItemViewType(int position) {
        // todo ads position
//        if(position % 5 == 0 && position != 0){
//            return R.layout.row_ads;
//        } else
        if(Setting.centerFlagRow == rowLayout){
            return R.layout.row_center_flag;
        } else if(Setting.sideFlagRow == rowLayout){
            return R.layout.row_side_flag;
        } else {
            return R.layout.row_side_flag;
        }
    }
    // on Create View Holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        // get holder depends on viewType
        switch (viewType){ // use R.layout.row_center_flag
            case R.layout.row_center_flag:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_center_flag,parent,false);
                holder = new CenterFlagHolder(view,mListener);
                break;
                // ads between rows
            // todo ads in 5 position in rows
//            case R.layout.row_ads:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ads,parent,false);
//                holder = new AdsHolder(view);
//                break;
            case R.layout.row_side_flag: // use R.layout.row_side_flag
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_side_flag,parent,false);
                holder = new CountryInfoHolder(view, mListener);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_side_flag,parent,false);
                holder = new CountryInfoHolder(view, mListener);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CountryCode code = countryCode.get(position);
        String countryFlagImage = code.getName().replace(" ", "_").toLowerCase();
        if (holder instanceof CenterFlagHolder){ // row in center
            ((CenterFlagHolder) holder).countryName.setText(code.getName());
            ((CenterFlagHolder) holder).countryCallCode.setText(String.format("Phone code : +%s", code.getCallingCodes().get(0)));
            ((CenterFlagHolder) holder).countryCapital.setText(String.format("Capital : %s",code.getCapital()));

            ((CenterFlagHolder) holder).countryFlag.setImageURI(Uri.parse("android.resource://com.technowd.countryinfo/drawable/" + countryFlagImage));
        } else if (holder instanceof CountryInfoHolder){
            ((CountryInfoHolder) holder).countryName.setText(code.getName());
            ((CountryInfoHolder) holder).countryCallCode.setText(String.format("Phone code : +%s",code.getCallingCodes().get(0)));
            ((CountryInfoHolder) holder).countryFlag.setImageURI(Uri.parse("android.resource://com.technowd.countryinfo/drawable/" + countryFlagImage));
        }
    }

    @Override
    public int getItemCount() {
        return countryCode.size();
    }

    // Filter data => search in list
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            // create new array list from object
            List<CountryCode> filterdeCountryCodes = new ArrayList<>();
            // if user doesn't type any thing then return all List
            if(charSequence == null || charSequence.length() == 0){
                filterdeCountryCodes.addAll(countryCodeFull);
            } else {
                String filterString = charSequence.toString().toLowerCase().trim();
                for (CountryCode code : countryCodeFull){
                    // check if search word start with user input or country code like user entered
                    if(code.getName().toLowerCase().startsWith(filterString) || code.getCallingCodes().get(0).contains(filterString)){
                        filterdeCountryCodes.add(code);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterdeCountryCodes;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            countryCode.clear();
            countryCode.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
