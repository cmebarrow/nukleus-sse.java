// TODO: license
package org.reaktivity.nukleus.sse.internal.types.state;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.OctetsFW;

public final class RouteEntryFW extends Flyweight {
  public static final int FIELD_OFFSET_ROUTE_SIZE = 0;

  private static final int FIELD_SIZE_ROUTE_SIZE = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_ROUTE = FIELD_OFFSET_ROUTE_SIZE + FIELD_SIZE_ROUTE_SIZE;

  private final OctetsFW routeRO = new OctetsFW();

  public long routeSize() {
    return (long)(buffer().getInt(offset() + FIELD_OFFSET_ROUTE_SIZE) & 0xFFFF_FFFFL);
  }

  public OctetsFW route() {
    return routeRO;
  }

  @Override
  public RouteEntryFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    routeRO.wrap(buffer, offset + FIELD_OFFSET_ROUTE, offset + FIELD_OFFSET_ROUTE + (int) routeSize());
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return routeRO.limit();
  }

  @Override
  public String toString() {
    return String.format("ROUTE_ENTRY [routeSize=%d, route=%s]", routeSize(), route());
  }

  public static final class Builder extends Flyweight.Builder<RouteEntryFW> {
    private static final int INDEX_ROUTE_SIZE = 0;

    private static final long DEFAULT_ROUTE_SIZE = 0;

    private static final int INDEX_ROUTE = 1;

    private static final int FIELD_COUNT = 2;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_ROUTE_SIZE);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "routeSize",
      "route"
    };

    private int dynamicOffsetRouteSize;

    private final OctetsFW.Builder routeRW = new OctetsFW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new RouteEntryFW());
    }

    private Builder routeSize(long value) {
      if (value < 0) {
        throw new IllegalArgumentException(String.format("Value %d too low for field \"routeSize\"", value));
      }
      if (value > 0xFFFFFFFFL) {
        throw new IllegalArgumentException(String.format("Value %d too high for field \"routeSize\"", value));
      }
      checkFieldsSet(0, INDEX_ROUTE_SIZE);
      int newLimit = limit() + FIELD_SIZE_ROUTE_SIZE;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), (int)(value & 0xFFFF_FFFFL));
      dynamicOffsetRouteSize = limit();
      fieldsSet.set(INDEX_ROUTE_SIZE);
      limit(newLimit);
      return this;
    }

    private OctetsFW.Builder route() {
      checkFieldNotSet(INDEX_ROUTE);
      if (!fieldsSet.get(INDEX_ROUTE_SIZE)) {
        routeSize(DEFAULT_ROUTE_SIZE);
      }
      checkFieldsSet(0, INDEX_ROUTE);
      return routeRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder route(OctetsFW value) {
      int size$;
      int newLimit;
      OctetsFW.Builder routeRW = route();
      if (value == null) {
        throw new IllegalArgumentException("value cannot be null for field \"route\" that does not default to null");
      }
      newLimit = routeRW.build().limit();
      size$ = newLimit - limit();
      limit(dynamicOffsetRouteSize);
      routeSize(size$);
      limit(newLimit);
      fieldsSet.set(INDEX_ROUTE);
      return this;
    }

    public Builder route(Consumer<OctetsFW.Builder> mutator) {
      OctetsFW.Builder routeRW = route();
      mutator.accept(routeRW);
      int newLimit = routeRW.build().limit();
      int size$ = newLimit - limit();
      limit(dynamicOffsetRouteSize);
      routeSize(size$);
      limit(newLimit);
      fieldsSet.set(INDEX_ROUTE);
      return this;
    }

    public Builder route(DirectBuffer buffer, int offset, int length) {
      OctetsFW.Builder routeRW = route();
      routeRW.set(buffer, offset, length);
      int newLimit = routeRW.build().limit();
      int size$ = newLimit - limit();
      limit(dynamicOffsetRouteSize);
      routeSize(size$);
      limit(newLimit);
      fieldsSet.set(INDEX_ROUTE);
      return this;
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      fieldsSet.clear();
      super.wrap(buffer, offset, maxLimit);
      limit(offset);
      return this;
    }

    @Override
    public RouteEntryFW build() {
      checkFieldsSet(0, FIELD_COUNT);
      fieldsSet.clear();
      return super.build();
    }

    private void checkFieldNotSet(int index) {
      if (fieldsSet.get(index)) {
        throw new IllegalStateException(String.format("Field \"%s\" has already been set", FIELD_NAMES[index]));
      }
    }

    private void checkFieldsSet(int fromIndex, int toIndex) {
      int fieldNotSet = fromIndex - 1;
      do {
        fieldNotSet = fieldsSet.nextClearBit(fieldNotSet + 1);
      } while (fieldNotSet < toIndex && FIELDS_WITH_DEFAULTS.get(fieldNotSet));
      if (fieldNotSet < toIndex) {
        throw new IllegalStateException(String.format("Required field \"%s\" is not set", FIELD_NAMES[fieldNotSet]));
      }
    }
  }
}
