package com.claro.cv.util;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.claro.cv.dto.MapDataDTO;


public class MapJsonUtil {

   private static final String STRING = "string";

   private static final String NUMBER = "number";

   public static String generateJson(ArrayList<MapDataDTO> listMapData) {
      JSONObject mapJson = new JSONObject();

      JSONArray cols = new JSONArray();
      JSONArray rows = new JSONArray();

      cols.add(getCol("Cities", MapJsonUtil.STRING));
      cols.add(getCol("Name", MapJsonUtil.STRING));
      cols.add(getCol("Servicios", MapJsonUtil.NUMBER));

      mapJson.put("cols", cols);

      JSONArray cs = new JSONArray();
      JSONObject c = new JSONObject();
      for (MapDataDTO mapData : listMapData) {
         c = new JSONObject();
         cs = new JSONArray();
         cs.add(getRowString(mapData.getCodeDepartament()));
         cs.add(getRowString(mapData.getNameDepartament()));
         cs.add(getRowNumber(mapData.getNumberServices()));
         c.put("c", cs);
         rows.add(c);
      }

      mapJson.put("rows", rows);

      return mapJson.toString();

   }

   private static JSONObject getCol(String label, String type) {
      JSONObject col = new JSONObject();
      col.put("id", "");
      col.put("label", label);
      col.put("pattern", "");
      col.put("type", type);

      return col;

   }

   private static JSONObject getRowString(String value) {
      JSONObject row = new JSONObject();
      row.put("v", value);

      return row;
   }

   private static JSONObject getRowNumber(int value) {
      JSONObject row = new JSONObject();
      row.put("v", value);

      return row;
   }

}
