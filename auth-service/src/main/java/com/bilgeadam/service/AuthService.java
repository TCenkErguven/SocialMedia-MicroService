package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateStatusRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IUserManager iUserManager;
    private final IAuthRepository iAuthRepository;
    public AuthService(IAuthRepository iAuthRepository,
                       IUserManager iUserManager){
        super(iAuthRepository);
        this.iAuthRepository = iAuthRepository;
        this.iUserManager = iUserManager;
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

    public Boolean login(LoginRequestDto dto){
        Optional<Auth> optionalAuth = iAuthRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(optionalAuth.isPresent()){
            if(optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
                return true;
            }
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
         throw new AuthManagerException(ErrorType.LOGIN_ERROR);
    }
}
