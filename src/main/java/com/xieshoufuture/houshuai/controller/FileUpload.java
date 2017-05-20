package com.xieshoufuture.houshuai.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.xieshoufuture.houshuai.pojo.User;
import com.xieshoufuture.houshuai.server.FileUploadListner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

/**
 * Created by housh on 2017/5/20.
 */

@Controller
@RequestMapping("/")
public class FileUpload {


    @Autowired
    private FileUploadListner fileUploadListner;

    /**
     * 更新用户头像
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public void uploadPicFile(HttpServletRequest req, HttpServletResponse response) {
        System.out.print("----插入图片----");

        try {
            String id = req.getParameter("userId");
            System.out.print(id);
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) req;

            MultipartFile imageFile = multipartHttpServletRequest.getFile("imageFile");
            byte[] photo = imageFile.getBytes();
            boolean b = fileUploadListner.insertUserPhoto(id, photo);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("result:" + b);
        } catch (IOException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取用户头像
     */
    @RequestMapping(value = "/readPic", method = RequestMethod.GET)
    public void readPhoto(HttpServletRequest req, HttpServletResponse response) throws IOException {
        System.out.print("----readPhoto---");
        User userId = fileUploadListner.selectUserPhoto(req.getParameter("userId"));
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileOutputStream = new FileInputStream(new File(userId.getPhoto()));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileOutputStream);
            byte[] ch = new byte[1024 * 5];
            int lenth = 0;
            while ((lenth = (bufferedInputStream.read(ch))) != -1) {
                outputStream.write(ch, 0, lenth);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
    }


    }

}
