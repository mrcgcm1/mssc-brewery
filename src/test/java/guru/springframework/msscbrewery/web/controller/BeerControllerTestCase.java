package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTestCase {

    @MockBean
    BeerService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    BeerDto validBeer;

    @Before
    public void setUp(){
        validBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("Birra").beerStyle("Pale Ale").upc(1L).build();
    }

    @Test
    public void ottieniUnaBirra() throws Exception {
        given(service.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
//                .andExpect((ResultMatcher) jsonPath("$.id", is(validBeer.getId().toString())));

    }

    @Test
    public void inserisciUnaBirra() throws Exception {

        BeerDto beerDto = validBeer;
        beerDto.setId(null);

        BeerDto savedBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("mia birra").beerStyle("My Style").build();

        String beerToJson = mapper.writeValueAsString(beerDto);

        given(service.saveNewBeer(any())).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beer/").contentType(MediaType.APPLICATION_JSON).content(beerToJson))
                .andExpect(status().isCreated());

    }
    @Test
    public void aggiornaUnaBirra() throws Exception {

        BeerDto beerDto = validBeer;

        beerDto.setId(null);
        String beerToJson = mapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(beerToJson))
                .andExpect(status().isNoContent());

        then(service).should().updateBeer(any(), any());
    }

}
