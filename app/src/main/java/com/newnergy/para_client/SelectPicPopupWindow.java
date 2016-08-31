package com.newnergy.para_client;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class SelectPicPopupWindow extends Activity {


    private ImageButton button_Camera, button_Local;
    private LinearLayout layout;
    private static ImageView profilePicture_new;
    private static ImageView profilePictureSlidingMenu_new;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 1;
    private ImageUnity imageUnity;
    private static AppCompatActivity a;
    private ImageView profilePictureSlidingMenu;
    private ImageView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.client_pic_selecting_popup);


        profilePicture= (ImageView) findViewById(R.id.imageView_profile_profileImage);
        profilePictureSlidingMenu=(ImageView) findViewById(R.id.imageView_sideMenu_pic);

        button_Camera = (ImageButton) this.findViewById(R.id.imageButton_takingPic);
        button_Local = (ImageButton) this.findViewById(R.id.imageButton_gallery);
        layout = (LinearLayout) findViewById(R.id.pop_layout);
        button_Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoAlbum();
            }

        });
        button_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoCamera();
            }

        });

        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "123",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RESULT_EXTERNAL_STORAGE_RESULT){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //invoke image gallery by intent
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //where do we get the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //set data and type, get all image type
                galleryIntent.setDataAndType(data,"image/*");

                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
            else{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ID_button_Camera:
//                PhotoCamera();
//                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.ID_button_Local:
//                PhotoAlbum();
//                Toast.makeText(getApplicationContext(), "local", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//        finish();
//
//    }

    public void PhotoAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_EXTERNAL_STORAGE_RESULT);
        }

        else{
            //invoke image gallery by intent
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //where do we get the data
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureDirectoryPath = pictureDirectory.getPath();
            //URI representation
            Uri data = Uri.parse(pictureDirectoryPath);

            //set data and type, get all image type
            galleryIntent.setDataAndType(data,"image/*");

            startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
        }
    }


    public void PhotoCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }

    public static void targetControl(ImageView profilePicture, ImageView profilePictureSlidingMenu) {
       profilePicture_new=profilePicture;
        profilePictureSlidingMenu_new=profilePictureSlidingMenu;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            //URI address on SD card
            Uri selectedImage = data.getData();
            // Declare a stream to read the image data
            InputStream inputStream;
            try {

                imageUnity = new ImageUnity();
                //getting an input stream from the image data
                inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap=imageUnity.toRoundBitmap(bitmap);
                bitmap = imageUnity.compressImage(bitmap, 5);

                ValueMessager.profileBitmap = bitmap;

                writeData(ValueMessager.profileBitmap);


                profilePicture_new.setImageBitmap(bitmap);
                profilePictureSlidingMenu_new.setImageBitmap(bitmap);
                //profilePicture.setImageDrawable(roundImage);
              //  profilePictureSlidingMenu.setImageDrawable(roundImage);
                sendImage(bitmap, ValueMessager.email.toString());
                finish();


            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void writeData(Bitmap image){

        try {
            FileOutputStream fileOutputStream = openFileOutput("profile_data_picture",MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(Bitmap newImg,String username) {

        SendImageController controller = new SendImageController() {
            @Override
            public void onResponse(Boolean result) {
                super.onResponse(result);
                if (result) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            }
        };
        controller.setBitmap(newImg);
        controller.execute("http://para.co.nz/api/ClientProfile/UploadImage/"+username);
    }
}
