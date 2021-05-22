package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import db.CochePersistencia;
import db.EmpleadosPersistencia;
import model.Coche;
import model.Empleados;
import view.VCliente;
import view.PEmpleBorrar;
import view.PEmpleCons;
import view.PEmpleModi;
import view.VEmpleado;
import view.VVerificacion;
import view.VPMenu;

public class AlcoListener implements ActionListener {

	static final int INTENTOS = 3;
	private int contInt = 0;

	private VPMenu vMenu;
	private VCliente vc;
	private VVerificacion vv;
	private VEmpleado ve;
	private PEmpleBorrar peb;
	private PEmpleCons pec;
	private PEmpleModi pem;
	private EmpleadosPersistencia modeloEmple;
	private CochePersistencia modelCoche;

	public AlcoListener(VPMenu vMenu, VCliente vc, VVerificacion pv, VEmpleado ve, PEmpleBorrar peb, PEmpleCons pec,
			PEmpleModi pem) {
		this.vMenu = vMenu;
		this.vc = vc;
		this.vv = pv;
		this.ve = ve;
		this.peb = peb;
		this.pec = pec;
		this.pem = pem;
		modeloEmple = new EmpleadosPersistencia();
		modelCoche = new CochePersistencia();

	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() instanceof JMenuItem) {
			if (ev.getActionCommand().equals(ve.MNTM_VOLVER)) {			
				vMenu.hacerVisible();
				ve.dispose();
				vc.hacerInvisible();
			} else if (ev.getActionCommand().equals(ve.MNTM_BORRAR)) {
				ve.cargarPanel(peb);
			} else if (ev.getActionCommand().equals(ve.MNTM_CONSULTAR)) {
				ve.cargarPanel(pec);
			} else if (ev.getActionCommand().equals(ve.MNTM_MODIFICAR)) {
				ve.cargarPanel(pem);
			}
		} else if (ev.getSource() instanceof JButton) {
			if (ev.getActionCommand().equals(VPMenu.BTN_ACC_CLIENTE)) {
				vc.hacerVisible();
				vc.limpiartabla();
				vc.cargarCmbMarcas(modelCoche.selectDisctinctMarca());
				vc.cargarCmbModelos(modelCoche.selectDisctinctModelo());
				vMenu.hacerInvisible();
			} else if (ev.getActionCommand().equals(VCliente.BTN_VOLVER)) {				
				vMenu.hacerVisible();
				vc.hacerInvisible();
			}else if (ev.getActionCommand().equals(VPMenu.BTN_ACC_EMPLE)) {
				contInt = 0;
				vv.hacerVisible();
				vMenu.hacerInvisible();
			}else if (ev.getActionCommand().equals(VVerificacion.BTN_BYPASS)) {
				ve.hacerVisible();
				vv.dispose();
			} else if (ev.getActionCommand().equals(VVerificacion.BTN_LOGIN)) {
				Empleados empleado = vv.getDatos();

				if (empleado != null) {
					String pwdDB = modeloEmple.getPasswordUser(empleado.getUser());

					if (pwdDB == null) {
						vv.mostrarMsjError("El usuario indicado no existe");
					} else {
						if (pwdDB.equals(empleado.getPwd())) {
							// PERMITIR ACCESO
							vv.dispose();
							ve.hacerVisible();
							vv.limpiarDatos();

						} else {
							// CONTRASE�A NO COINCIDE
							contInt++;
							vv.mostrarMsjError(
									"La contrase�a no es v�lida. Te quedan " + (INTENTOS - contInt) + " intentos.");
							if (contInt == INTENTOS) {
								System.exit(0);
							}
						}
					}
				}
			} else if (ev.getActionCommand().equals(VCliente.BTN_COMPROBAR)) {
				String marcaFiltro = vc.getMarcaFiltro();
				String modeloFiltro = vc.getModeloFiltro();

				ArrayList<Coche> listaCoches = null;

				if (marcaFiltro.equals(VCliente.OPT_CUALQUIER) && modeloFiltro.equals(VCliente.OPT_CUALQUIER)) {
					listaCoches = modelCoche.selectCocheCliente();
					vc.cargarTabla(listaCoches);
					if (listaCoches.isEmpty()) {
						vc.mostrarMsjInfo("NO HAY COCHES DISPONIBLES");
					}
				} else if ((marcaFiltro.equals(VCliente.OPT_CUALQUIER) && !modeloFiltro.equals(VCliente.OPT_CUALQUIER))) {
					vc.mostrarMsjError("Debe seleccionar marca");
				} else if ((!marcaFiltro.equals(VCliente.OPT_CUALQUIER) && modeloFiltro.equals(VCliente.OPT_CUALQUIER))) {
					listaCoches = modelCoche.selectCocheMarca(marcaFiltro);
					vc.cargarTabla(listaCoches);					
				} else {
					listaCoches = modelCoche.selectCocheMarcaModelo(marcaFiltro, modeloFiltro);					
					vc.cargarTabla(listaCoches);
					if (listaCoches.isEmpty()) {
						vc.mostrarMsjInfo("NO SE ENCONTRARON RESULTADOS DE LA B�SQUEDA");
					}
				}
				
			}

		}

	}
}
