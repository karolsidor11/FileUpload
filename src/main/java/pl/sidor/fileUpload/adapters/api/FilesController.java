package pl.sidor.fileUpload.adapters.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.domain.model.entity.Files;
import pl.sidor.fileUpload.adapters.service.FilesService;
import pl.sidor.fileUpload.adapters.response.UploadFileResponse;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FilesService filesService;

    @Autowired
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        Files file = filesService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }
    @GetMapping()
    public ResponseEntity<Resource> findByName(@RequestParam String  fileName){
        Files file = filesService.findByFilename(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }

    @PostMapping()
    public UploadFileResponse uploadFileResponse(@RequestBody MultipartFile file) {
        filesService.saveFile(file);
        return new UploadFileResponse(file.getName(), file.getContentType(), "URI", file.getSize());
    }
}
