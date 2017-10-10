package com.hy.utils ;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.log4j.spi.LoggerFactory;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;

/**
 * Created by raymondlei.ljw on 2017/6/19.
 */
public class MockUtils {

//    private static Logger logger = LoggerFactory.getLogger(MockUtils.class);

    public static <T> T create(Class<T> clazz) {
        try {
            return _createTest(clazz);
        } catch (Exception e) {
//            logger.error("err", e);
        }
        return null;
    }


    public static <T> List<T> createList(Class<T> clazz, int min, int max) {
        return createList(clazz, createInteger(min, max));
    }

    public static <T> List<T> createList(Class<T> clazz, int times) {
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < times; i++) {
            result.add(create(clazz));
        }
        return result;
    }

    private static <T> T _createTest(Class<T> clazz) throws Exception {
        T instance = clazz.newInstance();
        Class<?> nowClazz = clazz;
        while (!nowClazz.getName().contains("java.lang.Object")) {
            Field[] fields = nowClazz.getDeclaredFields();
            for (Field field : fields) {
                setProperty(instance, field);
            }
            nowClazz = nowClazz.getSuperclass();
        }
        return instance;
    }

    private static <T> void setProperty(T instance, Field field) throws IllegalAccessException {
        if (field.getName().contains("serialVersionUID")) {
            return;
        }
        Class<?> type = field.getType();
        field.setAccessible(true);
        String fieldName = field.getName().toLowerCase();
        if (type == String.class) {
            if (fieldName.contains("no")) {
                field.set(instance, createInteger(10000, 100000) + "");
            } else if (fieldName.contains("creator") || fieldName.contains("modifier")) {
                field.set(instance, createCnString(3));
            } else {
                field.set(instance, createCnString(createInteger(4, 10)));
            }
        } else if (type == Integer.class || type == int.class) {
            if (fieldName.contains("status")) {
                field.set(instance, createInteger(2));
            } else {
                field.set(instance, createInteger(1024));
            }
        } else if (type == Long.class || type == long.class) {
            field.set(instance, createLong());
        } else if (type == Date.class) {
            field.set(instance, createDate());
        }
    }

    public static String createString() {
        return createCnString(createInteger(4, 10));
    }

    public static int createInteger(int limit) {
        return createInteger() % limit;
    }

    public static int createInteger() {
        int value = new Random().nextInt();
        return value < 0 ? -value : value;
    }

    public static int createInteger(int min, int max) {
        int offset = max - min;
        int start = createInteger() % offset;
        return min + start;
    }

    public static boolean createBool(float ratio) {
        return new Random().nextFloat() < ratio;
    }

    public static long createLong() {
        return createInteger();
    }

    public static Date createDate() {
        return new DateTime().plusDays(-(new Random().nextInt() % 2000)).toDate();
    }

    public static String createCnString(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // ����ߵ�λ
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //��ȡ��λֵ
            lowPos = (161 + Math.abs(random.nextInt(93))); //��ȡ��λֵ
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk"); //ת������
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    //HwecResult<List<FullSceneHotelBaseDO>>
//    public static void main(String[] args) {
//        List<HotelAroundRecommenderExtendDO> resultList = MockUtils.createList(HotelAroundRecommenderExtendDO.class, 20, 30);
//        System.out.println(JSON.toJSON(resultList));
//    }
}
