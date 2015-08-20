package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CurrencyExchangeDao;
import com.lambo.smartpay.core.persistence.entity.CurrencyExchange;
import org.springframework.stereotype.Repository;

/**
 * Created by swang on 3/3/2015.
 */
@Repository("currencyExchangeDao")
public class CurrencyExchangeDaoImpl extends GenericDaoImpl<CurrencyExchange, Long>
        implements CurrencyExchangeDao {
}
