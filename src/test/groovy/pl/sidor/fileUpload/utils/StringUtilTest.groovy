package pl.sidor.fileUpload.utils

import pl.sidor.fileUpload.exception.MessageException
import spock.lang.Specification

class StringUtilTest extends Specification {

    def "should return expected message"() {
        when:
        def result = StringUtil.createMessage(message, param)

        then:
        result == expectedResult

        where:
        message                                     | param      | expectedResult
        MessageException.FILE_NOT_FOUND             | "1"        | "Nie znaleziono pliku o podanym id : 1"
        MessageException.FILE_NOT_FOUND_BY_FILENAME | "document" | "Nie znaleziono pliku o podanej nazwie : document"
    }
}
