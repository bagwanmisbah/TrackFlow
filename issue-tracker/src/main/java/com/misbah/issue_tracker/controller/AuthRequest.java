package com.misbah.issue_tracker.controller;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}