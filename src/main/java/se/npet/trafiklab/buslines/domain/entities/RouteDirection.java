package se.npet.trafiklab.buslines.domain.entities;

public enum RouteDirection {
  A("0"), B("1");
  private final String directionCode;

  RouteDirection(String directionCode) {
    this.directionCode = directionCode;
  }

  public String getDirectionCode() {
    return directionCode;
  }
}
