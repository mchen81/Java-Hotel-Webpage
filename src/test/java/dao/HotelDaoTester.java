package dao;

import dao.bean.Hotel;
import dao.interfaces.HotelDaoInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HotelDaoTester {

    private HotelDaoInterface hotelDao;

    @Before
    public void setup() {
        hotelDao = new HotelDao();
    }

    @Test
    public void findHotelByIdTest() {
        Hotel hotel = hotelDao.getHotelByHotelId("360");
        Assert.assertEquals("360", hotel.getId());
        Assert.assertEquals("Courtyard by Marriott San Francisco Union Square", hotel.getName());
        Assert.assertEquals("USA", hotel.getCountry());
        Assert.assertEquals("CA", hotel.getState());
        Assert.assertEquals("San Francisco", hotel.getCity());
        Assert.assertEquals("761 Post Street", hotel.getAddress());
        Assert.assertEquals("37.787489", hotel.getLatitude().toString());
        Assert.assertEquals("-122.414263", hotel.getLongitude().toString());
    }

    @Test
    public void findAllHotelsTest(){
        List<Hotel> hotels = hotelDao.getAllHotels();
        Assert.assertEquals(157,hotels.size());

    }

}
