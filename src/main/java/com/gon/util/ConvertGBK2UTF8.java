package com.gon.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.manyit.common.util.StreamUtil;
import com.manyit.common.util.StringUtil;

public class ConvertGBK2UTF8 {

    public static void main(String[] args) {
//        converFileCharSet("C:\\Program Files\\Java\\work_space\\cms\\src\\main\\java", "gbk", "utf-8");
//        converFileCharSet("C:\\Program Files\\Java\\work_space\\cms\\src\\main\\webapp", "gbk", "utf-8");
        converFileCharSet("C:\\Program Files\\Java\\work_space\\cms\\src\\main\\webapp\\WEB-INF\\common", "gbk", "utf-8");

    }

    public static void converFileCharSet(String pFileURI,String pCharSet,String rCharSet){
        List<File> resultFile = new ArrayList<File>();
        File pFile = new File(pFileURI);
        getDirectoryFiles(pFile, resultFile);
        System.out.println("文件数量："+resultFile.size());
        for(int i =0,j=resultFile.size();i<j;i++){
            try {
                transferFile(resultFile.get(i), pCharSet, rCharSet);
                System.out.println("成功转换【"+i+"】文件！");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("转换文件【"+resultFile.get(i).getPath()+"】异常！"+e.getMessage());
            }
        }
    }

    private static void getDirectoryFiles(File file,List<File> resultFile){
        if(null != file && null != resultFile){
            if(file.isDirectory()){
                File[] fileArr = file.listFiles();
                for(File itemFile : fileArr){
                    getDirectoryFiles(itemFile, resultFile);
                }
            }else if(file.isFile()){
                resultFile.add(file);
            }
        }
    }

    /**
     * 把文件从p编码转换成r编码，具体根据传入的参数决定
     * TODO <功能简述> <br/>
     * TODO <功能详细描述>
     * @param orgFile
     * @param p
     * @param r
     * @throws Exception
     */
    private static void transferFile(File orgFile,String p,String r) throws Exception{
        if(null != orgFile){
            FileInputStream fis = new FileInputStream(orgFile);
            if(null != fis){
                byte[] content = StreamUtil.InputStreamToByte(fis);
                String charset = StringUtil.convertToString(CharsetDetectUtil.detect(content));
                System.out.println("charset:" + charset + ";r:" + r + ";equals:" + charset.toLowerCase().equals(r));
                if(!charset.toLowerCase().equals(r)){
                    String orgFileContent = FileUtils.readFileToString(orgFile,p);
                    FileUtils.writeStringToFile(orgFile, orgFileContent,r);
                }else{
                    System.out.println("此文件不需要转换...>>"+orgFile);
                }
            }
        }
    }
}
