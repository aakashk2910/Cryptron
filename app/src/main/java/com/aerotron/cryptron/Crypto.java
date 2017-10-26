/**
 * Created by Aakash Kamble on 16-03-2016.
 */
package com.aerotron.cryptron;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {
    public void encrypt(String path, String pass){
        String Ext = path.substring(path.indexOf('.'));
        String Name = path.substring(path.lastIndexOf('/')+1,path.indexOf('.'));
        String gPath = path.substring(0,path.lastIndexOf('/'));
        String Folder = gPath.substring(0,gPath.lastIndexOf('/')+1);
        try{
            FileInputStream file = new FileInputStream(path);

            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Cryptron/Encrypted");
            if(!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Cryptron/Encrypted/"+Name+Ext);
            byte j[]=pass.getBytes();
            SecretKeySpec kye = new SecretKeySpec(j,"DES");
            System.out.println(kye);
            Cipher enc = Cipher.getInstance("DES");
            enc.init(Cipher.ENCRYPT_MODE,kye);
            CipherOutputStream cos = new CipherOutputStream(output, enc);
            byte[] buf = new byte[1024];
            int read;
            while((read=file.read(buf))!=-1){
                cos.write(buf,0,read);
            }
            file.close();
            output.flush();
            cos.close();}
        catch(Exception e){}
    }

    public void decrypt(String path,String pass){
        try{
            String Ext = path.substring(path.indexOf('.'));
            String Name = path.substring(path.lastIndexOf('/')+1,path.indexOf('.'));
            String Path1 = path.substring(0,path.lastIndexOf('/')+1);
            String Path2 = Path1.substring(0,Path1.lastIndexOf('/'));
            String Path3 = Path2.substring(0,Path2.lastIndexOf('/'));
            String Folder = Path3.substring(0,Path3.lastIndexOf('/')+1);

            FileInputStream file = new FileInputStream(path);

            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Cryptron/Decrypted");
            if(!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Cryptron/Decrypted/"+Name+Ext);
            byte j[]=pass.getBytes();
            SecretKeySpec kye = new SecretKeySpec(j,"DES");
            System.out.println(kye);
            Cipher enc = Cipher.getInstance("DES");
            enc.init(Cipher.DECRYPT_MODE,kye);
            CipherOutputStream cos = new CipherOutputStream(output, enc);
            byte[] buf = new byte[1024];
            int read;
            while((read=file.read(buf))!=-1){
                cos.write(buf,0,read);
            }
            file.close();
            output.flush();
            cos.close();}
        catch(Exception e){}
    }
}

