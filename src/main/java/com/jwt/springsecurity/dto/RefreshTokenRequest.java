package com.jwt.springsecurity.dto;

public class RefreshTokenRequest {
   private String token;

   public void setToken(String token){
     this.token=token;
   }

   public String getToken(){
       return this.token;
   }
}
