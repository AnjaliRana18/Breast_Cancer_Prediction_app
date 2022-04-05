package com.example.breast_cancer_prediction_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ct,su,shu,ma,es,bn,bc,nn,m;
    Button predict;

    String url1 = "https://breast-cancer-prediction1-app.herokuapp.com/predict";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ct = findViewById(R.id.clump_thickness);
        su = findViewById(R.id.size_uniformity);
        shu = findViewById(R.id.shape_uniformity);
        ma = findViewById(R.id.marginal_adhesion);
        es = findViewById(R.id.epithelial_size);
        bn = findViewById(R.id.bare_nucleoli);
        bc = findViewById(R.id.bland_chromatin);
        nn = findViewById(R.id.normal_nucleoli);
        m = findViewById(R.id.mitoses);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("class");
                            if(data.equals("1")){
                                result.setText("Malignant Cancerous");
                            }else{
                                result.setText("Benign Non-Cancerous");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Internet Not Working", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("clump_thickness",ct.getText().toString());
                        params.put("size_uniformity",su.getText().toString());
                        params.put("shape_uniformity",shu.getText().toString());
                        params.put("marginal_adhesion",ma.getText().toString());
                        params.put("epithelial_size",es.getText().toString());
                        params.put("bare_nucleoli",bn.getText().toString());
                        params.put("bland_chromatin",bc.getText().toString());
                        params.put("normal_nucleoli",nn.getText().toString());
                        params.put("mitoses",m.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}