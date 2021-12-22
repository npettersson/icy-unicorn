/*
 * This file is generated by jOOQ.
 */
package se.npet.trafiklab.buslines.adapters.outbound.db.jooq;


import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.BusLine;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.BusStop;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.BusStopOnLine;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>BUS_LINE</code>.
     */
    public final BusLine BUS_LINE = BusLine.BUS_LINE;

    /**
     * The table <code>BUS_STOP</code>.
     */
    public final BusStop BUS_STOP = BusStop.BUS_STOP;

    /**
     * The table <code>BUS_STOP_ON_LINE</code>.
     */
    public final BusStopOnLine BUS_STOP_ON_LINE = BusStopOnLine.BUS_STOP_ON_LINE;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            BusLine.BUS_LINE,
            BusStop.BUS_STOP,
            BusStopOnLine.BUS_STOP_ON_LINE);
    }
}