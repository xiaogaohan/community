package xyz.hangao.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.hangao.community.community.dto.AccesstokenDTO;
import xyz.hangao.community.community.dto.GithubUser;
import xyz.hangao.community.community.privoder.GithubPrivoder;

/**
 * Created By gh
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubPrivoder githubPrivoder;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = githubPrivoder.getAccessToken(accesstokenDTO);
        GithubUser user = githubPrivoder.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}