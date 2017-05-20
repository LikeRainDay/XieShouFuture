package com.xieshoufuture.houshuai.server;

import com.xieshoufuture.houshuai.pojo.User;
import sun.awt.image.ImageAccessException;

import java.awt.image.ImagingOpException;

/**
 * Created by housh on 2017/5/20.
 */
public interface FileUploadListner {

    User selectUserPhoto(String id) throws ImagingOpException;


    boolean insertUserPhoto(String userId, byte[] photo) throws Exception;

}
