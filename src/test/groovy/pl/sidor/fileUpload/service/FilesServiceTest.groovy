package pl.sidor.fileUpload.service

import org.springframework.web.multipart.MultipartFile
import pl.sidor.fileUpload.exception.FileStorageException
import pl.sidor.fileUpload.domain.model.Files
import pl.sidor.fileUpload.adapters.repository.FilesRepository
import pl.sidor.fileUpload.adapters.service.FilesService
import pl.sidor.fileUpload.exception.MessageException
import spock.lang.Specification

class FilesServiceTest extends Specification {

    private FilesRepository filesRepository = Mock(FilesRepository.class)
    private FilesService service = [filesRepository]

    def "should save file"() {
        given:
        MultipartFile multipartFile = getMultipartFile()

        when:
        filesRepository.save(multipartFile) >> multipartFile
        service.saveFile(multipartFile)

        then:
        1 * filesRepository.save(_)
    }

    def "should  throw FileStorageException"() {
        given:
        MultipartFile multipartFile = Stub() {
            getOriginalFilename() >> ".."
        }

        when:
        service.saveFile(multipartFile)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message == MessageException.INVALID_FILENAME.message
    }

    def "should throw FileStorageException when file  is null"() {
        given:
        MultipartFile multipartFile = null

        when:
        service.saveFile(multipartFile)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message == MessageException.FILE_IS_NULL.message
    }

    def "should get File by id"() {
        given:
        Long fileID = 123
        Files files = prepareFile()

        when:
        filesRepository.findById(fileID) >> Optional.of(files)
        Files file = service.findById(fileID)

        then:
        file != null
    }

    def "should  throw  exception when get File  by id"() {
        given:
        Long fileID = 123L

        when:
        filesRepository.findById(fileID) >> Optional.empty()
        service.findById(fileID)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message == "Nie znaleziono pliku o podanym id : " + fileID
    }

    private MultipartFile getMultipartFile() {
        MultipartFile multipartFile = Stub() {
            getName() >> "Summer"
            getBytes() >> 2000
            getOriginalFilename() >> "Holiday in Paris"
        }
        return multipartFile
    }

    private static Files prepareFile() {
        Files files = new Files()
        files.setFileName("file.jpg")
        files.setFileType("jpg")
        files.setFile("CONTENT".getBytes())

        return files
    }
}
