package com.newnergy.para_client.Image_package;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by G7 on 3/08/2016.
 */
public class ImageUnity {


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //// TODO: png or jpeg 
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri contentURI, Context inContext)
    {
        String result;
        Cursor cursor = inContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public Bitmap rotateBitmapByExif(String srcPath, Bitmap bitmap) {
        ExifInterface exif;
        Bitmap newBitmap = null;
        try {
            exif = new ExifInterface(srcPath);
            if (exif != null) {
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                int degree = 0;
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
                if (degree != 0) {
                    Matrix m = new Matrix();
                    m.postRotate(degree);
                    newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), m, true);
                    recycleBitmap(bitmap);
                    return newBitmap;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc();
            bitmap = null;
        }
    }



//    public Bitmap compressBySize(InputStream is) throws IOException {
//
//        int targetWidth=300;
//        int targetHeight=300;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buff = new byte[1024];
//        int len = 0;
//        while ((len = is.read(buff)) != -1) {
//            baos.write(buff, 0, len);
//        }
//
//        byte[] data = baos.toByteArray();
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        //TODO:inJustDecodeBounds
//        opts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
////        double imageSize = data.length/1024;
////        System.out.println("or image size:"+imageSize);
//
//        int imgWidth = opts.outWidth;
//        int imgHeight = opts.outHeight;
//        //System.out.println("width: "+imgWidth);
//        if(imgWidth>=1080||imgHeight>=1080)
//        {
//            targetWidth=200;
//            targetHeight=200;
//        }
//
//
//        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
//        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
//        if (widthRatio > 1 || heightRatio > 1) {
//            if (widthRatio > heightRatio) {
//                opts.inSampleSize = widthRatio;
//            } else {
//                opts.inSampleSize = heightRatio;
//            }
//        }
//
//        opts.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
//        CalculateImageSize(bitmap);
//        return bitmap;
//    }

    public Bitmap compressBySize(InputStream is) throws IOException {

        int targetWidth=800;
        int targetHeight=800;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }

        byte[] data = baos.toByteArray();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //TODO:inJustDecodeBounds
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
//        double imageSize = data.length/1024;
//        System.out.println("Calculate Or-Image Size:"+imageSize+"kb");

        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;
        //System.out.println("width: "+imgWidth);
        if(imgWidth>=1080||imgHeight>=1080)//kelvinkelvin change pic compressing amount
        {
            targetWidth=800;
            targetHeight=800;

        }


        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }

        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        //CalculateImageSize(bitmap);
        return bitmap;
    }




    //resize image for according the target Scale





    ///
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    //////
    public void saveImage(Bitmap bm, String imageURL) {

        String sdStatus = Environment.getExternalStorageState();
//        //check sd
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }


        FileOutputStream out = null;
        File file = new File("/sdcard/myImage/");
        file.mkdirs();

      // String filename="/sdcard/myImage/"+imageURL+ ".jpg";

        try {
            out= new FileOutputStream(imageURL);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/saved_images");
//        myDir.mkdirs();
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String fname = "Image-"+ n +".jpg";
//        File file = new File (myDir, fname);
//        if (file.exists ()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//            System.out.println("qqqqqqqqq111: "+out);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



    ////

    public void setImage(Context context, ImageView v, String url)
    {

        Picasso
                .with(context)
                .load(url)
                //.fit()
                .into(v);
    }
    public void setChatImage(Context context, ImageView v, String url)
    {
        Picasso
                .with(context)
                .load(url)
                .resize(250,250)
                .centerCrop()
                .into(v);
    }
    ////////////////////////////
    ///////////

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    public byte[] getImageByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}
