package com.bilgeadam.service;

import com.bilgeadam.dto.request.NewCreateUserRequestDto;
import com.bilgeadam.dto.request.UserGeneralUpdateDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserProfileManagerException;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.rabbitmq.consumer.RegisterConsumer;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userProfileRepository;
    private final IAuthManager iAuthManager;
    private final JwtTokenProvider tokenProvider;

    public UserProfileService(IUserProfileRepository userProfileRepository,
                              IAuthManager iAuthManager,
                              JwtTokenProvider tokenProvider){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.iAuthManager = iAuthManager;
        this.tokenProvider = tokenProvider;
    }
    public Boolean createUser(NewCreateUserRequestDto dto){
        try {
            save(IUserProfileMapper.INSTANCE.fromDtoToUserProfile(dto));
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu");
        }
    }

    public Boolean createUserWithRabbitMq(RegisterModel model){
        try {
            save(IUserProfileMapper.INSTANCE.fromRegisterModelToUserProfile(model));
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu");
        }
    }

    public Boolean activateStatus(Long authId){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new RuntimeException("Auth id bulunamadı");
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public UserProfile updateUser(UserGeneralUpdateDto dto){
        Optional<Long> authId = tokenProvider.getIdFromToken(dto.getToken());
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);

        /*
        -----UserProfile çözümü------
        update(IUserProfileMapper.INSTANCE.updateUserFromDto(dto,optionalUserProfile.get()));
        iAuthManager.updateUsernameOrEmail(IUserProfileMapper.INSTANCE.toUpdateEmailOrUsernameRequestDto(optionalUserProfile.get());

        -----Auth Service-------
        user-service'deki UserService içerisindeki UserProfile parametresi ile çözüm için kullanılacak.
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
         */
        update(IUserProfileMapper.INSTANCE.updateUserFromDto(dto,optionalUserProfile.get()));
        iAuthManager.updateUserAuth(IUserProfileMapper.INSTANCE.toUpdateEmailOrUsernameRequestDto(optionalUserProfile.get()));
        return optionalUserProfile.get();
    }

    public Boolean delete(Long authId){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }
}
