package com.my.kramarenko.taxService.web.command.util;

import com.my.kramarenko.taxService.web.Util;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.util.UtilForTests;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilTest {

    @Test
    void getIntValue() {
        assertEquals(1, Util.getIntValue(0, "1"));
        assertEquals(0, Util.getIntValue(0, "vfr"));
        assertEquals(1, Util.getIntValue(0, "  1 "));
    }

    /**
     * Generate simple list {1,2,3}
     *
     * @return simple List example
     */
    private static List<String> generateInputList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        return list;
    }

    @Test
    void setReportsWithPagination() {
        HttpServletRequest request = UtilForTests.setMockito();
        List<String> list = generateInputList();

        Mockito.when(request.getParameter("recordsPerPage")).thenReturn("2");
        Mockito.when(request.getParameter("selectedPage")).thenReturn("1");
        Util.setReportsWithPagination(list, request);
        assertEquals(2, request.getAttribute("noOfPages"));
        assertEquals(2, request.getAttribute("recordsPerPage"));
        List<String> expectedList = list.subList(0, 2);
        assertEquals(expectedList, request.getAttribute("paginationList"));
    }

    @Test
    void setReportsWithPaginationWithoutParameters() {
        HttpServletRequest request = UtilForTests.setMockito();
        List<String> list = generateInputList();

        List<String> expectedList = list.subList(0, 3);
        Util.setReportsWithPagination(list, request);
        assertEquals(1, request.getAttribute("noOfPages"));
        assertEquals(10, request.getAttribute("recordsPerPage"));
        assertEquals(expectedList, request.getAttribute("paginationList"));
    }

    @Test
    void setUserFieldsTest() {
        User user = new User();
        user.setId(0);

        HttpServletRequest request = UtilForTests.setMockito();

        String email = "123@123";
        String is_individual = "true";
        String code = "123456";
        String company_name = "company name";
        String first_name = "first name";
        String last_name = "last name";
        String patronymic = "patronymic";
        String phone = "0987654321";

        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("is_individual")).thenReturn(is_individual);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("company_name")).thenReturn(company_name);
        Mockito.when(request.getParameter("first_name")).thenReturn(first_name);
        Mockito.when(request.getParameter("last_name")).thenReturn(last_name);
        Mockito.when(request.getParameter("patronymic")).thenReturn(patronymic);
        Mockito.when(request.getParameter("phone")).thenReturn(phone);

        Util.setUserFieldsFromRequest(user, request);

        assertEquals(phone,user.getPhone());
        assertEquals(patronymic,user.getPatronymic());
        assertEquals(last_name,user.getLastName());
        assertEquals(first_name,user.getFirstName());
        assertEquals(company_name,user.getCompanyName());
        assertEquals(code,user.getCode());
        assertEquals(true,user.isIndividual());
        assertEquals(email,user.getEmail());
    }
}