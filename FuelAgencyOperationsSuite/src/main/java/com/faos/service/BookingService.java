package com.faos.service;

import com.faos.model.Booking;
import com.faos.model.Customer;
import com.faos.repository.BookingRepository;
import com.faos.repository.CustomerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;


 // Add Booking
    public Booking addBooking(Long customerId, Booking booking) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: \" + customerId"));

        if (customer.getConnectionStatus().toString().equals("INACTIVE")) {
            throw new RuntimeException("Cannot book for Inactive Customer");
        }

        booking.setCustomer(customer);
        booking.setBookingDate(LocalDate.now()); // Booking Date Auto Set
        booking.setBookingStatus(com.faos.model.enums.BookingStatus.PENDING); // Default Status
        booking.setPaymentStatus(com.faos.model.enums.PaymentStatus.PENDING); // Default Payment Status

        return bookingRepository.save(booking);
    }

    // Get All Bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    
    // ✅ Get booking by ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    // ✅ Update Booking
    @Transactional
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking booking = getBookingById(id);

        // Update allowed fields
        booking.setTimeSlot(updatedBooking.getTimeSlot());
        booking.setDeliveryOption(updatedBooking.getDeliveryOption());
        booking.setPaymentMode(updatedBooking.getPaymentMode());
        booking.setPaymentStatus(updatedBooking.getPaymentStatus());
        booking.setBookingStatus(updatedBooking.getBookingStatus());

        return bookingRepository.save(booking);
    }
 // ✅ Delete Booking
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
    
}
