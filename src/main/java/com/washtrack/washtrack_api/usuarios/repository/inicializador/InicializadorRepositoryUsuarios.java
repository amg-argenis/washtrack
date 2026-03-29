package com.washtrack.washtrack_api.usuarios.repository.inicializador;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;
import com.washtrack.washtrack_api.usuarios.rowmapper.UsuarioRowMapper;
import com.washtrack.washtrack_api.usuarios.util.MapearObjetosUsuario;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InicializadorRepositoryUsuarios {
  
  private SimpleJdbcCall buscarUsuarioPorIdSimpleJdbcCall;
  private SimpleJdbcCall loginUsuarioSimpleJdbcCall;
  private SimpleJdbcCall listarUsuarioSimpleJdbcCall;
  private SimpleJdbcCall insertarUsuarioSimpleJdbcCall;
  private SimpleJdbcCall actualizarUsuarioSimpleJdbcCall;
  private SimpleJdbcCall eliminarUsuarioSimpleJdbcCall;
  private SimpleJdbcCall reactivarUsuarioSimpleJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetosUsuario mapearObjetosUsuario;
  
  public InicializadorRepositoryUsuarios(JdbcTemplate jdbcTemplate, MapearObjetosUsuario mapearObjetosUsuario) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetosUsuario = mapearObjetosUsuario;
  }
  
  @PostConstruct
  public void init() {
    
    this.buscarUsuarioPorIdSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_USUARIO_ID)
        .declareParameters(
            // IN
            new SqlParameter("pa_idusuario", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("usuariorecuperado", new UsuarioRowMapper());
    
    this.loginUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_LOGIN_USUARIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_password", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("usuariologinrec", new UsuarioRowMapper());
    
    this.listarUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_LISTAR_USUARIOS)
        .declareParameters(
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("listausuarios", new UsuarioRowMapper());
    
    this.insertarUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_INSERTAR_USUARIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_idusuario", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_password", Types.VARCHAR),
            new SqlParameter("pa_rol", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("usuarioinsertado", new UsuarioRowMapper());
    
    this.actualizarUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName("SP_ACTUALIZAR_USUARIO")
        .declareParameters(
            // IN
            new SqlParameter("pa_idusuario", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_password", Types.VARCHAR),
            new SqlParameter("pa_rol", Types.VARCHAR),
            new SqlParameter("pa_tenantId", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("listausuarios", new UsuarioRowMapper());
    
    this.eliminarUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ELIMINAR_USUARIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_idusuario", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
    
    this.reactivarUsuarioSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_REACTIVAR_USUARIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("usuarioreactivado", new UsuarioRowMapper());
  }
  
  // EJECUCIONES EN BD ************************************************************************************************
  
  /**
   * Consultar usuario por email y password | Login
   */
  public Map<String, Object> logInUsuarioJdbcMethod(String email, String password) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_email", email);
    params.put("pa_password", password);
    return this.loginUsuarioSimpleJdbcCall.execute(params);
  }
  
  /**
   * Consultar usuario por Id Usuario | Login
   */
  public Map<String, Object> buscarUsuarioPorIdJdbcMethod(String iDUsuario) {
    log.info("[Valor de idUsuario recibido en inicializador]: '{}'", iDUsuario);
    Map<String, Object> params = new HashMap<>();
    params.put("pa_idusuario", iDUsuario);
    return this.buscarUsuarioPorIdSimpleJdbcCall.execute(params);
  }
  
  /**
   * Listar usuarios | Login
   */
  public Map<String, Object> listarUsuariosJdbcMethod() {
    return this.listarUsuarioSimpleJdbcCall.execute();
  }
  
  /**
   * Insertar usuario por Id Usuario | Login
   */
  public Map<String, Object> insertarUsuarioPorIdJdbcMethod(UsuarioInsertEntity usuarioInsertEntity) {
    Map<String, Object> params = this.mapearObjetosUsuario.insertarUsuarioMapper(usuarioInsertEntity);
    return this.insertarUsuarioSimpleJdbcCall.execute(params);
  }
  
  /**
   * Actualizar datos usuario por Id Usuario | Login
   */
  public Map<String, Object> actualizarUsuarioPorIdJdbcMethod(UsuarioInsertEntity usuarioInsertEntity) {
    Map<String, Object> params = this.mapearObjetosUsuario.actualizarUsuarioMapper(usuarioInsertEntity);
    return this.actualizarUsuarioSimpleJdbcCall.execute(params);
  }
  
  /**
   * Eliminar usuario por Id Usuario | Login
   */
  public Map<String, Object> eliminarUsuarioJdbcMethod(String idUsuario, String email, String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_idusuario", idUsuario);
    params.put("pa_email", email);
    params.put("pa_tenantid", tenantId);
    return this.eliminarUsuarioSimpleJdbcCall.execute(params);
  }
  
  /**
   * Reactivar usuario por email y tenant Id | Login
   */
  public Map<String, Object> reactivarUsuarioJdbcMethod(String email, String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_email", email);
    params.put("pa_tenantid", tenantId);
    return this.reactivarUsuarioSimpleJdbcCall.execute(params);
  }
  
}
