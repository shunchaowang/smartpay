package com.lambo.smartpay.core.persistence.dao.impl;

import com.lambo.smartpay.core.persistence.dao.CurrencyDao;
import com.lambo.smartpay.core.persistence.entity.Currency;
import org.springframework.stereotype.Repository;

/**
 * Dao impl for currency.
 * Created by swang on 2/26/2015.
 */
@Repository("currencyDao")
public class CurrencyDaoImpl extends GenericLookupDaoImpl<Currency, Long> implements CurrencyDao {
}
