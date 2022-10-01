package io.github.tubean.eureka.gallery;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.github.tubean.eureka.clients.image.ImageClient;
import io.github.tubean.eureka.gallery.entity.Gallery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GalleryService {

    private final ImageClient imageClient;


    @HystrixCommand(fallbackMethod = "fallback")
    public Gallery getGallery(int id) {
        log.info("Creating gallery object ... ");
        // create gallery object
        Gallery gallery = new Gallery();
        gallery.setId(id);

        // get list of available images
        @SuppressWarnings("unchecked")    // we'll throw an exception from image service to simulate a failure
        List<Object> images = imageClient.getImages();
        //restTemplate.getForObject("http://image-service/images/", List.class);
        gallery.setImages(images);

        log.info("Returning images ... ");
        return gallery;
    }

    public Gallery fallback(int galleryId, Throwable hystrixCommand) {
        return new Gallery(galleryId);
    }
}
