package com.csc340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CatController {

    @GetMapping("/random-cat")
    public Object getRandomCat() {
        try {
            // api url to fetch a random cat image
            String urlCatImage = "https://api.thecatapi.com/v1/images/search";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(urlCatImage, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            // the response from the above API is a JSON Array which we loop through
            for (JsonNode catImage : root) {
                // extract relevant info from the response and print to the console
                String id = catImage.get("id").asText();
                String url = catImage.get("url").asText();
                int width = catImage.get("width").asInt();
                int height = catImage.get("height").asInt();
            }

            return root;

            /**
             * Example response:
             * [
             *     {
             *         "id": "ae2",
             *         "url": "https://cdn2.thecatapi.com/images/ae2.jpg",
             *         "width": 480,
             *         "height": 640
             *     }
             * ]
             */

        } catch (JsonProcessingException ex) {
            // log error in case of failure
            Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
            return "Error fetching random cat image";
        }
    }

}