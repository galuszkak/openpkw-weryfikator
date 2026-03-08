package org.openpkw.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openpkw.web.controllers.TestController;

/**
 *
 * @author Waldemar
 */
public class EchoControllerTest {

    public EchoControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of echo method, of class EchoController.
     */
    @Test
    public void testEcho() {
        System.out.println("echo");
        Map<String, String> object = null;
        TestController instance = new TestController();
        Map<String, String> expResult = null;
        Map<String, String> result = instance.echo(object);
        assertEquals(expResult, result);
    }
}
