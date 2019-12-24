package com.springboot.common.uploadFile;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

public class FastdfsUploadOrDown {
    //上传fastdfs

    public static  int deleteReturnPatn(String file){
        int i = 1;
        try {
            //读取配置文件
            ClassPathResource resource = new ClassPathResource("fastdfs-client.properties"); //从classpath路径下读取文件
            ClientGlobal.init(resource.getClassLoader().getResource("fastdfs-client.properties").getPath());
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //连接storage的客户端
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            i = storageClient1.delete_file("group1",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    public  String returnPatn(MultipartFile file){
        String allPath = "";
        try {
            //读取配置文件
            ClassPathResource resource = new ClassPathResource("fastdfs-client.properties"); //从classpath路径下读取文件

            Properties properties = new Properties();
            properties.load(resource.getInputStream());
//            ClientGlobal.init(resource.getClassLoader().getResource("fastdfs-client.properties").getPath());
//            ClientGlobal.init(this.getClass().getResource("/").getPath()+resource);
            ClientGlobal.initByProperties(properties);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //连接storage的客户端
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            //文件上传到文件服务器
            String name = file.getOriginalFilename();
            //获取扩展名
            String ext = FilenameUtils.getExtension(name);
            //获取文件大小
            long size = file.getSize();
            String filePath = storageClient1.upload_file1(file.getBytes(), ext,null); //返回存储的文件路径
            allPath = Constant.FASTDFS_PATH + filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPath;
    }
    /*public static  String returnPatn(MultipartFile file){
        String allPath = "";
        try {
            //读取配置文件
            ClassPathResource resource = new ClassPathResource("fastdfs-client.properties"); //从classpath路径下读取文件
            ClientGlobal.init(resource.getClassLoader().getResource("fastdfs-client.properties").getPath());
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //连接storage的客户端
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            //文件上传到文件服务器
            String name = file.getOriginalFilename();
            //获取扩展名
            String ext = FilenameUtils.getExtension(name);
            //获取文件大小
            long size = file.getSize();
            String filePath = storageClient1.upload_file1(file.getBytes(), ext,null); //返回存储的文件路径
            allPath = "https://fdfs.baodingjl.com/" + filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPath;
    }*/

    public static  String returnPatnByByte(byte[] fileContent){
        String allPath = "";
        try {
            //读取配置文件
            ClassPathResource resource = new ClassPathResource("fastdfs-client.properties"); //从classpath路径下读取文件
            ClientGlobal.init(resource.getClassLoader().getResource("fastdfs-client.properties").getPath());
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //连接storage的客户端
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            //获取扩展名
            String ext = "jpg";
            String filePath = storageClient1.upload_file1(fileContent, ext,null); //返回存储的文件路径
            allPath =  Constant.FASTDFS_PATH  + filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPath;
    }
}
