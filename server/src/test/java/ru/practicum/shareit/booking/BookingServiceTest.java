package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.BadRequestExcep;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ItemStorage itemStorage;
    @Autowired
    private UserStorage userStorage;
    private User owner;
    private User user1;
    private BookingRequestDto bookingRequestDto;
    private Item item;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setName("Иван");
        user1.setEmail("vanya@mail.ru");
        userStorage.save(user1);

        owner = new User();
        owner.setName("Владелец");
        owner.setEmail("owner@owner.ru");
        userStorage.save(owner);

        item = new Item();
        item.setName("Вещь для ДБ");
        item.setDescription("Описание вещи для ДБ");
        item.setAvailable(true);
        item.setOwner(owner);
        itemStorage.save(item);

        bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setItemId(item.getId());
        bookingRequestDto.setStart(LocalDateTime.now().minusDays(2));
        bookingRequestDto.setEnd(LocalDateTime.now().minusDays(1));
    }

    @Test
    @DisplayName("Добавление бронирования")
    void addBooking() {
        BookingDto bookingDto = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        assertThat(bookingDto).isNotNull();
        assertThat(bookingDto.getItem().getName()).isEqualTo("Вещь для ДБ");
        assertThat(bookingDto.getBooker().getId()).isEqualTo(user1.getId());
    }

    @Test
    @DisplayName("Подтверждение бронирования")
    void updateBooking() {
        BookingDto bookingDto = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        BookingDto updatedDto = bookingService.updateBooking(owner.getId(), bookingDto.getId(), true);
        assertThat(updatedDto.getStatus()).isEqualTo(BookingStatus.APPROVED);
    }

    @Test
    @DisplayName("Обновление статуса бронирования не владельцем предмета")
    void updateBookingNotOwner() {
        BookingDto bookingDto = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        assertThatExceptionOfType(BadRequestExcep.class)
                .isThrownBy(() -> bookingService.updateBooking(user1.getId(), bookingDto.getId(), true))
                .withMessageContaining("подтверждает только владелец предмета");
    }

    @Test
    @DisplayName("Обновление статуса бронирования не существующим пользователем")
    void updateBookingWithNullUser() {
        BookingDto bookingDto = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        assertThatExceptionOfType(BadRequestExcep.class)
                .isThrownBy(() -> bookingService.updateBooking(123, bookingDto.getId(), true))
                .withMessageContaining("не найден");
    }

    @Test
    @DisplayName("Полечение бронирование пользователя")
    void getByUserId() {
        BookingDto bookingDto = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        BookingDto bookingFromUser1 = bookingService.getByUserId(user1.getId(), bookingDto.getId());
        assertThat(bookingFromUser1).isNotNull();
        assertThat(bookingFromUser1.getBooker().getName()).isEqualTo(user1.getName());
        assertThat(bookingFromUser1.getStatus()).isEqualTo(BookingStatus.WAITING);
    }

    @Test
    @DisplayName("Получение всех бронирований владельца с фильтром ALL")
    void getByOwnerIdWithAll() {
        BookingDto bookingDto1 = bookingService.addNewBooking(user1.getId(), bookingRequestDto);
        bookingService.updateBooking(owner.getId(), bookingDto1.getId(), true);

        BookingRequestDto bookingRequestDto2 = new BookingRequestDto();
        bookingRequestDto2.setItemId(item.getId());
        bookingRequestDto2.setStart(LocalDateTime.now().plusDays(1));
        bookingRequestDto2.setEnd(LocalDateTime.now().plusDays(3));
        BookingDto bookingDto2 = bookingService.addNewBooking(user1.getId(), bookingRequestDto2);

        List<BookingDto> bookingDtos = bookingService.getByOwnerId(owner.getId(), BookingState.ALL);

        assertThat(bookingDtos).hasSize(2);
        assertThat(bookingDtos).extracting(BookingDto::getStatus)
                .containsExactlyInAnyOrder(BookingStatus.APPROVED, BookingStatus.WAITING);
    }


}
