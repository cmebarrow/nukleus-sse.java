// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.OctetsFW;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class RouteFW extends Flyweight {
  public static final int FIELD_OFFSET_CORRELATION_ID = 0;

  private static final int FIELD_SIZE_CORRELATION_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_ROLE = FIELD_OFFSET_CORRELATION_ID + FIELD_SIZE_CORRELATION_ID;

  public static final int FIELD_OFFSET_SOURCE = 0;

  public static final int FIELD_OFFSET_SOURCE_REF = 0;

  private static final int FIELD_SIZE_SOURCE_REF = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TARGET = FIELD_OFFSET_SOURCE_REF + FIELD_SIZE_SOURCE_REF;

  public static final int FIELD_OFFSET_TARGET_REF = 0;

  private static final int FIELD_SIZE_TARGET_REF = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_AUTHORIZATION = FIELD_OFFSET_TARGET_REF + FIELD_SIZE_TARGET_REF;

  private static final int FIELD_SIZE_AUTHORIZATION = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_EXTENSION = FIELD_OFFSET_AUTHORIZATION + FIELD_SIZE_AUTHORIZATION;

  public static final int TYPE_ID = 0x00000001;

  private final RoleFW roleRO = new RoleFW();

  private final StringFW sourceRO = new StringFW();

  private final StringFW targetRO = new StringFW();

  private final OctetsFW extensionRO = new OctetsFW();

  public long correlationId() {
    return buffer().getLong(offset() + FIELD_OFFSET_CORRELATION_ID);
  }

  public RoleFW role() {
    return roleRO;
  }

  public StringFW source() {
    return sourceRO;
  }

  public long sourceRef() {
    return buffer().getLong(sourceRO.limit() + FIELD_OFFSET_SOURCE_REF);
  }

  public StringFW target() {
    return targetRO;
  }

  public long targetRef() {
    return buffer().getLong(targetRO.limit() + FIELD_OFFSET_TARGET_REF);
  }

  public long authorization() {
    return buffer().getLong(targetRO.limit() + FIELD_OFFSET_AUTHORIZATION);
  }

  public OctetsFW extension() {
    return extensionRO;
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public RouteFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    roleRO.wrap(buffer, offset + FIELD_OFFSET_ROLE, maxLimit);
    sourceRO.wrap(buffer, roleRO.limit() + FIELD_OFFSET_SOURCE, maxLimit);
    targetRO.wrap(buffer, sourceRO.limit() + FIELD_OFFSET_TARGET, maxLimit);
    extensionRO.wrap(buffer, targetRO.limit() + FIELD_OFFSET_EXTENSION, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return extensionRO.limit();
  }

  @Override
  public String toString() {
    return String.format("ROUTE [correlationId=%d, role=%s, source=%s, sourceRef=%d, target=%s, targetRef=%d, authorization=%d, extension=%s]", correlationId(), role(), sourceRO.asString(), sourceRef(), targetRO.asString(), targetRef(), authorization(), extension());
  }

  public static final class Builder extends Flyweight.Builder<RouteFW> {
    private static final int INDEX_CORRELATION_ID = 0;

    private static final int INDEX_ROLE = 1;

    private static final int INDEX_SOURCE = 2;

    private static final int INDEX_SOURCE_REF = 3;

    private static final int INDEX_TARGET = 4;

    private static final int INDEX_TARGET_REF = 5;

    private static final int INDEX_AUTHORIZATION = 6;

    private static final long DEFAULT_AUTHORIZATION = 0;

    private static final int INDEX_EXTENSION = 7;

    private static final int FIELD_COUNT = 8;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_ROLE);
        set(INDEX_AUTHORIZATION);
        set(INDEX_EXTENSION);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "correlationId",
      "role",
      "source",
      "sourceRef",
      "target",
      "targetRef",
      "authorization",
      "extension"
    };

    private final RoleFW.Builder roleRW = new RoleFW.Builder();

    private final StringFW.Builder sourceRW = new StringFW.Builder();

    private final StringFW.Builder targetRW = new StringFW.Builder();

    private final OctetsFW.Builder extensionRW = new OctetsFW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new RouteFW());
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

    public Builder role(Consumer<RoleFW.Builder> mutator) {
      checkFieldNotSet(INDEX_ROLE);
      checkFieldsSet(0, INDEX_ROLE);
      RoleFW.Builder roleRW = this.roleRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(roleRW);
      limit(roleRW.build().limit());
      fieldsSet.set(INDEX_ROLE);
      return this;
    }

    private StringFW.Builder source() {
      checkFieldNotSet(INDEX_SOURCE);
      if (!fieldsSet.get(INDEX_ROLE)) {
        role(b -> { });
      }
      checkFieldsSet(0, INDEX_SOURCE);
      return sourceRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder source(String value) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_SOURCE);
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder source(StringFW value) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(value);
      fieldsSet.set(INDEX_SOURCE);
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder source(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_SOURCE);
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder sourceRef(long value) {
      checkFieldNotSet(INDEX_SOURCE_REF);
      checkFieldsSet(0, INDEX_SOURCE_REF);
      int newLimit = limit() + FIELD_SIZE_SOURCE_REF;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_SOURCE_REF);
      limit(newLimit);
      return this;
    }

    private StringFW.Builder target() {
      checkFieldNotSet(INDEX_TARGET);
      checkFieldsSet(0, INDEX_TARGET);
      return targetRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder target(String value) {
      StringFW.Builder targetRW = target();
      targetRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_TARGET);
      limit(targetRW.build().limit());
      return this;
    }

    public Builder target(StringFW value) {
      StringFW.Builder targetRW = target();
      targetRW.set(value);
      fieldsSet.set(INDEX_TARGET);
      limit(targetRW.build().limit());
      return this;
    }

    public Builder target(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder targetRW = target();
      targetRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_TARGET);
      limit(targetRW.build().limit());
      return this;
    }

    public Builder targetRef(long value) {
      checkFieldNotSet(INDEX_TARGET_REF);
      checkFieldsSet(0, INDEX_TARGET_REF);
      int newLimit = limit() + FIELD_SIZE_TARGET_REF;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_TARGET_REF);
      limit(newLimit);
      return this;
    }

    public Builder authorization(long value) {
      checkFieldNotSet(INDEX_AUTHORIZATION);
      checkFieldsSet(0, INDEX_AUTHORIZATION);
      int newLimit = limit() + FIELD_SIZE_AUTHORIZATION;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_AUTHORIZATION);
      limit(newLimit);
      return this;
    }

    private OctetsFW.Builder extension() {
      checkFieldNotSet(INDEX_EXTENSION);
      if (!fieldsSet.get(INDEX_AUTHORIZATION)) {
        authorization(DEFAULT_AUTHORIZATION);
      }
      checkFieldsSet(0, INDEX_EXTENSION);
      return extensionRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder extension(OctetsFW value) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(value);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
      return this;
    }

    public Builder extension(Consumer<OctetsFW.Builder> mutator) {
      OctetsFW.Builder extensionRW = extension();
      mutator.accept(extensionRW);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
      return this;
    }

    public Builder extension(DirectBuffer buffer, int offset, int length) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(buffer, offset, length);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
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
    public RouteFW build() {
      if (!fieldsSet.get(INDEX_EXTENSION)) {
        extension(b -> { });
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
