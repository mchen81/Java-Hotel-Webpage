package common.parser;

import com.google.gson.stream.JsonReader;
import common.bean.ReviewJsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReviewParser {

    private List<ReviewJsonObject> reviews;

    /**
     * parse all json files under a directory, and stored in reviewsBook
     *
     * @param dirPath a directory's path
     */
    public void parse(String dirPath) throws IOException {
        reviews = new ArrayList<>();
        List<String> files = readDir(dirPath);
        for (String filepath : files) {
            List<ReviewJsonObject> reviewList = parseSingleJson(filepath);
            reviews.addAll(reviewList);
        }
    }

    /**
     * parse a single json file to a List of a hotel's reviews
     *
     * @param filePath a json filepath
     * @return a List of a hotel's reviews
     */
    private List<ReviewJsonObject> parseSingleJson(String filePath) {
        List<ReviewJsonObject> result = new ArrayList<>();
        String hotelId = "";
        try (JsonReader jsonReader = new JsonReader(new FileReader(filePath))) {
            jsonReader.beginObject(); // begin first json object (start with reviewDetails)
            while (jsonReader.hasNext()) {
                String reviewName = jsonReader.nextName();
                if (reviewName.equals("reviewDetails")) {
                    jsonReader.beginObject(); // begin obj2 (start with startIndex)
                    while (jsonReader.hasNext()) { // in obj2 - review details
                        String detailName = jsonReader.nextName();
                        if (detailName.equals("reviewCollection")) {
                            jsonReader.beginObject(); // begin obj3
                            while (jsonReader.hasNext()) { // in obj3- review collection
                                String collectionName = jsonReader.nextName();
                                if (collectionName.equals("review")) {
                                    // in review array
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) { // in arry
                                        jsonReader.beginObject();
                                        ReviewJsonObject review = new ReviewJsonObject();
                                        while (jsonReader.hasNext()) {
                                            String reviewInfoName = jsonReader.nextName();
                                            switch (reviewInfoName) {
                                                case "hotelId":
                                                    review.setHotelId(jsonReader.nextString());
                                                    break;
                                                case "reviewId":
                                                    review.setReviewId(jsonReader.nextString());
                                                    break;
                                                case "title":
                                                    review.setTitle(jsonReader.nextString());
                                                    break;
                                                case "reviewText":
                                                    review.setReviewText(jsonReader.nextString());
                                                    break;
                                                case "userNickname":
                                                    review.setUserNickname(jsonReader.nextString());
                                                    break;
                                                case "ratingOverall":
                                                    review.setRatingOverall(jsonReader.nextInt());
                                                    break;
                                                case "reviewSubmissionTime":
                                                    review.setSubmissionTime(jsonReader.nextString());
                                                    break;
                                                default:
                                                    jsonReader.skipValue();
                                            }
                                        }
                                        result.add(review);
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                } else {
                                    jsonReader.skipValue(); // obj 3
                                }
                            }
                            jsonReader.endObject(); // end obj3
                        } else if (detailName.equals("reviewSummaryCollection")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String summaryName = jsonReader.nextName();
                                if (summaryName.equals("reviewSummary")) {
                                    jsonReader.beginArray();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        String summaryInfoName = jsonReader.nextName();
                                        if (summaryInfoName.equals("hotelId")) {
                                            hotelId = jsonReader.nextString();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }
                                    jsonReader.endObject();
                                    jsonReader.endArray();
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();// end obj2
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject(); // obj 1
        } catch (IOException e) {
            System.out.println("Cannot find or open the file: " + e);
        }
        return result;
    }

    /**
     * get all file path in a directory
     *
     * @param dir the path of a dir
     * @return a string list of json file paths
     */
    private List<String> readDir(String dir) {
        Path dirPath = Paths.get(dir);
        List<String> filePaths = new ArrayList<>();
        try {
            DirectoryStream<Path> filesList = Files.newDirectoryStream(dirPath);
            for (Path p : filesList) {
                if (p.toString().endsWith(".json")) {
                    filePaths.add(p.toString());
                } else if (Files.isDirectory(p)) {
                    filePaths.addAll(readDir(p.toString()));
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot find review json file");
        }
        return filePaths;
    }

    public List<ReviewJsonObject> getReviews() {
        return new ArrayList<>(reviews);
    }
}
