package com.lambo.smartpay.service.impl;

import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.persistence.dao.EncryptionDao;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.service.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swang on 3/24/2015.
 */
@Service("encryptionService")
public class EncryptionServiceImpl implements EncryptionService {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionServiceImpl.class);
    @Autowired
    private EncryptionDao encryptionDao;

    @Override
    public Encryption create(Encryption encryption) throws MissingRequiredFieldException,
            NotUniqueException {
        return encryptionDao.create(encryption);
    }

    @Override
    public Encryption get(Long id) throws NoSuchEntityException {
        return encryptionDao.get(id);
    }

    @Override
    public Encryption update(Encryption encryption) throws MissingRequiredFieldException,
            NotUniqueException {
        return encryptionDao.update(encryption);
    }

    @Override
    public Encryption delete(Long id) throws NoSuchEntityException {
        Encryption encryption = get(id);
        encryptionDao.delete(id);
        return encryption;
    }

    @Override
    public List<Encryption> getAll() {
        return encryptionDao.getAll();
    }

    @Override
    public Long countAll() {
        return encryptionDao.countAll();
    }
}
