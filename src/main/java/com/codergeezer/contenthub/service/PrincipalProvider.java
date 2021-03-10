package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.mapper.ModelMapper;
import com.codergeezer.contenthub.auth.UserPrincipal;
import com.codergeezer.core.base.data.BaseService;
import com.codergeezer.core.base.exception.BaseException;
import com.codergeezer.core.base.exception.CommonErrorCode;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author haidv
 * @version 1.0
 */
public class PrincipalProvider extends BaseService {

    protected ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    protected UserPrincipal getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (UserPrincipal) authentication.getPrincipal();
        } else {
            throw new BaseException(CommonErrorCode.UNAUTHORIZED);
        }
    }

    protected Long getCurrentUserId() {
        return getCurrentPrincipal().getUserId();
    }

    protected String getCurrentUserCode() {
        return getCurrentPrincipal().getCode();
    }

    protected Long getCurrentGroupId() {
        return getCurrentPrincipal().getGroupId();
    }

    protected String getCurrentGroupCode() {
        return getCurrentPrincipal().getGroupCode();
    }

    protected boolean hasCreateGroup() {
        return getCurrentGroupId() != null;
    }
}
