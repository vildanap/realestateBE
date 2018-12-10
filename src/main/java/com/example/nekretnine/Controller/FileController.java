package com.example.nekretnine.Controller;

import com.example.nekretnine.Model.File;
import com.example.nekretnine.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONException;


import java.io.IOException;

@RestController
public class FileController {
    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException, JSONException {

        for(MultipartFile uploadedFile : files) {
            long id = fileService.save(uploadedFile);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/files/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) {

        File file = new File();

            file = fileService.getFile(id);
            ByteArrayResource resource = new ByteArrayResource(file.getFile());
            MediaType mediaType = MediaType.parseMediaType(file.getFiletype());
            return ResponseEntity.ok()
                        .header("X-Content-Type-Options", "nosniff")
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename())
                        .header("Access-Control-Expose-Headers", "Content-Disposition")
                        .contentType(mediaType)
                        .contentLength(file.getFile().length) //
                        .body(resource);


    }
}
