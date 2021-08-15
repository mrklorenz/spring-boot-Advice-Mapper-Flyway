package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.hamcrest.Matchers;
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
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void should_return_all_employees_when_get_all_employees_API() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gronk"))
                .andExpect(jsonPath("$[0].age").value(33))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[2].name").value("Linne"))
                .andExpect(jsonPath("$[2].age").value(21))
                .andExpect(jsonPath("$[2].gender").value("female"));
    }

    @Test
    public void should_return_employee_when_get_employee_given_employee_id() throws Exception {
        //given
        int id = 1;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeID}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gronk"))
                .andExpect(jsonPath("$.age").value(33))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender_given_employee_gender() throws Exception {
        //given
        String gender = "male";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].gender", Matchers.hasItems("male")));
    }

    @Test
    public void should_return_employees_when_get_employee_by_pagination_given_page_index_and_size() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", "1").param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gronk"))
                .andExpect(jsonPath("$[1].name").value("Kees"))
                .andExpect(jsonPath("$[2].name").value("Linne"));
    }

    @Test
    public void should_add_employees_when_add_employee() throws Exception {
        //given
        String newEmployee = "{\n" +
                "    \"name\" : \"Mang Goryo\",\n" +
                "    \"age\" : 101,\n" +
                "    \"gender\" : \"male\",\n" +
                "    \"salary\" : \"1\",\n" +
                "    \"companyid\" : 2\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(jsonPath("$.name").value("Mang Goryo"))
                .andExpect(jsonPath("$.age").value("101"))
                .andExpect(jsonPath("$.salary").value("1"));
    }

    @Test
    public void should_update_employee_when_update_employee_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(60, "DJ Khaled", 31, "male", 9090, 2);
        Employee savedEmployee = employeeRepository.save(employee);

        String updateEmployeeDetails = "{\n" +
                "    \"name\" : \"DJ Mrk\",\n" +
                "    \"age\" : 23,\n" +
                "    \"gender\" : \"male\",\n" +
                "    \"salary\" : \"999\",\n" +
                "    \"companyid\" : 2\n" +
                "}";
        int id = savedEmployee.getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeDetails))
                .andExpect(jsonPath("$.name").value("DJ Mrk"))
                .andExpect(jsonPath("$.age").value("23"))
                .andExpect(jsonPath("$.salary").value("999"));
    }

    @Test
    public void should_remove_employee_when_delete_employee_by_id_given_employee_id() throws Exception {
        //given
        int id = employeeRepository.findAll().get(employeeRepository.findAll().size()-1).getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


}
