package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by GaoxinHuang on 2016/6/1.
 */
public class ServerConnection {

    public static boolean validateDataToServer(String urlString, String urlParameters, String method) {
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
            dataOutputStream.close();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.toString().equals("true")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static int getIdFromServer(String urlString, String urlParameters, String method) {
        int result = 0;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
            dataOutputStream.close();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                result = Integer.parseInt(buffer.toString());
            } else {
                return 0;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }
        return result;
    }

    public static String getStringFromServer(String urlString, String urlParameters, String method) {
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
//            dataOutputStream.writeBytes(urlParameters);
//            dataOutputStream.flush();
//            dataOutputStream.close();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                result = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap getImageFromServer(String urlString, String urlParameters, String method) {
        String result = "";
        BufferedInputStream reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
//            dataOutputStream.writeBytes(urlParameters);
//            dataOutputStream.flush();
//            dataOutputStream.close();
            conn.connect();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream input = conn.getInputStream();
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = false;
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable=true;
                opt.inInputShareable=true;
                Bitmap img = BitmapFactory.decodeStream(input, null, opt);
                input.close();
                return img;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean sendImageToServer(String urlString, Bitmap bitmap, String method) {
        String attachmentName = "bitmap";
        String attachmentFileName = "bitmap.png";
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens + boundary + crlf);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            dataOutputStream.writeBytes(crlf);
            dataOutputStream.write(getStringImage(bitmap));
            dataOutputStream.writeBytes(crlf);
            dataOutputStream.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);
            dataOutputStream.flush();
            dataOutputStream.close();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.toString().equals("true")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static byte[] getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageBytes;
    }

    public static boolean x(String urlString, Bitmap bitmap, String method) {
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(method);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.connect();
            int reposeCode = conn.getResponseCode();
            if (reposeCode == 200) {
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.toString().equals("true")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
