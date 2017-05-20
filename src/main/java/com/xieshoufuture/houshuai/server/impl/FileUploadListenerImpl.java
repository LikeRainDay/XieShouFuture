package com.xieshoufuture.houshuai.server.impl;

import com.xieshoufuture.houshuai.dao.UserMapper;
import com.xieshoufuture.houshuai.pojo.User;
import com.xieshoufuture.houshuai.server.FileUploadListner;
import com.xieshoufuture.houshuai.utils.DateUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import sun.awt.TracedEventQueue;
import sun.awt.image.ImageAccessException;

import javax.rmi.CORBA.Util;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.cert.TrustAnchor;

/**
 * Created by housh on 2017/5/20.
 */
public class FileUploadListenerImpl implements FileUploadListner {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectUserPhoto(String id) throws ImagingOpException {
        User user = userMapper.selectByPrimaryKey(Long.valueOf(id));
        if (null == user)
            throw new NullPointerException("用户名不存在！");

        System.out.print("当前用户获取的图片为：" + user.getPhoto());
        return user;
    }

    @Override
    public boolean insertUserPhoto(String userId, byte[] photo) throws Exception {
        if (null == userId || userId.trim().isEmpty())
            throw new NullPointerException("用户不存在!");
        User user = userMapper.selectByPrimaryKey(Long.valueOf(userId));
        if (null == user)
            throw new NullPointerException("用户id不存在");
        FileOutputStream hello = null;
        String picPath = "/photo/" + DateUtil.getCurrentDateStr() + "user.jpg";
        try {
            hello = new FileOutputStream(new File(picPath), false);
            hello.write(photo);
            hello.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != hello)
                hello.close();
            user.setPhoto(picPath);
        }
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i == 1 ? true : false;
    }
}
