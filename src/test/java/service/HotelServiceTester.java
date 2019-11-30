package service;

import dao.bean.Hotel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.interfaces.HotelServiceInterface;

import java.util.List;

public class HotelServiceTester {

    private HotelServiceInterface hotelService;

    @Before
    public void setUp() {
        hotelService = ServicesSingleton.getHotelService();
    }

    @Test
    public void findByKeywordsTest() {
        List<Hotel> hotels = hotelService.findHotelsByKeyWords("San Francisco", "Merid");
        Assert.assertEquals("Le Meridien San Francisco", hotels.get(0).getName());
    }
}
