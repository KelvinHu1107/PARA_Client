package com.newnergy.para_client;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.newnergy.para_client.Image_package.ImageUnity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectPicPopupWindowUploadImage extends Activity {
    private ImageButton button_Camera, button_Local;
    private LinearLayout layout;
    private static final int RESULT_LOAD_IMAGE = 2;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 2;
    private ImageUnity imageUnity;
    Context context = this;


    private static final int TAKE_PICTURE = 93;



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
//                Toast.makeText(getApplicationContext(), "123",
//                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    //touch would do nothing
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

            Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        imageUnity = new ImageUnity();

        switch (requestCode) {
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
                    File file = new File("/sdcard/myImage222/");
                    file.mkdirs();


                    String str = null;
                    Date date = null;
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    date = new Date();
                    str = format.format(date);
                    String fileName = "/sdcard/myImage/" + str + ".jpg";
                    try {
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// save data into file

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            //Bitmap cameraBitmap= (Bitmap) data.getExtras().get("data");
                            Uri selectedImage =imageUnity.getImageUri(this,bitmap);
                            InputStream inputStream = getContentResolver().openInputStream(selectedImage);

                            ValueMessager.uriBuffer =  selectedImage;
                            //cameraBitmap=imageUnity.compressBySize(inputStream);

                            SendServiceImageController controller = new SendServiceImageController() {
                                @Override
                                public void onResponse(String result) {
                                    super.onResponse(result);
                                    ReturnImage(imageUnity.getRealPathFromURI(ValueMessager.uriBuffer,context));
                                }
                            };
                            controller.setBitmap(bitmap);
                            controller.execute("http://para.co.nz/api/JobService/UploadImage/"+ValueMessager.serviceId);

                            //ReturnImage(imageUnity.getRealPathFromURI(selectedImage,this));

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            }

            //////////////////////
            case 2: {
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

                    //URI address on SD card
                    Uri selectedImage = data.getData();
                    // Declare a stream to read the image data
                    InputStream inputStream;
                    try {

                        imageUnity = new ImageUnity();
                        inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap  bitmap=imageUnity.compressBySize(inputStream);

                        bitmap=imageUnity.rotateBitmapByExif(imageUnity.getRealPathFromURI(selectedImage,this),bitmap);
                        ValueMessager.uriBuffer =  selectedImage;

                        SendServiceImageController controller = new SendServiceImageController() {
                            @Override
                            public void onResponse(String result) {
                                super.onResponse(result);
                                ReturnImage(imageUnity.getRealPathFromURI(ValueMessager.uriBuffer,context));
                            }
                        };
                        controller.setBitmap(bitmap);
                        controller.execute("http://para.co.nz/api/JobService/UploadImage/"+ValueMessager.serviceId);

                        //ReturnImage(imageUnity.getRealPathFromURI(selectedImage,this));


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Unable to load image", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                break;
            }

        }
    }


    private void ReturnImage(String  url)
    {
        Intent i = new Intent();
        i.putExtra("ImageUrl",url);
        i.putExtra("test_String","456");
        setResult(RESULT_OK, i);
        finish();

//        switch (ValueMessager.lastPageSelectPic){
//            case "EditDraft":
//                Intent intent = new Intent(SelectPicPopupWindowUploadImage.this, Client_EditDraft.class);
//                startActivity(intent);
//                break;
//            case "EditOrder":
//                Intent intent2 = new Intent(SelectPicPopupWindowUploadImage.this, Client_EditOrder.class);
//                startActivity(intent2);
//                break;
//        }

    }
    /////////////////////end
}
