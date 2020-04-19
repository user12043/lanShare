package ogr.user12043.lanShare.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created on 15.04.2020 - 17:19
 * part of project lanShare
 *
 * @author user12043
 */
public class UtilsTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getTimeAsStringTest() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2020, Calendar.MARCH, 20, 19, 38, 12);
        Date date = calendar.getTime();
        String expectedString = "20/03/2020 19:38:12";
        Assert.assertEquals(expectedString, Utils.getTimeAsString(date));
    }

    @Test
    public void encodeFileNameTest() throws UnsupportedEncodingException {
        String fileName = "/mnt/windows/Users/user12043/Desktop/türkçe-karakter-ve boşluk içeren dosya adı.txt";
        String encoded = Utils.encodeFileName(fileName, false);
        Assert.assertEquals("/mnt/windows/Users/user12043/Desktop/tÃ¼rkÃ§e-karakter-ve boÅ\u009Fluk iÃ§eren dosya adÄ±.txt", encoded);
    }

    @Test
    public void encodeFileNameTestIE() throws UnsupportedEncodingException {
        String fileName = "/mnt/windows/Users/user12043/Desktop/türkçe-karakter-ve boşluk içeren dosya adı.txt";
        String encoded = Utils.encodeFileName(fileName, true);
        Assert.assertEquals("/mnt/windows/Users/user12043/Desktop/türkçe-karakter-ve boºluk içeren dosya ad?.txt", encoded);
    }
}
