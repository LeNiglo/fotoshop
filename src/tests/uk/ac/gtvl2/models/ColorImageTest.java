package uk.ac.gtvl2.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by leniglo on 01/12/15.
 */
public class ColorImageTest {

    private ColorImage image;
    private final Color red = Color.RED;
    private final Color blue = Color.BLUE;

    @Before
    public void setUp() throws Exception {
        image = new ColorImage(10, 10);
        image.setName("TestPicture");
    }

    @After
    public void tearDown() throws Exception {
        image = null;
    }

    @Test
    public void testSetPixelInsidePic() throws Exception {
        image.setPixel(0, 0, red);
        image.setPixel(9, 9, blue);
        image.setPixel(0, 9, red);
        image.setPixel(9, 0, blue);
        image.setPixel(5, 5, red);
    }

    @Test
    public void testSetPixelOutsidePic() throws Exception {
        image.setPixel(1, 10, red);
        image.setPixel(10, 1, red);
        image.setPixel(10, 10, blue);
        image.setPixel(100, 100, blue);
        image.setPixel(-1, -1, red);
    }

    @Test
    public void testSetPixelColorError() throws Exception {
        image.setPixel(5, 5, null);
    }

    @Test
    public void testGetPixelInsidePic() throws Exception {
        image.setPixel(0, 0, red);
        image.setPixel(9, 9, blue);
        image.setPixel(0, 9, red);
        image.setPixel(9, 0, blue);
        image.setPixel(5, 5, red);
        assertEquals("Colors are the same", red, image.getPixel(5, 5));
        assertEquals("Colors are the same", blue, image.getPixel(9, 0));
    }

    @Test
    public void testGetPixelOutsidePic() throws Exception {
        assertNull("Color is out of the picture", image.getPixel(10, 10));
        assertNull("Color is out of the picture", image.getPixel(100, 100));
        assertNull("Color is out of the picture", image.getPixel(-1, 0));
        assertNull("Color is out of the picture", image.getPixel(-1, -1));
    }
}