package com.dream.pay.secure.storage.dao;

import com.dream.pay.secure.storage.model.EncryptData;
import org.apache.ibatis.annotations.Param;

public interface EncryptDataDao {
    int insert(EncryptData record);

    EncryptData queryByEncryptIndex(@Param("encryptIndex") String encryptIndex);

}