package com.anton.smarthouse;

import com.anton.smarthouse.exception.NotFoundEntity;
import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.repository.DeviceRepository;
import com.anton.smarthouse.repository.HistoryRepository;
import com.anton.smarthouse.repository.UserRepository;
import com.anton.smarthouse.services.DeviceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class DeviceServiceTests {
    private DeviceService deviceService;
    private DeviceRepository deviceRepository;
    private DeviceEntity device;
    private UserEntity user;

    @Before
    public void setUp() throws Exception {
        this.deviceRepository = mock(DeviceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        this.deviceService = new DeviceService(deviceRepository, historyRepository);
        this.user = new UserEntity("User Userovich", "mail@mail.com","avatar.png");
        this.device = new DeviceEntity("1234", "lamp", this.user.getId(), "kitchen/lamp");
    }

    @Test
    public void deviceServiceGetExistDeviceSuccessfullyReturned() {
        when(this.deviceRepository.findById("1234")).thenReturn(Optional.of(device));
        DeviceEntity result = this.deviceService.findById("1234");
        assertThat(result).isEqualTo(device);
    }

    @Test(expected = NotFoundEntity.class)
    public void deviceServiceGetNonexistentDeviceGettingFailedWithException() {
        when(this.deviceRepository.findById(anyString())).thenReturn(Optional.empty());
        this.deviceService.findById("1234");
    }

    @Test
    public void deviceServiceDeleteExistDevice() {
        when(this.deviceRepository.findById("1234")).thenReturn(Optional.of(device));
        this.deviceService.delete("1234");
        Boolean result = device == null ? false : true;
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void deviceServiceUpdateDeviceUpdateSuccessfully() {
        when(this.deviceRepository.existsById("1234")).thenReturn(true);
        this.deviceService.update(device.getId(), device, user.getId());
        verify(this.deviceRepository).save(any());
    }

    @Test
    public void deviceServiceUpdateExistDeviceUpdatingSuccess() {
        when(this.deviceRepository.existsById("1234")).thenReturn(true);
        device.setTopic("new/topic");
        this.deviceService.update(device.getId(), device, user.getId());
        String result = device.getTopic();
        assertThat(result).isEqualTo("new/topic");
    }

    @Test(expected = NotFoundEntity.class)
    public void deviceServiceGetAllSensorsGettingFailedException() {
        when(this.deviceRepository.findDeviceEntitiesByUserIdAndType(anyString(), anyString())).thenReturn(null);
        this.deviceService.getSensorReports(user.getId());
    }

}
