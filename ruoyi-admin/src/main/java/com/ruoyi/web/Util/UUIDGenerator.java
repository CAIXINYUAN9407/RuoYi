package com.ruoyi.web.Util;

import com.ruoyi.common.utils.uuid.UUID;

public class UUIDGenerator {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int random = (int) (Math.random() * Integer.MAX_VALUE);
        UUID uuid = new UUID(time, random);
        System.out.println("生成的UUID为：" + uuid.toString());
    }

    public String setUUID(){
        long time = System.currentTimeMillis();
        int random = (int) (Math.random() * Integer.MAX_VALUE);
        UUID uuid = new UUID(time, random);
        return uuid.toString();

    }

}
