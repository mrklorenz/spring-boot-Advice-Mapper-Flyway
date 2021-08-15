package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void should_return_all_companies_when_get_all_companies() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("COSCO"))
                .andExpect(jsonPath("$[1].name").value("MAERSK"));

    }

    @Test
    public void should_return_company_when_get_company_given_company_id() throws Exception {
        //given
        int id = 2;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("COSCO"));
    }

    @Test
    public void should_return_employees_when_get_employees_by_company_id_given_company_id() throws Exception {
        //given
        int id = 2;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gronk"));
    }

    @Test
    public void should_return_companies_when_get_companies_by_pagination_given_page_index_and_size() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies")
                .param("pageIndex", "1").param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("COSCO"))
                .andExpect(jsonPath("$[1].name").value("MAERSK"));
    }

    @Test
    public void should_add_company_when_add_company() throws Exception {
        //given
        String newCompany = "{\n" +
                "    \"name\": \"GOOGLE\"\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newCompany))
                .andExpect(jsonPath("$.name").value("GOOGLE"));
    }

    @Test
    public void should_update_company_when_update_company_given_company_id() throws Exception {
        //given
        Company company = new Company(33, "AMAZON");
        Company savedCompany = companyRepository.save(company);

        String updateCompanyDetails = "{\n" +
                "    \"name\" : \"AMAZON\"\n" +
                "}";
        int id = savedCompany.getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCompanyDetails))
                .andExpect(jsonPath("$.name").value("AMAZON"));
    }

    @Test
    public void should_remove_company_when_delete_company_by_id_given_company_id() throws Exception {
        //given
        int id = companyRepository.findAll().get(companyRepository.findAll().size()-1).getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }




}
