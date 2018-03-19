// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control.auth;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.ListFW;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class ResolveFW extends Flyweight {
  public static final int FIELD_OFFSET_CORRELATION_ID = 0;

  private static final int FIELD_SIZE_CORRELATION_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_REALM = FIELD_OFFSET_CORRELATION_ID + FIELD_SIZE_CORRELATION_ID;

  public static final int FIELD_OFFSET_ROLES = 0;

  public static final int TYPE_ID = 0x00000011;

  private final StringFW realmRO = new StringFW();

  private final ListFW<StringFW> rolesRO = new ListFW<StringFW>(new StringFW());

  public long correlationId() {
    return buffer().getLong(offset() + FIELD_OFFSET_CORRELATION_ID);
  }

  public StringFW realm() {
    return realmRO;
  }

  public ListFW<StringFW> roles() {
    return rolesRO;
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public ResolveFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    realmRO.wrap(buffer, offset + FIELD_OFFSET_REALM, maxLimit);
    rolesRO.wrap(buffer, realmRO.limit() + FIELD_OFFSET_ROLES, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return rolesRO.limit();
  }

  @Override
  public String toString() {
    return String.format("RESOLVE [correlationId=%d, realm=%s, roles=%s]", correlationId(), realmRO.asString(), roles());
  }

  public static final class Builder extends Flyweight.Builder<ResolveFW> {
    private static final int INDEX_CORRELATION_ID = 0;

    private static final int INDEX_REALM = 1;

    private static final int INDEX_ROLES = 2;

    private static final int FIELD_COUNT = 3;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_ROLES);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "correlationId",
      "realm",
      "roles"
    };

    private final StringFW.Builder realmRW = new StringFW.Builder();

    private final ListFW.Builder<StringFW.Builder, StringFW> rolesRW = new ListFW.Builder<StringFW.Builder, StringFW>(new StringFW.Builder(), new StringFW());

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new ResolveFW());
    }

    public Builder correlationId(long value) {
      checkFieldNotSet(INDEX_CORRELATION_ID);
      checkFieldsSet(0, INDEX_CORRELATION_ID);
      int newLimit = limit() + FIELD_SIZE_CORRELATION_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_CORRELATION_ID);
      limit(newLimit);
      return this;
    }

    private StringFW.Builder realm() {
      checkFieldNotSet(INDEX_REALM);
      checkFieldsSet(0, INDEX_REALM);
      return realmRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder realm(String value) {
      StringFW.Builder realmRW = realm();
      realmRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_REALM);
      limit(realmRW.build().limit());
      return this;
    }

    public Builder realm(StringFW value) {
      StringFW.Builder realmRW = realm();
      realmRW.set(value);
      fieldsSet.set(INDEX_REALM);
      limit(realmRW.build().limit());
      return this;
    }

    public Builder realm(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder realmRW = realm();
      realmRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_REALM);
      limit(realmRW.build().limit());
      return this;
    }

    public Builder roles(Consumer<ListFW.Builder<StringFW.Builder, StringFW>> mutator) {
      checkFieldNotSet(INDEX_ROLES);
      checkFieldsSet(0, INDEX_ROLES);
      ListFW.Builder<StringFW.Builder, StringFW> rolesRW = this.rolesRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(rolesRW);
      limit(rolesRW.build().limit());
      fieldsSet.set(INDEX_ROLES);
      return this;
    }

    public Builder rolesItem(Consumer<StringFW.Builder> mutator) {
      checkFieldsSet(0, INDEX_ROLES);
      if (!fieldsSet.get(INDEX_ROLES)) {
        ListFW.Builder<StringFW.Builder, StringFW> rolesRW = this.rolesRW.wrap(buffer(), limit(), maxLimit());
      }
      rolesRW.item(mutator);
      limit(rolesRW.build().limit());
      fieldsSet.set(INDEX_ROLES);
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
    public ResolveFW build() {
      if (!fieldsSet.get(INDEX_ROLES)) {
        roles(b -> { });
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
