package test;

import java.io.File;

import com.claro.cv.util.Constant;


public class Test {

   public String getPathClient(String idClient, String idCodeService) {
      String path = Constant.PATH_UPLOAD_FILE_ENGINEERING;
      path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
      return path;
   }

   public String getPathSettings(String idClient, String idCodeService) {
      String path = Constant.PATH_UPLOAD_FILE_SETTINGS;
      path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
      path = path.replaceAll(Constant.TAG_CODE_SERVICE, idCodeService);
      return path;
   }

   public String getPathEngineeringService(String idClient, String idCodeService) {
      String path = Constant.PATH_UPLOAD_FILE_ENGINEERING_SERVICE;
      path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
      path = path.replaceAll(Constant.TAG_CODE_SERVICE, idCodeService);
      return path;
   }

   public static void main(String[] args) {
      Test t = new Test();

      String idClient = "9002";
      String idCodeService = "AAA002";

      String path = t.getPathClient(idClient, null);
      File folder = new File(path);
      folder.mkdirs();

      path = t.getPathSettings(idClient, idCodeService);
      folder = new File(path);
      folder.mkdirs();

      path = t.getPathEngineeringService(idClient, idCodeService);
      folder = new File(path);
      folder.mkdirs();

   }

}
