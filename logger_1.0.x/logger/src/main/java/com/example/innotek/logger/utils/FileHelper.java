/**
 * 
 */
package com.example.innotek.logger.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wuhao 2016-6-1
 */
public class FileHelper {
	 /**
     * 文件是否存在
     * @param filePathName
     * @return
     */
    public static boolean isExist(String filePathName) {
        File file = new File(filePathName);
        return (!file.isDirectory() && file.exists());
    }
     
    public static boolean isDirExist(String filePathName) {
        if(!filePathName.endsWith("/")) filePathName +="/";
        File file = new File(filePathName);
        return (file.isDirectory() && file.exists());
    }
     
    /**
     * 获取路径，不带文件名，末尾带'/'
     * @param filePathName
     * @return
     */
    public static String getPath(String filePathName) {
        try {
            return filePathName.substring(0, filePathName.lastIndexOf('/') + 1);
        } catch (Exception e) {
            return "";
        }
    }
     
    /**
     * 获取目录的名称 注意：只能获取如：/aaaa/ssss/ 或 /aaaa/dsddd
     * @param filePathName
     * @return
     */
    public static String getDirPathName(String filePathName) {
        try {
            if(filePathName.endsWith("/"))
                filePathName = filePathName.substring(0, filePathName.lastIndexOf('/'));
            return filePathName.substring(filePathName.lastIndexOf("/") + 1, filePathName.length());
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 获取文件名，带后缀
     * @param filePathName
     * @return
     */
    public static String getName(String filePathName) {
        try {
            return filePathName.substring(filePathName.lastIndexOf('/') + 1);
        } catch (Exception e) {
            return "";
        }
    }
     
    /**
     * 获取文件名，不带后缀
     * @return
     */
    public static String getNameNoPostfix(String filePathName) {
        try {
            return filePathName.substring(filePathName.lastIndexOf('/') + 1, filePathName.lastIndexOf('.'));
        } catch (Exception e) {
            return "";
        }
    }
     
    /**
     * 重命名
     * @param filePathName
     * @param newPathName
     */
    public static void rename(String filePathName, String newPathName) {
        if(isNullString(filePathName)) return ;
        if(isNullString(newPathName)) return ;   
        FileHelper.delete(newPathName); //新名称对应的文件可能已经存在，先删除         
        File file = new File(filePathName);
        File newFile = new File(newPathName);
        file.renameTo(newFile);
    }
     
    /**
     * 删除文件
     */
    public static void delete(String filePathName) {
        if(isNullString(filePathName)) return ;
        File file = new File(filePathName);
        if (file.isFile() && file.exists()) {
            boolean flag = file.delete();
            Log.i("LOG", "filePathName reset:"+filePathName+" flag:"+flag);
        }
    } 
    /**
     * 创建目录，整个路径上的目录都会创建
     * @param path
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }  
    /** 尝试创建空文件
     * <br> 如果文件已经存在不操作,返回true
     * @param path 路径
     * @return  如果创建失败(Exception) 返回false，否则true
     */
    public static boolean createEmptyFile(String path){
        File file = new File(path);
        if(!file.exists()){
            try{
                return file.createNewFile();
            }
            catch (Exception e) {
                return false;
            }
        }
        return true;
    }
     
    /**
     * 获取文件大小
     * @param filePathName
     * @return
     */
    public static long getSize(String filePathName) {
        if(isNullString(filePathName)) return 0;
        File file = new File(filePathName);
        if (file.isFile()) return file.length();
        return 0;
    }
     
    /**
     * 读取文件数据到byte数组
     * @param filePathName 文件名
     * @param readOffset 从哪里开始读
     * @param readLength 读取长度
     * @param dataBuf 保存数据的缓冲区
     * @param bufOffset 从哪里保存
     * @return
     */
    public static boolean readData(String filePathName, int readOffset, int readLength, byte[] dataBuf, int bufOffset) {
        try {
            int readedTotalSize = 0;
            int onceReadSize = 0;
             
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePathName));
            in.skip(readOffset);
            while (readedTotalSize < readLength 
                    && (onceReadSize = in.read(dataBuf, bufOffset + readedTotalSize, readLength - readedTotalSize)) >= 0) {
                readedTotalSize += onceReadSize;
            }
            in.read(dataBuf, bufOffset, readLength);
            in.close();
            in = null;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
     
    /** 将某个流的内容输出到文件
     * @param in 输入流
     * @param filePathName 目标文件
     * @return 
     */
    public static boolean writeFile(InputStream in,String filePathName){
        boolean flag = false;
        OutputStream outStream = null;
        try {
            File destFile = new File(filePathName);
            if (destFile.exists()) {
                destFile.delete();
            } else {
                destFile.createNewFile();
            }
            outStream = new FileOutputStream(filePathName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while(true){
                int length = in.read(buffer, 0, 1024);
                if(length > 0){
                    outStream.write(buffer, 0, length);
                }else{
                    break;
                }
                count+=length;
            }
            if(count > 0){
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(outStream != null){
                try{outStream.close();}catch(Exception e){e.printStackTrace();}
            }
        }
        return flag;
    }
     
    /**
     * 判断当前字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNullString(String str) {
        if(str == null || str.equals("")) return true ;
        return false;
    }
     
    /**
     * 复制文件
     * @param fromPathName
     * @param toPathName
     * @return
     */
    public static int copy(String fromPathName, String toPathName) {
        try {
            InputStream from = new FileInputStream(fromPathName);
            return copy(from, toPathName);
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
     
    /**
     * 复制文件
     * @param from
     * @param toPathName
     * @return
     */
    public static int copy(InputStream from, String toPathName) {
        try {
        	FileHelper.delete(toPathName); 
            OutputStream to = new FileOutputStream(toPathName);
            byte buf[] = new byte[1024];
            int c;
            while ((c = from.read(buf)) > 0) {
                to.write(buf, 0, c);
            }
            from.close();
            to.close();
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }
     
     
    /**
     * 根据zip文件路径转换为文件路径
     *  @param zipFullPath 必须带.zip
     * @return
     */
    public static String zipFileFullPath(String zipFullPath) {
        int zipIndex = zipFullPath.lastIndexOf(".zip");
        int zipIndexTmp = zipFullPath.lastIndexOf(".ZIP");
        String tmp = "";
        if(zipIndex > -1) {
            tmp = zipFullPath.substring(0, zipIndex);
        } else if(zipIndexTmp > -1) {
            tmp = zipFullPath.substring(0, zipIndexTmp);
        }
        return tmp;
    }
     
    /**
     * 改变文件权限
     * @param permission
     * @param filePathName
     */
    public static void chmod(String permission, String filePathName)
    {
        try {
            String command = "chmod " + permission + " " + filePathName;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
