package com.encryption.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtil {
    /**
     */
    public static void AESEncodeFile(String encodeRules,String filePath,String destFilePath){
//        获取加密key
        SecretKey secreKey = getSecreKey(encodeRules);
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secreKey);

            InputStream in = new FileInputStream(filePath);
            OutputStream os = new FileOutputStream(destFilePath);
            CipherInputStream cin = new CipherInputStream(in, cipher);
            byte[] cache = new byte[1024];
            int len = 1024;

            while (-1 != (len = cin.read(cache))){
                os.write(cache,0,len);
                os.flush();
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void AESDncode(String encodeRules,String filePath, String destFilePath){
//        获取加密key
        SecretKey secreKey = getSecreKey(encodeRules);
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secreKey);

            InputStream in = new FileInputStream(filePath);
            OutputStream os = new FileOutputStream(destFilePath);
            CipherOutputStream cos = new CipherOutputStream(os,cipher);

            byte[] cache = new byte[1024];
            int len = 1024;

            while (-1 != (len = in.read(cache))){
                cos.write(cache,0,len);
                cos.flush();
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
    }


    public static String AESEncode(String encodeRules,String content){
        try {
            SecretKey key = getSecreKey(encodeRules);
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }

    public static String AESDncode(String encodeRules,String content){
        try {
            SecretKey key = getSecreKey(encodeRules);
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }


    private static SecretKey getSecreKey(String ruleStr){
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen= null;
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        keygen.init(128, new SecureRandom(ruleStr.getBytes()));
        //3.产生原始对称密钥
        SecretKey original_key=keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte [] raw=original_key.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKey key=new SecretKeySpec(raw, "AES");

        return key;
    }

}
