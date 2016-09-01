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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectingPicForChat extends Activity {
    private static ListAdapter_ChatArray adapter;
    private ImageButton button_Camera, button_Local;
    private LinearLayout layout;
    private static final int RESULT_LOAD_IMAGE = 2;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 2;
    private ImageUnity imageUnity;
    private boolean side = false;
    private static AppCompatActivity a;
    private static final int TAKE_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.client_pic_selecting_popup);


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
        if (requestCode == RESULT_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //invoke image gallery by intent
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //where do we get the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //set data and type, get all image type
                galleryIntent.setDataAndType(data, "image/*");

                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }




    public void PhotoAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_EXTERNAL_STORAGE_RESULT);
        } else {
            //invoke image gallery by intent
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //where do we get the data
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureDirectoryPath = pictureDirectory.getPath();
            //URI representation
            Uri data = Uri.parse(pictureDirectoryPath);

            //set data and type, get all image type
            galleryIntent.setDataAndType(data, "image/*");

            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }


    public void PhotoCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},TAKE_PICTURE);
        }
        else {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 1);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        imageUnity = new ImageUnity();

        switch (requestCode) {
            //carme

            case TAKE_PICTURE: {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == Activity.RESULT_OK) {

                    String sdStatus = Environment.getExternalStorageState();
                    //check sd
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                        return;
                    }

                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    FileOutputStream b = null;
                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();


                    String str = null;
                    Date date = null;
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    date = new Date();
                    str = format.format(date);
                    String fileName = "/sdcard/myImage/" + str + ".jpg";
                    try {
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);// save data into file
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (data != null) {
                            Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                            System.out.println("fdf=================" + data.getDataString());

                            //TODO:  put imgae here
                            //example: img.setImageBitmap(cameraBitmap);

                            imageUnity.compressImage(cameraBitmap, 1);
                            ValueMessager.bitmapBuffer = cameraBitmap;
                            ValueMessager.chatAddMessageFlag = 1;

                            SendImageController controller = new SendImageController() {
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);
                                    if (result) {
                                        System.out.println("yes");
                                        Intent nextPage_Search = new Intent(SelectingPicForChat.this, Client_Chat.class);
                                        startActivity(nextPage_Search);
                                    } else {
                                        System.out.println("no");
                                        Intent nextPage_Search = new Intent(SelectingPicForChat.this, Client_Chat.class);
                                        startActivity(nextPage_Search);
                                    }
                                }
                            };
                            controller.setBitmap(cameraBitmap);
                            String FromUsername = ValueMessager.email.toString();
                            String ToUsername = ValueMessager.providerEmail;
                            controller.execute("http://para.co.nz/api/ChatClient/SendImageMessage/" + FromUsername + "/" + ToUsername, "", "POST");

                        }
                    }
                }
                break;
            }

            case 2: {
                //read image from local
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

                    //URI address on SD card
                    Uri selectedImage = data.getData();
                    // Declare a stream to read the image data
                    InputStream inputStream;
                    try {

                        imageUnity = new ImageUnity();
                        //getting an input stream from the image data
                        inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //bitmap = imageUnity.toRoundBitmap(bitmap);
                        bitmap = imageUnity.compressImage(bitmap, 5);

                        ValueMessager.bitmapBuffer = bitmap;
                        ValueMessager.chatAddMessageFlag = 1;

                        SendImageController controller = new SendImageController() {
                            @Override
                            public void onResponse(Boolean result) {
                                super.onResponse(result);
                                if (result) {
                                    System.out.println("yes");
                                    Intent nextPage_Search = new Intent(SelectingPicForChat.this, Client_Chat.class);
                                    startActivity(nextPage_Search);
                                } else {
                                    System.out.println("no");
                                    Intent nextPage_Search = new Intent(SelectingPicForChat.this, Client_Chat.class);
                                    startActivity(nextPage_Search);
                                }
                            }
                        };
                        controller.setBitmap(bitmap);

                        String FromUsername = ValueMessager.email.toString();
                        String ToUsername = ValueMessager.providerEmail;
                        controller.execute("http://para.co.nz/api/ChatClient/SendImageMessage/" + FromUsername + "/" + ToUsername, "", "POST");


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Unable to load image", Toast.LENGTH_LONG).show();
                    }

                }
                finish();
                break;
            }
        }
    }
}
