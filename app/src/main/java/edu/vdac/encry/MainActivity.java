package edu.vdac.encry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    //String txt="Stamping$$, Hello, Harvey, Budgie";
    String txt="NEmbSVNCnTs/zuXrjm4peArEdOUvZn7azqUZ3xzMQ6Cmb1F12407nf6eAH6+XW04";
    private static final String llave="hola";
    TextView aa;
    Button b,a;
    private static final String AES="AES";
    String ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String cv="/storage/extSdCard/Firmas/conf.txt";
        try {
            new rol().leerConfiguracion(cv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        aa=findViewById(R.id.name);
        b=findViewById(R.id.button);

        a = findViewById(R.id.encriptar);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ts = encriptar(txt, llave);
                    aa.setText(ts);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                   ts= des(aa.getText().toString(),llave);
                    aa.setText(ts);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public static String des(String datos,String password) throws Exception{

        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDescoficados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }

    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
    }

    private static SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

}
