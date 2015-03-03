package com.lambo.smartpay.persistence.dao.impl;

import com.lambo.smartpay.persistence.dao.CurrencyDao;
import com.lambo.smartpay.persistence.entity.Currency;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for currency.
 * Created by swang on 2/26/2015.
 */
@Repository("currencyDao")
public class CurrencyDaoImpl extends LookupGenericDaoImpl<Currency, Long> implements CurrencyDao {
}
