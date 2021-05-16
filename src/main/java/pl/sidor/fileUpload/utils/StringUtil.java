package pl.sidor.fileUpload.utils;

import lombok.experimental.UtilityClass;
import pl.sidor.fileUpload.exception.MessageException;

@UtilityClass
public class StringUtil {

    public static String createMessage(MessageException message, Object param) {
        return String.format(message.getMessage(), param);
    }
}
