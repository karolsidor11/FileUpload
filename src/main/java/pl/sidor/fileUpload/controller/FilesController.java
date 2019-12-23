package pl.sidor.fileUpload.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.model.Files;
import pl.sidor.fileUpload.service.FilesService;
import pl.sidor.fileUpload.upload.UploadFileResponse;

@RestController
@AllArgsConstructor
public class FilesController {

    @Autowired
    private final FilesService filesService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        Files file = filesService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFileResponse(@RequestBody MultipartFile file) {
        filesService.storeFiles(file);
        return new UploadFileResponse(file.getName(), file.getContentType(), "URI", file.getSize());
    }
}
