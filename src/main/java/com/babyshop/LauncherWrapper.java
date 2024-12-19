package com.babyshop;

import com.babyshop.backend.DBUtil;

/**
 * @author : L.H.J
 * @File: LauncherWrapper
 * @mailto : lharshana2002@gmail.com
 * @created : 2024-12-19, Thursday
 **/
public class LauncherWrapper {
    public static void main(String[] args) {
//        DBUtil.createDatabaseIfNotExists();
        Launcher.main(args);
    }
}