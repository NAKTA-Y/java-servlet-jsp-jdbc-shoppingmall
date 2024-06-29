package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseController {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException;
}