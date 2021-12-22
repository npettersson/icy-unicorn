/*
 * This file is generated by jOOQ.
 */
package se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos;


import java.time.LocalDate;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.BusLine;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.records.BusLineRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BusLineDao extends DAOImpl<BusLineRecord, BusLineEntity, Integer> {

    /**
     * Create a new BusLineDao without any configuration
     */
    public BusLineDao() {
        super(BusLine.BUS_LINE, BusLineEntity.class);
    }

    /**
     * Create a new BusLineDao with an attached configuration
     */
    public BusLineDao(Configuration configuration) {
        super(BusLine.BUS_LINE, BusLineEntity.class, configuration);
    }

    @Override
    public Integer getId(BusLineEntity object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<BusLineEntity> fetchRangeOfId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(BusLine.BUS_LINE.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<BusLineEntity> fetchById(Integer... values) {
        return fetch(BusLine.BUS_LINE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public BusLineEntity fetchOneById(Integer value) {
        return fetchOne(BusLine.BUS_LINE.ID, value);
    }

    /**
     * Fetch records that have <code>DESIGNATION BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<BusLineEntity> fetchRangeOfDesignation(String lowerInclusive, String upperInclusive) {
        return fetchRange(BusLine.BUS_LINE.DESIGNATION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>DESIGNATION IN (values)</code>
     */
    public List<BusLineEntity> fetchByDesignation(String... values) {
        return fetch(BusLine.BUS_LINE.DESIGNATION, values);
    }

    /**
     * Fetch records that have <code>EXISTS_FROM BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<BusLineEntity> fetchRangeOfExistsFrom(LocalDate lowerInclusive, LocalDate upperInclusive) {
        return fetchRange(BusLine.BUS_LINE.EXISTS_FROM, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>EXISTS_FROM IN (values)</code>
     */
    public List<BusLineEntity> fetchByExistsFrom(LocalDate... values) {
        return fetch(BusLine.BUS_LINE.EXISTS_FROM, values);
    }
}
