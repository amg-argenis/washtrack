package com.washtrack.washtrack_api.orden.constants;

public class ConstantesBaseDatos {
  
  // STORED PROCEDURES
  public static final String SP_LISTAR_ORDENES = "SP_LISTAR_ORDENES";
  public static final String SP_LISTARPOR_FECHAINGRESO = "SP_LISTARPOR_FECHAINGRESO";
  public static final String SP_BUSCAR_ORDENSERVICIO = "SP_BUSCAR_ORDENSERVICIO";
  public static final String SP_ACTUALIZAR_ORDENSERVICIO = "SP_ACTUALIZAR_ORDENSERVICIO";
  
  public static final String CODIGOBD = "pa_codigobd";
  public static final String PAMENSAJEBD = "pa_mensaje";
  
  public static final String OPERACION_EXITOSA = "Operacion exitosa";
  public static final String SIN_REGISTROS = "Sin registro de ordenes";
  public static final String ERROR_BD = "Hubo un error en la BD";
  public static final String ERROR_INSERT = "Error al crear la orden";
  public static final String ERROR_ACTUALIZAR = "Error al actualizar la orden";
  public static final String ERROR_ELIMINAR = "Error al eliminar la orden";
}
