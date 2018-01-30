package com.dream.pay.secure.storage.access.impl;

import com.dream.pay.bean.DataResult;
import com.dream.pay.secure.storage.access.facade.Decryption;
import com.dream.pay.secure.storage.access.facade.Encryption;
import com.dream.pay.secure.storage.access.facade.SecureStorageFacade;
import com.dream.pay.secure.storage.service.SecureStorageService;
import com.dream.pay.secure.storage.service.exception.ErrorEnum;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @Author mengzhenbin
 * @Since 2018/1/29
 */
@Path("/standard")
@Slf4j
public class SecureStorageFacadeImpl implements SecureStorageFacade {

    @Resource
    private SecureStorageService secureStorageService;

    @Path("encryption")
    @POST
    public DataResult<Decryption> standardEncryption(Encryption encryption) {
        log.info("安全存储,[加密]开始-[{}]", encryption);

        Decryption decryption = secureStorageService.standardEncryption(encryption);

        log.info("安全存储,[加密]结束-[{}]", decryption);

        DataResult result = new DataResult();

        result.setSuccess(Boolean.TRUE);
        result.setCode(ErrorEnum.SUCCESS.getCode());
        result.setMessage(ErrorEnum.SUCCESS.getDescription());
        result.setData(decryption);

        return result;
    }

    @Path("decryption")
    @POST
    public DataResult<Encryption> standardDecryption(Decryption decryption) {
        log.info("安全存储,[解密]开始-[{}]", decryption);

        Encryption encryption = secureStorageService.standardDecryption(decryption);

        log.info("安全存储,[解密]结束-[{}]", encryption);

        DataResult result = new DataResult();
        result.setSuccess(Boolean.TRUE);
        result.setCode(ErrorEnum.SUCCESS.getCode());
        result.setMessage(ErrorEnum.SUCCESS.getDescription());
        result.setData(encryption);
        return result;
    }
}
