/*
 * This file is generated by jOOQ.
 */
package se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos;


import java.io.Serializable;
import java.time.LocalDate;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BusStopOnLineEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer   busLineId;
    private Integer   busStopId;
    private String    direction;
    private Integer   stopOrder;
    private LocalDate existsFrom;

    public BusStopOnLineEntity() {}

    public BusStopOnLineEntity(BusStopOnLineEntity value) {
        this.busLineId = value.busLineId;
        this.busStopId = value.busStopId;
        this.direction = value.direction;
        this.stopOrder = value.stopOrder;
        this.existsFrom = value.existsFrom;
    }

    public BusStopOnLineEntity(
        Integer   busLineId,
        Integer   busStopId,
        String    direction,
        Integer   stopOrder,
        LocalDate existsFrom
    ) {
        this.busLineId = busLineId;
        this.busStopId = busStopId;
        this.direction = direction;
        this.stopOrder = stopOrder;
        this.existsFrom = existsFrom;
    }

    /**
     * Getter for <code>BUS_STOP_ON_LINE.BUS_LINE_ID</code>.
     */
    public Integer getBusLineId() {
        return this.busLineId;
    }

    /**
     * Setter for <code>BUS_STOP_ON_LINE.BUS_LINE_ID</code>.
     */
    public void setBusLineId(Integer busLineId) {
        this.busLineId = busLineId;
    }

    /**
     * Getter for <code>BUS_STOP_ON_LINE.BUS_STOP_ID</code>.
     */
    public Integer getBusStopId() {
        return this.busStopId;
    }

    /**
     * Setter for <code>BUS_STOP_ON_LINE.BUS_STOP_ID</code>.
     */
    public void setBusStopId(Integer busStopId) {
        this.busStopId = busStopId;
    }

    /**
     * Getter for <code>BUS_STOP_ON_LINE.DIRECTION</code>.
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Setter for <code>BUS_STOP_ON_LINE.DIRECTION</code>.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Getter for <code>BUS_STOP_ON_LINE.STOP_ORDER</code>.
     */
    public Integer getStopOrder() {
        return this.stopOrder;
    }

    /**
     * Setter for <code>BUS_STOP_ON_LINE.STOP_ORDER</code>.
     */
    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    /**
     * Getter for <code>BUS_STOP_ON_LINE.EXISTS_FROM</code>.
     */
    public LocalDate getExistsFrom() {
        return this.existsFrom;
    }

    /**
     * Setter for <code>BUS_STOP_ON_LINE.EXISTS_FROM</code>.
     */
    public void setExistsFrom(LocalDate existsFrom) {
        this.existsFrom = existsFrom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BusStopOnLineEntity other = (BusStopOnLineEntity) obj;
        if (busLineId == null) {
            if (other.busLineId != null)
                return false;
        }
        else if (!busLineId.equals(other.busLineId))
            return false;
        if (busStopId == null) {
            if (other.busStopId != null)
                return false;
        }
        else if (!busStopId.equals(other.busStopId))
            return false;
        if (direction == null) {
            if (other.direction != null)
                return false;
        }
        else if (!direction.equals(other.direction))
            return false;
        if (stopOrder == null) {
            if (other.stopOrder != null)
                return false;
        }
        else if (!stopOrder.equals(other.stopOrder))
            return false;
        if (existsFrom == null) {
            if (other.existsFrom != null)
                return false;
        }
        else if (!existsFrom.equals(other.existsFrom))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.busLineId == null) ? 0 : this.busLineId.hashCode());
        result = prime * result + ((this.busStopId == null) ? 0 : this.busStopId.hashCode());
        result = prime * result + ((this.direction == null) ? 0 : this.direction.hashCode());
        result = prime * result + ((this.stopOrder == null) ? 0 : this.stopOrder.hashCode());
        result = prime * result + ((this.existsFrom == null) ? 0 : this.existsFrom.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BusStopOnLineEntity (");

        sb.append(busLineId);
        sb.append(", ").append(busStopId);
        sb.append(", ").append(direction);
        sb.append(", ").append(stopOrder);
        sb.append(", ").append(existsFrom);

        sb.append(")");
        return sb.toString();
    }
}