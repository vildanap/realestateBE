package com.example.nekretnine.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
public class File implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="file_generator")
    @SequenceGenerator(name="file_generator", sequenceName="file_seq", allocationSize=1)
    private long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filetype")
    private String filetype;

    @Column(name = "file_")
    @Lob
    private byte[] file;

    public File() {

    }

    public File(String filename, String filetype, byte[] file) {
        this.filename = filename;
        this.filetype = filetype;
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
