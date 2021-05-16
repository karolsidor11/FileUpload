package pl.sidor.fileUpload.exception;

import lombok.Getter;

@Getter
public enum MessageException {

    INTERNAL_ERROR("Nieoczekiwany bład  systemu."),
    FILE_IS_NULL("Przekazywany plik  jest pusty."),
    INVALID_FILENAME("Nie znaleziono nazwy pliku."),
    FILE_NOT_FOUND("Nie znaleziono pliku o podanym id : %s"),
    FILE_NOT_FOUND_BY_FILENAME("Nie znaleziono pliku o podanej nazwie : %s");

    MessageException(String message) {
        this.message = message;
    }

    private String message;
}
