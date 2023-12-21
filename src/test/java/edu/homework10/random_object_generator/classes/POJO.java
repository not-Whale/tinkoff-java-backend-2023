package edu.homework10.random_object_generator.classes;

public class POJO {
    private byte byteField;

    private short shortField;

    private final int intField;

    private final long longField;

    public POJO(byte byteField, short shortField, int intField, long longField) {
        this.byteField = byteField;
        this.shortField = shortField;
        this.intField = intField;
        this.longField = longField;
    }

    public byte getByteField() {
        return byteField;
    }

    public short getShortField() {
        return shortField;
    }

    public int getIntField() {
        return intField;
    }

    public long getLongField() {
        return longField;
    }
}
