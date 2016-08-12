package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Kelvin on 2016/8/11.
 */
public class ListAdapter_Selecting extends ArrayAdapter<String> {

    CharSequence[] firstName, acceptTime;
    CharSequence[] lastName;
    CharSequence[] providerPhoto;
    Integer[] providerId;
    int[] serviceId, status;
    Context c;
    CharSequence[] providerUserName;
    Double[] budget, providerRating;
    LayoutInflater inflaterSelecting;

    public ListAdapter_Selecting(Context context, String[] objectName, CharSequence[] firstName, CharSequence[] lastName,CharSequence[] providerPhoto,
                               CharSequence[] providerUserName,Double[] budget, CharSequence[] acceptTime, Integer[] providerId, int[] serviceId, Double[] providerRating,
                                 int[] status) {

        super(context, R.layout.pending_list_sample, objectName);

        this.c = context;
        this.firstName = firstName;
        this.lastName = lastName;
        this.providerPhoto = providerPhoto;
        this.providerUserName = providerUserName;
        this.budget = budget;
        this.acceptTime = acceptTime;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.providerRating = providerRating;
        this.status = status;

    }

    public class ViewHolder {
        TextView nameTv, priceTv;
        ImageView providerPhoto;
        LinearLayout linearLayout;
        Button confirm;
        RatingBar ratingBar;

    }



    public void getImageData(String profilePhotoUrl, final ImageView imageView) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap mBitmap) {
                super.onResponse(mBitmap);
                if (mBitmap == null) {
                    imageView.setImageResource(R.drawable.client_photo_round);
                }
                imageView.setImageBitmap(mBitmap);
            }
        };
        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profilePhotoUrl, "","POST");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            inflaterSelecting = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterSelecting.inflate(R.layout.client_selecting_list_sample, null);
        }

        //assign id to items , convert view
        final ViewHolder holderPending = new ViewHolder();
        holderPending.nameTv = (TextView) convertView.findViewById(R.id.textView_selectingName);
        holderPending.providerPhoto = (ImageView) convertView.findViewById(R.id.imageView_selectingProviderPhoto);
        holderPending.priceTv = (TextView) convertView.findViewById(R.id.textView_selectingPrice);
        holderPending.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout_selecting);
        holderPending.confirm = (Button) convertView.findViewById(R.id.button_selecting_confirm);
        holderPending.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar_selecting);

        holderPending.ratingBar.setRating(Float.parseFloat(providerRating[position].toString()));

        holderPending.nameTv.setText(firstName[position]+" "+lastName[position]);

        holderPending.priceTv.setText(budget[position].toString());

        getImageData(providerPhoto[position].toString(), holderPending.providerPhoto);

        holderPending.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holderPending.providerPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = 5;

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = 5;

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.nameTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = 5;

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.priceTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = 5;

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent nextPage_Confirm = new Intent(c,Client_Confirm.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        // <Scrollable list layout>end
        return convertView;
    }

}
