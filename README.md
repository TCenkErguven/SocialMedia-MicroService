## property --> id, username, email, password, activationCode(String), ERole, EStatus
## Bu sınıfın repository, service, controller'ını oluşturunuz.
## Utility katmanını oluşturunuz, CRUD metotları için gerekli repo patternini kurunuz.

## Register için bir requestDto ve responseDto oluşturunuz.
## Mapper katmanından yararlanarak AuthService'de register metodunu yazınız ve test ediniz.
## requestDto --> username,email,password
## responseDto --> id, username, activationCode
## Register metodu yazılacak.

## (boolean)activateStatus metodunu yazınız. Bu metot için bir dto oluşturunuz. Dto --> id, activationCode
## activateStatus metodu login metodundan önce çalışacaktır. Register olduğumuzda gelen kod burada girilerek kullanıcının durumu
## ACTIVE olarak değiştirilir. Daha sonra login işlemi yapılabilir.

## (boolean)login metodu yazınız. Login metodu için bir dto oluşturunuz. Dto --> username, password
## Kullanıcının status' ü ACTIVE yapılmadıysa hata dönmelidir. yazınız. Bu metot için bir dto oluşturunuz. Dto --> id,activateStatus
## Exception katmanını projeye dahil ediniz.

## OpenFeign için gerekli işlemleri yapınız. Bağımlılığını ekleyniz, bir dto(NewCreateUserRegisterDto) oluşturarak auth-service'de 
## yapılan register işleminin user-service'de çalışmasını sağlayınız. OpenFeign için 'manager' kaymanı oluşturunuz.

## Auth-Service'deki activateStatus metodu kullanıldığında register olan kullanıcının durumu ACTIVE olarak değişmektedir.
## Ancak bu değişiklik User-Profile service'e aktarılan kullanıcının durumunda bir değişiklik yaratmamaktadır.

## Bu durumu gidermek için activateStatus metodu feign client ile user-profile service' e aktarılmalıdır.
## Bunun için gerekli işlemleri yapınız.
## UserManager' da yazacağınız activateStatus metodu yalnızca authId parametresi almalıdır.

### UserManager'da yazacağınız activateStatus metodu yalnızca authId parametresi almalıdır.

## 6 - UserProfile'daki eksik kalan bilgiler veya username,email gibi AuthService'den gelen bilgiler update edilebilmelidir.
### Bu işlem için UserProfileUpdateDto adında bir dto oluşturarak gerekli işlemleri yapınız.

### Bu bilgiler içerisinde username ve email bilgilerinin OpenFeign kullanılarak AuthService'e gönderilmesi gerekmektedir.
### Bunun için de bir dto'ya ihtiyaç duyulabilir. 'UpdateEmailOrUsernameRequestDto' oluşturup bu dto üzerinden veriler
### AuthService'e gönderilmelidir.

## Login metodu kullanıcıya bir token dönmelidir.Bunun için Login metodunu refactor ediniz.
### Burada değiştireceğiniz kısım metodun dönüşüm tipidir.
### Exception katmanına token'ın üretilemediğine dair bir enum yazınız ve bunu login metodunda kullanınız.

## Login işleminden alınan token bilgisi, user-profile-service'de update işlemi için kullanılmalıdır.
## Bu işlem için update metoduna gönderilen dto'ya bir 'token' property'si eklenmelidir.
## user-profile-service'de JwtTokenProvider oluşturulmalıdır. Daha sonra update metodu içerisinde kullanılabilir.

## 7-Config server için gerekli işlemleri yapınız.
## config-server'In application.yml'ını yazınız.
## config-repo adında bir paket oluşturarak içerisine auth-service ve userprofile-service'in ayaralarını giriniz.
## userprofile ve auth service'de içi boşalan .yml dosyalarını config-server üzerinden çalıştıracak ayarları yazınız.

# 8-Projenin Zipkin'in bağımlılıklarını ekleyiniz.
## Zipkin için bir imaj oluşturunuz ve gerekli ayarları projenizde yapınız.