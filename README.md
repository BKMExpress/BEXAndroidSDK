# BKM EXPRESS ANDROID SDK

## NE İŞE YARAR?
> Hizmetinize sunulan BKM Express Android SDK paketi ile son kullanıcının Android cihazında BKMExpress uygulaması kurulu olmasa dahi, "Kart Eşleme ve Ödeme Yapma" özelliklerini uygulamanızdan çıkış yapma gereksinimi olmadan halletmenize olanak sunar.

## SİSTEM GEREKSİNİMLERİ NELERDİR?

 *  BKM Express Android SDK paketi, Android Studio ile geliştirilen uygulamalar baz alınarak tasarlanmıştır.
 *  Min SDK Version 15 desteklenmektedir.

## NASIL ÇALIŞIR?

Işyerleri BKM Express entegrasyonlarını tamamlayarak gerekli **kullanıcı adı** ve **şifrelerini** almalıdırlar. Bu kullanıcı adı ve şifre
BKM Express Android SDK paketinin entegre edeceğiniz uygulamaya görünür olması için gerekmektedir. İşyeri servis uygulamaları, BKMExpress core servislerine bağlanarak kendileri için hazırlanan **TOKEN**'ı sunulan methodlara parametrik olarak ileterek, istenen BKMExpress akışı başlatılır. Daha detaylı bilgi ilerleyen kısımlarda verilecektir.

## GRADLE ENTEGRASYONU

* Entegrasyona başlarken lütfen size sunduğumuz kullanıcı adı ve şifreyi, local.properties dosyasına aşağıdaki gibi ekleyiniz.

                bkm_username=<<YOUR_USERNAME>>
                bkm_password=<<YOUR_PASSWORD>>
                bkm_maven_url = http://18.208.172.191/artifactory/bexandroidsdk-release-android

* SDK paketini gradle dependency ile eklemek için, <u>projenizin</u> build.gradle dosyasındaki repositories kısmına aşağıdaki kod bloğunu ekleyiniz.

                Properties properties = new Properties()
                properties.load(project.rootProject.file('local.properties').newDataInputStream())


                allprojects {
                    repositories {
                        mavenCentral()
                        maven {
                            url = properties.getProperty("bkm_maven_url")
                            credentials {
                                username = properties.getProperty("bkm_username")
                                password = properties.getProperty("bkm_password")
                            }
                        }
                    }
                }


* AndroidX desteği olan uygulamalar için;

        implementation 'com.bkm:bexandroidsdk:2.1.7'

* AndroidX desteği BULUNMAYAN uygulamalar için;

        implementation 'com.bkm:bexandroidsdk:1.1.23'



* Yukarıdaki eklemeleri yapıp, projenizi gradle ile sync ettikten sonra BEX SDK nın,  BEXStarter sınıfına erişebilirsiniz. **BEXStarter** sınıfı, sunulan servis paketlerinin çalışmalarını sağlamakta, ve parametrik olarak verilen **BEXSubmitConsumerListener** && **BEXPaymentListener** interfaceleri ile de asenkron olarak sonucu işyerine iletmektedir. (Ayrıntılı bilgi için lütfen Örnek Projeye Bakınız!)

***

### BEXStarter

                public static void startSDKForSubmitConsumer(Context context, Environment environment, String token, BEXSubmitConsumerListener listener);
                public static void startSDKForPayment(Context context, Environment environment, String token, BEXPaymentListener paymentListener);
                public static void startSDKForReSubmitConsumer(Context context, Environment environment, String ticketId, BEXSubmitConsumerListener listener);
                public static void startSDKForOtpPayment(Context context, Environment environment, String ticket, BEXOtpPaymentListener listener);

### BEXSubmitConsumerListener

                 public void onSuccess(String firtst6, String last2); //BAŞARILI EŞLEŞME - Başarılı eşleşme sonrası, eşleşen kartın ilk 6 ve son 2 hanesi işyerine geri dönülür.
                 public void onCancelled(); //KULLANICI İŞLEMİ İPTAL ETTİ
                 public void onFailure(int errorId,String errorMsg); //İŞLEM VERİLEN HATA YÜZÜNDEN İPTAL EDİLDİ

### BEXPaymentListener

                 public void onSuccess(PosResult posResult); //BAŞARILI ÖDEME İŞLEMİ - PosResult objesi pos cevabını taşımaktadır.
                 public void onCancelled(); //KULLANICI ÖDEME İŞLEMİNİ İPTAL ETTİ
                 public void onFailure(int errorId,String errorMsg); //ÖDEME İŞLEMİ VERİLEN HATA YÜZÜNDEN İPTAL EDİLDİ

### BEXOtpPaymentListener

                 public void onSuccess(); //BAŞARILI ÖDEME İŞLEMİ
                 public void onCancelled(); //KULLANICI ÖDEME İŞLEMİNİ İPTAL ETTİ
                 public void onFailure(int errorId, String errorMsg); //ÖDEME İŞLEMİ VERİLEN HATA YÜZÜNDEN İPTAL EDİLDİ



### ÖRNEK KULLANIM - SUBMIT CONSUMER (KART EŞLEME)

                  BEXStarter.startSDKForSubmitConsumer(MainActivity.this,Environment.PREPROD, "MERCHANT-TOKEN", new  BEXSubmitConsumerListener() {

                                @Override
                                public void onSuccess(String firtst6, String last2) {
                                    Toast.makeText(MainActivity.this,"Sync Completed!!!",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled() {
                                    Toast.makeText(MainActivity.this,"Sync Cancelled By User!!!",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(int errorId, String errorMsg) {
                                    Toast.makeText(MainActivity.this,"Sync failed!!! Cause :: "+errorMsg,Toast.LENGTH_LONG).show();
                                }
                            });


### ÖRNEK KULLANIM - RESUBMIT CONSUMER (KART EŞLEME)

* Diğer işlemlerden farklı olarak, ReSubmitConsumer operasyonu <u>daha önceden kart eklemiş</u> kullanıcının tekrardan sisteme giriş yapmadan kart değiştirmesine olanak sağlamaktadır. Bahsi geçen Ticket, BEX Core servisleri tarafından sağlanmaktadır.

                BEXStarter.startSDKForReSubmitConsumer(Start.this, Environment.TEST, ticket, new BEXSubmitConsumerListener() {

                            @Override
                            public void onSuccess(String first6,String last2) {
                                Toast.makeText(Start.this,"Consumer resubmitted !!!\nFirst6 :: "+first6+"\nLast2 :: "+last2,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled() {
                                Toast.makeText(Start.this,"cancelled!!!",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int errorId, String errorMsg) {
                                Toast.makeText(Start.this,errorMsg,Toast.LENGTH_LONG).show();
                            }
                        });



### ÖRNEK KULLANIM - PAYMENT (ÖDEME)

                  BEXStarter.startSDKForPayment(Payment.this, Environment.PREPROD, "MERCHANT-TOKEN", new BEXPaymentListener() {

                                 @Override
                                 public void onSuccess() {
                                     Toast.makeText(Payment.this, "Ödeme Başarılı !!", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCancelled() {
                                     Toast.makeText(Payment.this, "Kullanıcı ödemeyi iptal etti !!", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                  public void onFailure(int errorId, String errorMsg) {
                                     Toast.makeText(Payment.this, "Hata :: " + errorMsg + " !!", Toast.LENGTH_SHORT).show();
                                  }
                  });

### ÖRNEK KULLANIM - OTP PAYMENT (OTP'Lİ HIZLI ÖDEME DOĞRULAMA)
                  
                  BEXStarter.startSDKForOtpPayment(Payment.this, Environment.PREPROD, "MERCHANT-TICKET", new BEXOtpPaymentListener() {
                                  
                                  @Override
                                  public void onSuccess() {
                                      Toast.makeText(Payment.this, "Ödeme Başarılı !!", Toast.LENGTH_SHORT).show();
                                  }
                      
                                  @Override
                                  public void onCancelled() {
                                      Toast.makeText(Payment.this, "Kullanıcı ödemeyi iptal etti !!", Toast.LENGTH_SHORT).show();
                                  }
                      
                                  @Override
                                  public void onFailure(int errorId, String errorMsg) {
                                      Toast.makeText(Payment.this, "Hata :: " + errorMsg + " !!", Toast.LENGTH_SHORT).show();
                                  }
                          });

## PROGUARD AYARLARI

 Eğer uygulamanızın release versiyonunu proguard ile koruyorsanız, lütfen aşağıdaki satırı proguard-rules dosyanıza ekleyiniz.

                      -keep class com.bkm.** { *; }
## ORTAMLAR

BKM Express Android SDK paketi üç farklı ortamda çalışmaktadır. (Ortam değişikliği => Environment parametresi ile gerçekleşmektedir. Lütfen örnek koda bakınız.)

* TEST
* PREPROD
* PROD

**Her ortam için kullanıcı adı ve şifre aynıdır.**

**NOT:** Entegrasyon sırasında işyerlerine verilen kullanıcı adı ve şifrenin sorumluluğu, **işyerine** aittir.
