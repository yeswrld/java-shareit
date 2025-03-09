package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper MAPPER = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "start", target = "start")
    @Mapping(source = "end", target = "end")
    @Mapping(source = "item", target = "item")
    @Mapping(source = "booker", target = "booker")
    @Mapping(source = "status", target = "status")
    BookingDto toBookingDto(Booking booking);

    BookingRequestDto toBookingRequestDto(BookingRequestDto bookingRequestDto);

    Booking requestToBooking(BookingRequestDto bookingRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "start", target = "start")
    @Mapping(source = "end", target = "end")
    @Mapping(source = "item", target = "item")
    @Mapping(source = "booker", target = "booker")
    @Mapping(source = "status", target = "status")
    Booking toBooking(BookingDto bookingDto);

    default List<BookingDto> toBookingDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toBookingDto)
                .collect(Collectors.toList());
    }
}

