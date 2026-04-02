package org.midheaven.io;

public class ByteContentFormats{
    
    public static final ByteContentFormat CSV = ByteContentFormat.of("csv", "text/csv");
    public static final ByteContentFormat DOCX = ByteContentFormat.of("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final ByteContentFormat HTML = ByteContentFormat.of("html", "text/html");
    public static final ByteContentFormat PDF = ByteContentFormat.of("pdf", "application/pdf");
    public static final ByteContentFormat PFX = ByteContentFormat.of("pfx", "application/x-pkcs12");
    public static final ByteContentFormat TXT = ByteContentFormat.of("txt", "text/plain");
    public static final ByteContentFormat XLSX  = ByteContentFormat.of("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final ByteContentFormat XLS  = ByteContentFormat.of("xls","application/vnd.ms-excel");
    public static final ByteContentFormat XML = ByteContentFormat.of("xml", "application/xml");
    public static final ByteContentFormat YAML  = ByteContentFormat.of("yml", "application/yaml");
    public static final ByteContentFormat ZIP  = ByteContentFormat.of("zip", "application/zip");
}
