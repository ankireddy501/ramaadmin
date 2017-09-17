package com.rama.admin.cucumber.stepdefs;

import com.rama.admin.RamaadminApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = RamaadminApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
