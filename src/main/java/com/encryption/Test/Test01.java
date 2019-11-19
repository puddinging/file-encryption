package com.encryption.Test;

import com.encryption.AESByte;
import com.encryption.utils.AesUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Test01 {
    public static void main(String[] args) throws IOException {
        String path1 = "E:\\test.xlsx";
        String path2 = "E:\\test1.xlsx";
        String path3 = "E:\\test2.xlsx";
        AesUtil.AESEncodeFile("jie",path1,path2);
        AesUtil.AESDncode("jie",path2,path3);
    }
}
