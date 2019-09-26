package edu.vdac.encry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author admin
 */
class rol {
    public static int numLin;
    private static int numRes = 0;
    int numCar = 0;
    String array[] = new String[5];
    //String instruc[] = new String[30];
    int i;
    String root="hola";

    public void leerConfiguracion(String archivo) throws Exception {
        int cont = 0, u = 0;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String cad = b.readLine();
        System.out.println("La cadena es "+cad);
        //des(cad,root);
        /*while (cad != null) {
            array[cont] = cad;
            des(array[cont],root);
            cont++;
            cad = b.readLine();

        }*/
        b.close();
        f.close();
        des(cad,root);
    }//metodo leer

    public String des(String datos,String password) throws Exception{

        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDescoficados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        metodo(datosDesencriptadosString);
        return datosDesencriptadosString;
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
    public void metodo(String datos){
        String[] instruc = datos.split(",");
        for (int i = 0;i<=instruc.length; i++){
            System.out.println("Mirar "+instruc[i]);
        }
    }
}