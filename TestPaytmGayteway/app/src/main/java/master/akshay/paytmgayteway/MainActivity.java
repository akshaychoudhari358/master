package master.akshay.paytmgayteway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button paytmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paytmButton = findViewById(R.id.goToPaytm);

        setListeners();

    }

    private void setListeners(){

        paytmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }

                final String M_ID = "bfpbBI22855242503964";
                final String cust_id = "101";
                final String order_id = UUID.randomUUID().toString().substring(0,28);
                String url = "https://abcdprojects.000webhostapp.com/paytm/generateChecksum.php";
                final String callbackUrl ="https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("CHECKSUMHASH")){
                                String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");

                                PaytmPGService paytmPGService = PaytmPGService.getStagingService(null);


                                HashMap<String, String> paramMap = new HashMap<String,String>();
                                paramMap.put( "MID" , M_ID);
                                paramMap.put( "ORDER_ID" , order_id);
                                paramMap.put( "CUST_ID" , cust_id);
                                paramMap.put( "CHANNEL_ID" , "WAP");
                                paramMap.put( "TXN_AMOUNT" , "500");
                                paramMap.put( "WEBSITE" , "WEBSTAGING");
                                paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                                paramMap.put( "CALLBACK_URL", callbackUrl);
                                paramMap.put("CHECKSUMHASH", CHECKSUMHASH);

                                PaytmOrder order = new PaytmOrder(paramMap);


                                paytmPGService.initialize(order, null);

                                paytmPGService.startPaymentTransaction(MainActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                                    @Override
                                    public void onTransactionResponse(Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void networkNotAvailable() {
                                        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void clientAuthenticationFailed(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "clientAuthenticationFailed = "+inErrorMessage, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void someUIErrorOccurred(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                        Toast.makeText(getApplicationContext(), "onErrorLoadingWebPage " + inErrorMessage , Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onBackPressedCancelTransaction() {
                                        Toast.makeText(getApplicationContext(), "onBackPressedCancelTransaction " , Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "onTransactionCancel " + inErrorMessage , Toast.LENGTH_LONG).show();
                                    }
                                });


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();

                    }
                },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();


                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> paramMap = new HashMap<String,String>();
                        paramMap.put( "MID" , M_ID);
                        paramMap.put( "ORDER_ID" , order_id);
                        paramMap.put( "CUST_ID" , cust_id);
                        paramMap.put( "CHANNEL_ID" , "WAP");
                        paramMap.put( "TXN_AMOUNT" , "500");
                        paramMap.put( "WEBSITE" , "WEBSTAGING");
                        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                        paramMap.put( "CALLBACK_URL", callbackUrl);

                        return paramMap;
                    }
                };

                requestQueue.add(stringRequest);
             }
        });
    }
}
