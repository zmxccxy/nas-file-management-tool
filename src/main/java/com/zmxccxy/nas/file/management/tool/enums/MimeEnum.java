package com.zmxccxy.nas.file.management.tool.enums;

public enum MimeEnum {

    // 文本格式
    TXT("txt", "文本文件", "text/plain"),
    CSV("csv", "逗号分隔值（CSV）", "text/csv"),
    JSON("json", "JSON格式", "application/json"),
    XML("xml", "XML文件", "application/xml"),
    RTF("rtf", "富文本格式", "application/rtf"),
    HTML("html", "HTML网页文件", "text/html"),
    CSS("css", "CSS样式表", "text/css"),
    LOG("log", "日志文件", "text/plain"),
    MARKDOWN("md", "Markdown文件", "text/markdown"),
    TEX("tex", "LaTeX源文件", "application/x-tex"),

    // 图片格式
    JPG("jpg", "JPEG图像", "image/jpeg"),
    JPEG("jpeg", "JPEG图像", "image/jpeg"),
    PNG("png", "便携式网络图形（PNG）", "image/png"),
    GIF("gif", "图形交换格式（GIF）", "image/gif"),
    BMP("bmp", "Windows OS / 2位图图形", "image/bmp"),
    TIFF("tiff", "标记图像文件格式（TIFF）", "image/tiff"),
    SVG("svg", "可缩放矢量图形（SVG）", "image/svg+xml"),
    ICO("ico", "图标格式", "image/vnd.microsoft.icon"),
    WEBP("webp", "WEBP图像", "image/webp"),
    HEIF("heif", "高效图像文件格式（HEIF）", "image/heif"),
    HEIC("heic", "高效图像文件格式（HEIC）", "image/heic"),

    // 视频格式
    MP4("mp4", "MP4视频格式", "video/mp4"),
    MKV("mkv", "Matroska视频格式", "video/x-matroska"),
    MOV("mov", "QuickTime视频格式", "video/quicktime"),
    WEBM("webm", "WEBM视频", "video/webm"),
    AVI("avi", "音频视频交错格式", "video/x-msvideo"),
    MPEG("mpeg", "MPEG视频", "video/mpeg"),
    OGV("ogv", "OGG视频", "video/ogg"),
    TS("ts", "MPEG传输流", "video/mp2t"),
    MIME_3GP("3gp", "3GPP音频/视频容器", "video/3gpp"),
    MIME_3G2("3g2", "3GPP2音频/视频容器", "video/3gpp2"),
    FLV("flv", "Flash视频格式", "video/x-flv"),
    RM("rm", "RealMedia视频格式", "application/vnd.rn-realmedia"),
    SWF("swf", "小型Web格式（SWF）或Adobe Flash文档", "application/x-shockwave-flash"),

    // 音频格式
    AAC("aac", "AAC音频", "audio/aac"),
    MP3("mp3", "MP3音频", "audio/mpeg"),
    OGA("oga", "OGG音讯", "audio/ogg"),
    WAV("wav", "波形音频格式", "audio/wav"),
    FLAC("flac", "无损音频格式", "audio/flac"),
    OPUS("opus", "OPUS音频", "audio/opus"),
    MIDI("midi", "乐器数字接口（MIDI）", "audio/midi"),
    WEBA("weba", "WEBM音频", "audio/webm"),
    WMA("wma", "Windows Media Audio", "audio/x-ms-wma"),
    RA("ra", "RealAudio格式", "audio/vnd.rn-realaudio"),

    // 镜像文件
    ISO("iso", "ISO镜像文件", "application/x-iso9660-image"),
    IMG("img", "磁盘镜像文件", "application/x-img"),
    DAA("daa", "Direct Access Archive", "application/x-daa"),
    NRG("nrg", "Nero磁盘映像", "application/x-nrg"),

    // 压缩包格式
    ZIP("zip", "ZIP压缩文件", "application/zip"),
    RAR("rar", "RAR档案", "application/vnd.rar"),
    TAR("tar", "磁带存档（TAR）", "application/x-tar"),
    GZ("gz", "GZip压缩档案", "application/gzip"),
    BZ("bz", "BZip存档", "application/x-bzip"),
    BZ2("bz2", "BZip2存档", "application/x-bzip2"),
    SEVEN_Z("7z", "7-zip压缩文件", "application/x-7z-compressed"),
    LZMA("lzma", "LZMA压缩文件", "application/x-lzma"),
    ZTAR("ztar", "Z压缩档案", "application/x-ztar"),

    // 脚本格式
    SH("sh", "Bourne Shell脚本", "application/x-sh"),
    CSH("csh", "C-Shell脚本", "application/x-csh"),
    BAT("bat", "Windows批处理脚本", "application/x-bat"),
    JS("js", "JavaScript", "text/javascript"),
    PHP("php", "PHP脚本", "application/x-httpd-php"),
    PY("py", "Python脚本", "text/x-python"),
    RUBY("rb", "Ruby脚本", "application/x-ruby"),
    PERL("pl", "Perl脚本", "application/x-perl"),
    LUA("lua", "Lua脚本", "text/x-lua"),
    TCL("tcl", "Tcl脚本", "application/x-tcl"),

    // 可执行文件格式
    EXE("exe", "Windows可执行文件", "application/x-msdownload"),
    APP("app", "Mac OS X应用程序", "application/octet-stream"),
    ELF("elf", "Linux可执行文件", "application/x-elf"),
    DMG("dmg", "Mac OS X磁盘镜像", "application/x-apple-diskimage"),
    BIN("bin", "二进制文件", "application/octet-stream"),

    // 代码文件格式
    JAVA("java", "Java源代码文件", "text/x-java-source"),
    C("c", "C语言源代码", "text/x-c"),
    CPP("cpp", "C++源代码", "text/x-c++src"),
    H("h", "C头文件", "text/x-c-header"),
    PYTHON("py", "Python源代码", "text/x-python"),
    SQL("sql", "SQL文件", "application/sql"),
    R("r", "R语言源代码", "text/x-rsrc"),
    GO("go", "Go语言源代码", "text/x-go"),

    // 模型文件格式
    H5("h5", "HDF5模型格式", "application/x-hdf"),
    ONNX("onnx", "ONNX模型格式", "application/onnx"),
    PB("pb", "TensorFlow模型格式", "application/x-protobuf"),
    PTH("pth", "PyTorch模型格式", "application/octet-stream"),
    KERAS("keras", "Keras模型格式", "application/octet-stream"),
    TFLITE("tflite", "TensorFlow Lite模型格式", "application/octet-stream"),

    // 设计文件格式
    PSD("psd", "Photoshop文件", "image/vnd.adobe.photoshop"),
    AI("ai", "Adobe Illustrator文件", "application/postscript"),
    INDD("indd", "Adobe InDesign文件", "application/x-indesign"),
    CDR("cdr", "CorelDRAW文件", "application/coreldraw"),
    FONTS("ttf", "TrueType字体文件", "font/ttf"),
    OTF("otf", "OpenType字体文件", "font/otf"),
    EOT("eot", "Embedded OpenType字体", "application/vnd.ms-fontobject"),

    // Microsoft Office格式
    DOC("doc", "Microsoft Word文件", "application/msword"),
    DOCX("docx", "Microsoft Word（OpenXML）", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PPT("ppt", "Microsoft PowerPoint", "application/vnd.ms-powerpoint"),
    PPTX("pptx", "Microsoft PowerPoint（OpenXML）", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    XLS("xls", "Microsoft Excel文件", "application/vnd.ms-excel"),
    XLSX("xlsx", "Microsoft Excel（OpenXML）", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    // PDF格式
    PDF("pdf", "PDF文档", "application/pdf");

    private String extension;
    private String description;
    private String mimeType;

    MimeEnum(String extension, String description, String mimeType) {
        this.extension = extension;
        this.description = description;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getDescription() {
        return description;
    }

    public String getMimeType() {
        return mimeType;
    }

    /**
     * 根据 MIME 类型获取文件扩展名
     *
     * @param mimeType MIME 类型
     * @return 对应的扩展名
     */
    public static String getExtensionByMimeType(String mimeType) {
        for (MimeEnum mimeEnum : MimeEnum.values()) {
            if (mimeEnum.getMimeType().equalsIgnoreCase(mimeType)) {
                return mimeEnum.getExtension();
            }
        }
        return "其他"; // 如果没有匹配的 MIME 类型，返回 null
    }

    /**
     * 根据 MIME 类型获取类型说明
     *
     * @param mimeType MIME 类型
     * @return 对应的类型说明
     */
    public static String getExplainByMimeType(String mimeType) {
        for (MimeEnum mimeEnum : MimeEnum.values()) {
            if (mimeEnum.getMimeType().equalsIgnoreCase(mimeType)) {
                return mimeEnum.getDescription();
            }
        }
        return "其他"; // 如果没有匹配的 MIME 类型，返回 null
    }
}
