package pl.sidor.fileUpload.domain.ports;

import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.domain.model.Files;

public interface FilesPorts {

    Files findById(final Long fileID);

    Files findByFilename(final String fileName);

    void saveFile(final MultipartFile file);
}
