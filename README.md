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
