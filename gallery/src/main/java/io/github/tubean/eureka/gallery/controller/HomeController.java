package io.github.tubean.eureka.gallery.controller;


import io.github.tubean.eureka.gallery.GalleryService;
import io.github.tubean.eureka.gallery.entity.Gallery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
	
	@Autowired
	private Environment env;

	private final GalleryService galleryService;

	@RequestMapping("/")
	public String home() {
		// This is useful for debugging
		// When having multiple instance of gallery service running at different ports.
		// We load balance among them, and display which instance received the request.
		return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
	}

	@RequestMapping("/{id}")
	public Gallery getGallery(@PathVariable final int id) {
		return galleryService.getGallery(id);
	}
	
	// -------- Admin Area --------
	// This method should only be accessed by users with role of 'admin'
	// We'll add the logic of role based auth later
	@RequestMapping("/admin")
	public String homeAdmin() {
		return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
	}


}