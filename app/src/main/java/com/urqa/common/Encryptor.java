package com.urqa.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.urqa.common.Network.Method;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

    public static final String ENCRYPTION = "ENTRYPTION";
    public static final String ENCRYPTION_BASE_KEY = "ENTRYPTION_BASE_KEY";
    public static final String ENCRYPTION_TOKEN = "ENCRYPTION_PRIVATE_KEY";

    public static String baseKey;
    public static String token;

    private static Cipher encryptor;
    private static Cipher decryptor;
    private static String IV = "0000000000000000";
    private static SecretKey secureKey;

    public static final String ENC_DATA = "enc_data";

    public static void requestToken(final Context context) throws Exception {

        // Generate RSA key pairs
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        final KeyPair keypair = keyGen.genKeyPair();
        byte[] publicKey = keypair.getPublic().getEncoded();

        String public_key = "-----BEGIN PUBLIC KEY-----\\n"
                + Base64.encodeToString(publicKey, Base64.NO_WRAP)
                + "\\n-----END PUBLIC KEY-----\\n";

        // request Key
        String url = StateData.ServerAddress + "client/get_key";
        String data = "{\"public\":\"" + public_key + "\"}";

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                try {
                    String response = (String)msg.obj;
                    // token parse
                    if (!response.contains(ENC_DATA)) {
                        Log.v("", "enc data does not exist");
                        return;
                    }
                    String enc_data = Encryptor.getJsonToken(response, ENC_DATA);

                    Cipher rsa;
                    rsa = Cipher.getInstance("RSA");
                    rsa.init(Cipher.DECRYPT_MODE, keypair.getPrivate());
                    byte[] utf8 = rsa.doFinal(Base64.decode(enc_data, Base64.NO_WRAP));
                    String enc_data2 = new String(utf8, "UTF8");

                    Encryptor.baseKey = Encryptor.getJsonToken(enc_data2, "basekey");
                    Encryptor.token = Encryptor.getJsonToken(enc_data2, "token");

                    SharedPreferences prefs = context.getSharedPreferences(Encryptor.ENCRYPTION,
                            Context.MODE_PRIVATE);
                    Editor editor = prefs.edit();
                    editor.putString(Encryptor.ENCRYPTION_BASE_KEY, Encryptor.baseKey);
                    editor.putString(Encryptor.ENCRYPTION_TOKEN, Encryptor.token);
                    editor.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Network network = new Network();
        network.setNetworkOption(url, data, Method.POST, false);
        network.setHandler(handler);
        network.start();
    }



    private static String getJsonToken(String json, String key) {
        String wrapped_key = "\"" + key + "\"";
        int start_idx = json.indexOf(wrapped_key) + wrapped_key.length();
        start_idx = json.indexOf('"', start_idx) + 1;
        int end_idx = json.indexOf('"', start_idx);
        return json.substring(start_idx, end_idx);
    }

    /**
     *
     * @return AEC-256-cbc-pkcs5padding + BASE64
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static String encrypt(String data) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        if (baseKey == null || token == null) {
            throw new IllegalArgumentException(
                    "you dont initialize basekey or token");
        }
        data = "{  \"token\":\"" + token + "\", \"enc_data\" : \""
                + encrypt(baseKey, data.getBytes()) + "\", \"src_len\":"
                + data.getBytes().length + " }";
        return data;
    }

    private static String encrypt(String baseKey, byte[] src)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        byte[] key = SHA256(baseKey);

        if (secureKey == null) {
            secureKey = new SecretKeySpec(key, "AES");
        }

        if (encryptor == null) {
            encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(
                    IV.getBytes()));
        }

        byte[] encrypted = null;

        try {
            encrypted = encryptor.doFinal(src);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }

    /**
     * 
     * @param src
     * @return source data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public byte[] decrypt(String baseKey, String src)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        byte[] key = SHA256(baseKey);

        if (secureKey == null) {
            secureKey = new SecretKeySpec(key, "AES");
        }

        if (decryptor == null) {
            decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptor.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(
                    IV.getBytes()));
        }

        byte[] dec = Base64.decode(src, 0);
        byte[] ret = null;

        try {
            ret = decryptor.doFinal(dec);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static byte[] SHA256(String str) {
        try {

            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            return byteData;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}