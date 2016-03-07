package com.vobi.team.presentation.backingBeans;

import com.vobi.team.exceptions.*;
import com.vobi.team.modelo.*;
import com.vobi.team.modelo.dto.VtSprintDTO;
import com.vobi.team.presentation.businessDelegate.*;
import com.vobi.team.utilities.*;

import com.vobi.team.exceptions.ZMessManager;
import com.vobi.team.modelo.VtSprint;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;


/**
 * @author Zathura Code Generator http://zathuracode.org
 * www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class VtSprintView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtSprintView.class);

	private SelectOneMenu somActivo;
	private SelectOneMenu somPilaProducto;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somPilaProductoCambio;
	private SelectOneMenu somProyectoCambio;
	private List<SelectItem> esActivoItems;
	private List<SelectItem> losProyectosItems;
	private List<SelectItem> esPilaProductoItems;
	private InputText txtNombre;
	private InputText txtObjetivo;
	private InputText txtSpriCodigo;
	private InputText txtNombrePilaProducto;
	private InputText txtCodigoPilaProducto;
	private Calendar txtFechaFin;
	private Calendar txtFechaInicio;
	private CommandButton btnCrearS;
	private CommandButton btnCrear;
	private CommandButton btnLimpiarS;
	private CommandButton btnGuardar;
	private CommandButton btnLimpiar;
	private List<VtSprintDTO> data;
	private VtSprintDTO selectedVtSprint;
	private VtSprint entity;
	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public VtSprintView() {
		super();
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public SelectOneMenu getSomPilaProducto() {
		return somPilaProducto;
	}

	public void setSomPilaProducto(SelectOneMenu somPilaProducto) {
		this.somPilaProducto = somPilaProducto;
	}

	public List<SelectItem> getEsActivoItems() {
		if(esActivoItems==null){
			esActivoItems=new ArrayList<SelectItem>();
			esActivoItems.add(new SelectItem("Si"));
			esActivoItems.add(new SelectItem("No"));
		}
		return esActivoItems;
	}

	public void setEsActivoItems(List<SelectItem> esActivoItems) {
		this.esActivoItems = esActivoItems;
	}

	public List<SelectItem> getEsPilaProductoItems() {
		try{
			if(esPilaProductoItems==null){
				List<VtPilaProducto> listaPilasProducto=businessDelegatorView.getVtPilaProducto();
				esPilaProductoItems=new ArrayList<SelectItem>();
				for (VtPilaProducto vtPilaProducto:listaPilasProducto) {
					esPilaProductoItems.add(new SelectItem(vtPilaProducto.getPilaCodigo(),vtPilaProducto.getNombre()));
				}
			}

		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return esPilaProductoItems;
	}

	public void setEsPilaProductoItems(List<SelectItem> esPilaProductoItems) {
		this.esPilaProductoItems = esPilaProductoItems;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtObjetivo() {
		return txtObjetivo;
	}

	public void setTxtObjetivo(InputText txtObjetivo) {
		this.txtObjetivo = txtObjetivo;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public Calendar getTxtFechaFin() {
		return txtFechaFin;
	}

	public void setTxtFechaFin(Calendar txtFechaFin) {
		this.txtFechaFin = txtFechaFin;
	}

	public Calendar getTxtFechaInicio() {
		return txtFechaInicio;
	}

	public void setTxtFechaInicio(Calendar txtFechaInicio) {
		this.txtFechaInicio = txtFechaInicio;
	}

	public void listener_txtFechaFin() {
		Date inputDate = (Date) txtFechaFin.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaInicio() {
		Date inputDate = (Date) txtFechaInicio.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public CommandButton getBtnCrearS() {
		return btnCrearS;
	}

	public void setBtnCrearS(CommandButton btnCrearS) {
		this.btnCrearS = btnCrearS;
	}

	public CommandButton getBtnLimpiarS() {
		return btnLimpiarS;
	}

	public void setBtnLimpiarS(CommandButton btnLimpiarS) {
		this.btnLimpiarS = btnLimpiarS;
	}

	public List<VtSprintDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtSprint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtSprintDTO> vtSprintDTO) {
		this.data = vtSprintDTO;
	}

	public VtSprintDTO getSelectedVtSprint() {
		return selectedVtSprint;
	}

	public void setSelectedVtSprint(VtSprintDTO vtSprint) {
		this.selectedVtSprint = vtSprint;
	}

	public InputText getTxtSpriCodigo() {
		return txtSpriCodigo;
	}

	public void setTxtSpriCodigo(InputText txtSpriCodigo) {
		this.txtSpriCodigo = txtSpriCodigo;
	}

	public InputText getTxtNombrePilaProducto() {
		return txtNombrePilaProducto;
	}

	public void setTxtNombrePilaProducto(InputText txtNombrePilaProducto) {
		this.txtNombrePilaProducto = txtNombrePilaProducto;
	}

	public InputText getTxtCodigoPilaProducto() {
		return txtCodigoPilaProducto;
	}

	public void setTxtCodigoPilaProducto(InputText txtCodigoPilaProducto) {
		this.txtCodigoPilaProducto = txtCodigoPilaProducto;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}
	
	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}
	
	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	public SelectOneMenu getSomPilaProductoCambio() {
		return somPilaProductoCambio;
	}

	public void setSomPilaProductoCambio(SelectOneMenu somPilaProductoCambio) {
		this.somPilaProductoCambio = somPilaProductoCambio;
	}

	public SelectOneMenu getSomProyectoCambio() {
		return somProyectoCambio;
	}

	public void setSomProyectoCambio(SelectOneMenu somProyectoCambio) {
		this.somProyectoCambio = somProyectoCambio;
	}

	public List<SelectItem> getLosProyectosItems() {
		
		try{
			if(losProyectosItems==null){
				List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
				losProyectosItems=new ArrayList<SelectItem>();
				for (VtProyecto vtProyecto:listaProyectos) {
					losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
				}
			}

		}catch(Exception e) {
			log.error(e.getMessage());
		}
		
		return losProyectosItems;
	}

	public void setLosProyectosItems(List<SelectItem> losProyectosItems) {
		this.losProyectosItems = losProyectosItems;
	}

	// TODO: Metodos
	public String crearSprint(){
		log.info("Guardando..");

		try {
			VtSprint vtSprint = new VtSprint();
			String activo = somActivo.getValue().toString().trim();
			if (activo.equalsIgnoreCase("Si")) {
				vtSprint.setActivo("S");
			} else {
				vtSprint.setActivo("N");
			}
			VtPilaProducto vtPilaProducto;
			String longPila=somPilaProducto.getValue().toString().trim();
			Long codigoPila= Long.valueOf(longPila);
			vtPilaProducto=businessDelegatorView.getVtPilaProducto(codigoPila);
			vtSprint.setVtPilaProducto(vtPilaProducto);
			//vtSprint.setVtPilaProducto(vtPilaProducto);
			Date fechaCreacion= new Date();
			vtSprint.setFechaCreacion(fechaCreacion);
			vtSprint.setFechaFin(FacesUtils.checkDate(txtFechaFin));
			vtSprint.setFechaInicio(FacesUtils.checkDate(txtFechaInicio));
			vtSprint.setNombre(txtNombre.getValue().toString().trim());
			vtSprint.setObjetivo(txtObjetivo.getValue().toString().trim());
			


			businessDelegatorView.saveVtSprint(vtSprint);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El sprint se creó con exito"));

		} catch (Exception e) {
			log.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String modificar(ActionEvent evt) {
		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes()
				.get("selectedVtSprint"));

		txtFechaInicio.setValue(selectedVtSprint.getFechaInicio());
		txtFechaInicio.setDisabled(false);
		txtFechaFin.setValue(selectedVtSprint.getFechaFin());
		txtFechaFin.setDisabled(false);

		txtNombre.setValue(selectedVtSprint.getNombre());
		txtNombre.setDisabled(false);
		txtObjetivo.setValue(selectedVtSprint.getObjetivo());
		txtObjetivo.setDisabled(false);
		somPilaProductoCambio.setValue(selectedVtSprint.getPilaCodigo_VtPilaProducto());
		somPilaProductoCambio.setDisabled(false);
		btnGuardar.setDisabled(false);
		setShowDialog(true);

		return "";
	}


	public String action_clear(){
		log.info("Limpiando pantalla..");
		somPilaProductoCambio.setValue("-1");
		somActivoCambio.setValue("-1");
		somProyectoCambio.setValue("-1");
		txtNombre.resetValue();
		txtObjetivo.resetValue();
		txtFechaFin.setValue(null);
		txtFechaInicio.setValue(null);
		log.info("Limpiado correctamente");
		return "";
	}
	
	public String action_save() {
        try {
            if ((selectedVtSprint == null) && (entity == null)) {
                
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
                Long spriCodigo = new Long(selectedVtSprint.getSpriCodigo());
                entity = businessDelegatorView.getVtSprint(spriCodigo);
            }

            String activo = somActivoCambio.getValue().toString().trim();
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
			
			String pilaproducto= somPilaProductoCambio.getValue().toString().trim();
			
			
            entity.setFechaFin(FacesUtils.checkDate(txtFechaFin));
            entity.setFechaInicio(FacesUtils.checkDate(txtFechaInicio));
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setObjetivo(FacesUtils.checkString(txtObjetivo));
            businessDelegatorView.updateVtSprint(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }


}