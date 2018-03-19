// TODO: license
package org.reaktivity.nukleus.sse.internal.types;

import java.nio.ByteOrder;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

public final class ListFW<T extends Flyweight> extends Flyweight {
  private static final int FIELD_SIZE_LENGTH = Integer.BYTES;

  private final ByteOrder byteOrder;

  private final T itemRO;

  public ListFW(T itemRO) {
    this.itemRO = Objects.requireNonNull(itemRO);
    this.byteOrder = ByteOrder.nativeOrder();
  }

  public ListFW(T itemRO, ByteOrder byteOrder) {
    this.itemRO = Objects.requireNonNull(itemRO);
    this.byteOrder = byteOrder;
  }

  @Override
  public int limit() {
    return offset() + FIELD_SIZE_LENGTH + length0();
  }

  @Override
  public ListFW<T> wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(offset + FIELD_SIZE_LENGTH, maxLimit);
    if (length0() < 0) {
      throw new IllegalArgumentException(String.format("Invalid list at offset %d: size < 0", offset));
    }
    checkLimit(limit(), maxLimit);
    return this;
  }

  public ListFW<T> forEach(Consumer<? super T> consumer) {
    for (int offset = offset() + FIELD_SIZE_LENGTH; offset < limit(); offset = itemRO.limit()) {
      itemRO.wrap(buffer(), offset, limit());
      consumer.accept(itemRO);
    }
    return this;
  }

  public boolean anyMatch(Predicate<? super T> predicate) {
    for (int offset = offset() + FIELD_SIZE_LENGTH; offset < limit(); offset = itemRO.limit()) {
      itemRO.wrap(buffer(), offset, maxLimit());
      if (predicate.test(itemRO)) {
        return true;
      }
    }
    return false;
  }

  public T matchFirst(Predicate<? super T> predicate) {
    for (int offset = offset() + FIELD_SIZE_LENGTH; offset < limit(); offset = itemRO.limit()) {
      itemRO.wrap(buffer(), offset, maxLimit());
      if (predicate.test(itemRO)) {
        return itemRO;
      }
    }
    return null;
  }

  public boolean isEmpty() {
    return length0() == 0;
  }

  @Override
  public String toString() {
    return String.format("ARRAY containing %d bytes of data", length0());
  }

  private int length0() {
    return buffer().getInt(offset(), byteOrder);
  }

  public static final class Builder<B extends Flyweight.Builder<T>, T extends Flyweight> extends Flyweight.Builder<ListFW<T>> {
    private final ByteOrder byteOrder;

    private final B itemRW;

    public Builder(B itemRW, T itemRO) {
      super(new ListFW<T>(itemRO));
      this.byteOrder = ByteOrder.nativeOrder();
      this.itemRW = itemRW;
    }

    public Builder(B itemRW, T itemRO, ByteOrder byteOrder) {
      super(new ListFW<T>(itemRO));
      this.byteOrder = byteOrder;
      this.itemRW = itemRW;
    }

    public Builder<B, T> wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      super.wrap(buffer, offset, maxLimit);
      int newLimit = offset + FIELD_SIZE_LENGTH;
      checkLimit(newLimit, maxLimit);
      super.limit(newLimit);
      return this;
    }

    public Builder<B, T> item(Consumer<B> mutator) {
      itemRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(itemRW);
      limit(itemRW.build().limit());
      return this;
    }

    @Override
    public ListFW<T> build() {
      int sizeInBytes = limit() - offset() - FIELD_SIZE_LENGTH;
      buffer().putInt(offset(), sizeInBytes, byteOrder);
      return super.build();
    }
  }
}
