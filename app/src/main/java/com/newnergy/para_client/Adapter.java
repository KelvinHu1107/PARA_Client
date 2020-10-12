package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kelvin on 2016/5/24.
 */
public class Adapter extends ArrayAdapter<String> {
    // declaration

    private static final int PERMISSION_REQUEST = 100;
    CharSequence[] name = {};
    CharSequence[] createDate = {};
    CharSequence[] clientDueDate = {};
    int[] id = {};
    Double[] rating;
    CharSequence[] street, suburb, city = {};
    CharSequence[] profilePhoto, type = {};
    CharSequence[] getTitle = {};
    CharSequence[] userName = {};
    CharSequence[] companyPhone = {};
    Context c;
    LayoutInflater inflater;
    ImageUnity imageUnity = new ImageUnity();
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    public Adapter(Context context, String[] objectName, CharSequence[] name, CharSequence[] createDate, CharSequence[] clientDueDate,
                   int[] id,CharSequence[] street, CharSequence[] suburb,CharSequence[] city, CharSequence[] profilePhoto,CharSequence[] getTitle, CharSequence[] userName, CharSequence[] companyPhone
            ,Double[] rating, CharSequence[] type) {
        super(context, R.layout.client_incoming_services_list_sample720x1080, objectName);

        this.c = context;
        this.name = name;
        this.createDate = createDate;
        this.clientDueDate = clientDueDate;
        this.id = id;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
        this.profilePhoto = profilePhoto;
        this.getTitle = getTitle;
        this.userName = userName;
        this.companyPhone = companyPhone;
        this.rating = rating;
        this.type = type;

    }

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
        }

    private boolean checkCallPermission(){
        String permission = "android.permission.CALL_PHONE";
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
        //return true;
    }

    public class ViewHolder {
        TextView nameTv,createDateTv,jobSurburbTv;
        ImageView img, star1, star2, star3, star4, star5;
        HorizontalScrollView scrollViewTv;
        LinearLayout linearLayoutTv, starContainer;
        ImageButton rollbackTv, furtherInfoBtn, dialButton, locationBtn, chatBtn;
        ListView listViewTv;

}

//    public void getImageData(String profilePhotoUrl, final ImageView imageView) {
//
//        GetImageController controller = new GetImageController() {
//            @Override
//            public void onResponse(Bitmap mBitmap) {
//                super.onResponse(mBitmap);
//
//                if (mBitmap == null) {
//
//                    imageView.setImageBitmap(readBitMap(c,R.drawable.client_photo_round));
//
//                    ValueMessager.providerProfileBitmap = mBitmap;
//
//                    //mBitmap.recycle();
//                    return;
//                }
//                System.out.println("qqqqqqqqqqqqqqqqq"+imageView.getDrawable());
//                    imageView.setImageBitmap(imageUnity.toRoundBitmap(mBitmap));
//                //}
//                ValueMessager.providerProfileBitmap = imageUnity.toRoundBitmap(mBitmap);
//
//                mBitmap.recycle();
//
//            }
//        };
//        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profilePhotoUrl, "","POST");
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(ValueMessager.resolution1080x720) {
                convertView = inflater.inflate(R.layout.client_incoming_services_list_sample720x1080, null);
            }
            else if(ValueMessager.resolution800x480) {
                convertView = inflater.inflate(R.layout.client_incoming_services_list_sample480x800, null);
            }
            else if(ValueMessager.resolution1920x1080) {
                convertView = inflater.inflate(R.layout.client_incoming_services_list_sample1080x1920, null);
            }
            else
                convertView = inflater.inflate(R.layout.client_incoming_services_list_sample1080x1920, null);
        }
        //assign id to items , convert view
        final ViewHolder holder = new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.textView_name);
        holder.jobSurburbTv = (TextView) convertView.findViewById(R.id.textView_location);
        holder.img = (CircleImageView) convertView.findViewById(R.id.imageView_pic);
        holder.scrollViewTv = (HorizontalScrollView) convertView.findViewById(R.id.horizontalScrollView);
        holder.linearLayoutTv = (LinearLayout) convertView.findViewById(R.id.firstSection);
        holder.starContainer = (LinearLayout) convertView.findViewById(R.id.linearLayout_starContainer);
        holder.star1 = (ImageView) convertView.findViewById(R.id.imageView_star1);
        holder.star2 = (ImageView) convertView.findViewById(R.id.imageView_star2);
        holder.star3 = (ImageView) convertView.findViewById(R.id.imageView_star3);
        holder.star4 = (ImageView) convertView.findViewById(R.id.imageView_star4);
        holder.star5 = (ImageView) convertView.findViewById(R.id.imageView_star5);
        holder.listViewTv = (ListView) convertView.findViewById(R.id.listView2);
        holder.createDateTv = (TextView) convertView.findViewById(R.id.textView_status);
        holder.locationBtn = (ImageButton) convertView.findViewById(R.id.imageButton_location_furtherInfo);


        //assign id to items , convert view

        //format date string
//        Calendar currentTime = Calendar.getInstance();
//        Long diff, diffDayLong;
//        int diffDay, day = 24*60*60*1000, hour = 60*60*1000;
//        Date date;
//        Date currentDate;
//        String calculatedDate = null;
//        currentDate = currentTime.getTime();
//
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MMMM");
////            date = format.parse(createDate[position].toString());
//            diff = (currentDate.getTime() - currentDate.getTime()% day) - date.getTime();
//            diffDayLong = diff / (60 * 60 * 1000);
//            diffDay = Math.round(diffDayLong);
//            if(diffDay < 0)
//                        calculatedDate = "Listed Today";
//                else if(diffDay <24)
//                        calculatedDate = "Yesterday";
//                else if(diffDay <48)
//                        calculatedDate = "2 Days Ago";
//                else if(diffDay <72)
//                        calculatedDate = "3 Days Ago";
//                else if(diffDay <96)
//                        calculatedDate = "4 Days Ago";
//                else if(diffDay <120)
//                        calculatedDate = "5 Days Ago";
//                else if(diffDay <144)
//                        calculatedDate = "6 Days Ago";
//                else
//                calculatedDate = finalFormat.format(date);
//
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        if(profilePhoto[position].toString().equals(""))
        {
            holder.img.setImageResource(R.drawable.client_photo_round);
        }
        else {
            imageUnity.setImage(c, holder.img, "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + profilePhoto[position].toString());
        }
        holder.nameTv.setText(name[position]);
        holder.jobSurburbTv.setText(type[position]);
        holder.createDateTv.setText(suburb[position]);

        if(rating[position]>=0.0 && rating[position]<0.5){
            holder.star1.setImageResource(R.drawable.median_empty_star);
            holder.star2.setImageResource(R.drawable.median_empty_star);
            holder.star3.setImageResource(R.drawable.median_empty_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=0.5 && rating[position]<1.0){
            holder.star1.setImageResource(R.drawable.median_half_star);
            holder.star2.setImageResource(R.drawable.median_empty_star);
            holder.star3.setImageResource(R.drawable.median_empty_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=1.0 && rating[position]<1.5){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_empty_star);
            holder.star3.setImageResource(R.drawable.median_empty_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=1.5 && rating[position]<2.0){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_half_star);
            holder.star3.setImageResource(R.drawable.median_empty_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=2.0 && rating[position]<2.5){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_empty_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=2.5 && rating[position]<3.0){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_half_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=3.0 && rating[position]<3.5){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_full_star);
            holder.star4.setImageResource(R.drawable.median_empty_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=3.5 && rating[position]<4.0){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_full_star);
            holder.star4.setImageResource(R.drawable.median_half_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=4.0 && rating[position]<4.5){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_full_star);
            holder.star4.setImageResource(R.drawable.median_full_star);
            holder.star5.setImageResource(R.drawable.median_empty_star);
        }else if(rating[position]>=4.5 && rating[position]<5.0){
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_full_star);
            holder.star4.setImageResource(R.drawable.median_full_star);
            holder.star5.setImageResource(R.drawable.median_half_star);
        }else{
            holder.star1.setImageResource(R.drawable.median_full_star);
            holder.star2.setImageResource(R.drawable.median_full_star);
            holder.star3.setImageResource(R.drawable.median_full_star);
            holder.star4.setImageResource(R.drawable.median_full_star);
            holder.star5.setImageResource(R.drawable.median_full_star);
        }




        holder.linearLayoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.img.setDrawingCacheEnabled(true);
                ValueMessager.providerProfileBitmap = holder.img.getDrawingCache();
                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];
                ValueMessager.providerRating = rating[position];

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);
            }
        });

        holder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);

            }
        });

        holder.jobSurburbTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];


                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);

            }
        });

        holder.createDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);

            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);

            }
        });

        holder.starContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = "IncomingJobs";
                ValueMessengerTaskInfo.providerUserName = userName[position];

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);
            }
        });

        // <Scrollable list layout>end
        return convertView;
    }

}