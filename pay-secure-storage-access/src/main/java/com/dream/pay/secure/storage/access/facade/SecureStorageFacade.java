package com.dream.pay.secure.storage.access.facade;

import com.dream.pay.bean.DataResult;

/**
 * @Author mengzhenbin
 * @Since 2018/1/29
 */
public interface SecureStorageFacade {
    /**
     * 通用加密算法
     *
     * @return
     */
    public DataResult<Decryption> standardEncryption(Encryption encryption);

    /**
     * 通用解密算法
     *
     * @return
     */
    public DataResult<Encryption> standardDecryption(Decryption decryption);

}
