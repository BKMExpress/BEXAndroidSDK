#BKM EXPRESS ANDROID SDK

##NE İŞE YARAR?
> Hizmetinize sunulan BKM Express Android SDK paketi ile son kullanıcının Android cihazında BKMExpress uygulaması kurulu olmasa dahi, "Kart Eşleme ve Ödeme Yapma" özelliklerini uygulamanızdan çıkış yapma gereksinimi olmadan halletmenize olanak sunar.

##SİSTEM GEREKSİNİMLERİ NELERDİR?

 *  BKM Express Android SDK paketi, Android Studio ile geliştirilen uygulamalar baz alınarak tasarlanmıştır.
 *  Min SDK Version 15 desteklenmektedir.

##NASIL ÇALIŞIR?

Işyerleri BKM Express entegrasyonlarını tamamlayarak gerekli **API Key**lerini almalıdırlar. Bu API Key daha sonra
BKM Express Android SDK paketinin kullanılabilmesi için gerekmektedir. İşyeri servis uygulamaları, BKMExpress core servislerine bağlanarak kendileri için hazırlanan **TOKEN**'ı ve **API Key**'i sunulan methodlara parametrik olarak ileterek, istenen BKMExpress akışı başlatılır. Daha detaylı bilgi ilerleyen kısımlarda verilecektir.

##GRADLE ENTEGRASYONU

* SDK paketini gradle dependency ile eklemek için, uygulamanızın build.gradle dosyasındaki repositories kısmına aşağıdaki kod bloğunu ekleyiniz.

                repositories{
                    jcenter()
                    maven{
                          url 'https://dl.bintray.com/bkmexpress/maven'
                    }
                 }

* Daha sonra yine aynı dosyadaki dependencies kısmına aşağıdaki gradle bağlantılarını ekleyiniz.

                compile 'com.android.support:support-v4:24.1.1' 
                compile 'com.android.support:appcompat-v7:24.1.1'
 
 * Preprod ortamda çalışacak paket için
 
                compile 'com.bkm.bexandroidsdk:bexandroidsdk:Preprod_1.0.8'
                
 * Prod ortamda çalışacak paket için
 
                compile 'com.bkm.bexandroidsdk:bexandroidsdk:1.0.8'

* Yukarıdaki eklemeleri yapıp, projenizi gradle ile sync ettikten sonra BEX SDK nın,  BEXStarter sınıfına erişebilirsiniz. **BEXStarter** sınıfı, sunulan servis paketlerinin çalışmalarını sağlamakta, ve parametrik olarak verilen **BEXSubmitConsumerListener** && **BEXPaymentListener** interfaceleri ile de asynchrone olarak sonucu işyerine iletmektedir. (Ayrıntılı bilgi için lütfen Örnek Projeye Bakınız!)

###BEXStarter

                public static void startSDKForSubmitConsumer(Context context, String token, String api_key,BEXSubmitConsumerListener bexSubmitConsumerListener);

                public static void startSDKForPayment(Context context, String token, String api_key,BEXResponseListener listener);
***

###BEXSubmitConsumerListener

                 public void onSuccess(); //BAŞARILI EŞLEŞME 
                 public void onCancelled(); //KULLANICI İŞLEMİ İPTAL ETTİ
                 public void onFailure(int errorId,String errorMsg); //İŞLEM VERİLEN HATA YÜZÜNDEN İPTAL EDİLDİ

###BEXPaymentListener

                 public void onSuccess(); //BAŞARILI ÖDEME İŞLEMİ 
                 public void onCancelled(); //KULLANICI ÖDEME İŞLEMİNİ İPTAL ETTİ
                 public void onFailure(int errorId,String errorMsg); //ÖDEME İŞLEMİ VERİLEN HATA YÜZÜNDEN İPTAL EDİLDİ



###ÖRNEK KULLANIM - SUBMIT CONSUMER (KART EŞLEME)
                  BEXStarter.startSDKForSubmitConsumer(MainActivity.this, "MERCHANT-TOKEN", getString(R.string.dummyApiKey), new BEXSubmitConsumerListener() {

                                @Override
                                public void onSuccess() {
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


###ÖRNEK KULLANIM - PAYMENT (ÖDEME)
                  BEXStarter.startSDKForPayment(Payment.this, response.body(), API_KEY, new BEXPaymentListener() {
                                 
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
##ORTAMLAR

BKM Express Android SDK paketi iki farklı ortamda çalışmaktadır. 

* PREPROD
* PROD

**Her ortamın API KEY i diğerlerinden farklıdır.**

**NOT:** Entegrasyon sırasında işyerlerine verilen anahtarların sorumluluğu, **işyerine** aittir.




