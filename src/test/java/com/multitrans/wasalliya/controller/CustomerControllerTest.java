package com.multitrans.wasalliya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multitrans.wasalliya.model.dto.CustomerDTO;
import com.multitrans.wasalliya.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerSer;


    @Test
    void save_shouldReturn201_WhenDataIsValid() throws Exception {

        CustomerDTO dtoToSave = new CustomerDTO(null, "zabba", "chta sabba", 12.50, 10.50, "l7wa", null);

        CustomerDTO dtoSaved = new CustomerDTO(1L, "zabba", "chta sabba", 12.50, 10.50, "l7wa", null);

        when( customerSer.createCustomer( any(CustomerDTO.class) ) ).thenReturn( dtoSaved );

        mockMvc.perform(
                        post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoToSave))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("zabba"));
    }

    @Test
    void save_shouldReturn400_WhenNameIsNull() throws Exception {
        CustomerDTO dtoWithNullName = new CustomerDTO(null, null, "chta sabba", 12.50, 10.50, "l7wa", null);

        mockMvc.perform(
                        post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoWithNullName))
                )
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.name").value("Customer must have a name!"));
    }
}