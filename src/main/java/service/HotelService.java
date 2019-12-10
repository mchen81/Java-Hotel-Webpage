package service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dao.HotelDao;
import dao.bean.Hotel;
import dao.interfaces.HotelDaoInterface;
import service.bean.TouristAttraction;
import service.interfaces.HotelServiceInterface;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.util.*;

public class HotelService implements HotelServiceInterface {

    private HotelDaoInterface hotelDao;

    private Map<String, Hotel> hotelsCache;

    public HotelService() {
        hotelDao = new HotelDao();
        hotelsCache = new HashMap<>();
        loadAllHotels();
    }

    @Override
    public Hotel findHotelById(String id) {
        return hotelsCache.get(id);
    }

    @Override
    public List<Hotel> findHotelsByKeyWords(String city, String name) {
        List<Hotel> result = new ArrayList<>();
        boolean cityIsEmpty = null == city || city.isEmpty();
        boolean nameIsEmpty = null == name || name.isEmpty();
        for (Hotel hotel : hotelsCache.values()) {
            if ((cityIsEmpty || hotel.getCity().equals(city)) && (nameIsEmpty || hotel.getName().contains(name))) {
                result.add(hotel);
            }
        }
        return result;
    }

    @Override
    public List<TouristAttraction> findTouristAttraction(String hotelId, double miles) {
        Hotel hotel = hotelsCache.get(hotelId);
        if (hotel == null) return null;
        if (miles <= 0) return new ArrayList<>();

        String host = "https://maps.googleapis.com/";
        String path = "maps/api/place/textsearch/json";
        String query = "?query=tourist%20attractions+in+" + hotel.getCity().replace(" ", "%20");
        String queryLocation = String.format("&location=%.6f,%.6f&", hotel.getLatitude(), hotel.getLongitude());
        String queryRaduis = String.format("radius=%.1f", miles * 1609.0);
        String myApi = "&key=" + "AIzaSyDEvPZcqj3aXoII9X8eZc6xbXxnm3AxtD0";
        String pathResourceQuery = path + query + queryLocation + queryRaduis + myApi;
        System.out.println(host + pathResourceQuery);
        return parseUrlToAttractions(host + pathResourceQuery);

    }

    @Override
    public List<Hotel> findAllHotels() {
        return new ArrayList<>(hotelsCache.values());
    }

    /**
     * Takes a host and a string containing path/resource/query and creates a
     * string of the HTTP GET request
     *
     * @param host              Hots
     * @param pathResourceQuery path resource query
     * @return
     */
    private String getRequest(String host, String pathResourceQuery) {
        String request = "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator() // GET
                // request
                + "Host: " + host + System.lineSeparator() // Host header required for HTTP/1.1
                + "Connection: close" + System.lineSeparator() // make sure the server closes the
                // connection after we fetch one page
                + System.lineSeparator();
        return request;
    }


    /**
     * parse a url into a list of attractions
     *
     * @param urlString
     */
    private List<TouristAttraction> parseUrlToAttractions(String urlString) {
        //System.out.println(urlString);
        URL url;
        PrintWriter out = null;
        BufferedReader in = null;
        SSLSocket socket = null;
        try {
            url = new URL(urlString);
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            // HTTPS uses port 443
            socket = (SSLSocket) factory.createSocket(url.getHost(), 443);
            // output stream for the secure socket
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String request = getRequest(url.getHost(), url.getPath() + "?" + url.getQuery());
            // System.out.println("Request: " + request);
            out.println(request); // send   a request to the server
            out.flush();
            // input stream for the secure socket.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // use input stream to read server's response
            String line;
            StringBuilder jsonData = new StringBuilder();
            boolean jsonStart = false;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("{")) {
                    jsonStart = true;
                }
                if (jsonStart) {
                    jsonData.append(line);
                }
            }
            //  jsonReader starts from tourist attraction array, parse the jsonReader to an array of TouristAttraction
            TouristAttraction[] touristAttractionArray = parseJsonToTouristAttractions(jsonData.toString());
            return Arrays.asList(touristAttractionArray);
        } catch (IOException e) {
            System.out.println("An IOException occured while writing to the socket stream or reading from the stream: " + e);
        } catch (Exception e) {
            System.out.println("An Exception happen:" + e);
        } finally {
            try {
                // close the streams and the socket
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("An exception occured while trying to close the streams or the socket: " + e);
            }
        }
        return new ArrayList<>();
    }

    /**
     * parse jsonData of String to an array of TouristAttraction
     *
     * @param jsonData JsonData in String
     * @return an array of TouristAttractions
     * @throws IOException
     */
    private TouristAttraction[] parseJsonToTouristAttractions(String jsonData) throws IOException {
        //---------parse json data to gson
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new StringReader(jsonData)); // read the json data from html
        jsonReader.beginObject();
        while (jsonReader.hasNext()) { // start the jsonReader from "results", the value of results is an json array
            String basicInfo = jsonReader.nextName();
            if (basicInfo.equals("results")) {
                break;
            } else {
                jsonReader.skipValue();
            }
        }
        return gson.fromJson(jsonReader, TouristAttraction[].class);
    }


    /**
     * load hotel data into hotels cache
     */
    private void loadAllHotels() {
        for (Hotel hotel : hotelDao.getAllHotels()) {
            hotelsCache.put(hotel.getId(), hotel);
        }
    }
}







