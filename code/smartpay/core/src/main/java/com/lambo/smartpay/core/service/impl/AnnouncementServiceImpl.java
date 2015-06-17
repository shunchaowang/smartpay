package com.lambo.smartpay.core.service.impl;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.dao.AnnouncementDao;
import com.lambo.smartpay.core.persistence.entity.Announcement;
import com.lambo.smartpay.core.service.AnnouncementService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chensf on 5/9/2015.
 */
@Service("announcementService")
public class AnnouncementServiceImpl extends GenericDateQueryServiceImpl<Announcement, Long>
        implements AnnouncementService {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    private AnnouncementDao announcementDao;

    /**
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     *
     * @param announcement  contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     *                         it means no criteria on exact equals if t is null.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return number of the T matching criteria.
     */
    @Override
    public Long countByCriteria(Announcement announcement, String search, Date createdTimeStart,
                                Date createdTimeEnd) {
        return announcementDao.countByCriteria(announcement, search, createdTimeStart, createdTimeEnd);
    }

    /**
     * This one will be the abstract function acting as a template.
     * Dynamic search like grails findBy...
     * We create a dynamic criteria, like grails createCriteria() {}.
     * DataTables dynamic ordering is also supported.
     * To support DataTables pagination we have the start for the offset of the search, and
     * length for the max results we want to return.
     *
     * @param announcement  contains all criteria for equals, like name equals xx and active
     *                         equals
     *                         true, etc.
     *                         it means no criteria on exact equals if t is null.
     * @param search           instance wildcard search keyword, like name likes %xx%, etc.
     *                         it means no criteria with wildcard search if search is null.
     * @param start            first position of the result.
     * @param length           max record of the result.
     * @param order            order by field, default is id.
     * @param orderDir         order direction on the order field. default is DESC.
     * @param createdTimeStart start of date range.
     * @param createdTimeEnd   end of date range.
     * @return
     */
    @Override
    public List<Announcement> findByCriteria(Announcement announcement, String search, Integer start, Integer length,
                                      String order, ResourceProperties.JpaOrderDir orderDir,
                                      Date createdTimeStart, Date createdTimeEnd) {
        return announcementDao.findByCriteria(announcement, search, start, length,
                order, orderDir, createdTimeStart, createdTimeEnd);
    }

    @Override
    @Transactional
    public Announcement create(Announcement announcement) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (announcement == null) {
            throw new MissingRequiredFieldException("Announcement is null.");
        }
        if (announcement.getTitle() == null) {
            throw new MissingRequiredFieldException("Announcement title is null.");
        }
        if (announcement.getContent() == null) {
            throw new MissingRequiredFieldException("Announcement content is null.");
        }
        announcement.setCreatedTime(date);
        announcement.setActive(true);
        return announcementDao.create(announcement);
    }

    @Override
    public Announcement get(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Announcement announcement = announcementDao.get(id);
        if (announcement == null) {
            throw new NoSuchEntityException("No announcement " + id);
        }
        return announcement;
    }

    @Override
    @Transactional
    public Announcement update(Announcement announcement) throws MissingRequiredFieldException, NotUniqueException {

        Date date = Calendar.getInstance().getTime();
        if (announcement == null) {
            throw new MissingRequiredFieldException("Announcement is null.");
        }
        if (announcement.getId() == null) {
            throw new MissingRequiredFieldException("Announcement id is null.");
        }
        if (announcement.getTitle() == null) {
            throw new MissingRequiredFieldException("Announcement title is null.");
        }
        if (announcement.getContent() == null) {
            throw new MissingRequiredFieldException("Announcement content is null.");
        }
        return announcementDao.update(announcement);
    }

    @Override
    @Transactional
    public Announcement delete(Long id) throws NoSuchEntityException {
        if (id == null) {
            throw new NoSuchEntityException("Id is null.");
        }
        Announcement announcement = announcementDao.get(id);
        if (announcement == null) {
            throw new NoSuchEntityException("No announcement " + id);
        }
        announcementDao.delete(id);
        return announcement;
    }

    @Override
    public List<Announcement> getAll() {
        return announcementDao.getAll();
    }

    @Override
    public Long countAll() {
        return announcementDao.countAll();
    }

}
