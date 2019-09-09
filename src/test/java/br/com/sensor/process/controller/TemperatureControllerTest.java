package br.com.sensor.process.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sensor.process.repository.TemperatureRepository;
import br.com.sensor.process.service.TemperatureService;
import br.com.sensor.temperature.model.Temperature;

/**
 * Examples: https://www.youtube.com/watch?v=RbZvXCAtMus
*/
@RunWith(SpringRunner.class)
@WebMvcTest(TemperatureController.class)
public class TemperatureControllerTest {

    @Autowired
	protected MockMvc mockMvc;
    
    @MockBean
	private TemperatureService temperatureService;
    
	@MockBean
	private TemperatureRepository temperatureRepository;
    
    
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSaveTemperature() throws Exception {
		Temperature temperature1 = new Temperature("abc","Sensor1", 15.10, new Date());

		when(temperatureService.save(Mockito.any(Temperature.class))).thenReturn(temperature1);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/temperature/save")
				.accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(temperature1))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/temperature/findTemperatureById/abc", response.getHeader(HttpHeaders.LOCATION));

	}	

	@Test
	public void testFindTemperatureAll() throws Exception {

		Temperature temperature1 = new Temperature("Sensor1", 15.10, new Date());
		Temperature temperature2 = new Temperature("Sensor2", 18.87, new Date());
        List<Temperature> temperatures = Arrays.asList(temperature1, temperature2);

        Mockito.when(temperatureService.findTemperatureAll()).thenReturn(temperatures);

		MvcResult result = mockMvc.perform(get("/temperature/findTemperatureAll"))
				.andExpect(status().isOk())
				.andReturn();
		
		System.out.println("result: " + result.getResponse().getContentAsString());
		
		JSONAssert.assertEquals(writeListToJsonArray(temperatures),
								result.getResponse().getContentAsString(), false);


	}
	
	protected String mapToJson(Object obj) 
			throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}	
	
	public String writeListToJsonArray(List<?> list) 
			throws IOException {  

	    final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();

	    mapper.writeValue(out, list);

	    final byte[] data = out.toByteArray();    
	    return new String(data);
	}

}
