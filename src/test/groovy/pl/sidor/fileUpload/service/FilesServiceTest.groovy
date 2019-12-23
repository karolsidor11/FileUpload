package service

import org.springframework.web.multipart.MultipartFile
import pl.sidor.fileUpload.exception.FileStorageException
import pl.sidor.fileUpload.model.Files
import pl.sidor.fileUpload.repository.FilesRepository
import pl.sidor.fileUpload.service.FilesService
import spock.lang.Ignore
import spock.lang.Specification

class FilesServiceTest extends Specification {

    private FilesRepository filesRepository = Mock(FilesRepository.class)
    private FilesService service = [filesRepository]

    def "should save file"() {
        given:
        MultipartFile multipartFile = getMultipartFile()

        when:
        filesRepository.save(_) >> multipartFile
        service.storeFiles(multipartFile)

        then:
        1 * filesRepository.save(_)
    }

    def "should  throw FileStorageException"() {
        given:
        MultipartFile multipartFile = Stub() {
            getOriginalFilename() >> ".."
        }

        when:
        service.storeFiles(multipartFile)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message == "Nieoczekiwany bład  systemu!!!"
    }

    def "should throw FileStorageException when file  is null"() {
        given:
        MultipartFile multipartFile = null

        when:
        service.storeFiles(multipartFile)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message == "Przekazywany plik  jest pusty!!!"
    }

    @Ignore
    def "should get File by id"() {
        given:
        String fileID = "123"
        MultipartFile multipartFile = getMultipartFile()

        when:
        filesRepository.findById(fileID) >> Optional.of(multipartFile)
        Files file = service.getFile(fileID)

        then:
        file != null
    }

    def "should  throw  exception when get File  by id"() {
        given:
        String fileID = "123"

        when:
        filesRepository.findById(fileID) >> Optional.empty()
        service.getFile(fileID)

        then:
        Exception exception = thrown(FileStorageException.class)
        exception.message=="Nie znaleziono pliku o podanym id : "+fileID
    }

    private MultipartFile getMultipartFile(){
        MultipartFile multipartFile=Stub(){
            getName()>>"Summer"
            getBytes()>>2000
            getOriginalFilename()>>"Holiday in Paris"
        }
        return multipartFile
    }
}
