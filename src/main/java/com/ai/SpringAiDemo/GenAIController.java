//package com.ai.SpringAiDemo;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//public class GenAIController {
//
//    private final ChatService chatService;
//    private final ImageService imageService;
//    private final RecipeService recipeService;
//
//    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
//        this.chatService = chatService;
//        this.imageService = imageService;
//        this.recipService = recipeService;
//    }
//
//    @GetMapping("/ask-ai") // FIXED: Removed '@'
//    public String getResponse(@RequestParam String prompt) {
//        return chatService.getResponse(prompt);
//    }
//
//    @GetMapping("/ask-ai-options") // FIXED: Removed '@'
//    public String getResponseOptions(@RequestParam String prompt) {
//        return chatService.getResponseOptions(prompt);
//    }
//    e
//    @GetMapping("/generate-image") // FIXED: Removed '@'
//    public List<String> generateImages(
//            HttpServletResponse response,
//            @RequestParam String prompt,
//            @RequestParam(defaultValue = "hd") String quality,
//            @RequestParam(defaultValue = "1") int n,
//            @RequestParam(defaultValue = "1024") int width,
//            @RequestParam(defaultValue = "1024") int height) throws IOException {
//
//        ImageResponse imageResponse = imageService.generateImage(prompt, quality, n, width, height);
//
//        List<String> imageUrls = imageResponse.getResults().stream()
//                .map(result -> result.getOutput().getUrl())
//                .toList();
//
//        return imageUrls;
//    }
//
//    @GetMapping("/recipe-creator") // FIXED: Removed '@'
//    public String recipeCreator(
//            @RequestParam String ingredients,
//            @RequestParam(defaultValue = "any") String cuisine,
//            @RequestParam(defaultValue = "") String dietaryRestriction) {
//        return recipeService.createRecipe(ingredients, cuisine, dietaryRestriction);
//    }
//}


package com.ai.SpringAiDemo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GenAIController {

    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {
        return chatService.getResponseOptions(prompt);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/generate-image")
    public org.springframework.http.ResponseEntity<byte[]> generateImages(
            @RequestParam String prompt,
            @RequestParam(defaultValue = "1") int n,
            @RequestParam(defaultValue = "1024") int width,
            @RequestParam(defaultValue = "1024") int height) {

        try {
            byte[] imageBytes = imageService.generateImage(prompt, "hd", n, width, height);

            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_PNG);
            headers.setCacheControl("no-cache, no-store, must-revalidate");

            return new org.springframework.http.ResponseEntity<>(imageBytes, headers, org.springframework.http.HttpStatus.OK);
        } catch (Exception e) {
            return new org.springframework.http.ResponseEntity<>(null, new org.springframework.http.HttpHeaders(), org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @GetMapping("/recipe-creator")
    public String recipeCreator(
            @RequestParam String ingredients,
            @RequestParam(defaultValue = "any") String cuisine,
            @RequestParam(defaultValue = "") String dietaryRestriction) {
        return recipeService.createRecipe(ingredients, cuisine, dietaryRestriction);
    }
}


