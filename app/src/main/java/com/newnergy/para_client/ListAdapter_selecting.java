package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;

import java.io.InputStream;

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
    Double[] budget, providerRating, providerDeposit;
    LayoutInflater inflaterSelecting;
    ImageUnity imageUnity = new ImageUnity();

    public ListAdapter_Selecting(Context context, String[] objectName, CharSequence[] firstName, CharSequence[] lastName,CharSequence[] providerPhoto,
                               CharSequence[] providerUserName,Double[] budget, CharSequence[] acceptTime, Integer[] providerId, int[] serviceId, Double[] providerRating,
                                 int[] status, Double[] providerDeposit) {

        super(context, R.layout.pending_list_sample720x1080, objectName);

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
        this.providerDeposit = providerDeposit;

    }

    public class ViewHolder {
        TextView nameTv, priceTv;
        ImageView providerPhoto, star1, star2, star3, star4, star5;
        LinearLayout linearLayout, starContainer, accept;
        Button confirm;
    }

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

//    public void getImageData(String profilePhotoUrl, final ImageView imageView) {
//
//        GetImageController controller = new GetImageController() {
//            @Override
//            public void onResponse(Bitmap mBitmap) {
//                super.onResponse(mBitmap);
//                if (mBitmap == null) {
//                    imageView.setImageBitmap(readBitMap(c,R.drawable.client_photo_round));
//                }
//
//                imageView.setImageBitmap(imageUnity.toRoundBitmap(mBitmap));
//                 = imageUnity.toRoundBitmap(mBitmap);
//
//                mBitmap.recycle();
//            }
//        };
//        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profilePhotoUrl, "","POST");
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(ValueMessengerTaskInfo.itemStatus == 3){
            inflaterSelecting = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterSelecting.inflate(R.layout.client_selecting_list_sample_assigned, null);
        }
        else {
            inflaterSelecting = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterSelecting.inflate(R.layout.client_selecting_list_sample720x1080, null);
        }

            //assign id to items , convert view

        final ViewHolder holderPending = new ViewHolder();
        holderPending.nameTv = (TextView) convertView.findViewById(R.id.textView_selectingName);
        holderPending.providerPhoto = (ImageView) convertView.findViewById(R.id.imageView_selectingProviderPhoto);
        holderPending.priceTv = (TextView) convertView.findViewById(R.id.textView_selectingPrice);
        holderPending.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout_selecting);
        holderPending.accept = (LinearLayout) convertView.findViewById(R.id.linearLayout_accept);
        holderPending.confirm = (Button) convertView.findViewById(R.id.button_selecting_confirm);
        holderPending.starContainer = (LinearLayout) convertView.findViewById(R.id.linearLayout_starContainer);
        holderPending.star1 = (ImageView) convertView.findViewById(R.id.imageView_star1);
        holderPending.star2 = (ImageView) convertView.findViewById(R.id.imageView_star2);
        holderPending.star3 = (ImageView) convertView.findViewById(R.id.imageView_star3);
        holderPending.star4 = (ImageView) convertView.findViewById(R.id.imageView_star4);
        holderPending.star5 = (ImageView) convertView.findViewById(R.id.imageView_star5);
        holderPending.confirm.setText("Accept");
        holderPending.nameTv.setText(firstName[position]+" "+lastName[position]);
        holderPending.priceTv.setText(budget[position].toString());

        if(providerRating[position]>=0.0 && providerRating[position]<0.5){
            holderPending.star1.setImageResource(R.drawable.median_empty_star);
            holderPending.star2.setImageResource(R.drawable.median_empty_star);
            holderPending.star3.setImageResource(R.drawable.median_empty_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=0.5 && providerRating[position]<1.0){
            holderPending.star1.setImageResource(R.drawable.median_half_star);
            holderPending.star2.setImageResource(R.drawable.median_empty_star);
            holderPending.star3.setImageResource(R.drawable.median_empty_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=1.0 && providerRating[position]<1.5){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_empty_star);
            holderPending.star3.setImageResource(R.drawable.median_empty_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=1.5 && providerRating[position]<2.0){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_half_star);
            holderPending.star3.setImageResource(R.drawable.median_empty_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=2.0 && providerRating[position]<2.5){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_empty_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=2.5 && providerRating[position]<3.0){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_half_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=3.0 && providerRating[position]<3.5){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_full_star);
            holderPending.star4.setImageResource(R.drawable.median_empty_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=3.5 && providerRating[position]<4.0){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_full_star);
            holderPending.star4.setImageResource(R.drawable.median_half_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=4.0 && providerRating[position]<4.5){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_full_star);
            holderPending.star4.setImageResource(R.drawable.median_full_star);
            holderPending.star5.setImageResource(R.drawable.median_empty_star);
        }else if(providerRating[position]>=4.5 && providerRating[position]<5.0){
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_full_star);
            holderPending.star4.setImageResource(R.drawable.median_full_star);
            holderPending.star5.setImageResource(R.drawable.median_half_star);
        }else{
            holderPending.star1.setImageResource(R.drawable.median_full_star);
            holderPending.star2.setImageResource(R.drawable.median_full_star);
            holderPending.star3.setImageResource(R.drawable.median_full_star);
            holderPending.star4.setImageResource(R.drawable.median_full_star);
            holderPending.star5.setImageResource(R.drawable.median_full_star);
        }


        //getImageData(providerPhoto[position].toString(), holderPending.providerPhoto);
        if(providerPhoto[position].toString().equals("") || providerPhoto[position].toString().equals(null)){
            holderPending.providerPhoto.setImageResource(R.drawable.client_photo_round);
        }
        else {
            imageUnity.setImage(c, holderPending.providerPhoto, "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + providerPhoto[position].toString());
        }
        holderPending.providerPhoto.setDrawingCacheEnabled(true);

        if(ValueMessengerTaskInfo.itemStatus != 3) {

            holderPending.priceTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ValueMessagerFurtherInfo.userName = providerUserName[position];
                    ValueMessagerFurtherInfo.lastPage = "Selecting";

                    Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                    c.startActivity(nextPage_Confirm);
                }
            });

        }

        holderPending.providerPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = "Selecting";

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = "Selecting";

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.nameTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = "Selecting";

                Intent nextPage_Confirm = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_Confirm);
            }
        });



        holderPending.confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = providerUserName[position];
                ValueMessagerFurtherInfo.lastPage = "Selecting";
                ValueMessengerTaskInfo.providerOfferedPrice = budget[position];
                ValueMessengerTaskInfo.providerFirstName = firstName[position];
                ValueMessengerTaskInfo.providerLastName = lastName[position];
                ValueMessengerTaskInfo.providerProfilePicUrl = providerPhoto[position].toString();
                ValueMessengerTaskInfo.providerUserName = providerUserName[position];

                Intent nextPage_Confirm = new Intent(c, Client_AcceptOffer.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        // <Scrollable list layout>end
        return convertView;
    }

}