package com.barry.global.exception.controller;

import com.barry.global.exception.common.ExchangeReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    public static final String REQ_HEADERNAME_USID = "usid";
    public static final String REQ_HEADERNAME_VIN = "bmw-vin";
    public static final String REQ_HEADERNAME_LONGVIN = "bmw-longvin";
    private final static String REQ_HEADERNAME_REASON = "reason";

    @RequestMapping(path = "/api/v1/vehicleusermapping", method = RequestMethod.POST)
    public void setDriverForVin(@RequestHeader(value = REQ_HEADERNAME_USID) @NotNull String usId,
                                @RequestHeader(value = REQ_HEADERNAME_LONGVIN) @NotNull @NotEmpty String vin,
                                @RequestHeader(value = REQ_HEADERNAME_REASON) ExchangeReason reason, HttpServletRequest request) {
        try{
            logger.info("=========setDriverForVin============>usId:{}, vin:{}, reason:{}" ,usId ,vin ,reason);
        }catch(HttpClientErrorException e){
            logger.error("setDriverForVin error {} "+e.getMessage(), e);
        }
    }

}
