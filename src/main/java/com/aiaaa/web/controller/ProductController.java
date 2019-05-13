package com.aiaaa.web.controller;

import com.aiaaa.annotation.LuxLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("123")
@RestController
@RequestMapping("/pro")
public class ProductController {

    private static Logger logger =  LoggerFactory.getLogger(IndexController.class);

    @ApiOperation(value = "获取产品信息",notes = "根据产品ID获取产品信息")
    @ApiImplicitParam(name = "id",value = "产品ID",required = false,dataType = "Integer")
    @LuxLog("sel")
    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id){

        return "product info";
    }


}
