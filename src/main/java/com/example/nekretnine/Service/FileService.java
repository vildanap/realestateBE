package com.example.nekretnine.Service;

import com.example.nekretnine.Model.File;
import com.example.nekretnine.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService implements IFileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    @Override
    public ArrayList<File> getAllFiles() {
        ArrayList<File> files = new ArrayList<>();
        fileRepository.findAll().forEach(files::add); //fun with Java 8
        return files;
    }

    @Override
    public long save(MultipartFile f) throws IOException {

        File file = new File();
        file.setFilename(f.getOriginalFilename());
        file.setFiletype(f.getContentType());
        file.setFile(f.getBytes());
        return fileRepository.save(file).getId();
    }

    @Override
    public File getFile(long id) {
        return fileRepository.findById(id).get();
    }
}
