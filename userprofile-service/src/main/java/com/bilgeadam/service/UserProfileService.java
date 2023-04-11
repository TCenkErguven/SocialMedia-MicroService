package com.bilgeadam.service;

import com.bilgeadam.dto.request.NewCreateUserRequestDto;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository userProfileRepository;
    public UserProfileService(IUserProfileRepository userProfileRepository){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }
    public Boolean createUser(NewCreateUserRequestDto dto){
        try {
            save(IUserProfileMapper.INSTANCE.fromDtoToUserProfile(dto));
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


}
