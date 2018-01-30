package com.dream.pay.secure.storage.service;

import com.dream.pay.secure.storage.access.facade.Decryption;
import com.dream.pay.secure.storage.access.facade.Encryption;
import com.dream.pay.secure.storage.service.exception.DecryptException;
import com.dream.pay.secure.storage.service.exception.EncryptException;

/**
 * @Author mengzhenbin
 * @Since 2018/1/29
 */
public interface SecureStorageService {
    /**
     * 标准解密
     *
     * @param decryption
     * @return
     * @throws DecryptException
     */
    Encryption standardDecryption(Decryption decryption) throws DecryptException;

    /**
     * 标准加密
     *
     * @param encryption
     * @return
     * @throws EncryptException
     */
    Decryption standardEncryption(Encryption encryption) throws EncryptException;
}
