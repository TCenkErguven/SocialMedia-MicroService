package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateStatusRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateEmailOrUsernameRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IUserManager iUserManager;
    private final IAuthRepository iAuthRepository;
    private final JwtTokenProvider tokenProvider;
    public AuthService(IAuthRepository iAuthRepository,
                       IUserManager iUserManager,
                       JwtTokenProvider tokenProvider){
        super(iAuthRepository);
        this.iAuthRepository = iAuthRepository;
        this.iUserManager = iUserManager;
        this.tokenProvider = tokenProvider;
    }

    public RegisterResponseDto register(RegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        if(!dto.getPassword().equals(dto.getRepassword()))
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        iUserManager.createUser(IAuthMapper.INSTANCE.fromAuthToNewCreateUserDto(auth));
        RegisterResponseDto responseDto = IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        return responseDto;
    }
    public Boolean activateStatus(ActivateStatusRequestDto dto){
        Optional<Auth> optionalAuth = findById(dto.getId());
        if(optionalAuth.isPresent()){
            if(optionalAuth.get().getActivationCode().equals(dto.getActivationCode())) {
                optionalAuth.get().setStatus(EStatus.ACTIVE);
                update(optionalAuth.get());
                iUserManager.activateStatus(optionalAuth.get().getId());
                return true;
            }
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
    }

    public String login(LoginRequestDto dto){
        Optional<Auth> optionalAuth = iAuthRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(optionalAuth.isPresent()){
            if(optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
                Optional<String> token = tokenProvider.createToken(optionalAuth.get().getId(),optionalAuth.get().getRole());
                if(token.isEmpty()){
                    throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
                }
                return tokenProvider.createToken(optionalAuth.get().getId(),optionalAuth.get().getRole())
                        .orElseThrow(() -> {throw
                                            new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
                                            });
            }
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
         throw new AuthManagerException(ErrorType.LOGIN_ERROR);
    }

    public Boolean updateUserAuth(UpdateEmailOrUsernameRequestDto dto){
        Optional<Auth> optionalAuth = iAuthRepository.findById(dto.getAuthId());
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        optionalAuth.get().setEmail(dto.getEmail());
        optionalAuth.get().setUsername(dto.getUsername());
        save(optionalAuth.get());
        return true;
    }

    public Boolean delete(String token){
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        if(authId.isEmpty()){
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Auth> auth = iAuthRepository.findById(authId.get());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        return true;
    }
}
