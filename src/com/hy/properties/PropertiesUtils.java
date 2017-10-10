package com.hy.properties;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author wentao.awt
 * @since 14-4-22
 */
public class PropertiesUtils {

    private static Log log = LogFactory.getLog(PropertiesUtils.class);

    /**
     * 缓存配置文件信息
     */
    private static Map<String, Properties> propertiesMap = new HashMap<>();

    private static final String DEFAULT_URL = "/system.properties";

    /**
     * 缓存读，配置读取后保存到Map
     *
     * @param path 配置文件路径
     * @param key  配置信息键
     * @return
     */
    public static String getForCach(String path, String key) {
        Properties properties = propertiesMap.get(path);

        //不存在，缓存下来
        if (null == properties) {
            properties = readPatten(path);
            propertiesMap.put(path, properties);
        }

        return get(properties, key);
    }

    /**
     * 获取资源中的配置文件信息
     * @param url
     * @return
     */
    private static Properties readPatten(String url) {
        Properties properties = new Properties();
        InputStreamReader reader = null;
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(url);

            for (Resource resource : resources) {

                reader = new InputStreamReader(resource.getInputStream());
                properties.load(reader);
                break;
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("读取" + url + ".properties配置文件时出错!", e);
            }
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("关闭" + url + ".properties文件输入流过程中出错!", e);
                }
            }
        }
        return properties;
    }

    private static Properties read(String url) {
        Properties properties = new Properties();

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(PropertiesUtils.class.getResourceAsStream(url), "UTF-8");
            properties.load(inputStreamReader);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("读取" + url + ".properties配置文件时出错!", e);
            }
        } finally {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("关闭" + url + ".properties文件输入流过程中出错!", e);
                }
            }
        }
        return properties;
    }

    public static String get(String url, String key) {
        Properties properties = read(url);
        return get(properties, key);
    }

    public static String get(Properties properties, String key) {
        String value = StringUtils.EMPTY;
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }

    public static String get(String key) {
        return get(DEFAULT_URL, key);
    }

    public static Set<Object> getKeySet(String url) {
        Properties properties = read(url);
        return getKeySet(properties);
    }

    public static Set<Object> getKeySet(Properties properties) {
        return properties.keySet();
    }

    public static void updateProperty(String url, String key, String value) {
        File file = new File(PropertiesUtils.class.getClassLoader()
            .getResource(".").getPath(), url);
        InputStream inputStream = null;
        PrintStream printStream = null;

        try {
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();

            properties.load(inputStream);
            properties.setProperty(key, value);

            printStream = new PrintStream(file);
            properties.store(printStream, "update key:" + key + ",value:"
                + value);
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + url + ".properties配置文件时出错!", e);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + url + ".properties配置文件时出错!", e);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("关闭" + url + ".properties文件输入流过程中出错!", e);
                    }
                }
            }
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static void updateProperty(String key, String value) {
        updateProperty(DEFAULT_URL, key, value);
    }

    public static <K, V> void batchUpdate(String url, Map<K, V> map) {
        File file = new File(PropertiesUtils.class.getClassLoader()
            .getResource(".").getPath(), url);
        InputStream inputStream = null;
        PrintStream printStream = null;

        try {
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();

            properties.load(inputStream);
            for (K k : map.keySet()) {
                properties.setProperty(k.toString(), map.get(k).toString());
            }

            printStream = new PrintStream(file);
            properties.store(printStream, "batch update!");
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + url + ".properties配置文件时出错!", e);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + url + ".properties配置文件时出错!", e);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("关闭" + url + ".properties文件输入流过程中出错!", e);
                    }
                }
            }
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static <K, V> void batchUpdate(Map<K, V> map) {
        batchUpdate(DEFAULT_URL, map);
    }

    public static void saveProperty(String notExistsFileURL, String key,
                                    String value) {
        File file = new File(PropertiesUtils.class.getClassLoader()
            .getResource(".").getPath(), notExistsFileURL);
        PrintStream printStream = null;

        try {
            Properties properties = new Properties();
            properties.setProperty(key, value);

            printStream = new PrintStream(file);
            properties
                .store(printStream, "save key:" + key + ",value:" + value);
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + notExistsFileURL + ".properties配置文件时出错!", e);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + notExistsFileURL + ".properties配置文件时出错!", e);
            }
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static <K, V> void batchSave(String notExistsFileURL, Map<K, V> map) {
        File file = new File(PropertiesUtils.class.getClassLoader()
            .getResource(".").getPath(), notExistsFileURL);
        PrintStream printStream = null;

        try {
            Properties properties = new Properties();
            for (K k : map.keySet()) {
                properties.setProperty(k.toString(), map.get(k).toString());
            }

            printStream = new PrintStream(file);
            properties.store(printStream, "batch update!");
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + notExistsFileURL + ".properties配置文件时出错!", e);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("写入" + notExistsFileURL + ".properties配置文件时出错!", e);
            }
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static List<String> getResourceFile(String path) throws IOException {
        List<String> list=null;
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
            if(resources!=null&&resources.length>0){
                File f = resources[0].getFile();
                Reader reader = new FileReader(f);
                StringBuffer sb = new StringBuffer();
                char[] chars = new char[4096];
                int len = 0;
                while ((len = reader.read(chars)) != -1) {
                    sb.append(chars, 0, len);
                }
                if (!org.springframework.util.StringUtils.isEmpty(sb.toString())) {
                    String[] temps = sb.toString().split(System.lineSeparator());
                    list=new ArrayList<>();
                    for(String s:temps){
                        if(!s.trim().equals("")&&!s.substring(0,1).equals("#")&&!s.substring(0,2).equals("//")){
                            list.add(s);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Properties getProperty(String proName){
        InputStream in = null;
        Properties prop=new Properties();
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:"+proName);
            for (Resource resource : resources) {
                Properties p = new Properties();
                String path = resource.getFile().getPath();
                log.info("Loaded"+proName+"file: " + path.substring(path.indexOf("WEB-INF") + 16));
                in = resource.getInputStream();
                try{
                    p.load(in);
                    prop.putAll(p);
                }catch(Exception e){
                    log.error("Failed to load"+proName+" Properties...", e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to load "+proName+" Properties.", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    /**
     * 获取字符编码格式
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        StringBuilder encode = new StringBuilder("GB2312");
        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception) {
        }
        encode = encode.replace(0, encode.length(), "ISO-8859-1");
        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception1) {
        }
        encode = encode.replace(0, encode.length(), "UTF-8");
        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception2) {
        }
        encode = encode.replace(0, encode.length(), "GBK");
        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception3) {
        }
        encode = encode.replace(0, encode.length(), "GB18030");
        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception3) {
        }
        encode = encode.replace(0, encode.length(), "UTF-16");

        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception3) {
        }
        encode = encode.replace(0, encode.length(), "ASCII");

        try {
            if (str.equals(new String(str.getBytes(encode.toString()), encode.toString()))) {
                return encode.toString();
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * 转成UTF-8
     * @param str
     * @return
     */
    public static String encodeToGBK(String str) {
        String result = "";
        try {
            String encode = getEncoding(str);
            if (!encode.equals("GB2312") && !encode.equals("UTF-8")) {
                result = new String(str.getBytes(encode), "GBK");
            } else {
                return str;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
