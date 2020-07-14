/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * @author sunpeikai
 * @version FileUtil, v0.1 2020/7/14 9:32
 * @description
 */
@Slf4j
public class FileUtil {

    public static String fileReadBuffer(String fileFullPath){
        BufferedInputStream buffer = null;
        FileInputStream inputStream = null;
        byte[] data = new byte[1024];
        StringBuilder builder = new StringBuilder();
        try{
            inputStream = new FileInputStream(fileFullPath);
            buffer = new BufferedInputStream(inputStream);
            int byteRead = 0;
            while((byteRead = buffer.read(data)) != -1){
                builder.append(new String(data,0,byteRead));
            }
            return builder.toString();
        }catch (Exception e){
            e.printStackTrace();
            log.error("read file failed,exception is ==>>{}",e.getMessage());
            return "";
        }finally {
            try{
                if(buffer != null){
                    buffer.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }catch (Exception e){
                log.error("resources close failed");
            }
        }
    }
}
