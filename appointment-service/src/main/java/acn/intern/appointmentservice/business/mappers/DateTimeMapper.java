package acn.intern.appointmentservice.business.mappers;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMAT);
    }

    default LocalDateTime stringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMAT);
    }
}
