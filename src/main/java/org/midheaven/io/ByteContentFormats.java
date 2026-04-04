package org.midheaven.io;

/**
 * Represents Byte Content Formats.
 */
public class ByteContentFormats{
    
    /**
     * Constant for CSV.
     */
    public static final ByteContentFormat CSV = ByteContentFormat.of("csv", "text/csv");
    /**
     * Constant for DOCX.
     */
    public static final ByteContentFormat DOCX = ByteContentFormat.of("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    /**
     * Constant for HTML.
     */
    public static final ByteContentFormat HTML = ByteContentFormat.of("html", "text/html");
    /**
     * Constant for PDF.
     */
    public static final ByteContentFormat PDF = ByteContentFormat.of("pdf", "application/pdf");
    /**
     * Constant for PFX.
     */
    public static final ByteContentFormat PFX = ByteContentFormat.of("pfx", "application/x-pkcs12");
    /**
     * Constant for TXT.
     */
    public static final ByteContentFormat TXT = ByteContentFormat.of("txt", "text/plain");
    /**
     * Constant for XLSX.
     */
    public static final ByteContentFormat XLSX  = ByteContentFormat.of("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    /**
     * Constant for XLS.
     */
    public static final ByteContentFormat XLS  = ByteContentFormat.of("xls","application/vnd.ms-excel");
    /**
     * Constant for XML.
     */
    public static final ByteContentFormat XML = ByteContentFormat.of("xml", "application/xml");
    /**
     * Constant for YAML.
     */
    public static final ByteContentFormat YAML  = ByteContentFormat.of("yml", "application/yaml");
    /**
     * Constant for ZIP.
     */
    public static final ByteContentFormat ZIP  = ByteContentFormat.of("zip", "application/zip");
}
