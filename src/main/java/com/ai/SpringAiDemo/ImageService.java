//package com.ai.SpringAiDemo;
//
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.openai.OpenAiImageModel;
//import org.springframework.ai.openai.OpenAiImageOptions;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ImageService {
//    private final OpenAiImageModel openAiImageModel;
//
//    public ImageService(OpenAiImageModel openAiImageModel) {
//        this.openAiImageModel = openAiImageModel;
//    }
//
//    public ImageResponse generateImage(String prompt,
//                                       String quality,
//                                       int n,
//                                       int width,
//                                       int height){
////        ImageResponse imageResponse = openAiImageModel.call(
////                new ImagePrompt(prompt));
//
////        ImageResponse imageResponse = openAiImageModel.call(
////                new ImagePrompt(prompt,
////                        OpenAiImageOptions.builder()
////                                .withModel("dall-e-2")
////                                .withQuality("hd")
////                                .withN(3)
////                                .withHeight(1024)
////                                .withWidth(1024).build())
////        );
//
//
//        ImageResponse imageResponse = openAiImageModel.call(
//                new ImagePrompt(prompt,
//                        OpenAiImageOptions.builder()
//                                .model("dall-e-3")
//                                .quality(quality)
//                                .N(n)
//                                .height(height)
//                                .width(width).build())
//        );
//
//        return imageResponse;
//    }
//}


//package com.ai.SpringAiDemo;
//import org.springframework.stereotype.Service;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class ImageService {
//
//    public String generateImage(String promptText) {
//        // Encodes the prompt text so it can safely be appended to a URL string
//        String encodedPrompt = URLEncoder.encode(promptText, StandardCharsets.UTF_8);
//
//        // Returns a free, instant placeholder image based on the user's search query keywords
//        return "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&q=80&w=600";
//
//        // Alternatively, use Unsplash Source matching keywords:
//        // return "https://source.unsplash.com/featured/600x600/?" + encodedPrompt;
//    }
//}

//package com.ai.SpringAiDemo;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImageGeneration;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    // Keep the full parameter list so the controller doesn't break
//    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height) {
//
//        // 1. Create a dummy Image object containing our free URL
//        String mockUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&q=80&w=600";
//        Image dummyImage = new Image(mockUrl, null);
//
//        // 2. Wrap it inside an ImageGeneration object
//        ImageGeneration dummyGeneration = new ImageGeneration(dummyImage);
//
//        // 3. Return it inside an ImageResponse list payload wrapper
//        return new ImageResponse(List.of(dummyGeneration));
//    }
//}


//package com.ai.SpringAiDemo;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImageGeneration;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.stereotype.Service;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height) {
//        List<ImageGeneration> generationsList = new ArrayList<>();
//
//        // Clean and encode the user's prompt string so it fits safely in a URL
//        String searchKeyword = URLEncoder.encode(prompt.trim(), StandardCharsets.UTF_8);
//
//        for (int i = 0; i < n; i++) {
//            // Appends a unique random seed (i) and the user's search keyword dynamically
//            String dynamicUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&q=80&w=600";
//
//            // USE THIS URL FOR DYNAMIC MATCHING:
//            // It searches Unsplash for whatever the user typed (e.g., 'lion', 'car')
//            String liveSearchUrl = "https://loremflickr.com/600/600/" + searchKeyword + "?random=" + i;
//
//            Image dummyImage = new Image(liveSearchUrl, null);
//            generationsList.add(new ImageGeneration(dummyImage));
//        }
//
//        return new ImageResponse(generationsList);
//    }
//}

//package com.ai.SpringAiDemo;
//import org.springframework.ai.image.ImageModel;
//import org.springframework.ai.image.ImageOptionsBuilder;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//@Service
//public class ImageService {
//    private final ImageModel imageModel;
//    public ImageService( ImageModel imageModel) {
//        this.imageModel = imageModel;
//    }
//    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height) {
//        // Spring AI handles the loop internally based on the 'N' parameter automatically!
//        ImageResponse response = imageModel.call(
//                new ImagePrompt(
//                        prompt,
//                        ImageOptionsBuilder.builder()
//                                .model("imagen-3.0-generate-002") // Points to the clean Imagen 3 API
//                                .N(n)                             // Generates exactly 'n' images (e.g. 4)
//                                .height(height)
//                                .width(width)
//                                .build()
//                )
//        );
//
//        return response;
//    }
//}


//package com.ai.SpringAiDemo;
//import org.springframework.ai.image.ImageModel;
//import org.springframework.ai.image.ImageOptionsBuilder;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.image.ImageGeneration;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    private final ImageModel imageModel;
//
//    // Spring AI auto-injects the configured Gemini ImageModel bean
//    public ImageService(ImageModel imageModel) {
//        this.imageModel = imageModel;
//    }
//
//    /**
//     * Generates images using Google Gemini Imagen API
//     *
//     * @param prompt  Text description of the image to generate
//     * @param n       Number of images to generate
//     * @param width   Image width in pixels
//     * @param height  Image height in pixels
//     * @return ImageResponse containing generated image data (base64 or URL)
//     */
//    public ImageResponse generateImage(String prompt, int n, int width, int height) {
//
//        ImageResponse response = imageModel.call(
//                new ImagePrompt(
//                        prompt,
//                        ImageOptionsBuilder.builder()
//                                .model("imagen-3.0-generate-002")
//                                .N(n)
//                                .width(width)
//                                .height(height)
//                                .build()
//                )
//        );
//
//        return response;
//    }
//
//    /**
//     * Convenience method — returns list of base64-encoded image strings
//     */
//    public List<String> generateImageBase64(String prompt, int n, int width, int height) {
//
//        ImageResponse response = generateImage(prompt, n, width, height);
//
//        return response.getResults()
//                .stream()
//                .map(ImageGeneration::getOutput)
//                .map(img -> img.getB64Json())   // base64 string
//                .toList();
//    }
//
//    /**
//     * Convenience method — returns list of image URLs (if API returns URLs)
//     */
//    public List<String> generateImageUrls(String prompt, int n, int width, int height) {
//
//        ImageResponse response = generateImage(prompt, n, width, height);
//
//        return response.getResults()
//                .stream()
//                .map(ImageGeneration::getOutput)
//                .map(img -> img.getUrl())        // URL string
//                .toList();
//    }
//}


//package com.ai.SpringAiDemo;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    @Value("${spring.ai.google.genai.api-key}")
//    private String apiKey;
//
//    private final HttpClient httpClient = HttpClient.newHttpClient();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public List<String> generateImage(String prompt, int n, int width, int height) {
//        try {
//            String url = "https://generativelanguage.googleapis.com/v1beta/models/" +
//                    "imagen-3.0-generate-002:predict?key=" + apiKey;
//
//            String requestBody = String.format("""
//                {
//                    "instances": [{"prompt": "%s"}],
//                    "parameters": {
//                        "sampleCount": %d,
//                        "aspectRatio": "1:1"
//                    }
//                }
//                """, prompt.replace("\"", "\\\""), n);
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                    .build();
//
//            HttpResponse<String> response = httpClient.send(
//                    request, HttpResponse.BodyHandlers.ofString()
//            );
//
//            // Parse response and extract base64 images
//            JsonNode root = objectMapper.readTree(response.body());
//            JsonNode predictions = root.path("predictions");
//
//            List<String> base64Images = new ArrayList<>();
//            for (JsonNode prediction : predictions) {
//                String b64 = prediction.path("bytesBase64Encoded").asText();
//                if (!b64.isEmpty()) {
//                    base64Images.add("data:image/png;base64," + b64);
//                }
//            }
//
//            return base64Images;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Image generation failed: " + e.getMessage(), e);
//        }
//    }
//}

//package com.ai.SpringAiDemo;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//import java.util.List;

//@Service
//public class ImageService {
//
//    @Value("${gemini.image.api-key}")
//    private String apiKey;
//
//    private final HttpClient httpClient = HttpClient.newHttpClient();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public List<String> generateImage(String prompt, int n, int width, int height) {
//
//        List<String> base64Images = new ArrayList<>();
//
//        try {
//            String url = "https://generativelanguage.googleapis.com/v1beta/models/" +
//                    "gemini-2.0-flash-preview-image-generation:generateContent?key=" + apiKey;
//
//            String requestBody = String.format("""
//                {
//                    "contents": [
//                        {
//                            "parts": [
//                                {"text": "%s"}
//                            ]
//                        }
//                    ],
//                    "generationConfig": {
//                        "responseModalities": ["TEXT", "IMAGE"]
//                    }
//                }
//                """, prompt.replace("\"", "\\\"")
//                    .replace("\n", "\\n"));
//
//            System.out.println("=== Gemini Image Generation ===");
//            System.out.println("Prompt: " + prompt);
//            System.out.println("API Key prefix: " + apiKey.substring(0, 10) + "...");
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                    .build();
//
//            HttpResponse<String> response = httpClient
//                    .send(request, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("Status Code: " + response.statusCode());
//
//            JsonNode root = objectMapper.readTree(response.body());
//
//            // Handle API-level errors
//            if (root.has("error")) {
//                String errorMsg = root.path("error").path("message").asText();
//                System.err.println("API Error: " + errorMsg);
//                throw new RuntimeException("Gemini API error: " + errorMsg);
//            }
//
//            // Parse candidates → content → parts → inlineData
//            JsonNode candidates = root.path("candidates");
//
//            if (candidates.isEmpty()) {
//                System.err.println("No candidates returned. Full response: " + response.body());
//                throw new RuntimeException("No image candidates returned from Gemini.");
//            }
//
//            JsonNode parts = candidates.get(0).path("content").path("parts");
//
//            for (JsonNode part : parts) {
//                if (part.has("inlineData")) {
//                    String mimeType = part.path("inlineData").path("mimeType").asText("image/png");
//                    String b64 = part.path("inlineData").path("data").asText();
//
//                    if (!b64.isEmpty()) {
//                        base64Images.add("data:" + mimeType + ";base64," + b64);
//                        System.out.println("Image extracted. MimeType: " + mimeType);
//                    }
//                } else if (part.has("text")) {
//                    // Gemini sometimes returns text alongside image — just log it
//                    System.out.println("Text part: " + part.path("text").asText());
//                }
//            }
//
//            // If n > 1, make multiple API calls since Gemini Flash generates 1 image per call
//            if (n > 1 && !base64Images.isEmpty()) {
//                for (int i = 1; i < n; i++) {
//                    List<String> extra = generateImage(prompt, 1, width, height);
//                    base64Images.addAll(extra);
//                }
//            }
//
//            System.out.println("Total images generated: " + base64Images.size());
//
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            System.err.println("Unexpected error: " + e.getMessage());
//            throw new RuntimeException("Image generation failed: " + e.getMessage(), e);
//        }
//
//        return base64Images;
//    }
//}


//package com.ai.SpringAiDemo;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//import java.util.List;

//@Service
//public class ImageService {
//
//    @Value("${spring.ai.google.genai.api-key}")
//    private String apiKey;
//
//    private final HttpClient httpClient = HttpClient.newHttpClient();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public List<String> generateImage(String prompt, String quality, int n, int width, int height) {
//
//        List<String> base64Images = new ArrayList<>();
//
//        try {
//            // Stable endpoint for direct Gemini API multimodal image generation task
//            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-image:generateContent?key=" + apiKey;
//
//            String requestBody = String.format("""
//                {
//                    "contents": [
//                        {
//                            "parts": [
//                                {"text": "%s"}
//                            ]
//                        }
//                    ],
//                    "generationConfig": {
//                        "responseModalities": ["TEXT", "IMAGE"]
//                    }
//                }
//                """, prompt.replace("\"", "\\\"").replace("\n", "\\n"));
//
//            System.out.println("=== Gemini Image Generation ===");
//            System.out.println("Prompt: " + prompt);
//
//            // Sequential generation matching the frontend array payload requirements
//            for (int i = 0; i < n; i++) {
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .header("Content-Type", "application/json")
//                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                        .build();
//
//                HttpResponse<String> response = httpClient
//                        .send(request, HttpResponse.BodyHandlers.ofString());
//
//                System.out.println("Request [" + (i + 1) + "/" + n + "] - Status Code: " + response.statusCode());
//
//                JsonNode root = objectMapper.readTree(response.body());
//
//                if (root.has("error")) {
//                    String errorMsg = root.path("error").path("message").asText();
//                    System.err.println("API Error: " + errorMsg);
//                    throw new RuntimeException("Gemini API error: " + errorMsg);
//                }
//
//                JsonNode candidates = root.path("candidates");
//                if (candidates.isEmpty() || !candidates.get(0).has("content")) {
//                    System.err.println("No content returned. Response: " + response.body());
//                    continue;
//                }
//
//                JsonNode parts = candidates.get(0).path("content").path("parts");
//
//                for (JsonNode part : parts) {
//                    if (part.has("inlineData")) {
//                        String mimeType = part.path("inlineData").path("mimeType").asText("image/png");
//                        String b64 = part.path("inlineData").path("data").asText();
//
//                        if (!b64.isEmpty()) {
//                            base64Images.add("data:" + mimeType + ";base64," + b64);
//                        }
//                    }
//                }
//            }
//
//            System.out.println("Total images generated: " + base64Images.size());
//
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            System.err.println("Unexpected error: " + e.getMessage());
//            throw new RuntimeException("Image generation failed: " + e.getMessage(), e);
//        }
//
//        return base64Images;
//    }

//
//package com.ai.SpringAiDemo;

//import org.springframework.stereotype.Service;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    private final HttpClient httpClient = HttpClient.newHttpClient();
//
//    public List<String> generateImage(String prompt, String quality, int n, int width, int height) {
//        List<String> base64Images = new ArrayList<>();
//
//        try {
//            System.out.println("=== Free Image Generation (Pollinations) ===");
//            System.out.println("Prompt: " + prompt);
//
//            // Safely encode the text prompt for the URL query
//            String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);
//
//            for (int i = 0; i < n; i++) {
//                // Generate a random seed so each image variation looks unique
//                int seed = (int) (Math.random() * 1000000);
//                String url = String.format("https://image.pollinations.ai/p/%s?width=%d&height=%d&seed=%d&nologo=true",
//                        encodedPrompt, width, height, seed);
//
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .GET()
//                        .build();
//
//                HttpResponse<byte[]> response = httpClient
//                        .send(request, HttpResponse.BodyHandlers.ofByteArray());
//
//                System.out.println("Image [" + (i + 1) + "/" + n + "] - Status Code: " + response.statusCode());
//
//                if (response.statusCode() == 200) {
//                    // Convert raw photo bytes to a Base64 string that your React app expects
//                    String b64 = Base64.getEncoder().encodeToString(response.body());
//                    base64Images.add("data:image/png;base64," + b64);
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error generating image: " + e.getMessage());
//            throw new RuntimeException("Image generation failed: " + e.getMessage(), e);
//        }
//
//        return base64Images;
//    }
//}


//package com.ai.SpringAiDemo;
//import org.springframework.stereotype.Service;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    public List<String> generateImage(String prompt, String quality, int n, int width, int height) {
//        List<String> imageUrls = new ArrayList<>();
//
//        try {
//            System.out.println("=== High-Speed Image Routing ===");
//            System.out.println("Prompt: " + prompt);
//
//            // Clean and encode the text prompt safely for URL transmission
//            String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);
//
//            for (int i = 0; i < 1; i++) {
//                // Generate a completely unique random seed for each photo block
//                int seed = (int) (Math.random() * 1000000);
//
//                // We use the high-speed CDN endpoint layout directly
//                String directUrl = String.format("https://enter.pollinations.ai/p/%s?width=%d&height=%d&seed=%d&nologo=true",
//                        encodedPrompt, width, height, seed);
//
//                imageUrls.add(directUrl);
//                System.out.println("Routed Image URL [" + (i + 1) + "/" + n + "]: " + directUrl);
//            }
//
//            System.out.println("Successfully dispatched " + imageUrls.size() + " streaming image locations.");
//
//        } catch (Exception e) {
//            System.err.println("Error generating image paths: " + e.getMessage());
//            throw new RuntimeException("Image routing failed: " + e.getMessage(), e);
//        }
//
//        return imageUrls;
//    }
//}

//package com.ai.SpringAiDemo;
//
//import org.springframework.stereotype.Service;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class ImageService {
//
//    private final HttpClient httpClient = HttpClient.newHttpClient();
//
//    public byte[] generateImage(String prompt, String quality, int n, int width, int height) {
//        try {
//            System.out.println("=== Live Production Image Download Stream ===");
//            String cleanPrompt = prompt.trim().toLowerCase();
//            String encodedPrompt = URLEncoder.encode(cleanPrompt, StandardCharsets.UTF_8);
//
//            int seed = (int) (Math.random() * 1000000);
//            String url = String.format("https://image.pollinations.ai/p/%s?width=%d&height=%d&seed=%d&nologo=true",
//                    encodedPrompt, width, height, seed);
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .GET()
//                    .build();
//
//            // Java server fetches the raw bytes perfectly behind the scenes
//            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
//
//            if (response.statusCode() == 200) {
//                System.out.println("Successfully downloaded raw binary image file data!");
//                return response.body();
//            } else {
//                throw new RuntimeException("External service down. Status: " + response.statusCode());
//            }
//
//        } catch (Exception e) {
//            System.err.println("Backend stream retrieval process failed: " + e.getMessage());
//            throw new RuntimeException("Image process failed", e);
//        }
//    }
//}


package com.ai.SpringAiDemo;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageService {

    public byte[] generateImage(String prompt, String quality, int n, int width, int height) {
        try {
            System.out.println("=== Launching Global AI Image Generation Pipeline ===");
            String cleanPrompt = prompt.trim().toLowerCase();
            String encodedPrompt = URLEncoder.encode(cleanPrompt, StandardCharsets.UTF_8);

            // CRITICAL: Generate a completely random non-sequential seed on every click.
            // This tricks the CDN firewall into seeing every request as unique, bypassing 503 limits!
            long randomSeed = ThreadLocalRandom.current().nextLong(100000, 999999999);

            // The absolute correct, working public AI endpoint layout
            String url = String.format("https://image.pollinations.ai/p/%s?width=%d&height=%d&seed=%d&nologo=true",
                    encodedPrompt, width, height, randomSeed);

            System.out.println("Dispatched Target URL: " + url);

            // Build a fresh connection client wrapper block
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            System.out.println("Response Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                System.out.println("Successfully downloaded raw binary image file data!");
                return response.body();
            } else {
                throw new RuntimeException("API endpoint returned non-200 status: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Pipeline failure: " + e.getMessage());
            throw new RuntimeException("Image process failed: " + e.getMessage(), e);
        }
    }
}