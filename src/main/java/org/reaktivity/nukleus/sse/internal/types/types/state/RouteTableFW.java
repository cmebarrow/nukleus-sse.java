// TODO: license
package org.reaktivity.nukleus.sse.internal.types.state;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.ListFW;

public final class RouteTableFW extends Flyweight {
  public static final int FIELD_OFFSET_WRITE_LOCK_ACQUIRES = 0;

  private static final int FIELD_SIZE_WRITE_LOCK_ACQUIRES = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_WRITE_LOCK_RELEASES = FIELD_OFFSET_WRITE_LOCK_ACQUIRES + FIELD_SIZE_WRITE_LOCK_ACQUIRES;

  private static final int FIELD_SIZE_WRITE_LOCK_RELEASES = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_ROUTE_ENTRIES = FIELD_OFFSET_WRITE_LOCK_RELEASES + FIELD_SIZE_WRITE_LOCK_RELEASES;

  private final ListFW<RouteEntryFW> routeEntriesRO = new ListFW<RouteEntryFW>(new RouteEntryFW());

  public int writeLockAcquires() {
    return buffer().getInt(offset() + FIELD_OFFSET_WRITE_LOCK_ACQUIRES);
  }

  public int writeLockReleases() {
    return buffer().getInt(offset() + FIELD_OFFSET_WRITE_LOCK_RELEASES);
  }

  public ListFW<RouteEntryFW> routeEntries() {
    return routeEntriesRO;
  }

  @Override
  public RouteTableFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    routeEntriesRO.wrap(buffer, offset + FIELD_OFFSET_ROUTE_ENTRIES, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return routeEntriesRO.limit();
  }

  @Override
  public String toString() {
    return String.format("ROUTE_TABLE [writeLockAcquires=%d, writeLockReleases=%d, routeEntries=%s]", writeLockAcquires(), writeLockReleases(), routeEntries());
  }

  public static final class Builder extends Flyweight.Builder<RouteTableFW> {
    private static final int INDEX_WRITE_LOCK_ACQUIRES = 0;

    private static final int INDEX_WRITE_LOCK_RELEASES = 1;

    private static final int INDEX_ROUTE_ENTRIES = 2;

    private static final int FIELD_COUNT = 3;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_ROUTE_ENTRIES);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "writeLockAcquires",
      "writeLockReleases",
      "routeEntries"
    };

    private final ListFW.Builder<RouteEntryFW.Builder, RouteEntryFW> routeEntriesRW = new ListFW.Builder<RouteEntryFW.Builder, RouteEntryFW>(new RouteEntryFW.Builder(), new RouteEntryFW());

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new RouteTableFW());
    }

    public Builder writeLockAcquires(int value) {
      checkFieldNotSet(INDEX_WRITE_LOCK_ACQUIRES);
      checkFieldsSet(0, INDEX_WRITE_LOCK_ACQUIRES);
      int newLimit = limit() + FIELD_SIZE_WRITE_LOCK_ACQUIRES;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      fieldsSet.set(INDEX_WRITE_LOCK_ACQUIRES);
      limit(newLimit);
      return this;
    }

    public Builder writeLockReleases(int value) {
      checkFieldNotSet(INDEX_WRITE_LOCK_RELEASES);
      checkFieldsSet(0, INDEX_WRITE_LOCK_RELEASES);
      int newLimit = limit() + FIELD_SIZE_WRITE_LOCK_RELEASES;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      fieldsSet.set(INDEX_WRITE_LOCK_RELEASES);
      limit(newLimit);
      return this;
    }

    public Builder routeEntries(Consumer<ListFW.Builder<RouteEntryFW.Builder, RouteEntryFW>> mutator) {
      checkFieldNotSet(INDEX_ROUTE_ENTRIES);
      checkFieldsSet(0, INDEX_ROUTE_ENTRIES);
      ListFW.Builder<RouteEntryFW.Builder, RouteEntryFW> routeEntriesRW = this.routeEntriesRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(routeEntriesRW);
      limit(routeEntriesRW.build().limit());
      fieldsSet.set(INDEX_ROUTE_ENTRIES);
      return this;
    }

    public Builder routeEntriesItem(Consumer<RouteEntryFW.Builder> mutator) {
      checkFieldsSet(0, INDEX_ROUTE_ENTRIES);
      if (!fieldsSet.get(INDEX_ROUTE_ENTRIES)) {
        ListFW.Builder<RouteEntryFW.Builder, RouteEntryFW> routeEntriesRW = this.routeEntriesRW.wrap(buffer(), limit(), maxLimit());
      }
      routeEntriesRW.item(mutator);
      limit(routeEntriesRW.build().limit());
      fieldsSet.set(INDEX_ROUTE_ENTRIES);
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
    public RouteTableFW build() {
      if (!fieldsSet.get(INDEX_ROUTE_ENTRIES)) {
        routeEntries(b -> { });
      }
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
