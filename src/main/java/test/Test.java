package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Test {

   private static final String STRING = "stirng";

   private static final String NUMBER = "number";

   String codigoDepartamentos[] = { "CO-TOL", "CO-DC", "CO-SAN", "CO-ATL", "CO-CHO" };

   String nombreDepartamentos[] = { "Tolima", "Bogota", "Santander", "Atlantico", "Choco" };

   int numeroSerivcios[] = { 2, 90, 4, 9, 450 };

   private JSONObject getCol(String label, String type) {
      JSONObject col = new JSONObject();
      col.put("id", "");
      col.put("label", label);
      col.put("pattern", "");
      col.put("type", type);

      return col;

   }

   private JSONObject getRowString(String value) {
      JSONObject row = new JSONObject();
      row.put("v", value);

      return row;
   }

   private JSONObject getRowNumber(int value) {
      JSONObject row = new JSONObject();
      row.put("v", value);

      return row;
   }

   public String main() {
      JSONObject mapJson = new JSONObject();

      JSONArray cols = new JSONArray();
      JSONArray rows = new JSONArray();

      cols.add(getCol("Cities", Test.STRING));
      cols.add(getCol("Name", Test.STRING));
      cols.add(getCol("Service", Test.NUMBER));

      mapJson.put("cols", cols);

      JSONArray cs = new JSONArray();
      JSONObject c = new JSONObject();
      for (int i = 0; i < 5; i++) {
         c = new JSONObject();
         cs = new JSONArray();
         cs.add(getRowString(codigoDepartamentos[i]));
         cs.add(getRowString(nombreDepartamentos[i]));
         cs.add(getRowNumber(numeroSerivcios[i]));
         c.put("c", cs);
         rows.add(c);
      }

      mapJson.put("rows", rows);

      return mapJson.toString();
   }

   public static void main(String[] args) {
      Test test = new Test();
      System.out.println(test.main());

   }

}
