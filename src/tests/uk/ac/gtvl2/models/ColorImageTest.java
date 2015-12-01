package uk.ac.gtvl2.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by leniglo on 01/12/15.
 */
public class ColorImageTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetName() throws Exception {
        ColorImage image = new ColorImage(10, 10);
        String testName = "TEST_NAME";
        String testName2 = "OTHER_TEST_NAME";
        image.setName(testName);
        assertTrue("setName() does change the name.", image.getName().equals(testName));
        assertFalse("setName() doesn't change the name to a wrong one.", image.getName().equals(testName2));
    }

    @Test
    public void testGetName() throws Exception {
        ColorImage image = new ColorImage(10, 10);
        String testName = "TEST_NAME";
        String testName2 = "OTHER_TEST_NAME";
        image.setName(testName);
        assertTrue("getName() does return the name.", image.getName().equals(testName));
        assertFalse("getName() doesn't change the name to a wrong one.", image.getName().equals(testName2));
    }

    @Test
    public void testSetPixel() throws Exception {
        ColorImage image = new ColorImage(10, 10);
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;
        image.setPixel(1, 7, color1);
        image.setPixel(2, 7, color2);
        image.setPixel(3, 7, color1);
        image.setPixel(4, 7, color2);
        image.setPixel(5, 7, color1);
        assertEquals("Colors are the same", color1, image.getPixel(1, 7));
        assertEquals("Colors are the same", color2, image.getPixel(4, 7));
    }

    @Test
    public void testGetPixel() throws Exception {
        ColorImage image = new ColorImage(10, 10);
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;
        image.setPixel(1, 7, color1);
        image.setPixel(2, 7, color2);
        image.setPixel(3, 7, color1);
        image.setPixel(4, 7, color2);
        image.setPixel(5, 7, color1);
        assertEquals("Colors are the same", color1, image.getPixel(1, 7));
        assertEquals("Colors are the same", color2, image.getPixel(4, 7));
    }
}