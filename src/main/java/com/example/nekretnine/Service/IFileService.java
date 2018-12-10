package com.example.nekretnine.Service;

import com.example.nekretnine.Model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface IFileService {
    public ArrayList<File> getAllFiles();
    public long save(MultipartFile file) throws IOException;
    public File getFile(long id);
}
