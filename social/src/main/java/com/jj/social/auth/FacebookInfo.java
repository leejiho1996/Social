package com.jj.social.auth;

import java.util.Map;

public class FacebookInfo extends OAuth2UserInfo{
    public FacebookInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getImageUrl() {
        return "";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getUsername() {
        return "facebook__" + attributes.get("id");
    }

}
