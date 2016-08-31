package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.ByteArrayOutputStream;

/**
 * Created by G7 on 3/08/2016.
 */
public class ImageUnity {

    public static boolean isLimitSize (Bitmap image) {
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
        byte[] imageInByte = stream2.toByteArray();
        double originLengthBmp = imageInByte.length;


        originLengthBmp = originLengthBmp / 1024;//kb
        if (originLengthBmp > 10240) {
            return false;
        }
           return true;
    }

    public Bitmap compressImage(Bitmap image,int targetSize)
    {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        double originLengthBmp = imageInByte.length/1024;
        System.out.println("OR image size："+originLengthBmp+"kb");

        if(originLengthBmp<targetSize)
        {
            return image;
        }

        double quality=(targetSize/originLengthBmp)*100;
        stream.reset();
        image.compress(Bitmap.CompressFormat.JPEG, (int)quality, stream);
        imageInByte = stream.toByteArray();
        originLengthBmp=imageInByte.length/1024;


        System.out.println("number image size："+originLengthBmp+"kb");

        while (originLengthBmp / 1024 > 100) {
            stream.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 1, stream);
            imageInByte = stream.toByteArray();
            originLengthBmp=imageInByte.length/1024;
            System.out.println("hight image size："+originLengthBmp+"kb");
            return image;
        }

        return image;

    }





//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        //super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            String sdStatus = Environment.getExternalStorageState();
//            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //
//                Log.i("TestFile",
//                        "SD card is not avaiable/writeable right now.");
//                return;
//            }
//            new DateFormat();
//            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//            // Toast.makeText(get, name, Toast.LENGTH_LONG).show();
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");//
//
//            FileOutputStream b = null;
//            File file = new File("/sdcard/Image/");
//            file.mkdirs();// 创建文件夹
//            String fileName = "/sdcard/Image/"+name;
//
//            try {
//                b = new FileOutputStream(fileName);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    b.flush();
//                    b.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try
//            {
//                System.err.println("123");
//                //view.setImageBitmap(bitmap);// 将图片显示在ImageView里
//            }catch(Exception e)
//            {
//                Log.e("error", e.getMessage());
//            }
//
//        }
//    }






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

}
