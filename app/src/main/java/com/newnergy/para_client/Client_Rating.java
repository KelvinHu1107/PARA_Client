package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client_Rating extends AppCompatActivity {

    private TextView cancel, price;
    private RatingBar ratingBar;
    private EditText comment;
    private ImageButton confirm;
    Context context = this;
    Loading_Dialog myLoading;


    public String readData(String openFileName){
        try {

            FileInputStream fileInputStream = openFileInput(openFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            ValueMessager.readDataBuffer = bufferedReader.readLine();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValueMessager.readDataBuffer.toString();
    }

    public void btnFunction() {


        cancel = (TextView) findViewById(R.id.textView_cancel_rating);
        price = (TextView) findViewById(R.id.textView_rating_price);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_rating);
        comment = (EditText) findViewById(R.id.editText_rating_comment);
        confirm = (ImageButton) findViewById(R.id.imageButton_rating_confirm);

        cancel.setText("");

        price.setText(ValueMessager.edit_budget);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRatingDataConvert convert = new AddRatingDataConvert();
                AddRatingViewModel addRatingViewModel = new AddRatingViewModel();


                Client_RegisterController controller = new Client_RegisterController() {
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);

                            DataSendController c = new DataSendController(){
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);


                                    if(result){
                                        ValueMessager.commentLastPage = 1;
                                        ValueMessengerTaskInfo.providerUserName = ValueMessagerFurtherInfo.userName.toString();

                                        myLoading.CloseLoadingDialog();

                                        Intent intent = new Intent(Client_Rating.this, Client_CommentPage.class);
                                        startActivity(intent);
                                    }
                                }
                            };
                            JobServiceStatusViewModel model = new JobServiceStatusViewModel();
                            model.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());
                            model.setStatus(4);

                            String data2= new JobServiceStatusDataConvert().ModelToJson(model);
                            c.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data2, "PUT");
                    }
                };

                addRatingViewModel.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());
                addRatingViewModel.setComment(comment.getText().toString());
                addRatingViewModel.setRating(Double.parseDouble(Float.toString(ratingBar.getRating())));
                addRatingViewModel.setClientUsername(readData("userEmail"));

                String data = convert.ModelToJson(addRatingViewModel);
                myLoading.ShowLoadingDialog();
                controller.execute("http://para.co.nz/api/ProviderRating/AddRating", data, "POST");


            }
        });

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_rating);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        btnFunction();
    }
}
