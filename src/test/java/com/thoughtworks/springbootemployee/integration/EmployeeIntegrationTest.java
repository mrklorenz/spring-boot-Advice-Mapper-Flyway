package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Employee;
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
                .andExpect(jsonPath("$[0].name").value("Mark"))
                .andExpect(jsonPath("$[1].name").value("Karyn"))
                .andExpect(jsonPath("$[2].name").value("Brodie"));
    }

    @Test
    public void should_return_employee_when_get_employee_given_employee_id() throws Exception {
        //given
        int id = 1;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeID}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mark"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender_given_employee_gender() throws Exception {
        //given
        String gender = "Male";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].gender", Matchers.hasItems("Male")));
    }

    @Test
    public void should_return_employees_when_get_employee_by_pagination_given_page_index_and_size() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", "1").param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mark"))
                .andExpect(jsonPath("$[1].name").value("Karyn"))
                .andExpect(jsonPath("$[2].name").value("Brodie"));
    }

    @Test
    public void should_add_employees_when_add_employee() throws Exception {
        //given
        String newEmployee = "{\n" +
                "    \"name\" : \"Mang Goryo\",\n" +
                "    \"age\" : 101,\n" +
                "    \"gender\" : \"male\",\n" +
                "    \"salary\" : \"1\",\n" +
                "    \"companyId\" : 2\n" +
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
        Employee employee = new Employee(10, "SeanBolus", 71, "Female", 6969, 3);
        Employee savedEmployee = employeeRepository.save(employee);
        String updateEmployeeDetails = "{\n" +
                "    \"name\" : \"SeanBolus\",\n" +
                "    \"age\" : 71,\n" +
                "    \"gender\" : \"Male\",\n" +
                "    \"salary\" : \"9999\",\n" +
                "    \"companyId\" : 2\n" +
                "}";
        int id = savedEmployee.getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeDetails))
                .andExpect(jsonPath("$.name").value("SeanBolus"))
                .andExpect(jsonPath("$.age").value("71"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value("9999"));
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
                .andExpect(status().isNotFound());
    }
}
