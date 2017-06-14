package ch02DataAcquisiotion.handlers;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.*;
import util.Props;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aspire on 13.06.2017.
 */
public class FlickrExample {
    public static void main(String[] args) throws IOException, FlickrException {
        String apikey = Props.getProperty("flickrKey");
        String secret = Props.getProperty("flickrSecret");

        Flickr flickr = new Flickr(apikey, secret, new REST());

        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setBBox("-180", "-90", "180", "90");
        searchParameters.setMedia("photos");

        PhotosInterface pi = new PhotosInterface(apikey, secret, new REST());
        PhotoList<Photo>list = pi.search(searchParameters, 10, 0);

        System.out.println("Image list");
        for (int i = 0; i < list.size(); i++) {
            Photo photo = list.get(i);
            System.out.println("Image: " + i
            + "\nTitle: " + photo.getTitle()
            + "\nMedia: " + photo.getOriginalFormat()
            + "\nPublic: " + photo.isPublicFlag()
            + "\nUrl: " + photo.getUrl());
        }

        Photo currentPhoto = list.get(0);
//        BufferedImage bufferedImage = pi.getImage(currentPhoto.getUrl());
        BufferedImage bufferedImage = pi.getImage(currentPhoto, Size.SMALL);
        File outputfile = new File("image.jpg");
        ImageIO.write(bufferedImage, "jpg", outputfile);
    }
}
