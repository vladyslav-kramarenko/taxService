package com.my.kramarenko.taxService;

import com.my.kramarenko.taxService.util.UtilForTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void getIntValue() {
        assertEquals(1, Util.getIntValue(0, "1"));
        assertEquals(0, Util.getIntValue(0, "vfr"));
        assertEquals(1, Util.getIntValue(0, "  1 "));
    }

    private static List<String> generateInputList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        return list;
    }

    @Test
    void setReportsWithPagination() {
        HttpServletRequest request = UtilForTest.setMockito();
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
        HttpServletRequest request = UtilForTest.setMockito();
        List<String> list = generateInputList();

        List<String> expectedList = list.subList(0, 3);
        Util.setReportsWithPagination(list, request);
        assertEquals(1, request.getAttribute("noOfPages"));
        assertEquals(10, request.getAttribute("recordsPerPage"));
        assertEquals(expectedList, request.getAttribute("paginationList"));
    }
}