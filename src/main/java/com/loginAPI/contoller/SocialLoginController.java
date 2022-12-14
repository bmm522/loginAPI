//package com.loginAPI.contoller;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.loginAPI.model.GoogleLoginDto;
//import com.loginAPI.model.GoogleLoginRequest;
//import com.loginAPI.model.GoogleLoginResponse;
//import com.loginAPI.service.SocialLoginFormService;
//
//import net.bytebuddy.jar.asm.TypeReference;
//
//@Controller
//@RequestMapping(value="/google")
//public class SocialLoginController {
//	
//	private final SocialLoginFormService socialLoginFormService;
//	
//	SocialLoginController(SocialLoginFormService socialLoginFormService) {
//		this.socialLoginFormService=socialLoginFormService;
//	}
//	
//
//    @GetMapping(value = "/login")
//    public ResponseEntity<Object> moveGoogleInitUrl() {
//        String authUrl = socialLoginFormService.runGoogleLoginForm();
//        URI redirectUri = null;
//        try {
//            redirectUri = new URI(authUrl);
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//    @GetMapping(value = "/login/redirect")
//    public ResponseEntity<GoogleLoginDto> redirectGoogleLogin(
//            @RequestParam(value = "code") String authCode
//    ) {
//        // HTTP ????????? ?????? RestTemplate ??????
//        RestTemplate restTemplate = new RestTemplate();
//        GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
//                .clientId(socialLoginFormService.getGoogleClientId())
//                .clientSecret(socialLoginFormService.getGoogleClientSecret())
//                .code(authCode)
//                .redirectUri(socialLoginFormService.getGoogleRedirectUri())
//                .grantType("authorization_code")
//                .build();
//
//        try {
//            // Http Header ??????
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
//            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(socialLoginFormService.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);
//
//            // ObjectMapper??? ?????? String to Object??? ??????
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL??? ?????? ?????? ????????????(NULL??? ????????? ??????)
//            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});
//
//            // ???????????? ????????? JWT Token?????? ???????????? ??????, Id_Token??? ?????? ????????????.
//            String jwtToken = googleLoginResponse.getIdToken();
//
//            // JWT Token??? ????????? JWT ????????? ????????? ?????? ??????
//            String requestUrl = UriComponentsBuilder.fromHttpUrl(socialLoginFormService.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();
//
//            String resultJson = restTemplate.getForObject(requestUrl, String.class);
//
//            if(resultJson != null) {
//                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
//
//                return ResponseEntity.ok().body(userInfoDto);
//            }
//            else {
//                throw new Exception("Google OAuth failed!");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().body(null);
//	
//}
