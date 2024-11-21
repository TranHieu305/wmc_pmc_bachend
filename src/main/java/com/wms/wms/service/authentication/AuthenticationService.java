package com.wms.wms.service.authentication;

import com.wms.wms.dto.request.auth.AuthRequest;
import com.wms.wms.dto.response.auth.AuthResponse;

public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest request);
}
