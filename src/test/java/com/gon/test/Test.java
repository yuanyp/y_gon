package com.gon.test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        File imageFile = new File("F:/test/e.jpg");

        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        instance.setLanguage("chi_sim");
        instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
        try {
            String result = instance.doOCR(imageFile);
            long b = System.currentTimeMillis();
            System.out.println(result + "\n " + (b-a));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
