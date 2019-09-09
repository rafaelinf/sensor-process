package br.com.sensor.process.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sensor.process.exception.AlertException;
import br.com.sensor.process.exception.TemperatureException;
import br.com.sensor.process.repository.AlertRepository;
import br.com.sensor.temperature.model.Alert;
import br.com.sensor.temperature.model.Temperature;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlertServiceTest {

	@InjectMocks
	private AlertService alertService;

	@MockBean
	private AlertRepository alertRepository;
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }	
    
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSave_returnOK() {
		
		String description = "Sensor1";
		Double temperatureValue = 15.88;	
		
		Alert alert = new Alert();
		alert.setDescription(description);
		alert.setTemperatureValue(temperatureValue);

		when(alertRepository.save(alert)).thenReturn(alert);
		assertNotNull(alertService.save(alert));
				
	}
	
	@Test
	public void whenSaveAlertaObjectNull_thenExIsThrown() {
		Alert alert = null;

		try {
			alertService.save(alert);
			fail();
		} catch (AlertException expected) {
			assertEquals("O Alerta está vazio", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveAlertFieldDescriptionNull_thenExIsThrown() {
		Alert alert = new Alert(null, new Date(), 10.00);

		try {
			alertService.save(alert);
			fail();
		} catch (AlertException expected) {
			assertEquals("A descrição é obrigatória", expected.getMessage());
		}

	}		
	
	@Test
	public void whenSaveAlertFieldDescriptionEmpty_thenExIsThrown() {
		Alert alert = new Alert("", new Date(), 10.00);

		try {
			alertService.save(alert);
			fail();
		} catch (AlertException expected) {
			assertEquals("A descrição é obrigatória", expected.getMessage());
		}

	}	
	
	@Test
	public void whenSaveAlertFieldTemperatureValueNull_thenExIsThrown() {
		Alert alert = new Alert("Sensor1", new Date(), null);

		try {
			alertService.save(alert);
			fail();
		} catch (AlertException expected) {
			assertEquals("O valor da temperatura é obrigatório", expected.getMessage());
		}

	}		
	
	@Test
	public void whenSaveAlertFieldTemperatureValueIncorrect_thenExIsThrown() {
		Alert alert = new Alert("Sensor1", new Date(), 0.00);

		try {
			alertService.save(alert);
			fail();
		} catch (AlertException expected) {
			assertEquals("O campo Valor tem que ser maior que 0.00", expected.getMessage());
		}

	}		
	
}
