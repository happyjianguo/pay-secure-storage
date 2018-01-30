package com.dream.pay.secure.storage.service.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author mengzhenbin
 * @Since 2018/1/30
 * 工作密钥生成工具类
 */
public class GeneratorWorkKey {
    // 小写字母数组
    private static final String[] RANDOM_LOWER_CASE_STRING_ARRAY = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };
    // 大写字母数组
    private static final String[] RANDOM_UPER_CASE_STRING_ARRAY = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    // 数字数组
    private static final String[] RANDOM_DIGIT_STRING_ARRAY = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };
    // 特殊字符数组
    private static final String[] RANDOM_SPECIAL_STRING_ARRAY = {
            "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_",
            "+", "=", "{", "}", "[", "]", "|", "?"
    };
    // 系统默认产生工作密钥长度
    private static final int DEFAULT_KEY_LENGTH = 16;

    /**
     * 生加密索引
     *
     * @return 返回加密索引
     */
    public static String generateStoreIndex() {
        String uuid = generateUUID();
        String nanoTime = System.nanoTime() + "";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(9));
        }
        String message = uuid + nanoTime + sb.toString();
        String index = DigestUtils.md5Hex(message);
        return index;
    }

    public static void main(String[] args) {
        String index = GeneratorWorkKey.generateStoreIndex();
        System.out.println(index);
    }

    /**
     * 生成 uuid 去掉  "-" 连接符
     *
     * @return
     */
    private static String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        // 去掉 "-" 符号
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 生成默认的工作密钥，默认密钥长度是16
     *
     * @return 返回工作密钥
     */
    public static String generateWorkKey() {
        return generateWorkKey(16);
    }

    /**
     * 生成工作密钥
     * length 工作密钥长度
     *
     * @return 返回工作密钥
     */
    public static String generateWorkKey(int length) {
        if (length < 16) {
            length = DEFAULT_KEY_LENGTH;
        }
        String workKey = "";
        for (int i = 0; i < 10; i++) {
            workKey = randomKey(length);
            if (judgeWorkKey(workKey)) {
                break;
            }
        }
        return workKey;
    }

    /**
     * 判断工作密钥是否满足要求
     *
     * @param workKey
     * @return
     */
    private static boolean judgeWorkKey(String workKey) {
        Matcher charMatcher = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[~!@#$%^&*()-_+={}\\[\\]|\\?]).*", Pattern.CASE_INSENSITIVE).matcher(workKey);
        if (charMatcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 生成工作密钥核心方法
     *
     * @param length 可以指定工作密钥长度
     * @return
     */
    private static String randomKey(int length) {
        if (length < 16) {
            length = DEFAULT_KEY_LENGTH;
        }
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        int arrayIndex = 0;
        int index = 0;
        for (int i = 0; i < length; i++) {
            arrayIndex = random.nextInt(4);
            if (arrayIndex == 0) {
                index = random.nextInt(RANDOM_LOWER_CASE_STRING_ARRAY.length);
                key.append(RANDOM_LOWER_CASE_STRING_ARRAY[index]);
            } else if (arrayIndex == 1) {
                index = random.nextInt(RANDOM_UPER_CASE_STRING_ARRAY.length);
                key.append(RANDOM_UPER_CASE_STRING_ARRAY[index]);
            } else if (arrayIndex == 2) {
                index = random.nextInt(RANDOM_DIGIT_STRING_ARRAY.length);
                key.append(RANDOM_DIGIT_STRING_ARRAY[index]);
            } else if (arrayIndex == 3) {
                index = random.nextInt(RANDOM_SPECIAL_STRING_ARRAY.length);
                key.append(RANDOM_SPECIAL_STRING_ARRAY[index]);
            }
        }
        return key.toString();
    }
}
