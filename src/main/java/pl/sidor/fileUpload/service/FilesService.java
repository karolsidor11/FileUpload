package pl.sidor.fileUpload.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.exception.FileStorageException;
import pl.sidor.fileUpload.model.Files;
import pl.sidor.fileUpload.repository.FilesRepository;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@AllArgsConstructor
public class FilesService {

    @Autowired
    private final FilesRepository filesRepository;

    public void storeFiles(final MultipartFile file) {
        if (!isNull(file)) {
            String fileName = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));
            saveFileInDataBase(file, fileName);
        } else {
            throw new FileStorageException("Przekazywany plik  jest pusty!!!");
        }
    }

    public Files getFile(final String fileID) {
        return filesRepository.findById(fileID)
                .orElseThrow(() -> new FileStorageException("Nie znaleziono pliku o podanym id : " + fileID));
    }

    private void saveFileInDataBase(MultipartFile file, String fileName) {
        try {
            checkFilename(fileName);
            Files dbFile = new Files(null, fileName, file.getContentType(), file.getBytes());
            filesRepository.save(dbFile);

        } catch (Exception e) {
            throw new FileStorageException("Nieoczekiwany bład  systemu!!!");
        }
    }

    private void checkFilename(String fileName) {
        if (fileName.contains("..")) {
            throw new FileStorageException("Nie znaleziono nazwy pliku!!!");
        }
    }
}
