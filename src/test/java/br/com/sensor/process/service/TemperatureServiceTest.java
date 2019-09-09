package br.com.sensor.process.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sensor.process.exception.TemperatureException;
import br.com.sensor.process.repository.TemperatureRepository;
import br.com.sensor.temperature.model.Temperature;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemperatureServiceTest {

	@InjectMocks
	private TemperatureService temperatureService;

	@MockBean
	private TemperatureRepository temperatureRepository;
	
	@MockBean
	private AlertService alertService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }	
	
	@Test
	public void testSave_returnOK() {
		Temperature temperature = new Temperature("Sensor1", 15.10, new Date());
		when(temperatureRepository.save(temperature)).thenReturn(temperature);
		assertEquals(temperature, temperatureService.save(temperature));
	}

	@Test
	public void whenSaveTemperatureObjectNull_thenExIsThrown() {
		Temperature temperature = null;

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("A temperatura está vazia", expected.getMessage());
		}

	}
	 
	@Test
	public void whenSaveTemperatureFieldSensorNull_thenExIsThrown() {
		Temperature temperature = new Temperature(null, 15.10, new Date());

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("O campo Sensor é obrigatório", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveTemperatureFieldSensorEmpty_thenExIsThrown() {
		Temperature temperature = new Temperature("", 15.10, new Date());

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("O campo Sensor é obrigatório", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveTemperatureFieldValueNull_thenExIsThrown() {
		Temperature temperature = new Temperature("Sensor1", null, new Date());

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("O campo Valor é obrigatório", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveTemperatureFieldValueIncorrect_thenExIsThrown() {
		Temperature temperature = new Temperature("Sensor1", 0.00, new Date());

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("O campo Valor tem que ser maior que 0.00", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveTemperatureFieldDateNull_thenExIsThrown() {
		Temperature temperature = new Temperature("Sensor1", 15.00, null);

		try {
			temperatureService.save(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("O campo Data está vazio", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveTemperatureWithLowAlert_thenReturnTemperature() {
		Temperature temperature = new Temperature("Sensor1", 13.00,  new Date());

		when(temperatureRepository.save(temperature)).thenReturn(temperature);
		assertEquals(temperature, temperatureService.save(temperature));
	}	
	
	@Test
	public void whenSaveTemperatureWithHighAlert_thenReturnTemperature() {
		Temperature temperature = new Temperature("Sensor1", 20.00,  new Date());

		when(temperatureRepository.save(temperature)).thenReturn(temperature);
		
		assertEquals(temperature, temperatureService.save(temperature));
	}

	@Test
	public void deleteTest_returnOK() {
		Temperature temperature = new Temperature("Sensor1", 15.10, new Date());
				
		temperatureService.delete(temperature);
		
		verify(temperatureRepository, times(1)).delete(temperature);
	}
	
	@Test
	public void whenDeleteTemperatureObjectNull_thenExIsThrown() {
		Temperature temperature = null;

		try {
			temperatureService.delete(temperature);
			fail();
		} catch (TemperatureException expected) {
			assertEquals("A temperatura está vazia", expected.getMessage());
		}

	}

	@Test
	public void findTemperatureAllTest() {
		when(temperatureRepository.findAll()).thenReturn(
				Stream.of(new Temperature("Sensor1", 15.10, new Date()), new Temperature("Sensor2", 18.39, new Date()))
						.collect(Collectors.toList()));

		assertEquals(2, temperatureService.findTemperatureAll().size());
	}

	@Test
	public void findBySensorTest() {

		String sensor = "Sensor1";

		when(temperatureRepository.findBySensor(sensor))
				.thenReturn(Stream.of(new Temperature("Sensor1", 15.10, new Date())).collect(Collectors.toList()));

		assertEquals(1, temperatureService.findBySensor(sensor).size());
	}

}
