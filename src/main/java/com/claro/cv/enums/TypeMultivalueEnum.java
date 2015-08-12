package com.claro.cv.enums;

public enum TypeMultivalueEnum {

   TIPO_CONTACTO("TIPO_CONTACTO"),

   TIPO_SERVICIO("TIPO_SERVICIO"),

   PROVEEDOR_ULTIMA_MILLA("PROVEEDOR_ULTIMA_MILLA"),

   HORARIO_ATENCION("HORARIO_ATENCION");

   private String value;

   private TypeMultivalueEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}
