package com.labus;

import javax.xml.bind.DatatypeConverter;

public class Signature {
    private String name;
    private byte[] bytes;

    public Signature(String name, byte[] bytes) {
        this.bytes = bytes;
        this.name = name;
    }

    public Signature(String name, String hex) {
        bytes = DatatypeConverter.parseHexBinary(hex);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getHex() {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public void setHex(String hex) {
        bytes = DatatypeConverter.parseHexBinary(hex);
    }
}
