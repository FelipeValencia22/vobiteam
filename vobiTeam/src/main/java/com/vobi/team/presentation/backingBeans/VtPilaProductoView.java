package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtPilaProducto;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtPilaProductoDTO;

@ManagedBean 
@ViewScoped
public class VtPilaProductoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtEmpresaView.class);

	String proyectoSeleccionado;

	private SelectOneMenu somEmpresas;
	private SelectOneMenu somActivo;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somProyectoCambio;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnLimpiar;
	private CommandButton btnFiltrar;
	private CommandButton btnGuardar;

	private Panel panelDataTableVtPilaProducto; 

	String stringActivo;

	String stringFiltrado;

	private InputText txtNombre;
	private InputTextarea txtDescripcion;
	private List<SelectItem> esActivoItems;
	private List<SelectItem> losProyectosItems;
	private List<SelectItem> lasEmpresasItems;

	private List<VtPilaProductoDTO> data;
	private List<VtPilaProductoDTO> dataFiltro;
	private VtPilaProducto entity;
	private VtPilaProductoDTO selectedVtPilaProducto;
	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputTextarea getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputTextarea txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public List<SelectItem> getEsActivoItems() {
		if (esActivoItems == null) {
			esActivoItems = new ArrayList<SelectItem>();
			esActivoItems.add(new SelectItem("Si"));
			esActivoItems.add(new SelectItem("No"));

		}
		return esActivoItems;
	}

	public void setEsActivoItems(List<SelectItem> esActivoItems) {
		this.esActivoItems = esActivoItems;
	}

	public List<SelectItem> getLosProyectosItems() {
		try {
			if (losProyectosItems == null) {
				List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
				losProyectosItems = new ArrayList<SelectItem>();

				for (VtProyecto vtProyecto : listaProyectos) {
					if(vtProyecto.getActivo().equalsIgnoreCase("S")){
						losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losProyectosItems;
	}

	public void setLosProyectosItems(List<SelectItem> losProyectosItems) {
		this.losProyectosItems = losProyectosItems;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public String getStringActivo() {
		return stringActivo;
	}

	public void setStringActivo(String stringActivo) {
		this.stringActivo = stringActivo;
	}
	
	public SelectOneMenu getSomProyectoCambio() {
		return somProyectoCambio;
	}

	public void setSomProyectoCambio(SelectOneMenu somProyectoCambio) {
		this.somProyectoCambio = somProyectoCambio;
	}

	public Panel getPanelDataTableVtPilaProducto() {
		return panelDataTableVtPilaProducto;
	}

	public void setPanelDataTableVtPilaProducto(Panel panelDataTableVtPilaProducto) {
		this.panelDataTableVtPilaProducto = panelDataTableVtPilaProducto;
	}

	public List<SelectItem> getLasEmpresasItems() {
		try {
			if(lasEmpresasItems==null){
				List<VtEmpresa> listaEmpresas=businessDelegatorView.getVtEmpresa();
				lasEmpresasItems=new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa: listaEmpresas) {
					lasEmpresasItems.add(new SelectItem(vtEmpresa.getEmprCodigo(), vtEmpresa.getNombre()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return lasEmpresasItems;
	}

	public void setLasEmpresasItems(List<SelectItem> lasEmpresasItems) {
		this.lasEmpresasItems = lasEmpresasItems;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public CommandButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(CommandButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public String getStringFiltrado() {
		return stringFiltrado;
	}

	public void setStringFiltrado(String stringFiltrado) {
		this.stringFiltrado = stringFiltrado;
	}

	public VtPilaProductoDTO getSelectedVtPilaProducto() {
		return selectedVtPilaProducto;
	}

	public void setSelectedVtPilaProducto(VtPilaProductoDTO selectedVtPilaProducto) {
		this.selectedVtPilaProducto = selectedVtPilaProducto;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public List<VtPilaProductoDTO> getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(List<VtPilaProductoDTO> dataFiltro) {
		this.dataFiltro = dataFiltro;
	}
	
	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	//TODO: Obtener DTO para el Filtro
	public List<VtPilaProductoDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtPilaProducto();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtPilaProductoDTO> vtPilaProductoDTO) {
		this.data = vtPilaProductoDTO;
	}

	//TODO: Metodos
	public String crearPilaProducto() throws Exception {
		log.info("Creando la pila de producto");

		VtPilaProducto vtPilaProducto = new VtPilaProducto();
		vtPilaProducto.setNombre(txtNombre.getValue().toString().trim());
		vtPilaProducto.setDescripcion(txtDescripcion.getValue().toString().trim());
		Date fecha = new Date();
		vtPilaProducto.setFechaCreacion(fecha);

		String activo = somActivo.getValue().toString().trim();
		if (activo.equals("Si")) {
			vtPilaProducto.setActivo("S");
		} else {
			vtPilaProducto.setActivo("N");
		}

		String proyectos = somProyectos.getValue().toString().trim();
		Long proyecto = Long.parseLong(proyectos);
		VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(proyecto);
		vtPilaProducto.setVtProyecto(vtProyecto);

		VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
		vtPilaProducto.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());

		try {
			businessDelegatorView.saveVtPilaProducto(vtPilaProducto);
			limpiar();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La pila de producto se creo con exito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String limpiar() {
		log.info("Limpiando campos de texto");
		txtNombre.resetValue();
		txtDescripcion.resetValue();
		somActivo.setValue("-1");
		somProyectos.setValue("-1");

		btnCrear.setDisabled(true);
		return "";
	}

	public String filtrarTabla(){
		return "";

	}

	public String action_edit(ActionEvent evt) {
		selectedVtPilaProducto = (VtPilaProductoDTO) (evt.getComponent()
				.getAttributes()
				.get("selectedVtPilaProducto"));

		txtDescripcion.setValue(selectedVtPilaProducto.getDescripcion());
		txtDescripcion.setDisabled(false);

		txtNombre.setValue(selectedVtPilaProducto.getNombre());
		txtNombre.setDisabled(false);

		btnGuardar.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public void localeChanged(ValueChangeEvent e){
		setProyectoSeleccionado(e.getNewValue().toString());
		try {
			dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(getProyectoSeleccionado());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String filtrar(){
		try {
			dataFiltro=businessDelegatorView.getDataVtPilaProducto();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(String proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public String action_save() {
		try {
			if ((selectedVtPilaProducto == null) && (entity == null)) {

			} else {
				action_modify();
			}

			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modify() {
		try {
			if (entity == null) {
				Long pilaCodigo = new Long(selectedVtPilaProducto.getPilaCodigo());
				entity = businessDelegatorView.getVtPilaProducto(pilaCodigo);
			} 
			
			String activo = somActivo.getValue().toString().trim();
			if (activo.equalsIgnoreCase("Si")) {
				entity.setActivo("S");
			} else {
				if(activo.equals("-1")){
					entity.setActivo(entity.getActivo());
				}
				else{
					entity.setActivo("N");
				}

			}
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);
			
			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			entity.setDescripcion(FacesUtils.checkString(txtDescripcion));
			
			entity.setNombre(FacesUtils.checkString(txtNombre));
			
			String proyectos = somProyectos.getValue().toString().trim();
			Long proyecto = Long.parseLong(proyectos);
			VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(proyecto);
			entity.setVtProyecto(vtProyecto);
			
			businessDelegatorView.updateVtPilaProducto(entity);
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	public String action_closeDialog() {
        setShowDialog(false);

        return "";
    }

}
