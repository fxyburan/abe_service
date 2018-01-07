package com.vontroy.common.abe_source.algorithm;


import com.vontroy.common.abe_source.component.Decryptor;
import com.vontroy.common.abe_source.component.Encryptor;

import java.io.*;
import java.util.StringTokenizer;

public class FileUtils {

    public String encFile(String fileURL, String targetDirURL, String strPolicy, String objPK, byte[] symmetricKey, String ID) throws Exception {

        //File
        File file = new File(fileURL);
        if (!file.exists()) {
            System.out.println("the input file do not exit!");
            return null;
        }

        //FileName
        String fileName = file.getName();

        //call the encryption algorithm , obtain the cipertext (json)
        Encryptor encryptor = new Encryptor(ID);
        String cipertext = encryptor.encrypt(strPolicy, objPK, file, symmetricKey);
        String fullTargetURL = targetDirURL + "/" + "#$$#" + fileName;
        FileOutputStream outputStream = new FileOutputStream(fullTargetURL);
        outputStream.write(cipertext.getBytes());
        outputStream.close();
        return fullTargetURL;
    }

    public boolean decFile(String cipertextURL, String targetDirURL, String objSecretKey, String ID) throws Exception {

        String targetStr = null;
        //obtain the filename and filetype
        StringTokenizer outerST = new StringTokenizer(cipertextURL, "/");
        while (outerST.hasMoreTokens()) {
            targetStr = outerST.nextToken();
        }

        StringTokenizer innerST = new StringTokenizer(targetStr, "#$$#");
        targetStr = innerST.nextToken();

        //load the ciphertext
        String objCiphertext = this.readFile(cipertextURL);

        //call the decryption algorithm , obtain the plaintext (json)
        Decryptor decryptor = new Decryptor(ID);
        byte[] plaintext = decryptor.decrypt(objCiphertext, objSecretKey);
        if (plaintext == null) {
            return false;
        }

        FileOutputStream outputStream = new FileOutputStream(targetDirURL + "/Dec_" + targetStr);
        outputStream.write(plaintext);
        outputStream.close();
        return true;
    }

    public String readFile(String fileName) {

        File file = new File(fileName);
        BufferedReader reader = null;
        String str = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            // read line by line until null
            while ((tempString = reader.readLine()) != null) {
                str = tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
}
