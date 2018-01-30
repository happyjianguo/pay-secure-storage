package com.dream.pay.secure.storage.service;

import com.dream.pay.secure.storage.access.facade.Decryption;
import com.dream.pay.secure.storage.access.facade.Encryption;
import com.dream.pay.secure.storage.dao.EncryptDataDao;
import com.dream.pay.secure.storage.model.EncryptData;
import com.dream.pay.secure.storage.service.exception.DecryptException;
import com.dream.pay.secure.storage.service.exception.EncryptException;
import com.dream.pay.secure.storage.service.exception.ErrorEnum;
import com.dream.pay.secure.storage.service.util.GeneratorWorkKey;
import com.dream.pay.utils.DESUtil;
import com.dream.pay.utils.MD5Util;
import com.dream.pay.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author mengzhenbin
 * @Since 2018/1/29
 */
@Component
@Slf4j
public class SecureStorageServiceImpl implements SecureStorageService {


    public static Map<String, Object> keys = new HashMap<String, Object>();

    static {
        keys = RSAUtil.getKeys();
    }

    @Resource
    private EncryptDataDao encryptDataDao;

    public Encryption standardDecryption(Decryption decryption) throws DecryptException {
        String origData = null;
        try {
            //按照md5Index检索加密数据
            EncryptData encryptData = encryptDataDao.queryByEncryptIndex(decryption.getEncryptIndex());
            if (null == encryptData || StringUtils.isBlank(encryptData.getEncryptData())) {//加密记录不存在
                log.info("加密记录不存在,加密索引=[{}]-加密数据=[{}]", decryption.getEncryptIndex(), encryptData);
            } else {
                origData = decryptEncryptData(encryptData);
                log.info("加密记录解密完成，加密索引=[{}]-解密原始数据=[{}]", decryption.getEncryptIndex(), origData);
            }
        } catch (Exception e) {
            log.error("数据安全存储-[解密]出现异常", e);
            throw new DecryptException(ErrorEnum.SYSTEM_ERROR);
        }
        Encryption encryption = new Encryption();
        encryption.setOrigData(origData);
        return encryption;
    }

    public Decryption standardEncryption(Encryption encryption) throws EncryptException {
        String origData = encryption.getOrigData();
        EncryptData encryptData;
        try {
            String md5Index = MD5Util.md5Base64(origData);
            //按照md5Index检索是否已经存在加密数据
            encryptData = encryptDataDao.queryByEncryptIndex(md5Index);
            if (null != encryptData && StringUtils.isNotBlank(encryptData.getEncryptData())) {//加密记录已经存在。直接返回
                log.info("加密记录已经存在,加密索引=[{}]-加密数据=[{}]", md5Index, encryptData);
            } else {//加密记录不存在，加密保存后返回
                encryptData = buildStandardEncryptData(origData, md5Index);
                encryptDataDao.insert(encryptData);
                log.info("加密记录不存在,加密数据已落库,加密索引=[{}]-加密数据=[{}]", md5Index, encryptData);
            }
        } catch (Exception e) {
            log.error("数据安全存储-[加密]出现异常", e);
            throw new EncryptException(ErrorEnum.SYSTEM_ERROR);
        }
        Decryption decryption = new Decryption();
        decryption.setEncryptIndex(encryptData.getEncryptIndex());
        return decryption;
    }

    /**
     * 组装EncryptData数据
     *
     * @param origData
     * @param md5Index
     * @return EncryptData
     * @throws EncryptException
     */
    private EncryptData buildStandardEncryptData(String origData, String md5Index) throws Exception {
        //1.1 获取加密密钥
        String dataKey = GeneratorWorkKey.generateWorkKey();

        //1.2 用加密密钥对数据进行DES加密
        String encryptionData = DESUtil.encryptModeBase64(origData.getBytes(), dataKey);

        //2.1 获取RSA工作密钥对-加密公钥
        RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");

        //2.2 用密钥对的公钥对工作密钥进行RSA加密
        String workKeyEncrypt = RSAUtil.encryptByPublicKey(dataKey, publicKey);

        //3 生成加密后数据
        return EncryptData.builder()
                .encryptData(encryptionData)
                .dataKeyEncrypt(workKeyEncrypt)
                .encryptIndex(md5Index)
                .createTime(new Date())
                .updateTime(new Date()).build();
    }

    /**
     * 解密EncryptData数据
     *
     * @param encryptData
     * @throws String
     */
    private String decryptEncryptData(EncryptData encryptData) throws Exception {
        //1.1 获取RSA工作密钥对-解密私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keys.get("private");

        //1.2 用密钥对的私钥对工作密钥进行RSA解密
        String dataKey = RSAUtil.decryptByPrivateKey(encryptData.getDataKeyEncrypt(), privateKey);
        //2.1 用加密密钥对数据进行DES解密
        String origData = DESUtil.decryptModeBase64(encryptData.getEncryptData(), dataKey);

        //3 返回原始数据
        return origData;
    }

}