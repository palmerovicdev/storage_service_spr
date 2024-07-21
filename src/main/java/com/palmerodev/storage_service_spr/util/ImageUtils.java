package com.palmerodev.storage_service_spr.util;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ImageUtils {

    private static final int SIZE = 4 * 1024;

    public byte[] compressImage(byte[] data) {
        var deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        var outputStream = new ByteArrayOutputStream(data.length);
        var temporal = new byte[SIZE];
        while (!deflater.finished()) {
            var size = deflater.deflate(temporal);
            outputStream.write(temporal, 0, size);
        }

        try {
            outputStream.close();
        } catch (Exception ignored) {}

        return outputStream.toByteArray();
    }

    public byte[] decompressImage(byte[] data) {
        var inflater = new Inflater();
        inflater.setInput(data);
        var outputStream = new ByteArrayOutputStream(data.length);
        var temporal = new byte[SIZE];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(temporal);
                outputStream.write(temporal, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {}

        return outputStream.toByteArray();
    }

}
