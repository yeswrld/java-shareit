package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingStorage;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.BadRequestExcep;
import ru.practicum.shareit.exception.NotFoundExcep;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Transactional
    @Override
    public BookingDto addNewBooking(Integer userId, BookingRequestDto bookingRequestDto) {
        Item item = itemStorage.findById(bookingRequestDto.getItemId()).orElseThrow(() -> new NotFoundExcep("Предмет не найден"));
        Booking booking = bookingMapper.requestToBooking(bookingRequestDto);
        booking.setBooker(userStorage.findById(userId).orElseThrow(() -> new NotFoundExcep("Пользователь не найден")));
        booking.setItem(itemStorage.findById(bookingRequestDto.getItemId()).orElseThrow(() -> new NotFoundExcep("Предмет не найден")));
        if (item.getAvailable()) {
            booking.setStatus(BookingStatus.WAITING);
        } else throw new BadRequestExcep("Вещь недоступна для брони");
        return bookingMapper.toBookingDto(bookingStorage.save(booking));
    }

    @Transactional
    @Override
    public BookingDto updateBooking(Integer userId, Integer bookingId, boolean approved) {
        User user = userStorage.findById(userId).orElseThrow(() -> new BadRequestExcep("Пользователь не найден"));
        Booking booking = bookingStorage.findById(bookingId).orElseThrow(() -> new NotFoundExcep("Бронирование не найдено"));
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else booking.setStatus(BookingStatus.CANCELED);
        bookingStorage.save(booking);
        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto getByUserId(Integer userId, Integer bookingId) {
        User user = userStorage.findById(userId).orElseThrow(() -> new BadRequestExcep("Пользователь не найден"));
        Booking booking = bookingStorage.findById(bookingId).orElseThrow(() -> new NotFoundExcep("Бронирование не найдено"));
        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> findAll() {
        return bookingMapper.toBookingDtoList(bookingStorage.findAll());
    }


    @Override
    public List<BookingDto> searchByText(String text) {
        return List.of();
    }
}
